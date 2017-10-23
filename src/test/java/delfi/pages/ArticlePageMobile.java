package delfi.pages;

import delfi.helpers.CommentsHelper;
import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

public class ArticlePageMobile {
    private BaseFunctions baseFunctions;
    private static final Logger LOGGER = LogManager.getLogger(ArticlePageMobile.class);

    private static final By TITLE = tagName("h1");
    private static final By ARTICLE_IN_NEW_TAB = xpath("//*[contains(@class, 'article-title')]");
    private static final By ARTICLE_IN_NEW_TAB2 = xpath("//*[contains(@class, 'comments-about-title')]");

    private CommentsHelper commentsHelper = new CommentsHelper();

    public ArticlePageMobile(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public Map<Integer, Article> getTitlesAndComments(Map<Integer, String> articlesLinks) {
        LOGGER.info("Getting titles and comments from article page of mobile version");

        Map<Integer, Article> result = new HashMap<>();
        articlesLinks.forEach((key, value) -> result.putAll(getTitlesAndComments(key, value)));

        return result;
    }

    private Map<Integer, Article> getTitlesAndComments(int idx, String articleTitle) {
        Map<Integer, Article> result = new HashMap<>();

        LOGGER.info("Open article's page of mobile version");
        baseFunctions.goToUrl(articleTitle);

        LOGGER.info("Skip age check on article's page");
        baseFunctions.skipAgeCheck();
        WebElement articleInNewTab;

        if (baseFunctions.getElements(ARTICLE_IN_NEW_TAB).size() > 0) {
            articleInNewTab = baseFunctions.getElement(ARTICLE_IN_NEW_TAB);
        } else {
            articleInNewTab = baseFunctions.getElement(ARTICLE_IN_NEW_TAB2);
        }

        LOGGER.info("Get title from article's page of mobile version");
        String titleText;
        if (articleInNewTab.findElements(TITLE).size() > 0) {
            titleText = articleInNewTab.findElement(TITLE).getText();
        } else {
            titleText = articleInNewTab.getText();
        }

        LOGGER.info("Save title and comments count from article's page of mobile version");
        result.put(idx, new Article(titleText, commentsHelper.getComments(articleInNewTab)));
        return result;
    }
}
