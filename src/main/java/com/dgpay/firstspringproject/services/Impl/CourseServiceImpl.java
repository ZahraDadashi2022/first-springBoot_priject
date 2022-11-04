package com.dgpay.firstspringproject.services.Impl;

import com.dgpay.firstspringproject.globalExceptions.NotFoundException;
import com.dgpay.firstspringproject.models.CourseDocument;
import com.dgpay.firstspringproject.models.CourseEntity;
import com.dgpay.firstspringproject.repositories.CourseJpaRepository;
import com.dgpay.firstspringproject.repositories.CourseMongoRepository;
import com.dgpay.firstspringproject.services.CourseService;
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
public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final CourseJpaRepository courseJpaRepository;
    private final CourseMongoRepository courseMongoRepository;

    @Autowired
    public CourseServiceImpl(
            CourseJpaRepository courseJpaRepository, CourseMongoRepository courseMongoRepository) {
        this.courseJpaRepository = courseJpaRepository;
        this.courseMongoRepository = courseMongoRepository;
    }

    @Override
    @Transactional
    public List<CourseEntity> getCoursesFromMysql() {
        List<CourseEntity> courses = courseJpaRepository.findAll();
        if (courses.isEmpty())
            throw new NotFoundException("courses list is empty!");

        return courses;
    }

    @Override
    @Transactional
    public void saveCourseToMysql(CourseEntity courseEntity) {
        LOGGER.info(courseEntity.toString());
        if (Strings.isEmpty(courseEntity.getName())) {
            throw new NotFoundException("course name is null. please fill it.");
        }
        courseJpaRepository.save(courseEntity);
    }

    @Override
    @Transactional
    public void deleteCourseFromMysql(Long id) {

        try {
            courseJpaRepository.deleteById(id);

        } catch (Exception e) {// not sure! double check...
            throw new NotFoundException("student id was not found. " + id);
        }
    }

    @Override
    @Transactional
    public Optional<CourseEntity> getCourseByIdFromMysql(Long id) {
        Optional<CourseEntity> course = courseJpaRepository.findById(id);

        if (course.isPresent())
            course.get();
        else throw new NotFoundException("course id was not found. " + id);
        return course;
    }

    //using example.of()
    @Override
    public List<CourseEntity> getCoursesByExampleFromMysql(CourseEntity courseEntity) {

        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher(
                        "name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return courseJpaRepository.findAll(Example.of(courseEntity, customExampleMatcher));
    }

    @Override
    public List<CourseDocument> getCoursesFromMongo() {
        return courseMongoRepository.findAll();
    }

    @Override
    public void saveCourseToMongo(CourseDocument courseDocument) {
        if (courseDocument == null || Strings.isEmpty(courseDocument.getName())) {
            throw new NotFoundException("course fields are null. please fill them.");
        }
        courseMongoRepository.save(courseDocument);
    }

    @Override
    public void deleteCourseFromMongo(String id) {
        courseMongoRepository.deleteById(id);
    }

    @Override
    public Optional<CourseDocument> getCourseByIdFromMongo(String id) {
        return courseMongoRepository.findById(id);
    }
}
