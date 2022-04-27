package com.behoh.events.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Classe responsável por mapear a tabela Usuario do banco de dados.
 */
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonIgnoreProperties({"usuariosInscritos", "usuariosParticipando"})
    private List<EventoEntity> eventosInscritos;

    // evento em que o usuário está participando
    @ManyToOne
    @JsonIgnoreProperties({"usuariosInscritos", "usuariosParticipando"})
    private EventoEntity participaEvento;

}
