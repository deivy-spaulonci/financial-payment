package com.financialpayment.api.restcontroller;

import com.financialpayment.api.dto.ContaDTO;
import com.financialpayment.api.dto.ContaPorTipoDTO;
import com.financialpayment.api.mapper.ContaMapper;
import com.financialpayment.business.filter.ContaFilter;
import com.financialpayment.business.service.ContaService;
import com.financialpayment.domain.model.Conta;
import com.financialpayment.domain.model.TipoConta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/conta")
public class ContaRestController {

    @Autowired
    ContaService contaService;

    private static final ContaMapper contaMapper = ContaMapper.INSTANCE;

    @GetMapping()
    public List<ContaDTO> get(@ModelAttribute ContaFilter contaFilter, Sort sort){
        return contaMapper.toDtoList(contaService.searchByFilterOrder(contaFilter, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> contaFindById(@PathVariable("id") Long id){
        Optional<Conta> conta = contaService.findById(id);
        if(conta.isPresent()){
            return ResponseEntity.ok(contaMapper.toDTO(conta.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/page")
    public Page<ContaDTO> getPage(@ModelAttribute ContaFilter contaFilter, Pageable pageable){
        return contaService.getPageByFilters(contaFilter, pageable).map(contaMapper::toDTO);
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute ContaFilter contaFilter){
        return contaService.getValorTotal(contaFilter);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContaDTO> create(@RequestBody @Valid ContaDTO contaDTO,
                                           UriComponentsBuilder uriBuilder){
        Conta conta = contaService.create(contaMapper.toModel(contaDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(contaDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(contaMapper.toDTO(conta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ContaDTO> update(@RequestBody @Valid ContaDTO contaDTO){
        Optional<Conta> contaOptional = contaService.findById(contaDTO.getId());
        if(contaOptional.isPresent()){
            Conta conta = contaService.update(contaMapper.toModel(contaDTO));
            return ResponseEntity.ok(contaMapper.toDTO(conta));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Conta> conta = contaService.findById(id);
        if(conta.isPresent()){
            this.contaService.deleteById(conta.get().getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/contaPorTipo")
    public ResponseEntity<List<ContaPorTipoDTO>> getContaPorTipo(){
        List<ContaPorTipoDTO> lista = new ArrayList<>();

        List resultado = contaService.getContasPorTipo();

        if(resultado.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for(Object item : resultado){
            ContaPorTipoDTO contaPorTipoDTO = new ContaPorTipoDTO();
            String[] subitem = item.toString().
                    replace("[","").
                    replace("]","").
                    split(",");

            TipoConta tipoConta = new TipoConta();
            tipoConta.setNome(subitem[0]);
            contaPorTipoDTO.setTipoConta(tipoConta);
            contaPorTipoDTO.setValor(new BigDecimal(subitem[1].trim()));

            lista.add(contaPorTipoDTO);
        }

        return ResponseEntity.ok(lista);
    }
}
