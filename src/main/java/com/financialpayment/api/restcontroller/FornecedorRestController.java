package com.financialpayment.api.restcontroller;

import com.financialpayment.api.dto.FornecedorDTO;
import com.financialpayment.api.mapper.FornecedorMapper;
import com.financialpayment.business.filter.FornecedorFilter;
import com.financialpayment.business.service.FornecedorService;
import com.financialpayment.domain.model.Fornecedor;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fornecedor")
public class FornecedorRestController {

    @Autowired
    FornecedorService fornecedorService;

    private static final FornecedorMapper fornecedorMapper = FornecedorMapper.INSTANCE;

    @GetMapping()
    public List<FornecedorDTO> get(@ModelAttribute FornecedorFilter fornecedorFilter, Sort sort){
        return fornecedorMapper.toDtoList(fornecedorService.searchByFilterOrder(fornecedorFilter, sort));
    }

    @GetMapping("/search/{s}")
    public List<FornecedorDTO> getSearch(@PathVariable("s") String s){
        return fornecedorMapper.toDtoList(fornecedorService.searchStringFornecedor(s));
    }


    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> fornecedorFindById(@PathVariable("id") Long id){
        Optional<Fornecedor> fornecedor = fornecedorService.findById(id);
        if(fornecedor.isPresent()){
            return ResponseEntity.ok(fornecedorMapper.toDTO(fornecedor.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/page")
    public Page<FornecedorDTO> getPage(@ModelAttribute FornecedorFilter fornecedorFilter, Pageable pageable){
        return fornecedorService.getPageByFilters(fornecedorFilter, pageable).map(fornecedorMapper::toDTO);
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorDTO> create(@RequestBody @Valid FornecedorDTO fornecedorDTO,
                                           UriComponentsBuilder uriBuilder){
        Fornecedor fornecedor = fornecedorService.create(fornecedorMapper.toModel(fornecedorDTO));
        URI uri = uriBuilder.path("/{id}").buildAndExpand(fornecedorDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(fornecedorMapper.toDTO(fornecedor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<FornecedorDTO> update(@RequestBody @Valid FornecedorDTO fornecedorDTO){
        Optional<Fornecedor> fornecedorOptional = fornecedorService.findById(fornecedorDTO.getId());
        if(fornecedorOptional.isPresent()){
            Fornecedor fornecedor = fornecedorService.update(fornecedorMapper.toModel(fornecedorDTO));
            return ResponseEntity.ok(fornecedorMapper.toDTO(fornecedor));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Fornecedor> fornecedor = fornecedorService.findById(id);
        if(fornecedor.isPresent()){
            this.fornecedorService.deleteById(fornecedor.get().getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/consultacnpj", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String consultaCNPJ(@RequestParam(name = "cnpj") String cnpj){
        String URL_API = "https://www.receitaws.com.br/v1/cnpj/"+cnpj;
        HttpURLConnection con = null;

        try {
            URL url = null;
            url = new URL(URL_API);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return getJson(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(URL url) {
        if (url == null)
            throw new RuntimeException("URL é null");

        String html = null;
        StringBuilder sB = new StringBuilder();
        try (BufferedReader bR = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((html = bR.readLine()) != null)
                sB.append(html);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sB.toString();
    }

}
