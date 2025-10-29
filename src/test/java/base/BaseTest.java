package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("execution")
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("local") String execution) throws MalformedURLException {
        if (execution.equalsIgnoreCase("lambdatest")) {

            String username = "namanjainqa";
            String accessKey = "LT_0szUzBf7cIGYjT8RD754VVnmBjEMYHe3IBh3MH5E4KpljtI";

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "Chrome");
            capabilities.setCapability("browserVersion", "latest");
            capabilities.setCapability("platformName", "Windows 11");
            capabilities.setCapability("project", "AmazonAutomation");
            capabilities.setCapability("build", "AmazonAutomationBuild");
            capabilities.setCapability("name", "ParallelTestRun");

            String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";

            driver = new RemoteWebDriver(new URL(gridURL), capabilities);

        } else {
            //  LOCAL Execution
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
