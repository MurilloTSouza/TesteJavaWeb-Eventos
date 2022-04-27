package com.behoh.events.controller;

import com.behoh.events.dto.UsuarioCadastroDTO;
import com.behoh.events.model.UsuarioEntity;
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

/**
 * Mapeamento de requisições REST referente a tabela Usuario.
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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

}
