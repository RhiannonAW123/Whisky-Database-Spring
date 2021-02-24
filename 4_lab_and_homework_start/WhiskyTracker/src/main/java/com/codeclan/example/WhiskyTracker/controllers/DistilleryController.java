package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DistilleryController {

    @Autowired
    DistilleryRepository distilleryRepository;

//    @GetMapping(value = "/distilleries")
//    public ResponseEntity <List<Distillery>> getAllDistilleries(){
//        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
//    }

    @GetMapping(value = "/distilleries/{id}")
    public Optional<Distillery> getDistillery(@PathVariable Long id) {
        return distilleryRepository.findById(id);
    }

    @PostMapping(value = "/distilleries")
    public ResponseEntity<Distillery> postDistillery(@RequestBody Distillery distillery){
        distilleryRepository.save(distillery);
        return new ResponseEntity(distillery, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/distilleries/{id}")
    public ResponseEntity deleteWhisky(@PathVariable Long id) {
        Optional<Distillery> distilleryToDelete = distilleryRepository.findById(id);
        if (!distilleryToDelete.isPresent()) {
            return new ResponseEntity(id, HttpStatus.NOT_FOUND);
        } else {
            distilleryRepository.delete(distilleryToDelete.get());
            return new ResponseEntity(id, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/distilleries")
    public ResponseEntity<List<Distillery>> findDistilleriesFilteredByRegion(
            @RequestParam(name = "region", required = false) String region) {
        if (region != null) {
            return new ResponseEntity<>(distilleryRepository.findByRegion(region), HttpStatus.OK);
        }
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

}
