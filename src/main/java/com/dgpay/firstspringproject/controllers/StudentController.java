package com.dgpay.firstspringproject.controllers;

import com.dgpay.firstspringproject.globalExceptions.NotFoundException;
import com.dgpay.firstspringproject.mappers.StudentConverter;
import com.dgpay.firstspringproject.models.StudentDto;
import com.dgpay.firstspringproject.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private final StudentConverter studentConverter;
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentConverter studentConverter, StudentService studentService) {
        this.studentConverter = studentConverter;
        this.studentService = studentService;
    }

    // considering empty fields as null(trim) not working accurately! why???
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
    public List<StudentDto> getStudentsFromMysql() {
        LOGGER.info("fetching data"); // unsure!

        return studentService.getStudentsFromMysql()
                .stream()
                .map(studentConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    //get students by example  (incomplete) inaccurate response !
    @RequestMapping(value = "/findByExample", method = RequestMethod.GET)
    public List<StudentDto> getStudentsByExample(StudentDto studentDto) {

        return studentService.getStudentsByExampleFromMysql(studentConverter.convertDtoToEntity(studentDto))
                .stream()
                .map(studentConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/getStudentsMongo", method = RequestMethod.GET)
    public List<StudentDto> getStudentsFromMongo() {
        return studentService.getStudentsFromMongo()
                .stream()
                .map(studentConverter::convertDocumentToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public void saveStudent(@RequestBody StudentDto studentDto) {
        LOGGER.info(studentDto.toString());
        studentService.saveStudentToMysql(studentConverter.convertDtoToEntity(studentDto));
    }

    @RequestMapping(value = "/saveStudentMongo", method = RequestMethod.POST)
    public void saveStudentToMongo(@RequestBody StudentDto studentDto) {
        LOGGER.info(studentDto.toString());
        studentService.saveStudentToMongo(studentConverter.convertDtoToDocument(studentDto));
    }

    @RequestMapping(value = "/deleteStudent/{id}", method = RequestMethod.DELETE)
    public String deleteStudentFromMysql(@PathVariable Long id) {

        StudentDto studentDto = studentConverter.convertEntityToDto(
                studentService.getStudentByIdFromMysql(id).get());

        if (studentDto == null) {
            throw new NotFoundException("student id is not found : " + id);
        }
        studentService.deleteStudentFromMysql(id);
        return "selected student is deleted: " + id;
    }

    @RequestMapping(value = "/deleteStudentMongo/{id}", method = RequestMethod.DELETE)
    public void deleteStudentFromMongo(@PathVariable String id) {
        studentService.deleteStudentFromMongo(id);
    }

    @RequestMapping(value = "/findStudentById/{id}", method = RequestMethod.GET)
    public StudentDto findStudentById(@PathVariable Long id) {
        StudentDto studentDto = studentConverter.convertEntityToDto(
                studentService.getStudentByIdFromMysql(id).get());

        if (studentDto == null) {
            throw new NotFoundException("student id is not found : " + id);
        }
        return studentDto;
    }

    @RequestMapping(value = "/findStudentByIdMongo/{id}", method = RequestMethod.GET)
    public StudentDto findStudentByIdFromMongo(@PathVariable String id) {
        return studentConverter.convertDocumentToDto(
                studentService.getStudentByIdFromMongo(id).get());
    }
}
