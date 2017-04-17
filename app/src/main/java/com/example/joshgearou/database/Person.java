package com.example.joshgearou.database;

/**
 * Created by Josh Gearou on 4/16/2017.
 */

public class Person {

    String id;
    String name;
    String profession;
    String age;

    public Person() {

    }

    public Person(String id, String name, String profession, String age) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.profession = profession;
    }

   public String getAge() {
       return age;
    }

    public String getProfession() {
        return profession;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
