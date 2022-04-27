package com.behoh.events.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Classe responsável por definir e validar parametros
 * de inscricao de um usuário em um evento.
 */
@Data
public class InscricaoDTO {
    @NotNull(message = "'usuario_id' é obrigatório.")
    private Integer usuario_id;

    @NotNull(message = "'evento_id' é obrigatório.")
    private Integer evento_id;
}
