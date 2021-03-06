package com.behoh.events.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Contem apenas ID do usuário e evento,
 * parametros comum entre algumas operações da API.
 */
@Data
public class UsuarioEventoIdDTO {

    @ApiModelProperty("Número do ID do usuário.")
    @NotNull(message = "'usuario_id' é obrigatório.")
    private Integer usuario_id;

    @ApiModelProperty("Número do ID do evento.")
    @NotNull(message = "'evento_id' é obrigatório.")
    private Integer evento_id;
}
