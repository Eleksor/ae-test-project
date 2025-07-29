package ui.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FirstUiTests {
    WebDriver driver;
    protected WebDriverWait wait2;
    protected WebDriverWait wait5;
    protected WebDriverWait wait10;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait2 = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait10 = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();
    }

    @Test
    void addToCartTest() throws InterruptedException {
        driver.get("https://www.ae.com/");
        //driver.wait(1000);
        driver.findElement(By.xpath("//div[@data-testid='lockup-layout-primary']/span[text()=\"Men's New Arrivals\"]/..")).click();

    }
}
