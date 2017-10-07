package delfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.xpath;

public class MainPageWeb {
    private static final Logger LOGGER = LogManager.getLogger(MainPageWeb.class);


    static final By TITLE = xpath(".//h3[@class='top2012-title']");
    static final By TITLE2 = xpath(".//div[contains(@class, 'article-full-image')]");
    static final By TITLE3 = xpath(".//div[contains(@class, 'content-half-block')]");
    static final By TITLE4 = xpath(".//div[contains(@class, 'content-third-block')]");
    static final By TITLE5 = xpath(".//div[contains(@class, 'content-twothirds-block')]");
    static final By TITLE6 = xpath(".//div[contains(@class, 'article-full-image')]");
    static final By TITLE7 = xpath(".//div[contains(@class, 'article-link publishTime')]");
    static final By TITLE_CLASS1 = className("top2012-title");
    static final By TITLE_CLASS2 = className("article-title");

    private List<WebElement> allArticles1;
    private List<WebElement> allArticles2;
    private List<WebElement> allArticles3;
    private List<WebElement> allArticles4;
    private List<WebElement> allArticles5;
    private List<WebElement> allArticles6;
    private List<WebElement> allArticles7;

    private Map<Integer, String> linksToArticlesPages = new HashMap<>();

    private Map<Integer, Article> articles = new HashMap<>();
    static Map<Integer, WebElement> matchingArticles = new HashMap<>();

    public Map<Integer, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }

    public List<WebElement> getAllArticles1() {
        return allArticles1;
    }

    public void setAllArticles1(List<WebElement> allArticles1) {
        this.allArticles1 = allArticles1;
    }

    public List<WebElement> getAllArticles2() {
        return allArticles2;
    }

    public void setAllArticles2(List<WebElement> allArticles2) {
        this.allArticles2 = allArticles2;
    }

    public List<WebElement> getAllArticles3() {
        return allArticles3;
    }

    public void setAllArticles3(List<WebElement> allArticles3) {
        this.allArticles3 = allArticles3;
    }

    public List<WebElement> getAllArticles4() {
        return allArticles4;
    }

    public void setAllArticles4(List<WebElement> allArticles4) {
        this.allArticles4 = allArticles4;
    }

    public List<WebElement> getAllArticles5() {
        return allArticles5;
    }

    public void setAllArticles5(List<WebElement> allArticles5) {
        this.allArticles5 = allArticles5;
    }

    public List<WebElement> getAllArticles6() {
        return allArticles6;
    }

    public void setAllArticles6(List<WebElement> allArticles6) {
        this.allArticles6 = allArticles6;
    }

    public List<WebElement> getAllArticles7() {
        return allArticles7;
    }

    public void setAllArticles7(List<WebElement> allArticles7) {
        this.allArticles7 = allArticles7;
    }

    public static Map<Integer, WebElement> getMatchingArticles() {
        return matchingArticles;
    }

    public static void setMatchingArticles(Map<Integer, WebElement> matchingArticles) {
        MainPageWeb.matchingArticles = matchingArticles;
    }

    public Map<Integer, String> getLinksToArticlesPages() {
        return linksToArticlesPages;
    }

    public void setLinksToArticlesPages(Map<Integer, String> linksToArticlesPages) {
        this.linksToArticlesPages = linksToArticlesPages;
    }

    Map<Integer, Article> getTitleAndComments(Map<Integer, WebElement> articles, By locator, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon) {

        LOGGER.info("Titles ang comments extracted from main page of web version...");
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Article(e.getValue().findElement(locator).getText(), commentsPageCommon.getComments(e.getValue(), base))));
    }
}
