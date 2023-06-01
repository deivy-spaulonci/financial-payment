package com.financialpayment.api.restcontroller;

import com.financialpayment.domain.model.enuns.FormaPagamento;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/forma-pagamento")
public class FormaPagamentoRestController {
    @GetMapping()
    public List<FormaPagamento> get(){
        return Arrays.stream(FormaPagamento.values()).sorted().toList();
    }
}
