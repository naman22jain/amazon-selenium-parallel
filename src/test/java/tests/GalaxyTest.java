package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class GalaxyTest extends BaseTest {

    @Test
    public void testGalaxyPrice() {
        AmazonHomePage home = new AmazonHomePage(getDriver());
        SearchResultsPage results = new SearchResultsPage(getDriver());
        ProductPage product = new ProductPage(getDriver());

        home.open();
        home.search("Samsung Galaxy S25");
        results.selectFirstProduct();

        product.addToCart();

        System.out.println(product.getProductPrice());
    }
}
