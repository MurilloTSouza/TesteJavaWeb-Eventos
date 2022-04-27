package com.behoh.events.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe responsável por mapear a tabela Evento do banco de dados.
 */
@Data @Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;
    private Integer vagas;

    //TODO: definir conversor para salvar 'inicio' e 'fim'
    //      como DATE ou TIMESTAMP no banco de dados.
    private LocalDateTime inicio;
    private LocalDateTime fim;

    @ManyToMany(mappedBy = "eventosInscritos")
    private List<UsuarioEntity> usuariosInscritos;

    @OneToMany(mappedBy = "participaEvento")
    private List<UsuarioEntity> usuariosParticipando;
}
