package com.behoh.events.dto;

import com.behoh.events.model.UsuarioEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Classe responsável por definir e validar parametros de cadastro
 * que deverão estar presente no Body da Requisicao.
 * (Data Transfer Object)
 */
@Data
public class UsuarioCadastroDTO {

    @ApiModelProperty(value = "Nome do usuário.", example = "John Doe")
    @NotBlank(message = "'nome' e obrigatorio.")
    private String nome;

    public UsuarioEntity toEntity(){
        return UsuarioEntity.builder()
                .nome(getNome())
                .build();
    }
}
