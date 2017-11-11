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
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class MainPageWeb {

    private BaseFunctions baseFunctions;
    private static final Logger LOGGER = LogManager.getLogger(MainPageWeb.class);

    private static final By TOP_TITLES = xpath(".//h3[@class='top2012-title']");
    private static final By BLOCK_TITLES = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By BIG_TITLES = xpath(".//div[contains(@class, 'content-half-block')]");
    private static final By SMALL_TITLES = xpath(".//div[contains(@class, 'content-third-block')]");
    private static final By BIGGEST_TITLES = xpath(".//div[contains(@class, 'content-twothirds-block')]");
    private static final By INLINE_TITLES = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TEXT_TITLES = xpath(".//div[contains(@class, 'article-link publishTime')]");

    private static final By LOGO = className("headerLogo");
    private static final By DATE = id("nameDays");
    private static final By WEATHER = id("header-weather");
    private static final By TOP_MENU_MIDDLE = className("headerSeparatedNav");
    private static final By TOP_MENU_RIGHT = className("headerSeparatedNavLink");
    private static final By TOP_BANNER = className("top-banner");
    private static final By BIG_MENU = xpath(".//nav[contains(@class, 'headerMainNavigation headerSeparatedNav')]");
    private static final By UNDER_BIG_MENU = xpath(".//nav[contains(@class, 'headerSeparatedNav headerChannelCategories')]");
    private static final By ADVERTISEMENT = xpath(".//*[@id='column3-top']");
    private static final By ARTICLES_COLUMN = xpath(".//*[@id='column1-top']");

    public MainPageWeb(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        baseFunctions.isPresent(LOGO);
        baseFunctions.isPresent(DATE);
        baseFunctions.isPresent(WEATHER);
        baseFunctions.isPresent(TOP_MENU_MIDDLE);
        baseFunctions.isPresent(TOP_MENU_RIGHT);
        baseFunctions.isPresent(TOP_BANNER);
        baseFunctions.isPresent(BIG_MENU);
        baseFunctions.isPresent(UNDER_BIG_MENU);
        baseFunctions.isPresent(ADVERTISEMENT);
        baseFunctions.isPresent(ARTICLES_COLUMN);
    }

    private List<WebElement> extractTopArticles() {
        return baseFunctions.getElements(TOP_TITLES);
    }

    private List<WebElement> extractTopArticles(int size) {
        List<WebElement> result = new ArrayList<>();
        List<WebElement> allArticles = baseFunctions.getElements(TOP_TITLES);

        IntStream.range(0, size).forEach(idx -> result.add(allArticles.get(idx)));
        return result;
    }

    private List<WebElement> extractBlockArticles() {
        return baseFunctions.getElements(BLOCK_TITLES);
    }
    private List<WebElement> extractBigArticles() {
        return baseFunctions.getElements(BIG_TITLES);
    }
    private List<WebElement> extractSmallArticles() {
        return baseFunctions.getElements(SMALL_TITLES);
    }
    private List<WebElement> extractBiggestArticles() {
        return baseFunctions.getElements(BIGGEST_TITLES);
    }
    private List<WebElement> extractInlineArticles() {
        return baseFunctions.getElements(INLINE_TITLES);
    }
    private List<WebElement> extractTextArticles() {
        return baseFunctions.getElements(TEXT_TITLES);
    }

    public List<ArticleWrapperWeb> getAllArticlesWrappers() {

        //LOGGER.info("Get all articles from main page of web version");
        List<WebElement> articles = extractTopArticles();
        articles.addAll(extractBlockArticles());
        articles.addAll(extractBigArticles());
        articles.addAll(extractSmallArticles());
        articles.addAll(extractBiggestArticles());
        articles.addAll(extractInlineArticles());
        articles.addAll(extractTextArticles());

        return articles.stream()
                .map(webElement -> new ArticleWrapperWeb(baseFunctions, webElement))
                .collect(toList());
    }

    public List<ArticleWrapperWeb> getFirstArticlesBySize(int size) {
        return extractTopArticles(size).stream()
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

    public ArticleWrapperWeb getArticleWrapperByTitle(List <ArticleWrapperWeb> articles, String name) {
        LOGGER.info("Find article by title of web version");
        List<ArticleWrapperWeb> matchingArticles = articles.stream()
                .filter(a -> a.getTitleFromArticle().equals(name))
                .collect(toList());
        assertEquals(1, matchingArticles.size());
        return matchingArticles.get(0);
    }

    public Map<Integer, String> extractLinksToArticlePages(List<ArticleWrapperWeb> articles) {
        Map<Integer, String> linksToArticlesPages = new HashMap<>();

        LOGGER.info("Get links to articles pages of web version");
        IntStream.range(0, articles.size())
                .forEach(idx -> linksToArticlesPages.put(idx, articles.get(idx).getLinkToArticle()));

        return linksToArticlesPages;
    }

    public Map<Integer, String> extractLinksToCommentsPage(List<ArticleWrapperWeb> articles) {
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();

        LOGGER.info("Get links to comments page of web version");
        IntStream.range(0, articles.size())
                .forEach(idx -> commentsMainWebLinks.put(idx, articles.get(idx).getCommentsLink()));

        return commentsMainWebLinks;
    }

    public Map<Integer, Article> extractArticle(List<ArticleWrapperWeb> articles) {
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        IntStream.range(0, articles.size()).forEach(idx -> {

            LOGGER.info("Save found articles' title and comments count of web version");
            articlesFromMainPage.putAll(getTitleAndComments(new HashMap<Integer, ArticleWrapperWeb>() {{put(idx, articles.get(idx));}}));
        });
        return articlesFromMainPage;
    }

    public Article extractArticle(ArticleWrapperWeb article) {
        return new Article(article.getTitleFromArticle(),article.getComments());
    }

    private Map<Integer, Article> getTitleAndComments(Map<Integer, ArticleWrapperWeb> articles) {
        LOGGER.info("Extract title and comments from main page of web version");
        return articles.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> new Article(e.getValue().getTitleFromArticle(), e.getValue().getComments())));
    }
}