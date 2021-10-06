package br.com.zup.orquestrador.controller;

import br.com.zup.orquestrador.controller.dto.TransacaoDto;
import br.com.zup.orquestrador.service.client.TransacaoClient;
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

    private KafkaTemplate<String, TransacaoDto> template;

    @Value("${topico.transacao}")
    private String topico;

    @PutMapping("credita")
    public ResponseEntity<?> credita(
            @Valid @RequestBody TransacaoDto request,
            @PathVariable String numeroConta
    ) {
        Map<String, Object> credita = new HashMap<>();
        try {
            credita = client.credita(request, numeroConta);
        } catch (FeignException.NotFound e) {

        }
//        template.send(topico, request);

        return ResponseEntity.ok(credita);
    }
}
