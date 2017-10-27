package delfi.pages;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ArticleWrapperWeb;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;
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

    public MainPageWeb(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    private List<WebElement> extractArticles1() {
        return baseFunctions.getElements(TITLE1);
    }

    private List<WebElement> extractArticles1(int size) {
        List<WebElement> result = new ArrayList<>();
        List<WebElement> allArticles = baseFunctions.getElements(TITLE1);

        IntStream.range(0, size).forEach(idx -> result.add(allArticles.get(idx)));
        return result;
    }

    private List<WebElement> extractArticles2() {
        return baseFunctions.getElements(TITLE2);
    }
    private List<WebElement> extractArticles3() {
        return baseFunctions.getElements(TITLE3);
    }
    private List<WebElement> extractArticles4() {
        return baseFunctions.getElements(TITLE4);
    }
    private List<WebElement> extractArticles5() {
        return baseFunctions.getElements(TITLE5);
    }
    private List<WebElement> extractArticles6() {
        return baseFunctions.getElements(TITLE6);
    }
    private List<WebElement> extractArticles7() {
        return baseFunctions.getElements(TITLE7);
    }

    private List<ArticleWrapperWeb> getAllArticles() {

        //LOGGER.info("Get all articles from main page of web version");
        List<WebElement> articles = extractArticles1();
        articles.addAll(extractArticles2());
        articles.addAll(extractArticles3());
        articles.addAll(extractArticles4());
        articles.addAll(extractArticles5());
        articles.addAll(extractArticles6());
        articles.addAll(extractArticles7());

        return articles.stream()
                .map(webElement -> new ArticleWrapperWeb(baseFunctions, webElement))
                .collect(toList());
    }

    public List<ArticleWrapperWeb> getFirstArticlesBySize(int size) {
        return extractArticles1(size).stream()
                .map(webElement -> new ArticleWrapperWeb(baseFunctions, webElement))
                .collect(toList());
    }

    public String extractLinksToArticlePagesByTitle(ArticleWrapperWeb article) {
        LOGGER.info("Find link to article by title of web version");
        return article.getLinkToArticle();
    }

    public String extractLinksToCommentsPageByTitle(ArticleWrapperWeb article) {
        LOGGER.info("Find link to comments page by title of web version");
        return article.getCommentsLink();
    }

    public ArticleWrapperWeb getArticleWrapperByTitle(String name) {
        LOGGER.info("Find article by title of web version");
        List<ArticleWrapperWeb> matchingArticles = getAllArticles().stream()
                .filter(a -> a.getTitleFromArticle().equals(name))
                .collect(toList());
        assertEquals(1, matchingArticles.size());
        return matchingArticles.get(0);
    }

    public Map<Integer, String> extractLinksToArticlePages(List<ArticleWrapperWeb> articles) {
        Map<Integer, String> linksToArticlesPages = new HashMap<>();

        LOGGER.info("Get links to articles pages of web version");
        IntStream.range(0, articles.size())
                .forEach(idx -> linksToArticlesPages.put(idx, getFirstArticlesBySize(5).get(idx).getLinkToArticle()));

        return linksToArticlesPages;
    }

    public Map<Integer, String> extractLinksToCommentsPage(List<ArticleWrapperWeb> articles) {
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();

        LOGGER.info("Get links to comments page of web version");
        IntStream.range(0, articles.size())
                .forEach(idx -> commentsMainWebLinks.put(idx, getAllArticles().get(idx).getCommentsLink()));

        return commentsMainWebLinks;
    }

    public Map<Integer, Article> extractTitleWithComments(String articleTitle, Map<Integer, ArticleWrapperWeb> matchingArticles) {
        LOGGER.info("Save found article's title and comments count of web version");
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        getAllArticles().stream()
                .filter(a -> a.getTitleFromArticle().equals(articleTitle))
                .forEach(allArticle -> articlesFromMainPage.putAll(getTitleAndComments(matchingArticles)));
        return articlesFromMainPage;
    }

    public Map<Integer, Article> extractTitleWithComments(List<ArticleWrapperWeb> articles) {
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        IntStream.range(0, articles.size()).forEach(idx -> {

            LOGGER.info("Save found articles' title and comments count of web version");
            articlesFromMainPage.putAll(getTitleAndComments(new HashMap<Integer, ArticleWrapperWeb>() {{put(idx, articles.get(idx));}}));
        });
        return articlesFromMainPage;
    }

    public Article extractTitleWithComments(ArticleWrapperWeb article) {
        return new Article(article.getTitleFromArticle(),article.getComments());
    }

    private Map<Integer, Article> getTitleAndComments(Map<Integer, ArticleWrapperWeb> articles) {
        LOGGER.info("Extract title and comments from main page of web version");
        return articles.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> new Article(e.getValue().getTitleFromArticle(), e.getValue().getComments())));
    }
}