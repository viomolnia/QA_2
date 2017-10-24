package delfi.pages;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;
import utils.CommentHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

public class MainPageMobile {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper;

    private static final Logger LOGGER = LogManager.getLogger(MainPageMobile.class);

    private static final By TITLE = tagName("h1");
    private static final By ARTICLE1 = xpath(".//div[@class='md-mosaic-title']");
    private static final By ARTICLE2 = xpath(".//div[@class='md-specialblock-headline-title']");

    private static final By COMMENT_COUNT = className("comment-count");
    private static final By COMMENT_COUNT_JOINED = className("commentCount");
    private static final By TECH_COMMENT_COUNT = className("tech-comment-count");
    private static final String ZERO = "(0)";

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

    public Map<Integer, WebElement> extractArticlesMatchingByTitle(List<WebElement> allArticles, String articleTitle) {
        LOGGER.info("Save article found by title of mobile version");
        Map<Integer, WebElement> matchingArticles = new HashMap<>();
        allArticles.stream().filter(a -> getTitleFromArticle(a).getText().equals(articleTitle)).forEach(a -> {
            matchingArticles.put(0, a);
        });
        return matchingArticles;
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

    private WebElement getTitleFromArticle(WebElement article) {
        return article.findElements(TITLE).size() > 0 ? article.findElement(TITLE) :
                article.findElements(tagName("a")).get(0);
    }

    private By getCorrectCommentLocator(WebElement element) {
        if (element.findElements(COMMENT_COUNT).size() > 0) {
            return COMMENT_COUNT;
        } else if (element.findElements(COMMENT_COUNT_JOINED).size() > 0){
            return COMMENT_COUNT_JOINED;
        } else {
            return TECH_COMMENT_COUNT;
        }
    }

    private String getCommentsLink(WebElement article) {
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getAttribute("href") : ZERO;
    }

    public Map<Integer, String> extractLinksToCommentsPageByTitle(List<WebElement> allArticles, String articleTitle) {
        LOGGER.info("Save link of article found by title to comments page of mobile version");
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();
        allArticles.stream()
                .filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle))
                .forEach(allArticle -> commentsMainWebLinks.put(0, getCommentsLink(allArticle)));
        return commentsMainWebLinks;
    }

    public Map<Integer, String> extractLinksToCommentsPage(List<WebElement> allArticles) {
        LOGGER.info("Save articles' links to comments page");
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();
        IntStream.range(0, allArticles.size())
                .forEach(idx -> commentsMainWebLinks.put(idx, getCommentsLink(allArticles.get(idx))));
        return commentsMainWebLinks;
    }

    public Map<Integer, Article>  extractTitleWithComments(List<WebElement> allArticles, String articleTitle, Map<Integer, WebElement> matchingArticles) {
        LOGGER.info("Save articles' title and comments count from main page of mobile version ");
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        allArticles.stream()
                .filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle))
                .forEach(allArticle -> articlesFromMainPage.putAll(getTitleAndComments(matchingArticles)));
        return articlesFromMainPage;
    }

    public Map<Integer, Article>  extractTitleWithComments(List<WebElement> allArticles) {
        LOGGER.info("Save articles' titlea and comments count from main page of mobile version");
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        IntStream.range(0, allArticles.size())
                .forEach(idx -> articlesFromMainPage.putAll(getTitleAndComments(new HashMap<Integer, WebElement>() {{put(idx, allArticles.get(idx));}})));
        return articlesFromMainPage;
    }

    private Map<Integer, Article> getTitleAndComments(Map<Integer, WebElement> articles) {
        LOGGER.info("Extract title and comments from main page of web version");
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Article(getTitleFromArticle(e.getValue()).getText(), getComments(e.getValue()))));
    }

    private Integer getComments(WebElement article) {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments

        LOGGER.info("Get comments from main page");
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        String result = comments.size() > 0 ? comments.get(0).getText() : ZERO;
        return commentHelper.extractCommentCountFromString(result);
    }
}
