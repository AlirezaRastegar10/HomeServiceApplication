package com.example.mappers;


import com.example.dto.service.GetServicesDto;
import com.example.dto.service.ServicesDto;
import com.example.entity.Services;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicesMapper {

    Services dtoToServices(ServicesDto servicesDto);
    GetServicesDto servicesToDto(Services services);

    List<GetServicesDto> servicesListToDtoList(List<Services> services);
}
