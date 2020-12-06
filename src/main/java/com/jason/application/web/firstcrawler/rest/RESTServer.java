package com.jason.application.web.firstcrawler.rest;


import com.jason.application.web.firstcrawler.EarthTreksWebDriver.ETReservationDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RESTServer  {

    @Autowired
    ETReservationDriver earthTreksWebDriver;

    @GetMapping("/et1/{hour}/{morningOrAfternoon}/{week}/{day}")
    public String checkVRBForUpdates(@PathVariable int hour, @PathVariable String morningOrAfternoon, @PathVariable String week, @PathVariable String day)  {
        System.out.println("Received request to attempt reservation");
        System.out.println("Hour is " + hour + " morningOfAfternoon is " + morningOrAfternoon + " week is " + week + " day is " + day);
        return earthTreksWebDriver.processPageOne(hour, morningOrAfternoon, day, week);
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

