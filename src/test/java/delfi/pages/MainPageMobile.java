package delfi.pages;

import delfi.helpers.MainPageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

public class MainPageMobile extends MainPageHelper{
    private BaseFunctions baseFunctions;
    private static final Logger LOGGER = LogManager.getLogger(MainPageMobile.class);

    private static final By TITLE = tagName("h1");
    private static final By ARTICLE1 = xpath(".//div[@class='md-mosaic-title']");
    private static final By ARTICLE2 = xpath(".//div[@class='md-specialblock-headline-title']");

    public MainPageMobile(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public List<WebElement> extractArticles1() {
        return baseFunctions.getElements(ARTICLE1);
    }

    public  List<WebElement> extractArticles1(int size) {
        List<WebElement> result = new ArrayList<>();
        List<WebElement> allArticles = baseFunctions.getElements(ARTICLE1);

        IntStream.range(0, size).forEach(idx -> result.add(allArticles.get(idx)));
        return result;
    }

    public List<WebElement> extractArticles2() {
        return baseFunctions.getElements(ARTICLE2);
    }

    public Map<Integer, String> extractLinksToArticlesMobileByTitle(List<WebElement> allArticles, String articleTitle) {
        LOGGER.info("Save link of article found by title of mobile version");
        Map<Integer, String>  linksToArticles = new HashMap<>();
        allArticles.stream().filter(a -> getTitleFromArticle(a).getText().equals(articleTitle)).forEach(a -> {
            String link = a.findElements(tagName("a")).get(0).getAttribute("href");
            linksToArticles.put(0, link);
        });
        return linksToArticles;
    }

    public Map<Integer, String>  extractLinksToArticlesMobile(List<WebElement> allArticles) {
        LOGGER.info("Save articles' links of mobile version");
        Map<Integer, String>  linksToArticles = new HashMap<>();
        IntStream.range(0, allArticles.size()).forEach(idx -> {
            String link = allArticles.get(idx).findElements(tagName("a")).get(0).getAttribute("href");
            linksToArticles.put(idx, link);
        });
        return linksToArticles;
    }

    @Override
    protected WebElement getTitleFromArticle(WebElement article) {
        return article.findElements(TITLE).size() > 0 ? article.findElement(TITLE) :
                article.findElements(tagName("a")).get(0);
    }
}
