package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class IPhoneTest extends BaseTest {

    @Test
    public void testIphonePrice() {
        AmazonHomePage home = new AmazonHomePage(getDriver());
        SearchResultsPage results = new SearchResultsPage(getDriver());
        ProductPage product = new ProductPage(getDriver());

        home.open();
        home.search("iPhone 15");
        results.selectFirstProduct();

        product.addToCart();

        System.out.println(product.getProductPrice());
    }
}
