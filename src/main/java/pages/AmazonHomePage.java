package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AmazonHomePage {

    private WebDriver driver;
    private By searchBox = By.xpath("//input[@role='searchbox']");
    private By searchButton = By.id("nav-search-submit-button");

    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.amazon.com/");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        waitForSearchBox();
    }

    private void waitForSearchBox() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        } catch (Exception e) {
            System.out.println("Search box not found — refreshing the page...");
            driver.navigate().refresh();
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
            } catch (Exception ex) {
                throw new NoSuchElementException("Search box still not found after refresh.", ex);
            }
        }
    }

    public void search(String query) {
        waitForSearchBox();
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(query);
        driver.findElement(searchButton).click();
    }
}
