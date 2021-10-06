package br.com.zup.orquestrador.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TransacaoDto {
    private BigDecimal valor;
    private Long idCliente;
}
