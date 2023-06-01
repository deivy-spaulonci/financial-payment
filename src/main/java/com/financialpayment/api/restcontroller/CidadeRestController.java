package com.financialpayment.api.restcontroller;

import com.financialpayment.api.dto.CidadeDTO;
import com.financialpayment.api.mapper.CidadeMapper;
import com.financialpayment.business.filter.CidadeFilter;
import com.financialpayment.business.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cidade")
public class CidadeRestController {

    @Autowired
    CidadeService cidadeService;
    private static final CidadeMapper cidadeMapper = CidadeMapper.INSTANCE;

    @GetMapping()
    public List<CidadeDTO> get(@ModelAttribute CidadeFilter cidadeFilter, Sort sort){
        return cidadeMapper.toDtoList(cidadeService.list(cidadeFilter, sort));
    }

    @GetMapping("/{nome}")
    public List<CidadeDTO> listByNome(@PathVariable("nome") String nome){
        return cidadeMapper.toDtoList(cidadeService.listByNome(nome));
    }
}
