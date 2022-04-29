package com.behoh.events.controller;

import com.behoh.events.dto.UsuarioEventoIdDTO;
import com.behoh.events.dto.UsuarioCadastroDTO;
import com.behoh.events.exception.EntidadeNaoEncontradaException;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.model.UsuarioEntity;
import com.behoh.events.service.EventoService;
import com.behoh.events.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            @Valid @RequestBody UsuarioCadastroDTO usuarioCadastro){

        UsuarioEntity usuario = usuarioCadastro.toEntity();

        return new ResponseEntity<>(
                usuarioService.save(usuario),
                HttpStatus.CREATED);
    }

    @ApiOperation("Busca usuário por ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário encontrado."),
            @ApiResponse(code = 404, message = "Usuário não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Integer id)
            throws EntidadeNaoEncontradaException {

        return ResponseEntity.ok( usuarioService.find(id) );
    }

    @ApiOperation("Inscreve usuário em um evento.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Retorna o usuário com a lista de eventos inscritos."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    @PostMapping("/inscricao")
    public ResponseEntity inscricao(
            @Valid @RequestBody UsuarioEventoIdDTO inscricao)
            throws Exception{

        Integer usuario_id = inscricao.getUsuario_id();
        Integer evento_id = inscricao.getEvento_id();

        UsuarioEntity usuario = usuarioService.find(usuario_id);
        EventoEntity evento = eventoService.find(evento_id);

        UsuarioEntity usuarioInscrito =
                usuarioService.inscreverEm(usuario, evento);

        return new ResponseEntity(
                usuarioInscrito,
                HttpStatus.CREATED
        );
    }

    @ApiOperation("Cancela inscrição de usuário em um evento.")
    @DeleteMapping("/inscricao")
    public ResponseEntity cancelarInscricao(
            @Valid @RequestBody UsuarioEventoIdDTO inscricao)
            throws Exception{

        Integer usuario_id = inscricao.getUsuario_id();
        Integer evento_id = inscricao.getEvento_id();

        UsuarioEntity usuario = usuarioService.find(usuario_id);
        EventoEntity evento = eventoService.find(evento_id);

        UsuarioEntity usuarioAtualizado =
                usuarioService.cancelarInscricao(usuario, evento);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @ApiOperation("Entrada de usuário em evento")
    @PostMapping("/entrar")
    public ResponseEntity entrar(
            @Valid @RequestBody UsuarioEventoIdDTO inscricao)
            throws Exception{

        Integer usuario_id = inscricao.getUsuario_id();
        Integer evento_id = inscricao.getEvento_id();

        UsuarioEntity usuario = usuarioService.find(usuario_id);
        EventoEntity evento = eventoService.find(evento_id);

        UsuarioEntity usuarioAtualizado =
                usuarioService.entrarEmEvento(usuario, evento);

        return ResponseEntity.ok(usuarioAtualizado);
    }

}
