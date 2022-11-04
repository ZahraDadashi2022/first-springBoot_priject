package com.dgpay.firstspringproject.services;

import com.dgpay.firstspringproject.models.CourseDocument;
import com.dgpay.firstspringproject.models.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<CourseEntity> getCoursesFromMysql();

    void saveCourseToMysql(CourseEntity courseEntity);

    void deleteCourseFromMysql(Long id);

    Optional<CourseEntity> getCourseByIdFromMysql(Long id);

    List<CourseEntity> getCoursesByExampleFromMysql(CourseEntity courseEntity);

    List<CourseDocument> getCoursesFromMongo();

    void saveCourseToMongo(CourseDocument courseDocument);

    void deleteCourseFromMongo(String id);

    Optional<CourseDocument> getCourseByIdFromMongo(String id);
}
