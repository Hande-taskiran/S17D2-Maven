package com.workintech.s17d2.model;

import org.springframework.stereotype.Component;

@Component
public class SeniorDeveloper extends Developer{
    public SeniorDeveloper(Integer id, String name, Double salary, Experience experience) {
        super(id, name, salary, experience);
    }
    public SeniorDeveloper(Integer id, String name, Double salary) {
        super(id, name, salary, Experience.SENIOR);
    }
}
