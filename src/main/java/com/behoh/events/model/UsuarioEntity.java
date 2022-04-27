package com.behoh.events.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Classe responsável por mapear a tabela Usuario do banco de dados.
 */
@Data
@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    // lista de eventos em que o usuario está inscrito
    @ManyToMany
    @JoinTable(
            name = "inscricoes_eventos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<EventoEntity> eventosInscritos;

    // evento em que o usuário está participando
    @ManyToOne
    private EventoEntity participaEvento;
}
