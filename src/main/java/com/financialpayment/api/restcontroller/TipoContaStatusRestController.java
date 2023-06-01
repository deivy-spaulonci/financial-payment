package com.financialpayment.api.restcontroller;

import com.financialpayment.domain.model.enuns.TipoContaStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-conta-status")
public class TipoContaStatusRestController {
    @GetMapping()
    public List<TipoContaStatus> get(){
        return Arrays.stream(TipoContaStatus.values()).sorted().toList();
    }
}
