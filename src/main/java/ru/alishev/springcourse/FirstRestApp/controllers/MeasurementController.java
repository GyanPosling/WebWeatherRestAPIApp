package ru.alishev.springcourse.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementsResponse;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.services.MeasurementsService;
import ru.alishev.springcourse.FirstRestApp.utils.MeasurementErrorResponse;
import ru.alishev.springcourse.FirstRestApp.utils.MeasurementException;
import ru.alishev.springcourse.FirstRestApp.utils.MeasurementValidator;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final ModelMapper modelMapper;
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(ModelMapper modelMapper,
                                 MeasurementsService measurementsService,
                                 MeasurementValidator measurementValidator) {
        this.modelMapper = modelMapper;
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add (@RequestBody @Valid MeasurementDTO measurementDTO,
                                           BindingResult bindingResult) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurementValidator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.CREATED);

    }

    @GetMapping()

    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(
                measurementsService.findAll()
                        .stream().map(this::convertToMeasurementDTO)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
