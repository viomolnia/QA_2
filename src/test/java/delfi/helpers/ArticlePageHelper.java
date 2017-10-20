package delfi.helpers;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.HashMap;
import java.util.Map;

public abstract class ArticlePageHelper {

    private BaseFunctions baseFunctions;
    private final Logger LOGGER = LogManager.getLogger(ArticlePageHelper.class);
    private CommentsExtractor commentsHelper = new CommentsExtractor();

    public ArticlePageHelper(BaseFunctions baseFunctions) {
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

        LOGGER.info("Open article's page of web version");
        baseFunctions.goToUrl(articleTitle);

        LOGGER.info("Skip age check on article's page");
        baseFunctions.skipAgeCheck();
        WebElement articleInNewTab;

        if (baseFunctions.getElements(getArticleInNewTab()).size() > 0) {
            articleInNewTab = baseFunctions.getElement(getArticleInNewTab());
        } else {
            articleInNewTab = baseFunctions.getElement(getArticleInNewTab2());
        }

        LOGGER.info("Get title from article's page of web version");
        String titleText;
        if (articleInNewTab.findElements(getTitle()).size() > 0) {
            titleText = articleInNewTab.findElement(getTitle()).getText();
        } else {
            titleText = articleInNewTab.getText();
        }

        LOGGER.info("Save title and comments count from article's page of web version");
        result.put(idx, new Article(titleText, commentsHelper.getComments(articleInNewTab)));
        return result;
    }

    protected abstract By getTitle();
    protected abstract By getArticleInNewTab();
    protected abstract By getArticleInNewTab2();
}
