package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    // 🔐 LambdaTest Credentials
    private final String LAMBDATEST_USERNAME = "namanjainqa";
    private final String LAMBDATEST_ACCESS_KEY = "LT_0szUzBf7cIGYjT8RD754VVnmBjEMYHe3IBh3MH5E4KpljtI";
    private final String GRID_URL = "https://" + LAMBDATEST_USERNAME + ":" + LAMBDATEST_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    // 🧩 Toggle: local vs LambdaTest
    private final boolean runOnLambdaTest = true;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("Chrome") String browser, Method method) {
        try {
            if (runOnLambdaTest) {
                // Desired capabilities
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("browserName", browser);
                capabilities.setCapability("browserVersion", "latest");
                capabilities.setCapability("platformName", "Windows 11");

                // LambdaTest options (inside LT:Options)
                Map<String, Object> ltOptions = new HashMap<>();
                ltOptions.put("build", "AmazonAutomationBuild");
                ltOptions.put("name", method.getName()); // dynamically name test
                ltOptions.put("network", true);
                ltOptions.put("visual", true);
                ltOptions.put("video", true);
                ltOptions.put("console", true);
                ltOptions.put("selenium_version", "4.25.0");

                capabilities.setCapability("LT:Options", ltOptions);

                driver = new RemoteWebDriver(new URL(GRID_URL), capabilities);
            } else {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Driver setup failed: " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            //Report status to LambdaTest dashboard
            if (driver instanceof JavascriptExecutor && runOnLambdaTest) {
                ((JavascriptExecutor) driver).executeScript(
                        "lambda-status=" + (result.getStatus() == ITestResult.SUCCESS ? "passed" : "failed"));
            }
        } catch (Exception e) {
            System.out.println("Unable to update LambdaTest status: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
