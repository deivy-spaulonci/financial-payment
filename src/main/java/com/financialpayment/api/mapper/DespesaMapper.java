package com.financialpayment.api.mapper;

import com.financialpayment.api.dto.DespesaDTO;
import com.financialpayment.domain.model.Despesa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DespesaMapper {
    DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);

    @Mapping(source="tipoDespesa.value",target="tipoDespesa")
    Despesa toModel(DespesaDTO despesaDTO);

    DespesaDTO toDTO(Despesa despesa);
    List<DespesaDTO> toDtoList(List<Despesa> despesas);
}
