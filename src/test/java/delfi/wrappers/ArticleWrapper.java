package delfi.wrappers;

import delfi.delfi_demo.BaseFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticleWrapper {
    private final BaseFunctions baseFunctions;
    private final WebElement webElement;

    private static final By TITLE= By.xpath("title locator");

    public ArticleWrapper(BaseFunctions baseFunctions, WebElement webElement) {
        this.baseFunctions = baseFunctions;
        this.webElement = webElement;
    }

    public String getArticleTitle() {
        return webElement.findElements(TITLE).isEmpty() ? webElement.findElement(TITLE).getText() : null;
    }

    public void clickOnTitle() {
        webElement.click();
    }
}
