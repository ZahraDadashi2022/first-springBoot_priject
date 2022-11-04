package com.dgpay.firstspringproject.mappers;

import com.dgpay.firstspringproject.models.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseConverter {
    CourseDto convertEntityToDto(CourseEntity courseEntity);

    CourseEntity convertDtoToEntity(CourseDto courseDto);

    CourseDocument convertDtoToDocument(CourseDto courseDto);

    CourseDto convertDocumentToDto(CourseDocument courseDocument);
}
