package com.jason.application.web.firstcrawler.rest;


import com.jason.application.web.firstcrawler.EarthTreksWebDriver.ETPageOne;
import com.jason.application.web.firstcrawler.EarthTreksWebDriver.ETPageTwo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RESTServer  {


    @GetMapping("/et1/{hour}/{morningOrAfternoon}/{flagHeadless}")
    public String checkVRBForUpdates(@PathVariable boolean flagHeadless, @PathVariable int hour, @PathVariable String morningOrAfternoon) throws InterruptedException, IOException {
        ETPageOne earthTreksWebDriver = new ETPageOne();
        return earthTreksWebDriver.processPageOne(flagHeadless, hour, morningOrAfternoon);
    }

    @GetMapping("/et2/{headless}")
    public void processPageTwo(@PathVariable boolean headless){
        ETPageTwo etPageTwo = new ETPageTwo();
        etPageTwo.processPageTwo(headless);
    }
}

