package utils;

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

public class Utils {

    static Properties prop = new Properties();
    static InputStream input = null;

    public static WebDriver driver = initializeDriver();
    public static WebDriverWait wait = new WebDriverWait(driver, 10);

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


    public static void closeAllTabs() {
        Set<String> tabs = driver.getWindowHandles();
        for (int idx = tabs.toArray().length - 1; idx >= 0; idx--) {
            switchToTabByIdx(idx);
            driver.close();
        }
    }

    public static void switchToTabByIdx(int idx) {
        Set<String> tabs = driver.getWindowHandles();
        driver.switchTo().window((String) tabs.toArray()[idx]);
    }
}
