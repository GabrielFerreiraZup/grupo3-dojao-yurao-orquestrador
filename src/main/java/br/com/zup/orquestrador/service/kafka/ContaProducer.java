package br.com.zup.orquestrador.service.kafka;

import br.com.zup.orquestrador.controller.dto.ContaDigitalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ContaProducer {

    private static final Logger logger = LoggerFactory.getLogger(ContaProducer.class);
    private final String topic;
    private final KafkaTemplate<String, ContaMensagem> kafkaTemplate;

    public ContaProducer(
        @Value("${topico.transacao}")
        String topic,
        KafkaTemplate<String, ContaMensagem> kafkaTemplate
    ) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ContaMensagem mensagem) {

        kafkaTemplate.send(topic,mensagem);
    }
}
