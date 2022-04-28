package com.behoh.events.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Classe responsável por definir e validar parametros
 * de inscricao de um usuário em um evento.
 */
@Data
public class InscricaoDTO {

    @ApiModelProperty("Número do ID do usuário.")
    @NotNull(message = "'usuario_id' é obrigatório.")
    private Integer usuario_id;

    @ApiModelProperty("Número do ID do evento.")
    @NotNull(message = "'evento_id' é obrigatório.")
    private Integer evento_id;
}
