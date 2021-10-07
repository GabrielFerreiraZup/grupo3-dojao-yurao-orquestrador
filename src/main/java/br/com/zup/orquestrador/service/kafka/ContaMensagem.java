package br.com.zup.orquestrador.service.kafka;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ContaMensagem {
    private EnumOperacao operacao;
    private BigDecimal valor;
    private LocalDate dataOperacao;
    private Long clienteId;
    private String contaParticipante;

    public ContaMensagem(
            EnumOperacao operacao,
            BigDecimal valor,
            Long clienteId,
            String contaParticipante
    ) {
        this.operacao = operacao;
        this.valor = valor;
        this.dataOperacao = LocalDate.now();
        this.clienteId = clienteId;
        this.contaParticipante = contaParticipante;
    }

}
