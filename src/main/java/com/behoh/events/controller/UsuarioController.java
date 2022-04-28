package com.behoh.events.controller;

import com.behoh.events.dto.InscricaoDTO;
import com.behoh.events.dto.UsuarioCadastroDTO;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.model.UsuarioEntity;
import com.behoh.events.service.EventoService;
import com.behoh.events.service.UsuarioService;
import com.behoh.events.utils.BindingResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mapeamento de requisições REST referente a tabela Usuario.
 */
@Api("Informações e operações de usuário, como cadastro, inscrição em eventos, etc.")
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    @ApiOperation("Cadastro de um novo usuário.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Retorna o usuário cadastro com ID gerado."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    @PostMapping("/cadastro")
    public ResponseEntity cadastro(
            @Valid @RequestBody UsuarioCadastroDTO usuarioCadastro,
            BindingResult bindingResult){

        // se tiver algum erro de validação
        // retorna BAD_REQUEST
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    BindingResultUtil.listErrors(bindingResult),
                    HttpStatus.BAD_REQUEST
            );
        }

        // se nao houver erros, persiste usuario no banco
        UsuarioEntity usuarioCriado =
                usuarioService.save(usuarioCadastro.toEntity());

        return new ResponseEntity<>(
                usuarioCriado,
                HttpStatus.CREATED);
    }

    @ApiOperation("Busca usuário por ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário encontrado."),
            @ApiResponse(code = 404, message = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Integer id){

        Optional<UsuarioEntity> usuario = usuarioService.find(id);

        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(usuario.get());
    }

    @ApiOperation("Inscreve usuário em um evento.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Retorna o usuário com a lista de eventos inscritos."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    //TODO: Separar responsábilidade de validações
    @PostMapping("/inscricao")
    public ResponseEntity inscricao(
            @Valid @RequestBody InscricaoDTO inscricao,
            BindingResult bindingResult){

        // ===========================
        //     Erros de validação
        // ===========================
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    BindingResultUtil.listErrors(bindingResult),
                    HttpStatus.BAD_REQUEST
            );
        }

        List<String> erros = new ArrayList<>();

        // ============================
        // Usuario ou Evento nao existe
        // ============================
        Optional<UsuarioEntity> usuario =
                usuarioService.find(inscricao.getUsuario_id());
        Optional<EventoEntity> evento =
                eventoService.find(inscricao.getEvento_id());
        if(usuario.isEmpty()){
            erros.add("usuário com id fornecido não encontrado.");
        }
        if(evento.isEmpty()){
            erros.add("evento com id fornecido não encontrado.");
        }

        // --- conferindo erros ---
        // se usuario ou evento nao existir, retorna BAD_REQUEST
        if(!erros.isEmpty()){
            return new ResponseEntity(
                    erros, HttpStatus.BAD_REQUEST);
        }

        // ... obtendo entidades
        UsuarioEntity usuarioEntity = usuario.get();
        EventoEntity eventoEntity = evento.get();

        //============================
        //        Não há vagas
        //============================
        Integer vagasOcupadas = eventoEntity.getUsuariosInscritos().size();
        Integer vagasDisponiveis = eventoEntity.getVagas() - vagasOcupadas;
        if(vagasDisponiveis < 1) {
            erros.add("O evento nao possui mais vagas disponiveis.");
        }

        //============================
        //    Evento já inscrito
        //============================

        // conferindo se usuario ja nao
        // esta inscrito no evento
        boolean estaInscrito =
                usuarioService.estaInscritoEm(
                        usuarioEntity, eventoEntity.getId());
        if(estaInscrito){
            erros.add("Usuário já está inscrito no evento.");
        }

        //=============================
        //      Evento já iniciado
        //=============================
        // conferindo se evento nao foi iniciado
        LocalDateTime agora = LocalDateTime.now();
        if(eventoEntity.getInicio().isBefore(agora)){
            erros.add("Evento já iniciado.");
        }

        // --- conferindo erros ---
        // se usuario ou evento nao existir, retorna BAD_REQUEST
        if(!erros.isEmpty()){
            return new ResponseEntity(
                    erros, HttpStatus.BAD_REQUEST);
        }

        // ... prosseguindo com persistencia
        usuarioEntity.getEventosInscritos().add(eventoEntity);
        UsuarioEntity usuarioInscrito =
                usuarioService.save(usuarioEntity);

        return new ResponseEntity(
                usuarioInscrito,
                HttpStatus.CREATED
        );
    }

}
