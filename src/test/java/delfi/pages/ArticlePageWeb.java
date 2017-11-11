package delfi.pages;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;
import utils.CommentHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class ArticlePageWeb {
    private BaseFunctions baseFunctions;
    private CommentHelper commentHelper = new CommentHelper();

    private static final Logger LOGGER = LogManager.getLogger(ArticlePageWeb.class);

    private static final By TITLE = xpath("//span[@itemprop='headline name']");
    private static final By ARTICLE_IN_NEW_TAB = xpath("//*[contains(@class, 'article-title')]");
    private static final By ARTICLE_IN_NEW_TAB2 = xpath("//*[contains(@class, 'comments-about-title')]");
    private static final String ZERO = "(0)";
    private static final By COMMENT_COUNT = className("comment-count");
    private static final By COMMENT_COUNT_JOINED = className("commentCount");
    private static final By TECH_COMMENT_COUNT = className("tech-comment-count");

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

    public ArticlePageWeb(BaseFunctions baseFunctions) {
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

    public Map<Integer, Article> getTitleAndComments(int idx) {
        Map<Integer, Article> result = new HashMap<>();

        LOGGER.info("Skip age check on article's page");
        baseFunctions.skipAgeCheck();
        WebElement articleInNewTab;

        if (baseFunctions.getElements(ARTICLE_IN_NEW_TAB).size() > 0) {
            articleInNewTab = baseFunctions.getElement(ARTICLE_IN_NEW_TAB);
        } else {
            articleInNewTab = baseFunctions.getElement(ARTICLE_IN_NEW_TAB2);
        }

        LOGGER.info("Get title from article's page of web version");
        String titleText;
        if (articleInNewTab.findElements(TITLE).size() > 0) {
            titleText = articleInNewTab.findElement(TITLE).getText();
        } else {
            titleText = articleInNewTab.getText();
        }

        LOGGER.info("Save title and comments count from article's page of web version");
        result.put(idx, new Article(titleText, getComments(articleInNewTab)));
        return result;
    }

    public Article extractArticle() {

        LOGGER.info("Skip age check on article's page");
        baseFunctions.skipAgeCheck();
        WebElement articleInNewTab;

        if (baseFunctions.getElements(ARTICLE_IN_NEW_TAB).size() > 0) {
            articleInNewTab = baseFunctions.getElement(ARTICLE_IN_NEW_TAB);
        } else {
            articleInNewTab = baseFunctions.getElement(ARTICLE_IN_NEW_TAB2);
        }

        LOGGER.info("Get title from article's page of web version");
        String titleText;
        if (articleInNewTab.findElements(TITLE).size() > 0) {
            titleText = articleInNewTab.findElement(TITLE).getText();
        } else {
            titleText = articleInNewTab.getText();
        }

        LOGGER.info("Save title and comments count from article's page of web version");
        return new Article(titleText, getComments(articleInNewTab));
    }

    private Integer getComments(WebElement article) {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments

        LOGGER.info("Get comments count from article's page of web version");
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        String result = comments.size() > 0 ? comments.get(0).getText() : ZERO;
        return commentHelper.extractCommentCountFromString(result);
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
}
