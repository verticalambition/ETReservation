package com.jason.application.web.firstcrawler.rest;


import com.jason.application.web.firstcrawler.EarthTreksWebDriver.ETReservationDriver;
import com.jason.application.web.firstcrawler.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RESTServer  {

    @Autowired
    ETReservationDriver earthTreksWebDriver;

    @PostMapping("/attemptreservation")
    public String bookReservation(@RequestBody Reservation newReservation){
        System.out.println("Received request to attempt reservation using a POST");
        System.out.println("Hour is " + newReservation.getTime() + " morningOfAfternoon is " + newReservation.getAmpm() + " week is " + newReservation.getWeek() + " day is " + newReservation.getDay()
        );
        return earthTreksWebDriver.processPageOne(newReservation);
    }

    @GetMapping("/et2")
    public void processPageTwo(){
        earthTreksWebDriver.processPageTwo();
    }

    @GetMapping("/et3")
    public void processPageThree(){

        earthTreksWebDriver.processPageThree();
    }
}

