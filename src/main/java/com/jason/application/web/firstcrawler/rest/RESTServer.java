package com.jason.application.web.firstcrawler.rest;


import com.jason.application.web.firstcrawler.EarthTreksWebDriver.ETReservationDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RESTServer  {


    @GetMapping("/et1/{hour}/{morningOrAfternoon}")
    public String checkVRBForUpdates(@PathVariable int hour, @PathVariable String morningOrAfternoon) throws InterruptedException, IOException {
        ETReservationDriver earthTreksWebDriver = new ETReservationDriver();
        return earthTreksWebDriver.processPageOne(hour, morningOrAfternoon);
    }

    @GetMapping("/et2")
    public void processPageTwo(){
        ETReservationDriver etPageTwo = new ETReservationDriver();
        etPageTwo.processPageTwo();
    }

    @GetMapping("/et3")
    public void processPageThree(){
        ETReservationDriver etPageThree = new ETReservationDriver();
        etPageThree.processPageThree();
    }
}

