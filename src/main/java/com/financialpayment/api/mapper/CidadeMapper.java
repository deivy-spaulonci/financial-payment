package com.financialpayment.api.mapper;

import com.financialpayment.api.dto.CidadeDTO;
import com.financialpayment.domain.model.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CidadeMapper {
    CidadeMapper INSTANCE = Mappers.getMapper(CidadeMapper.class);
    Cidade toModel(CidadeDTO cidadeDTO);
    CidadeDTO toDTO(Cidade cidade);
    List<CidadeDTO> toDtoList(List<Cidade> cidades);
}

