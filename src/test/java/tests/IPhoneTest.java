package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

public class IPhoneTest extends BaseTest {

    @Test
    public void testIphonePrice() {
        AmazonHomePage home = new AmazonHomePage(driver);
        SearchResultsPage results = new SearchResultsPage(driver);
        ProductPage product = new ProductPage(driver);

        home.open();
        home.search("iPhone 15");
        results.selectFirstProduct();//span[contains(@id,'subtotal-amount-buybox')]/span



        product.addToCart();

        System.out.println(product.getProductPrice());
    }
}
