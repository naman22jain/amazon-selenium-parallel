package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    // Thread-safe WebDriver instance for parallel execution
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Get the driver for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    @Parameters({"execution"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("local") String execution, Method method) throws Exception {

        WebDriver webDriver;

        if (execution.equalsIgnoreCase("local")) {
            //Local execution
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();

        } else if (execution.equalsIgnoreCase("lambdatest")) {
            //LambdaTest execution
            String username = "namanjainqa";
            String accessKey = "LT_ACCESS_KEY";

            // LambdaTest capabilities
            Map<String, Object> ltOptions = new HashMap<>();
            ltOptions.put("build", "AmazonAutomationBuild");
            ltOptions.put("name", method.getDeclaringClass().getSimpleName() + " - " + method.getName()); // ✅ Dynamic name
            ltOptions.put("project", "AmazonAutomation");
            ltOptions.put("selenium_version", "4.25.0");
            ltOptions.put("w3c", true);

            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("browserName", "Chrome");
            caps.setCapability("browserVersion", "latest");
            caps.setCapability("platformName", "Windows 11");
            caps.setCapability("LT:Options", ltOptions);

            // Initialize remote driver for LambdaTest
            webDriver = new RemoteWebDriver(
                    new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"),
                    caps
            );

        } else {
            throw new IllegalArgumentException("Invalid execution type: " + execution);
        }

        // Store driver instance for current thread
        driver.set(webDriver);

        // Standard setup for both environments
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Defensive cookie cleanup
        try {
            getDriver().manage().deleteAllCookies();
        } catch (Exception e) {
            System.out.println("Warning: Could not delete cookies - " + e.getMessage());
        }

        // Maximize window
        getDriver().manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Quit driver and clean ThreadLocal storage
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception e) {
                System.out.println("Warning: Issue while quitting driver - " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
}
