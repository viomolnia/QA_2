package delfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

class ArticlePageCommon {

    private static final Logger LOGGER = LogManager.getLogger(ArticlePageCommon.class);

    protected Map<Integer, Article> getTitlesAndComments(int idx, String articleTitle, By title, WebDriver driver, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon) {
        Map<Integer, Article> result = new HashMap<>();
        driver.get(articleTitle);

        //skipping an age check pop-up shown for a few articles
        base.skipAgeCheck(driver);
        WebElement articleInNewTab;

        if (driver.findElements(ArticlePageWeb.ARTICLE_IN_NEW_TAB).size() > 0) {
            articleInNewTab = driver.findElement(ArticlePageWeb.ARTICLE_IN_NEW_TAB);
        } else {
            articleInNewTab = driver.findElement(ArticlePageWeb.ARTICLE_IN_NEW_TAB2);
        }

        String titleText;
        if (articleInNewTab.findElements(title).size() > 0) {
            titleText = articleInNewTab.findElement(title).getText();
        } else {
            titleText = articleInNewTab.getText();
        }
        result.put(idx, new Article(titleText, commentsPageCommon.getComments(articleInNewTab, base)));
        return result;
    }


    Map<Integer, Article> getTitlesAndComments(Map<Integer, String> articlesLinks, WebDriver driver, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon, ArticlePageCommon articlePageCommon) {
        LOGGER.info("Getting titles and comments from article page...");

        Map<Integer, Article> result = new HashMap<>();
        articlesLinks.entrySet().forEach(a -> result.putAll(articlePageCommon.getTitlesAndComments(a.getKey(), a.getValue(), ArticlePageWeb.TITLE, driver, base, commentsPageCommon)));

        LOGGER.info("Titles and comments extracted from article page...");
        return result;
    }

}
