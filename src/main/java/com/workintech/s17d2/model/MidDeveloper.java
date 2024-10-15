package com.workintech.s17d2.model;

import org.springframework.stereotype.Component;

@Component
public class MidDeveloper extends Developer{
    public MidDeveloper(Integer id, String name, Double salary, Experience experience) {
        super(id, name, salary, experience);
    }
    public MidDeveloper(Integer id, String name, Double salary) {
        super(id, name, salary, Experience.MID);
    }
}
