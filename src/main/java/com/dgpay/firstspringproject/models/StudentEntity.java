package com.dgpay.firstspringproject.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRST_NAME", nullable = false)
    private String fname;
    @Column(name = "LAST_NAME", nullable = false)
    private String lname;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "STUDENT_COURSE",
            joinColumns = {@JoinColumn(name = "STUDENT_ID", referencedColumnName = "id",
                    nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "COURSE_ID", referencedColumnName = "id",
                    nullable = false)})
    private Set<CourseEntity> courses = new HashSet<>();

    public StudentEntity() {
    }

    public StudentEntity(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
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

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", courses=" + courses +
                '}';
    }
}
