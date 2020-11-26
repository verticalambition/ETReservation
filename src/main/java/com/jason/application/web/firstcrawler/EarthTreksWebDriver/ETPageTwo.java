package com.jason.application.web.firstcrawler.EarthTreksWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ETPageTwo {

    private static WebDriver driver;

    public void processPageTwo(boolean headless) {
            System.setProperty("webdriver.gecko.driver", "/home/jason/development/tools/web/geckodriver/geckodriver");
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("/usr/bin/firefox");
            if(headless) {
                options.addArguments("-headless");
            }

            driver = new FirefoxDriver(options);
            new WebDriverWait(driver, 25);
            driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.com.userdetails.html");
            //First name
            driver.findElement(By.xpath("//*[@id=\"pfirstname-pindex-1-1\"]")).sendKeys("Jason");
            //Last  name
            driver.findElement(By.xpath("//*[@id=\"plastname-pindex-1-1\"]")).sendKeys("Elish");
            //Middle Init
            driver.findElement(By.xpath("//*[@id=\"pmiddle-pindex-1-1\"]")).sendKeys("P");

            //Month Birth Day
            WebElement element = driver.findElement(By.xpath("//*[@id=\"participant-birth-pindex-1month\"]"));
            Select monthSelection = new Select(element);
            monthSelection.selectByValue("10");

            //Day Birth Day
            element = driver.findElement(By.xpath("//*[@id=\"participant-birth-pindex-1day\"]"));
            Select daySelection = new Select(element);
            daySelection.selectByValue("22");

            //Year Birth Day
            element = driver.findElement(By.xpath("//*[@id=\"participant-birth-pindex-1year\"]"));
            Select yearSelection = new Select(element);
            yearSelection.selectByValue("1981");

            //Check Intro to Roped Climbing Button
            driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset[2]/div[2]/span/input")).click();

            //Check Cancellation Policy
            driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset[3]/div[2]/span/input")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Move on to next page
            driver.findElement(By.xpath("/html/body/div[1]/div/form/a[2]")).click();
        }
   }

