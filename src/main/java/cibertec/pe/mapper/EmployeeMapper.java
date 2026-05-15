package cibertec.pe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cibertec.pe.dto.EmployeeRequest;
import cibertec.pe.dto.EmployeeResponse;
import cibertec.pe.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(EmployeeRequest request, @MappingTarget Employee employee);
}
