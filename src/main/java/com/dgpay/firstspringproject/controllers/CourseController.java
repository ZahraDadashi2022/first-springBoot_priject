package com.dgpay.firstspringproject.controllers;

import com.dgpay.firstspringproject.globalExceptions.NotFoundException;
import com.dgpay.firstspringproject.mappers.CourseConverter;
import com.dgpay.firstspringproject.models.CourseDto;
import com.dgpay.firstspringproject.services.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:Zahra Dadashi
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;
    private final CourseConverter courseConverter;

    @Autowired
    public CourseController(CourseService courseService, CourseConverter courseConverter) {
        this.courseService = courseService;
        this.courseConverter = courseConverter;
    }
    // considering empty fields as null(trim) not working accurately! why???
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }
    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public List<CourseDto> getCoursesFromMysql() {
        LOGGER.info("fetching data is done successfully."); // unsure!

        return courseService.getCoursesFromMysql()
                .stream()
                .map(courseConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/findByExample",method = RequestMethod.GET)
    public List<CourseDto> getCoursesByExample(@RequestBody CourseDto courseDto) {

       return courseService.getCoursesByExampleFromMysql(courseConverter.convertDtoToEntity(courseDto))
                .stream()
                .map(courseConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/getCoursesMongo", method = RequestMethod.GET)
    public List<CourseDto> getCoursesFromMongo() {
        return courseService.getCoursesFromMongo()
                .stream()
                .map(courseConverter::convertDocumentToDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/saveCourse", method = RequestMethod.POST)
    public void saveCourse(@RequestBody CourseDto courseDto) {
        LOGGER.info(courseDto.toString());
        courseService.saveCourseToMysql(courseConverter.convertDtoToEntity(courseDto));
    }

    @RequestMapping(value = "/saveCourseMongo", method = RequestMethod.POST)
    public void saveCourseToMongo(@RequestBody CourseDto courseDto) {
        LOGGER.info(courseDto.toString());
        courseService.saveCourseToMongo(courseConverter.convertDtoToDocument(courseDto));
    }

    @RequestMapping(value = "/deleteCourse/{id}", method = RequestMethod.DELETE)
    public String deleteCourseFromMysql(@PathVariable Long id) {
        LOGGER.info(CourseDto.class.toString());  //true??

        CourseDto courseDto = courseConverter.convertEntityToDto(
                courseService.getCourseByIdFromMysql(id).get());

        if (courseDto == null) {
            throw new NotFoundException("course id is not found : " + id);
        }
        courseService.deleteCourseFromMysql(id);
        return "selected course is deleted: " + id;
    }

    @RequestMapping(value = "/deleteCourseMongo/{id}", method = RequestMethod.DELETE)
    public void deleteCourseFromMongo(@PathVariable String id) {
        courseService.deleteCourseFromMongo(id);
    }

    @RequestMapping(value = "/findCourseById/{id}",method = RequestMethod.GET)
    public CourseDto findCourseById(@PathVariable Long id) {
        CourseDto courseDto = courseConverter.convertEntityToDto(
                courseService.getCourseByIdFromMysql(id).get());

        if (courseDto == null) {
            throw new NotFoundException("course id is not found : " + id);
        }
        return courseDto;
    }

    @RequestMapping(value = "/findCourseByIdMongo/{id}",method = RequestMethod.GET)
    public CourseDto findCourseByIdFromMongo(@PathVariable String id) {
        return courseConverter.convertDocumentToDto(
                courseService.getCourseByIdFromMongo(id).get());
    }
}
