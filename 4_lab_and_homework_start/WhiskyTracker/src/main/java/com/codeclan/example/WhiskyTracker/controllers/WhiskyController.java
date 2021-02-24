package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

//    @GetMapping(value = "/whiskies")
//    public ResponseEntity<List<Whisky>> getAllWhiskies() {
//        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
//    }

    @GetMapping(value = "whiskies/{id}")
    public Optional<Whisky> getWhisky(@PathVariable Long id) {
        return whiskyRepository.findById(id);
    }

    @PostMapping(value = "/whiskies")
    public ResponseEntity<Whisky> postWhisky(@RequestBody Whisky whisky) {
        whiskyRepository.save(whisky);
        return new ResponseEntity(whisky, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/whiskies/{id}")
    public ResponseEntity deleteWhisky(@PathVariable Long id) {
        Optional<Whisky> whiskyToDelete = whiskyRepository.findById(id);
        if (!whiskyToDelete.isPresent()) {
            return new ResponseEntity(id, HttpStatus.NOT_FOUND);
        } else {
            whiskyRepository.delete(whiskyToDelete.get());
            return new ResponseEntity(id, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/whiskies")
    public ResponseEntity<List<Whisky>> findWhiskysFilteredByYear(
            @RequestParam(name = "year", required = false) Integer year) {
       if (year != null) {
           return new ResponseEntity<>(whiskyRepository.findByYear(year), HttpStatus.OK);
       }
       return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/whiskies/distilleries")
    public ResponseEntity<List<Whisky>> findWhiskiesThatHaveDistilleriesWithAgeInt(
            @RequestParam(name = "age") Integer age) {
        return new ResponseEntity<>(whiskyRepository.findByAge(age), HttpStatus.OK);
    }


}
