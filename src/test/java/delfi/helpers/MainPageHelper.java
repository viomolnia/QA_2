package delfi.helpers;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class MainPageHelper {

    private BaseFunctions baseFunctions;
    private static final Logger LOGGER = LogManager.getLogger(MainPageHelper.class);
    private CommentsExtractor commentsHelper = new CommentsExtractor();
    private static final String ZERO = "(0)";


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

    public Map<Integer, Article> extractTitleWithComments(List<WebElement> allArticles, String articleTitle, Map<Integer, WebElement> matchingArticles) {
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        allArticles.stream().filter(allArticle -> getTitleFromArticle(allArticle).getText().equals(articleTitle)).forEach(allArticle -> {

            LOGGER.info("Save found articles' title and comments count");
            articlesFromMainPage.putAll(getTitleAndComments(matchingArticles));
        });
        return articlesFromMainPage;
    }

    public Map<Integer, String> extractLinksToArticlePages(List<WebElement> allArticles) {
        Map<Integer, String> linksToArticlesPages = new HashMap<>();

        LOGGER.info("Save link to article found by name");
        IntStream.range(0, allArticles.size()).forEach(idx -> linksToArticlesPages.put(idx, getTitleFromArticle(allArticles.get(idx)).getAttribute("href")));
        ;
        return linksToArticlesPages;
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

    public Map<Integer, String> extractLinksToCommentsPage(List<WebElement> allArticles) {
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();

        LOGGER.info("Save article's link to comments page");
        IntStream.range(0, allArticles.size()).forEach(idx -> commentsMainWebLinks.put(idx, getCommentsLink(allArticles.get(idx))));

        return commentsMainWebLinks;
    }



    public Map<Integer, Article> extractTitleWithComments(List<WebElement> allArticles) {
        Map<Integer, Article> articlesFromMainPage = new HashMap<>();
        IntStream.range(0, allArticles.size()).forEach(idx -> {

            LOGGER.info("Save found articles' title and comments count");
            articlesFromMainPage.putAll(getTitleAndComments(new HashMap<Integer, WebElement>() {{put(idx, allArticles.get(idx));}}));
        });
        return articlesFromMainPage;
    }


    protected abstract WebElement getTitleFromArticle(WebElement article);

}
