package delfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

class ArticlePageMobile extends ArticlePageCommon {
    private static final Logger LOGGER = LogManager.getLogger(ArticlePageMobile.class);

    private static final By TITLE = tagName("h1");
    static final By ARTICLE1 = xpath(".//div[@class='md-mosaic-title']");
    static final By ARTICLE2 = xpath(".//div[@class='md-specialblock-headline-title']");

    private Map<Integer, Article> articles;

    Map<Integer, Article> getArticles() {
        return articles;
    }

    void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }

    Map<Integer, Article> getTitleAndComments(Map<Integer, String> articlesLink, WebDriver driver, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon, ArticlePageCommon articlePageCommon) {
        LOGGER.info("Getting title and comments from article's page mobile");
        Map<Integer, Article> result = new HashMap<>();
        articlesLink.forEach((key, value) -> result.putAll(articlePageCommon.getTitlesAndComments(key, value, ArticlePageMobile.TITLE, driver, base, commentsPageCommon)));

        LOGGER.info("Title and comments extracted from article's page mobile");
        return result;
    }
}
