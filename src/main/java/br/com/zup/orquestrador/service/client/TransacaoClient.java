package br.com.zup.orquestrador.service.client;

import br.com.zup.orquestrador.controller.dto.ContaDigitalResponse;
import br.com.zup.orquestrador.controller.dto.TransacaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;

@Component
@FeignClient(name = "contaDigital", url = "${contadigital.url}")
public interface TransacaoClient {

    @PutMapping("{numeroConta}/credita")
    public ContaDigitalResponse credita(
            @Valid @RequestBody TransacaoDto request,
            @PathVariable String numeroConta
    );

    @PutMapping("{numeroConta}/debita")
    public ContaDigitalResponse debita(
            @Valid @RequestBody TransacaoDto request,
            @PathVariable String numeroConta
    );
}
