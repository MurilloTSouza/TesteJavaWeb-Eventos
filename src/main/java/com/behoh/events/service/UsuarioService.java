package com.behoh.events.service;

import com.behoh.events.model.UsuarioEntity;
import com.behoh.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * Busca um usuario pelo ID
     * @param id id do usuario a ser buscado
     * @return objeto Optional contendo Usuario encontrado ou podendo estar vazio caso contrário.
     */
    public Optional<UsuarioEntity> find(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Confere se usuario está inscrito em evento com ID especificado.
     * @param eventoID ID do evento a ser conferido.
     */
    //TODO: mudar lógica para utilizar query de UsuarioRepository
    public boolean estaInscritoEm(UsuarioEntity usuario, Integer eventoID){
        // filtrando lista por ID e conferindo se exite resultado
        return usuario.getEventosInscritos()
                .stream().filter(e -> e.getId().equals(eventoID))
                .findFirst().isPresent();
    }

}
