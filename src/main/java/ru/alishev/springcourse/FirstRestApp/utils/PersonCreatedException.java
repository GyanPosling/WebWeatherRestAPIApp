package ru.alishev.springcourse.FirstRestApp.utils;

public class PersonCreatedException extends RuntimeException{
    public PersonCreatedException(String msg){
        super(msg);
    }
}
