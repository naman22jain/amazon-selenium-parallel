package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultsPage {

    WebDriver driver;

    private By firstProduct = By.xpath("(//div[@role='listitem']//a)[1]"); //used Indexing because I am selecting only the first matching element

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectFirstProduct() {
        WebElement product = driver.findElement(firstProduct);
        product.click();
    }
}
