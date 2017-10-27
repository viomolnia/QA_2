package delfi.pages;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ArticleWrapperMobile;
import utils.ArticleWrapperWeb;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.*;

public class MainPageMobile {
    private BaseFunctions baseFunctions;

    private static final Logger LOGGER = LogManager.getLogger(MainPageMobile.class);

    private static final By ARTICLE1 = xpath(".//div[@class='md-mosaic-title']");
    private static final By ARTICLE2 = xpath(".//div[@class='md-specialblock-headline-title']");

    public MainPageMobile(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    private List<ArticleWrapperMobile> getAllArticles() {
        List<WebElement> allArticles = extractArticles1();
        allArticles.addAll(extractArticles2());
        return allArticles.stream()
                .map(webElement -> new ArticleWrapperMobile(baseFunctions, webElement))
                .collect(toList());
    }

    public List<ArticleWrapperMobile> getFirstArticlesBySize(int size) {
        return extractArticles1(size).stream()
                .map(webElement -> new ArticleWrapperMobile(baseFunctions, webElement))
                .collect(toList());
    }

    public List<WebElement> extractArticles1(int size) {
        List<WebElement> result = new ArrayList<>();
        List<WebElement> allArticles = baseFunctions.getElements(ARTICLE1);

        IntStream.range(0, size).forEach(idx -> result.add(allArticles.get(idx)));
        return result;
    }

    private List<WebElement> extractArticles1() {
        return baseFunctions.getElements(ARTICLE1);
    }

    private List<WebElement> extractArticles2() {
        return baseFunctions.getElements(ARTICLE2);
    }

    public Map<Integer, ArticleWrapperMobile> extractArticlesMatchingByTitle(String articleTitle) {
        LOGGER.info("Save article found by title of mobile version");
        Map<Integer, ArticleWrapperMobile> matchingArticles = new HashMap<>();
        getAllArticles().stream().filter(a -> a.getTitleFromArticle().equals(articleTitle)).forEach(a -> {
            matchingArticles.put(0, a);
        });
        return matchingArticles;
    }

    public String extractLinksToArticlesMobileByTitle(ArticleWrapperMobile article) {
        LOGGER.info("Save link of article found by title of mobile version");
        return article.getLinkToArticleByTag();
    }

    public Map<Integer, String>  extractLinksToArticlesMobile(List<ArticleWrapperMobile> articles) {
        LOGGER.info("Save articles' links of mobile version");
        Map<Integer, String>  linksToArticles = new HashMap<>();
        IntStream.range(0, articles.size()).forEach(idx -> {
            String link =  articles.get(idx).getLinkToArticleByTag();
            linksToArticles.put(idx, link);
        });
        return linksToArticles;
    }

    public String extractLinksToCommentsPageByTitle(ArticleWrapperMobile article) {
        LOGGER.info("Save link of article found by title to comments page of mobile version");
        return article.getCommentsLink();
    }

    public Map<Integer, String> extractLinksToCommentsPage(List<ArticleWrapperMobile> articles) {
        LOGGER.info("Save articles' links to comments page of mobile version");
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();
        IntStream.range(0,  articles.size())
                .forEach(idx -> commentsMainWebLinks.put(idx, articles.get(idx).getCommentsLink()));
        return commentsMainWebLinks;
    }

    public Map<Integer, Article>  extractTitleWithComments(String articleTitle, Map<Integer, ArticleWrapperMobile>  matchingArticles) {
        LOGGER.info("Save articles' title and comments count from main page of mobile version ");
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        getAllArticles().stream()
                .filter(a -> a.getTitleFromArticle().equals(articleTitle))
                .forEach(allArticle -> articlesFromMainPage.putAll(getTitleAndComments(matchingArticles)));
        return articlesFromMainPage;
    }

    public Map<Integer, Article>  extractTitleWithComments(List<ArticleWrapperMobile> articles) {
        LOGGER.info("Save articles' title and comments count from main page of mobile version");
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        IntStream.range(0,  articles.size())
                .forEach(idx -> articlesFromMainPage.putAll(getTitleAndComments(new HashMap<Integer, ArticleWrapperMobile>() {{put(idx,  articles.get(idx));}})));
        return articlesFromMainPage;
    }

    private Map<Integer, Article> getTitleAndComments(Map<Integer, ArticleWrapperMobile>  articles) {
        LOGGER.info("Extract title and comments from main page of mobile version");
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Article(e.getValue().getTitleFromArticle(), e.getValue().getComments())));
    }

    public Article getTitleAndComments(ArticleWrapperMobile  article) {
        LOGGER.info("Extract title and comments from main page of mobile version");
        return new Article(article.getTitleFromArticle(), article.getComments());
    }

    public ArticleWrapperMobile getMatchingArticleWrapper(String name) {
        LOGGER.info("Find article by title of web version");
        List<ArticleWrapperMobile> matchingArticles = getAllArticles().stream()
                .filter(a -> a.getTitleFromArticle().equals(name))
                .collect(toList());
        assertEquals(1, matchingArticles.size());
        return matchingArticles.get(0);
    }
}
