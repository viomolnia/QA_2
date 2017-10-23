package delfi.pages;

import delfi.helpers.CommentsHelper;
import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.xpath;

public class MainPageWeb {

    private BaseFunctions baseFunctions;
    private static final Logger LOGGER = LogManager.getLogger(MainPageWeb.class);

    private static final By TITLE1 = xpath(".//h3[@class='top2012-title']");
    private static final By TITLE2 = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TITLE3 = xpath(".//div[contains(@class, 'content-half-block')]");
    private static final By TITLE4 = xpath(".//div[contains(@class, 'content-third-block')]");
    private static final By TITLE5 = xpath(".//div[contains(@class, 'content-twothirds-block')]");
    private static final By TITLE6 = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TITLE7 = xpath(".//div[contains(@class, 'article-link publishTime')]");
    private static final By TITLE_CLASS1 = className("top2012-title");
    private static final By TITLE_CLASS2 = className("article-title");
    private static final String ZERO = "(0)";

    private CommentsHelper commentsHelper = new CommentsHelper();

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

    public Map<Integer, WebElement> extractArticlesMatchingByTitle(List<WebElement> allArticles, String articleTitle) {
        Map<Integer, WebElement> matchingArticles = new HashMap<>();
        allArticles.stream().filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle)).forEach(allArticle -> {
            LOGGER.info("Save article found by title");
            matchingArticles.put(0, allArticle);
        });
        return matchingArticles;
    }

    public Map<Integer, String> extractLinksToArticlePagesByTitle(List<WebElement> allArticles, String articleTitle) {
        Map<Integer, String> linksToArticlesPages = new HashMap<>();
        allArticles.stream().filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle)).forEach(allArticle -> {
            LOGGER.info("Save link to article found by name");
            linksToArticlesPages.put(0, getTitleFromArticle(allArticle).getAttribute("href"));
        });
        return linksToArticlesPages;
    }

    public Map<Integer, String> extractLinksToCommentsPageByTitle(List<WebElement> allArticles, String articleTitle) {
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();
        allArticles.stream().filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle)).forEach(allArticle -> {
            LOGGER.info("Save article's link to comments page");
            commentsMainWebLinks.put(0, getCommentsLink(allArticle));
        });
        return commentsMainWebLinks;
    }

    public Map<Integer, String> extractLinksToArticlePages(List<WebElement> allArticles) {
        Map<Integer, String> linksToArticlesPages = new HashMap<>();

        LOGGER.info("Save link to article found by name");
        IntStream.range(0, allArticles.size()).forEach(idx -> linksToArticlesPages.put(idx, getTitleFromArticle(allArticles.get(idx)).getAttribute("href")));
            ;
        return linksToArticlesPages;
    }

    public Map<Integer, String> extractLinksToCommentsPage(List<WebElement> allArticles) {
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();

        LOGGER.info("Save article's link to comments page");
        IntStream.range(0, allArticles.size()).forEach(idx -> commentsMainWebLinks.put(idx, getCommentsLink(allArticles.get(idx))));

        return commentsMainWebLinks;
    }

    public Map<Integer, Article> extractTitleWithComments(List<WebElement> allArticles, String articleTitle, Map<Integer, WebElement> matchingArticles) {
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        allArticles.stream().filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle)).forEach(allArticle -> {

            LOGGER.info("Save found articles' title and comments count");
            articlesFromMainPage.putAll(getTitleAndComments(matchingArticles));
        });
        return articlesFromMainPage;
    }

    public Map<Integer, Article> extractTitleWithComments(List<WebElement> allArticles) {
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        IntStream.range(0, allArticles.size()).forEach(idx -> {

            LOGGER.info("Save found articles' title and comments count");
            articlesFromMainPage.putAll(getTitleAndComments(new HashMap<Integer, WebElement>() {{put(idx, allArticles.get(idx));}}));
        });
        return articlesFromMainPage;
    }

    private WebElement getTitleFromArticle(WebElement article) {
        return article.findElements(TITLE_CLASS1).size() > 0 ? article.findElement(TITLE_CLASS1) : article.findElement(TITLE_CLASS2);
    }

    private Map<Integer, Article> getTitleAndComments(Map<Integer, WebElement> articles) {
        LOGGER.info("Extract title and comments from main page of web version");
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Article(getTitleFromArticle(e.getValue()).getText(), commentsHelper.getComments(e.getValue()))));
    }

    private String getCommentsLink(WebElement article) {
        List<WebElement> comments = article.findElements(commentsHelper.getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getAttribute("href") : ZERO;
    }
}