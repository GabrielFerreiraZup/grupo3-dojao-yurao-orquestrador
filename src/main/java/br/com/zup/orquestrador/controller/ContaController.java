package br.com.zup.orquestrador.controller;

import br.com.zup.orquestrador.controller.dto.ContaDigitalResponse;
import br.com.zup.orquestrador.controller.dto.TransacaoDto;
import br.com.zup.orquestrador.service.client.TransacaoClient;
import br.com.zup.orquestrador.service.kafka.ContaMensagem;
import br.com.zup.orquestrador.service.kafka.ContaProducer;
import br.com.zup.orquestrador.service.kafka.EnumOperacao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("contas/{numeroConta}")
public class ContaController {

    @Autowired
    private TransacaoClient client;

    @Autowired
    private ContaProducer producer;

    @Value("${topico.transacao}")
    private String topico;

    @PutMapping("credita")
    public ResponseEntity<?> credita(
            @Valid @RequestBody TransacaoDto request,
            @PathVariable String numeroConta
    ) {
        ContaDigitalResponse credita = null;
        try {
            credita = client.credita(request, numeroConta);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.notFound().build();
        } catch (FeignException.UnprocessableEntity e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (FeignException.BadRequest e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        ContaMensagem mensagem = credita.paraMensagem(EnumOperacao.DEPOSITO, request.getValor());
        producer.send(mensagem);

        return ResponseEntity.ok(credita);
    }
}
