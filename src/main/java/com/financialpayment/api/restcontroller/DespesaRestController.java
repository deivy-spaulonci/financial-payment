package com.financialpayment.api.restcontroller;

import com.financialpayment.api.dto.DespesaDTO;
import com.financialpayment.api.dto.DespesaImportacaoDTO;
import com.financialpayment.api.dto.DespesaPorTipoDTO;
import com.financialpayment.api.mapper.DespesaMapper;
import com.financialpayment.business.filter.DespesaFilter;
import com.financialpayment.business.service.DespesaService;
import com.financialpayment.domain.model.Despesa;
import com.financialpayment.domain.model.enuns.TipoDespesa;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/despesa")
public class DespesaRestController {

    @Autowired
    DespesaService despesaService;

    private static final DespesaMapper despesaMapper = DespesaMapper.INSTANCE;

    @GetMapping()
    public List<DespesaDTO> get(@ModelAttribute DespesaFilter despesaFilter, Sort sort){
        return despesaMapper.toDtoList(despesaService.searchByFilterOrder(despesaFilter, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> contaFindById(@PathVariable("id") Long id){
        Optional<Despesa> despesa = despesaService.findById(id);
        if(despesa.isPresent()){
            return ResponseEntity.ok(despesaMapper.toDTO(despesa.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/page")
    public Page<DespesaDTO> getPage(@ModelAttribute DespesaFilter despesaFilter, Pageable pageable){
        return despesaService.getPageByFilters(despesaFilter, pageable).map(despesaMapper::toDTO);
    }

    @GetMapping("/valorTotal")
    public BigDecimal get(@ModelAttribute DespesaFilter despesaFilter){
        return despesaService.getValorTotal(despesaFilter);
    }

    @GetMapping("/despesaPorTipo")
    public ResponseEntity<List<DespesaPorTipoDTO>> getDespesaPorTipo(){
        List<DespesaPorTipoDTO> lista = new ArrayList<>();

        List resultado = despesaService.getDespesasPorTipo();

        if(resultado.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        for(Object item : resultado){
            DespesaPorTipoDTO despesaPorTipoDTO = new DespesaPorTipoDTO();
            String[] subitem = item.toString().
                    replace("[","").
                    replace("]","").
                    split(",");

            despesaPorTipoDTO.setTipoDespesa(TipoDespesa.valueOf(subitem[0]));
            despesaPorTipoDTO.setValor(new BigDecimal(subitem[1].trim()));
            despesaPorTipoDTO.setDataInicio(LocalDate.parse(subitem[2].trim()));
            despesaPorTipoDTO.setDataFim(LocalDate.parse(subitem[3].trim()));

            lista.add(despesaPorTipoDTO);
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DespesaDTO> create(@RequestBody @Valid DespesaDTO despesaDTO,
                                           UriComponentsBuilder uriBuilder){

        Despesa despesa = despesaService.create(despesaMapper.toModel(despesaDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(despesaDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(despesaMapper.toDTO(despesa));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DespesaDTO> update(@RequestBody @Valid DespesaDTO despesaDTO){
        Optional<Despesa> despesaOptional = despesaService.findById(despesaDTO.getId());
        if(despesaOptional.isPresent()){
            Despesa despesa = despesaService.update(despesaMapper.toModel(despesaDTO));
            return ResponseEntity.ok(despesaMapper.toDTO(despesa));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Despesa> despesa = despesaService.findById(id);
        if(despesa.isPresent()){
            this.despesaService.deleteById(despesa.get().getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value ="/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @RequestMapping(value = "/importar", headers = ("content-type=multipart/*"), method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<?> importarDespesa(@RequestPart("file") MultipartFile file){

        if(file.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                // create csv bean reader
                CsvToBean<DespesaImportacaoDTO> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(DespesaImportacaoDTO.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<DespesaImportacaoDTO> despesas = csvToBean.parse();
                despesaService.importarDespesaCVS(despesas);

                return new ResponseEntity<>("result successful result",  HttpStatus.OK);

            } catch (Exception ex) {
                return new ResponseEntity<>(ex.getMessage(),  HttpStatus.BAD_REQUEST);
            }
        }
    }
}
