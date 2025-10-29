package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AmazonHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By searchBox = By.xpath("//input[@role='searchbox']");
    private By searchButton = By.id("nav-search-submit-button");
    private By continueShoppingButton = By.xpath("//button[contains(text(),'Continue shopping')]");

    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        try {
            // Open Amazon
            driver.get("https://www.amazon.com/");

            //Handle region pop-up if it appears
            handleContinueShopping();

            //Wait for search box to be ready
            waitForSearchBox();

        } catch (Exception e) {
            throw new RuntimeException("Failed to open Amazon home page: " + e.getMessage(), e);
        }
    }

    private void waitForSearchBox() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        } catch (TimeoutException e) {
            System.out.println("Search box not found — refreshing the page...");
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        }
    }

    private void handleContinueShopping() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

            if (!driver.findElements(continueShoppingButton).isEmpty()) {
                WebElement continueBtn = driver.findElement(continueShoppingButton);

                if (continueBtn.isDisplayed()) {
                    try {
                        continueBtn.click();
                    } catch (ElementClickInterceptedException ex) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
                    }
                    System.out.println("Clicked 'Continue Shopping' button.");
                }
            } else {
                System.out.println("No 'Continue Shopping' button visible.");
            }

        } catch (Exception e) {
            System.out.println("Could not handle 'Continue Shopping' button: " + e.getMessage());
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    public void search(String query) {
        waitForSearchBox();
        WebElement box = driver.findElement(searchBox);
        box.clear();
        box.sendKeys(query);
        driver.findElement(searchButton).click();
    }
}
