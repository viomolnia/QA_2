package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.openqa.selenium.By.className;

public class BaseFunctions {
    private WebDriver driver;
    private static Properties prop = new Properties();
    private static InputStream input = null;
    private static final Logger LOGGER = LogManager.getLogger(BaseFunctions.class);


    public BaseFunctions() {
        LOGGER.info("Starting driver");
        this.driver = initializeDriver();

        LOGGER.info("Maximize browser window");
        this.driver.manage().window().maximize();

        LOGGER.info("Scroll to the end of the page");
        ((JavascriptExecutor) this.driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void goToUrl(String url) {
        LOGGER.info("Open URL: " + url);
        driver.get(url);
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public WebElement getElement(By locator) {
        //LOGGER.info("Receiving web element");
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By locator) {
        //LOGGER.info("Receiving web element");
        return driver.findElements(locator);
    }

    public void clickElement(By locator) {
        LOGGER.info("Click element");
        driver.findElement(locator).click();
    }

    private static RemoteWebDriver initializeDriver() {
        String browser = getBrowser();

        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", prop.getProperty("path.to.driver"));
                return new FirefoxDriver();
            case "chrome":
                System.setProperty("webdriver.chrome.driver", prop.getProperty("path.to.driver"));
                return new ChromeDriver();
            default:
                throw new RuntimeException("Invalid browser name");
        }
    }

    private static String getBrowser() {
        String browser = "";
        try {
            InputStream input = new FileInputStream("/home/anna/UserData/Repos/QA_2/src/test/resources/test.properties");
            prop.load(input);
            browser = prop.getProperty("browser.name");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return browser;
    }

    public void skipAgeCheck() {
        if (driver.findElements(className("yrscheck-yes-input")).size() > 0) {
            driver.findElements(className("yrscheck-yes-input")).get(0).click();
        }
    }

    public void closeAllTabs() {
        LOGGER.info("Close browser");
        Set<String> tabs = driver.getWindowHandles();
        for (int idx = tabs.toArray().length - 1; idx >= 0; idx--) {
            switchToTabByIdx(idx);
            driver.close();
        }
    }

    private void switchToTabByIdx(int idx) {
        Set<String> tabs = driver.getWindowHandles();
        driver.switchTo().window((String) tabs.toArray()[idx]);
    }

}
