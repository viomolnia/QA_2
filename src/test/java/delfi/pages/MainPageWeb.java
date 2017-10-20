package delfi.pages;

import delfi.helpers.MainPageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.xpath;

public class MainPageWeb extends MainPageHelper {

    private BaseFunctions baseFunctions;

    private static final By TITLE1 = xpath(".//h3[@class='top2012-title']");
    private static final By TITLE2 = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TITLE3 = xpath(".//div[contains(@class, 'content-half-block')]");
    private static final By TITLE4 = xpath(".//div[contains(@class, 'content-third-block')]");
    private static final By TITLE5 = xpath(".//div[contains(@class, 'content-twothirds-block')]");
    private static final By TITLE6 = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TITLE7 = xpath(".//div[contains(@class, 'article-link publishTime')]");
    private static final By TITLE_CLASS1 = className("top2012-title");
    private static final By TITLE_CLASS2 = className("article-title");

    public MainPageWeb(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public  List<WebElement> extractArticles1() {
        return baseFunctions.getElements(TITLE1);
    }

    public  List<WebElement> extractArticles1(int size) {
        List<WebElement> result = new ArrayList<>();
        List<WebElement> allArticles = baseFunctions.getElements(TITLE1);

        IntStream.range(0, size).forEach(idx -> result.add(allArticles.get(idx)));
        return result;
    }

    public List<WebElement> extractArticles2() {
        return baseFunctions.getElements(TITLE2);
    }

    public List<WebElement> extractArticles3() {
        return baseFunctions.getElements(TITLE3);
    }

    public List<WebElement> extractArticles4() {
        return baseFunctions.getElements(TITLE4);
    }

    public List<WebElement> extractArticles5() {
        return baseFunctions.getElements(TITLE5);
    }

    public List<WebElement> extractArticles6() {
        return baseFunctions.getElements(TITLE6);
    }

    public List<WebElement> extractArticles7() {
        return baseFunctions.getElements(TITLE7);
    }

    @Override
    protected WebElement getTitleFromArticle(WebElement article) {
        return article.findElements(TITLE_CLASS1).size() > 0 ? article.findElement(TITLE_CLASS1) : article.findElement(TITLE_CLASS2);
    }

}