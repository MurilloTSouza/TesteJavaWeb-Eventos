package com.behoh.events.controller;

import com.behoh.events.dto.EventoCadastroDTO;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.service.EventoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Mapeamento de requisições REST referente a tabela Evento.
 */

@Api("Cadastro e informaçoes sobre eventos.")
@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @ApiOperation("Cadastro de um novo evento.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Retorna evento cadastrado com ID gerado."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    @PostMapping("/cadastro")
    public ResponseEntity cadastro(
            @Valid @RequestBody EventoCadastroDTO eventoCadastro) {

        EventoEntity evento = eventoCadastro.toEntity();

        return new ResponseEntity<>(
                eventoService.save(evento),
                HttpStatus.CREATED);
    }

    @ApiOperation("Lista todos os eventos.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de eventos.")
    })
    @GetMapping
    public ResponseEntity listarTodos() {
        return ResponseEntity.ok(
                eventoService.findAll()
        );
    }

    @ApiOperation("Busca evento por ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Evento encontrado."),
            @ApiResponse(code = 404, message = "Evento não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable Integer id){

        Optional<EventoEntity> evento = eventoService.find(id);

        if(evento.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(evento.get());
    }

}
