package com.behoh.events.dto;

import com.behoh.events.model.EventoEntity;
import com.behoh.events.validation.iniciofim.ValidarInicioFim;
import com.behoh.events.validation.iniciofim.InicioFim;
import com.behoh.events.validation.parsabledate.ParsableDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por definir e validar parametros de cadastro
 * que deverão estar presente no Body da Requisicao.
 * (Data Transfer Object)
 */
@Data
@ValidarInicioFim( message = "'inicio' deve anteceder 'fim'.")
public class EventoCadastroDTO implements InicioFim {

    @ApiModelProperty(value = "Nome do evento.", example = "Evento de Natal")
    @NotBlank(message = "'nome' e obrigatorio.")
    private String nome;

    @ApiModelProperty(value = "Númerde vagas. (min 1).", example = "3")
    @Min(value = 1, message = "'vagas' deve ser maior que zero.")
    private Integer vagas;

    @ApiModelProperty(value = "Data de inicio. (data futura)", example = "2022-12-24T18:30")
    @NotBlank(message = "'inicio' e obrigatorio.")
    @ParsableDate(message =
            "'inicio' deve conter uma data válida. Ex.: 2022-12-24T18:30")
    private String inicio;

    @ApiModelProperty(value = "Data de termino. (posterior a inicio)", example = "2022-12-25T03:30")
    @NotBlank(message = "'fim' e obrigatorio.")
    @ParsableDate(message =
            "'fim' deve conter uma data válida. Ex.: 2022-12-25T03:30")
    private String fim;

    /**
     * Retorna o texto da data inicio convertida em LocalDateTime.
     * @return Data convertida, ou NULL caso haja Exception.
     */
    @ApiModelProperty(hidden = true)
    @Future(message = "'inicio' deve conter uma data futura.")
    public LocalDateTime getParsedInicio(){
        try {
            return LocalDateTime.parse(
                    inicio,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            // ignorando até o momento, qualquer exceção
            // deverá ser evitada por @ParsableDate
            return null;
        }
    }

    /**
     * Retorna o texto da data fim convertida em LocalDateTime.
     * @return Data convertida, ou NULL caso haja Exception.
     */
    @ApiModelProperty(hidden = true)
    @Future(message = "'fim' deve conter uma data futura.")
    public LocalDateTime getParsedFim(){
        try {
            return LocalDateTime.parse(
                    fim,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            // ignorando até o momento, qualquer exceção
            // deverá ser evitada por @ParsableDate
            return null;
        }
    }

    public EventoEntity toEntity() {
        return EventoEntity.builder()
                .nome(getNome()).vagas(getVagas())
                .inicio(getParsedInicio()).fim(getParsedFim())
                .build();
    }
}
