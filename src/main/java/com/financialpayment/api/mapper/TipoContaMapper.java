package com.financialpayment.api.mapper;

import com.financialpayment.api.dto.TipoContaDTO;
import com.financialpayment.domain.model.TipoConta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);
    TipoConta toModel(TipoContaDTO tipoContaDTO);
    TipoContaDTO toDTO(TipoConta tipoConta);
    List<TipoContaDTO> toDtoList(List<TipoConta> tiposConta);

}
