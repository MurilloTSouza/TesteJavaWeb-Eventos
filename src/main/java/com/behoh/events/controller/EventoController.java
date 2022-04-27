package com.behoh.events.controller;

import com.behoh.events.dto.EventoCadastroDTO;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.service.EventoService;
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

/**
 * Mapeamento de requisições REST referente a tabela Evento.
 */
@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

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
