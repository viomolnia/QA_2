package utils;

import delfi.ArticlesTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class BaseFunctions {

    WebDriver driver;
    WebDriverWait wait;
    static Properties prop = new Properties();
    static InputStream input = null;
    private static final Logger LOGGER = LogManager.getLogger(ArticlesTest.class);

    public BaseFunctions() {
        this.driver = initializeDriver();
        this.wait = new WebDriverWait(this.driver, 10);

        LOGGER.info("Maximize browser window size");
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public static Properties getProp() {
        return prop;
    }

    public static void setProp(Properties prop) {
        BaseFunctions.prop = prop;
    }

    public static InputStream getInput() {
        return input;
    }

    public static void setInput(InputStream input) {
        BaseFunctions.input = input;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public static RemoteWebDriver initializeDriver() {
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


    public void closeAllTabs() {
        Set<String> tabs = driver.getWindowHandles();
        for (int idx = tabs.toArray().length - 1; idx >= 0; idx--) {
            switchToTabByIdx(idx);
            driver.close();
        }
    }

    public void switchToTabByIdx(int idx) {
        Set<String> tabs = driver.getWindowHandles();
        driver.switchTo().window((String) tabs.toArray()[idx]);
    }

    public void goToUrl(String url) {
        if(!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        LOGGER.info("User goes to: " + url);
        driver.get(url);
    }
}
