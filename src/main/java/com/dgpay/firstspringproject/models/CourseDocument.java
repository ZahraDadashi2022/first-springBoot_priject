package com.dgpay.firstspringproject.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CourseDocument {
    @Id
    private String id;
    private String name;

    public CourseDocument(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CourseDocument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
