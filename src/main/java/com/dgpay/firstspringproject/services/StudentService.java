package com.dgpay.firstspringproject.services;

import com.dgpay.firstspringproject.models.CourseEntity;
import com.dgpay.firstspringproject.models.StudentDocument;
import com.dgpay.firstspringproject.models.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentEntity> getStudentsFromMysql();
    List<StudentEntity> getStudentsByExampleFromMysql(StudentEntity studentEntity);

    void saveStudentToMysql(StudentEntity studentEntity);

    void deleteStudentFromMysql(Long id);

    Optional<StudentEntity> getStudentByIdFromMysql(Long id);

    List<StudentDocument> getStudentsFromMongo();

    void saveStudentToMongo(StudentDocument studentDocument);

    void deleteStudentFromMongo(String id);

    Optional<StudentDocument> getStudentByIdFromMongo(String id);
}
