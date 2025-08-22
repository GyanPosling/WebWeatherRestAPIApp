package ru.alishev.springcourse.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.models.Person;
import ru.alishev.springcourse.FirstRestApp.services.PeopleService;
import ru.alishev.springcourse.FirstRestApp.utils.PersonCreatedException;
import ru.alishev.springcourse.FirstRestApp.utils.PersonErrorResponce;
import ru.alishev.springcourse.FirstRestApp.utils.PersonNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid Person person,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorsMsg.append(fieldError.getDefaultMessage());
            }

            throw new PersonCreatedException(errorsMsg.toString());
        }

        peopleService.save(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable int id) {
        return peopleService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponce> handleException(PersonNotFoundException e) {
        PersonErrorResponce personErrorResponce = new PersonErrorResponce(
                "This person dose not exist",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(personErrorResponce, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponce> handleAnotherException(PersonCreatedException e) {
        PersonErrorResponce personErrorResponce = new PersonErrorResponce(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(personErrorResponce, HttpStatus.BAD_REQUEST);
    }
}
