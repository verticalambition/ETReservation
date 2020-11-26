package com.jason.application.web.firstcrawler.EarthTreksWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Component
public class ETPageOne {


    private static WebDriver driver;

    public String processPageOne(boolean headless, int hour, String morningOrAfternoon) throws InterruptedException, IOException {
        System.setProperty("webdriver.gecko.driver", "/home/jason/development/tools/web/geckodriver/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("/usr/bin/firefox");
        if(headless) {
            options.addArguments("-headless");
        }

        driver = new FirefoxDriver(options);
        new WebDriverWait(driver, 25);
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
                    slot.findElement(By.tagName("a")).click();
                    //And hopefully moving on to PAGE 2
                }
                //Determine if its available to book

            }
            return "Processed Request";
        }
    }
}
