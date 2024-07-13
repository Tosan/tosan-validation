package com.tosan.validation.dto;

import com.tosan.validation.constraints.Length;
import com.tosan.validation.constraints.Max;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Samadi
 */
public class Student {

    //	@com.tosan.validation.constraints.Group(pointLiterals = {10, 50, 22.2})
    private double age;

    //	@Digits(integerKey = "INT_KEY", fraction = 2)
    //	@Length(maxKey = "NAME_MAX_LEN", minKey = "NAME_MIN_LEN")
    //	@Size(innerCollectionCheck = true, max = 10, min = 1)
    private Set<List<Integer>> incomes;

    //	@Digits(integer = 5,  fraction = 2, mapValidateType = {MapValidationType.KEY, MapValidationType.VALUE})
    private Map<String, String> map;

    //	@Pattern(regexpKey = "NUMBER", regexp = "\\d+")
    private String phoneNumber;

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public Set<List<Integer>> getIncomes() {
        return incomes;
    }

    public void setIncomes(Set<List<Integer>> incomes) {
        this.incomes = incomes;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void validationTest(@Max(value = 50) int age, @Length(maxKey = "NAME_MIN_LEN") String name) {
        System.out.println("Age: " + age + "\nName: " + name);
    }
}
