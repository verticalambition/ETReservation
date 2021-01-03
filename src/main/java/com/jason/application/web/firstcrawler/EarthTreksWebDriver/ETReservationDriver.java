package com.jason.application.web.firstcrawler.EarthTreksWebDriver;

import com.jason.application.web.firstcrawler.Reservation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ETReservationDriver {


    private static WebDriver driver;
    private static boolean headless = true;

    private Reservation myReservation;

    public ETReservationDriver(@Value("${firefoxLocation}") final String firefoxLocation, @Value("${geckodriverLocation}") final String geckodriverLocation) {
        System.out.println("\n" + firefoxLocation +"\n" + geckodriverLocation);
        System.setProperty("webdriver.gecko.driver", geckodriverLocation);
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxLocation);
        if(headless) {
            options.addArguments("-headless");
        }
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public String processPageOne(Reservation newReservation) {
        myReservation = newReservation;
        new WebDriverWait(driver, 25).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        //Get contents of page
        //driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.com.html");
        //driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.commainwhenslotavail.html");
        driver.get("https://app.rockgympro.com/b/widget/?a=offering&offering_guid=0077362cf5a04cfc9150386cfc7e6c03&random=5fbae5621d2f8&iframeid=&mode=p");
        waitForNextAction(1000, 1500);

        //Select One Member
        driver.findElement(By.xpath("/html/body/div[1]/div/form/div[6]/div/fieldset/table/tbody/tr[1]/td[1]/a[2]")).click();
        //This looks at particular day requested for the month. Currently will have to be determined by user by row/column of calendar
        WebElement day = driver.findElement(By.xpath("/html/body/div[1]/div/form/div[6]/fieldset/div/div/table/tbody/tr[" + myReservation.getWeek() + "]/td[" + myReservation.getDay() + "]"));
        //See if it says unavailable or sold out. Either is bad for us. Doesn't really matter which it is
        String dayStatus = day.getAttribute("class");
        System.out.println("Day Status is " + dayStatus + " and date is " + day.getText());
        if (dayStatus.contains("unavailable") || dayStatus.contains("soldout")) {
            System.out.println("Day is currently unselectable due to being unavailable or soldout");
            return "Day Sold Out";
        } else {
            //If we have the availability to still find something in this day
            System.out.println("This day has some openings");
            day.click();
            waitForNextAction(500, 750);
            //Get the table that contains all the time slots
            WebElement timeSlotContainer = driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset/div[1]/table/tbody"));
            //*[@id="offering-page-select-events-table"]/tbody
            //Get a List of individual time slot html tags
            List<WebElement> possibleTimeSlots = timeSlotContainer.findElements(By.tagName("tr"));
            //Look through slots to find time slot we want and then check if its bookable
            for (WebElement slot : possibleTimeSlots) {
                if(slot.getText().contains(" " + myReservation.getTime() + " " + myReservation.getAmpm() + " to ") && slot.getText().contains("Select")) {
                    System.out.println(slot.getText());
                    slot.findElement(By.tagName("a")).click();
                    waitForNextAction(1800, 2300);
                    System.out.println("Finished Processing Page One");
                    return processPageTwo();
                }
            }
            return ("The slot attempting to reserve " + myReservation.getTime() + " " + myReservation.getAmpm() + " was unavailable to book");
        }
    }

    public String processPageTwo() {
        //driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.com.userdetails.html");

        //First name
        driver.findElement(By.xpath("//*[@id=\"pfirstname-pindex-1-1\"]")).sendKeys(myReservation.getUserDetails().getFirstName());
        waitForNextAction(50, 100);

        //Last  name
        driver.findElement(By.xpath("//*[@id=\"plastname-pindex-1-1\"]")).sendKeys(myReservation.getUserDetails().getLastName());
        waitForNextAction(50, 100);

        //Middle Init
        driver.findElement(By.xpath("//*[@id=\"pmiddle-pindex-1-1\"]")).sendKeys(myReservation.getUserDetails().getMiddleName());
        waitForNextAction(50, 100);

        //Month Birth Day
        WebElement element = driver.findElement(By.xpath("//*[@id=\"participant-birth-pindex-1month\"]"));
        Select monthSelection = new Select(element);
        monthSelection.selectByValue(myReservation.getUserDetails().getBirthMonth());
        waitForNextAction(50, 100);

        //Day Birth Day
        element = driver.findElement(By.xpath("//*[@id=\"participant-birth-pindex-1day\"]"));
        Select daySelection = new Select(element);
        daySelection.selectByValue(myReservation.getUserDetails().getBirthDay());
        waitForNextAction(50, 100);

        //Year Birth Day
        element = driver.findElement(By.xpath("//*[@id=\"participant-birth-pindex-1year\"]"));
        Select yearSelection = new Select(element);
        yearSelection.selectByValue(myReservation.getUserDetails().getBirthYear());
        waitForNextAction(50, 100);

        //Check Intro to Roped Climbing Button
        driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset[2]/div[2]/span/input")).click();
        waitForNextAction(50, 100);

        //Check Cancellation Policy
        driver.findElement(By.xpath("/html/body/div[1]/div/form/fieldset[3]/div[2]/span/input")).click();
        waitForNextAction(50, 100);
        //Move on to next page
        driver.findElement(By.xpath("/html/body/div[1]/div/form/a[2]")).click();
        waitForNextAction(1800, 2000);
        System.out.println("Finished Processing Page Two");
        return processPageThree();
    }

    public String processPageThree() {
        //driver.get("file:///home/jason/IdeaProjects/applications/web/offlineHTML/app.rockgympro.comconfirmation.html");

        //Enter Email address
        driver.findElement(By.xpath("//*[@id=\"customer-email\"]")).sendKeys(myReservation.getUserDetails().getEmail());
       // waitForNextAction(50, 100);

        //Enter Phone Number
        driver.findElement(By.xpath("//*[@id=\"customer-phone\"]")).sendKeys(myReservation.getUserDetails().getPhoneNumber());
        waitForNextAction(50, 100);

        //Check box related to cancellation fee
        driver.findElement(By.xpath("//*[@id=\"theform\"]/fieldset[5]/div/strong[9]/span/input")).click();
        waitForNextAction(50, 100);

        //Complete the Booking
        driver.findElement(By.xpath("//*[@id=\"confirm_booking_button\"]")).click();
        waitForNextAction(3000, 5000);
        String pageSourceResponse = driver.getPageSource();
      //  driver.close();
        System.out.println("Finished Processing Page 3");
        return "Reservation Process Finished. Source returned from website was \n" + pageSourceResponse;
    }

    public void waitForNextAction(int min, int max){
        int range = (max - min) + 1;
        try {
            Thread.sleep((int)(Math.random() * range) + min);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

