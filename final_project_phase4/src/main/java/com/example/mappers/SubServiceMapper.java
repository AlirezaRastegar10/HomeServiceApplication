package com.example.mappers;


import com.example.dto.subservice.GetSubServiceDto;
import com.example.dto.subservice.SubServiceDto;
import com.example.dto.subservice.UpdateSubServiceDto;
import com.example.entity.SubService;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubServiceMapper {

    GetSubServiceDto subServiceToDto(SubService subService);

    SubService dtoToSubService(SubServiceDto subServiceDto);

    List<GetSubServiceDto> subServiceListToDtoList(List<SubService> subServices);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "expertSet", ignore = true)
    @Mapping(target = "services", ignore = true)
    SubService updateSubServiceDtoToDto(UpdateSubServiceDto updateSubServiceDto, @MappingTarget SubService subService);
}
