package com.jason.application.web.firstcrawler.EarthTreksWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Component
public class ETReservationDriver {


    private static WebDriver driver;
    private static boolean headless = false;

    public ETReservationDriver() {
        System.setProperty("webdriver.gecko.driver", "/home/jason/development/tools/web/geckodriver/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("/usr/bin/firefox");
        if(headless) {
            options.addArguments("-headless");
        }
        driver = new FirefoxDriver(options);
        new WebDriverWait(driver, 25);
    }

    public String processPageOne(int hour, String morningOrAfternoon) throws InterruptedException, IOException {
        //Get contents of page
        //driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.com.html");
        driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.commainwhenslotavail.html");
        //driver.get("https://app.rockgympro.com/b/widget/?a=offering&offering_guid=0077362cf5a04cfc9150386cfc7e6c03&random=5fbae5621d2f8&iframeid=&mode=p");
        Thread.sleep(1000);

        //This looks at particualr day requested for the month. Currently will have to be determined by user by row/column of calendar
        driver.findElement(By.xpath("/html/body/div[1]/div/form/div[6]/div/fieldset/table/tbody/tr[1]/td[1]/a[2]")).click();
        WebElement day = driver.findElement(By.xpath("/html/body/div[1]/div/form/div[6]/fieldset/div/div/table/tbody/tr[5]/td[3]"));
        //See if it says unavailable or sold out. Either is bad for us. Doesn't really matter which it is
        String dayStatus = day.getAttribute("class");
        if (dayStatus.contains("unavailable") || dayStatus.contains("soldout")) {
            System.out.println("Day is currently unselectable due to being unavailable or soldout");
            return "Day Sold Out";
        } else {
            //If we have the availability to still find something in this day
            System.out.println("This day has some openings");
            day.click();
            Thread.sleep(1000);
            WebElement timeSlotContainer = driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset/div[1]/table/tbody"));
            List<WebElement> possibleTimeSlots = timeSlotContainer.findElements(By.tagName("tr"));
            for (WebElement slot : possibleTimeSlots) {
                //System.out.println("Time slot is " + slot.findElement(By.className("offering-page-schedule-list-time-column")).getText());
                if(slot.getText().contains(" " + hour + " " + morningOrAfternoon + " to ") && slot.getText().contains("Select")) {
                    System.out.println(slot.getText());
                    //slot.findElement(By.tagName("a")).click();
                    processPageTwo();
                }
                //Determine if its available to book

            }
            return "Processed Request";
        }
    }

    public void processPageTwo() {
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
        //driver.findElement(By.xpath("/html/body/div[1]/div/form/a[2]")).click();
        //processPageThree();

    }

    public void processPageThree() {
        driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.comconfirmation.html");
        //Enter Email address
        driver.findElement(By.xpath("//*[@id=\"customer-email\"]")).sendKeys("jasonelish@gmail.com");
        //Enter Phone Number
        driver.findElement(By.xpath("//*[@id=\"customer-phone\"]")).sendKeys("5204197786");
        //Check box related to cancellation fee
        driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset[5]/div/strong[8]/span/input")).click();
        //Complete the Booking
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //driver.findElement(By.xpath("//*[@id=\"confirm_booking_button\"]")).click();
    }
}

