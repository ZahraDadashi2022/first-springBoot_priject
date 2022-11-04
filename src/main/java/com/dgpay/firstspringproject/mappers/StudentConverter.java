package com.dgpay.firstspringproject.mappers;

import com.dgpay.firstspringproject.models.StudentDocument;
import com.dgpay.firstspringproject.models.StudentDto;
import com.dgpay.firstspringproject.models.StudentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentConverter {
    StudentDto convertEntityToDto(StudentEntity studentEntity);

    StudentEntity convertDtoToEntity(StudentDto studentDto);

    StudentDto convertDocumentToDto(StudentDocument studentDocument);

    StudentDocument convertDtoToDocument(StudentDto studentDto);
}
