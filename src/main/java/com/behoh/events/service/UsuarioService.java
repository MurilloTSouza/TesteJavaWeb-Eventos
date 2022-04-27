package com.behoh.events.service;

import com.behoh.events.model.UsuarioEntity;
import com.behoh.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Responsável por resolver lógicas de negócio de acesso
 *  ao banco de dados no que se refere a entidade Usuario.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Persiste um novo usuario.
     * @param usuario objeto contendo as inforamcoes basicas para cadastro
     * @return usuario contendo o ID gerado
     */
    public UsuarioEntity save (UsuarioEntity usuario){
        return usuarioRepository.save(usuario);
    }
}
