package com.behoh.events.controller;

import com.behoh.events.dto.InscricaoDTO;
import com.behoh.events.dto.UsuarioCadastroDTO;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.model.UsuarioEntity;
import com.behoh.events.service.EventoService;
import com.behoh.events.service.UsuarioService;
import com.behoh.events.utils.BindingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mapeamento de requisições REST referente a tabela Usuario.
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

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