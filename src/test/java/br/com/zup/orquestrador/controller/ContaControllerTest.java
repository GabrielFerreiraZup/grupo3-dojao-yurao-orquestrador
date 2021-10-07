package br.com.zup.orquestrador.controller;

import br.com.zup.orquestrador.controller.dto.ContaDigitalResponse;
import br.com.zup.orquestrador.controller.dto.TransacaoDto;
import br.com.zup.orquestrador.service.client.TransacaoClient;
import br.com.zup.orquestrador.service.kafka.ContaMensagem;
import br.com.zup.orquestrador.service.kafka.ContaProducer;
import br.com.zup.orquestrador.service.kafka.EnumOperacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ContaControllerTest {

    /*
    1-) Deve creditar
    2-) Deve debitar
    3-) Não deve debitar caso não exista conta
    4-) Não deve debitar caso valor > saldo
    5-) Não deve debitar quando algo está fora do formato esperado
    6-) Não deve creditar caso não exista conta
    7-) Não deve creditar caso valor > saldo
    8-) Não deve creditar quando algo está fora do formato esperado
     */

    @Autowired
    private MockMvc mvc;

    @Mock
    private TransacaoClient client;

    @Mock
    private ContaProducer producer;




    @Test
    @DisplayName("Deve debitar")
    void testeOkDebito() throws Exception{

        TransacaoDto transacao = new TransacaoDto(BigDecimal.ONE,1L);
        ContaDigitalResponse response = new ContaDigitalResponse("1",1L,BigDecimal.ZERO);
        ContaMensagem mensagem = new ContaMensagem(EnumOperacao.DEBITO,BigDecimal.ONE,1L,"1");
        doNothing().when(producer).send(mensagem);
        when(client.debita(transacao,"1")).thenReturn(response);
        MockHttpServletRequestBuilder request = putRequest("/contas/1/debita",transacao);
        mvc.perform(request).andExpect(status().isOk());

    }

    public static MockHttpServletRequestBuilder putRequest(
            String url,
            TransacaoDto body
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body));

    }
}