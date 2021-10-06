package br.com.zup.orquestrador.controller.dto;

import br.com.zup.orquestrador.service.kafka.ContaMensagem;
import br.com.zup.orquestrador.service.kafka.EnumOperacao;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ContaDigitalResponse {
    private String numeroConta;
    private Long idCliente;
    private BigDecimal saldo;

    public ContaMensagem paraMensagem(EnumOperacao operacao) {
//        return new ContaMensagem(operacao, )
        return null;
    }
}
