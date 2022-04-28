package com.behoh.events.controller;

import com.behoh.events.dto.EventoCadastroDTO;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.service.EventoService;
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
            @Valid @RequestBody EventoCadastroDTO eventoCadastro,
            BindingResult bindingResult) {

        // se tiver algum erro de validação
        // retorna BAD_REQUEST
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    BindingResultUtil.listErrors(bindingResult),
                    HttpStatus.BAD_REQUEST
            );
        }

        // se nao houver erros, persiste evento no banco
        EventoEntity eventoCriado =
                eventoService.save(eventoCadastro.toEntity());

        return new ResponseEntity<>(
                eventoCriado,
                HttpStatus.CREATED);
    }

}
