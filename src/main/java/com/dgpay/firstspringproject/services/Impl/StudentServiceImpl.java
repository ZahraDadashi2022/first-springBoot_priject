package com.dgpay.firstspringproject.services.Impl;

import com.dgpay.firstspringproject.globalExceptions.NotFoundException;
import com.dgpay.firstspringproject.models.StudentDocument;
import com.dgpay.firstspringproject.models.StudentEntity;
import com.dgpay.firstspringproject.repositories.StudentJpaRepository;
import com.dgpay.firstspringproject.repositories.StudentMongoRepository;
import com.dgpay.firstspringproject.services.StudentService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentMongoRepository studentMongoRepository;
    private final StudentJpaRepository studentJpaRepository;

    @Autowired
    public StudentServiceImpl(
            StudentMongoRepository studentMongoRepository, StudentJpaRepository studentJpaRepository) {
        this.studentMongoRepository = studentMongoRepository;
        this.studentJpaRepository = studentJpaRepository;
    }

    @Override
    @Transactional
    public List<StudentEntity> getStudentsFromMysql() {

        List<StudentEntity> students = studentJpaRepository.findAll();
        if (students.isEmpty())
            throw new NotFoundException("student list is empty!");

        return students;
    }

    //using example
    @Override
    public List<StudentEntity> getStudentsByExampleFromMysql(StudentEntity studentEntity) {
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher(
                        "name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(
                        "name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return studentJpaRepository.findAll(Example.of(studentEntity, customExampleMatcher));
    }

    @Override
    @Transactional
    public void saveStudentToMysql(StudentEntity studentEntity) {
        LOGGER.info(studentEntity.toString());
        if (Strings.isEmpty(studentEntity.getFname()) || Strings.isEmpty(studentEntity.getLname())) {

            throw new NotFoundException("student fields are null. please fill them.");
        }
        studentJpaRepository.save(studentEntity);
    }

    @Override
    @Transactional
    public void deleteStudentFromMysql(Long id) {

        try {
            studentJpaRepository.deleteById(id);


        } catch (Exception e) {// not sure! double check...
            LOGGER.error("id not found");//not sure how to write !
            throw new NotFoundException("student id was not found. " + id);
        }

    }

    @Override
    @Transactional
    public Optional<StudentEntity> getStudentByIdFromMysql(Long id) {
        Optional<StudentEntity> student = studentJpaRepository.findById(id);
        if (student.isPresent())
            student.get();
        else throw new NotFoundException("course id was not found. " + id);
        return student;
    }

    @Override
    public List<StudentDocument> getStudentsFromMongo() {
        return studentMongoRepository.findAll();
    }

    @Override
    public void saveStudentToMongo(StudentDocument studentDocument) {
        if (studentDocument == null || Strings.isEmpty(studentDocument.getFname())
                || Strings.isEmpty(studentDocument.getLname())) {

            throw new NotFoundException("student fields are null. please fill them.");
        }
        studentMongoRepository.save(studentDocument);
    }

    @Override
    public void deleteStudentFromMongo(String id) {
        try {
            studentMongoRepository.deleteById(id);

        } catch (Exception e){// not sure! double check...
            LOGGER.error("id not found");  //do not know what to write exactly????
            throw new NotFoundException("student id was not found. " + id);
        }
    }

    @Override
    public Optional<StudentDocument> getStudentByIdFromMongo(String id) {
        return studentMongoRepository.findById(id);
    }
}
