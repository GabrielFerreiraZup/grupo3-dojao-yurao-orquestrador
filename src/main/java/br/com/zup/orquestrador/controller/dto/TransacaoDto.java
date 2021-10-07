package br.com.zup.orquestrador.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TransacaoDto {
    private BigDecimal valor;
    private Long idCliente;

    public TransacaoDto(BigDecimal valor, Long idCliente) {
        this.valor = valor;
        this.idCliente = idCliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
