package com.tosan.validation;

import com.tosan.validation.core.ValidatorBuilder;
import com.tosan.validation.dto.Student;
import com.tosan.validation.dto.University;
import jakarta.validation.ConstraintViolation;

import java.util.*;

public class Test {

    public static void main(String[] args) throws Exception {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setAge(30);
        Set<List<Integer>> incomes = new HashSet<>();
        incomes.add(List.of(10, 20, 30));
        student1.setIncomes(incomes);
        student1.setPhoneNumber("2225252");
        students.add(student1);

        Student student2 = new Student();
        student2.setAge(30);
        student2.setIncomes(incomes);
        student2.setPhoneNumber("2225252");
        students.add(student2);

        Student student3 = new Student();
        student3.setAge(22.2);
        student3.setPhoneNumber("091225021");
        Map<String, String> map = new HashMap<>();
        map.put("12345", "1");
        map.put("223", "2");
        map.put("12341", "23241");
        student3.setMap(map);
        students.add(student3);

        University university = new University();
        university.setStudents(students);

        ValidatorBuilder validator = new ValidatorBuilder();
        jakarta.validation.Validator val = validator.getValidator();
        Set<ConstraintViolation<University>> constraintViolationSet = val.validate(university);
        StringBuilder message = new StringBuilder();

        int i = 1;
        for (ConstraintViolation<University> invalidValue : constraintViolationSet) {
            message.append("\t").append(invalidValue.getPropertyPath().toString()).append(" : ")
                    .append(invalidValue.getMessage());
            if (i < constraintViolationSet.size()) {
                message.append(", ");
            }
            i++;
        }
        if (!message.isEmpty()) {// has error
            throw new Exception(message.toString());
        }
    }
}
