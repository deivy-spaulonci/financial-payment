package com.financialpayment.api.restcontroller;

import com.financialpayment.api.dto.TipoContaDTO;
import com.financialpayment.api.mapper.TipoContaMapper;
import com.financialpayment.business.filter.TipoContaFilter;
import com.financialpayment.business.service.TipoContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipo-conta")
public class TipoContaRestController {

    @Autowired
    TipoContaService tipoContaService;

    public static final TipoContaMapper tipoContaMapper = TipoContaMapper.INSTANCE;

    @GetMapping()
    public List<TipoContaDTO> get(@ModelAttribute TipoContaFilter tipoContaFilter, Sort sort){
        return tipoContaMapper.toDtoList(tipoContaService.list(tipoContaFilter, sort));
    }
}
