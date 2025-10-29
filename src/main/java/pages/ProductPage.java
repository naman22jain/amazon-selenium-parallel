package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;


    private By addToCartButton = By.xpath("//input[@title='Add to Shopping Cart']");
    private By priceLocator = By.xpath("//span[contains(@id,'subtotal-amount-buybox')]/span");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAddToCartAvailable() {
        List<WebElement> buttons = driver.findElements(addToCartButton);
        return !buttons.isEmpty() && buttons.get(0).isDisplayed();
    }

    public void addToCart() {
        if (isAddToCartAvailable()) {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
            driver.get("https://www.amazon.com/gp/cart/view.html");
        } else {
            System.out.println("Product is currently unavailable (No Add to Cart button found).");
        }
    }
    public String getProductPrice() {
        try {
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator));
            String price = priceElement.getText().trim();

            if (price.isEmpty()) {
                System.out.println("Found element but text is empty. Retrying...");
                Thread.sleep(2000);
                price = driver.findElement(priceLocator).getText().trim();
            }


            return "Cart Subtotal: " + price;

        } catch (Exception e) {
            System.out.println("Price not found using given locator: " + e.getMessage());
            return "N/A";
        }
    }
}
