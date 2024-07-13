package com.tosan.validation.dto;

import jakarta.validation.Valid;

import java.util.List;

public class University {

    @Valid
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
