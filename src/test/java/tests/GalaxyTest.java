package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class GalaxyTest extends BaseTest {

    @Test
    public void testGalaxyPrice() throws InterruptedException {
        AmazonHomePage home = new AmazonHomePage(driver);
        SearchResultsPage results = new SearchResultsPage(driver);
        ProductPage product = new ProductPage(driver);

        home.open();
        home.search("Samsung Galaxy S25");
        results.selectFirstProduct();

        product.addToCart();

        System.out.println(product.getProductPrice());
    }
}
