package com.behoh.events.service;

import com.behoh.events.exception.EntidadeNaoEncontradaException;
import com.behoh.events.model.EventoEntity;
import com.behoh.events.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Responsável por resolver lógicas de negócio de acesso
 *  ao banco de dados no que se refere a entidade Evento.
 */
@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Persiste um novo evento
     * @param evento objeto contendo as inforamcoes basicas para cadastro
     * @return evento contendo o ID gerado
     */
    public EventoEntity save(EventoEntity evento) {
        return eventoRepository.save(evento);
    }

    /**
     * Busca um evento pelo ID
     * @param id id do evento a ser buscado
     * @return objeto Optional contendo Evento encontrado ou podendo estar vazio caso contrário.
     */
    public EventoEntity find(Integer id) throws EntidadeNaoEncontradaException {
        Optional<EventoEntity> evento = eventoRepository.findById(id);

        if(evento.isEmpty()){
            throw new EntidadeNaoEncontradaException("Evento não encontrado.");
        }

        return evento.get();
    }

    /**
     * Lista todos os eventos.
     * @return Lista de eventos.
     */
    public List<EventoEntity> findAll(){
        return eventoRepository.findAll();
    }

}
