package com.dgpay.firstspringproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDto {

    private Long id;
    @JsonProperty("firstName")
    private String fname;
    @JsonProperty("lastName")
    private String lname;

    @JsonProperty("courses")
    private List<CourseDto> courses;

    public StudentDto() {
    }

    public StudentDto(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    public StudentDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", courses=" + courses +
                '}';
    }
}
