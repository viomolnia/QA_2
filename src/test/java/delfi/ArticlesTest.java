package delfi;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.*;
import static utils.Utils.*;

public class ArticlesTest {

    private static final String HOME_PAGE = "http://delfi.lv/";
    private static final String MOBILE_VERSION = "http://m.delfi.lv/";
    private static final By COMMENT_COUNT = className("comment-count");
    private static final By COMMENT_COUNT_JOINED = className("commentCount");
    private static final By TECH_COMMENT_COUNT = className("tech-comment-count");
    private static final By TITLE = xpath(".//h3[@class='top2012-title']");
    private static final By TITLE_CLASS = className("top2012-title");
    private static final By TITLE_MOBILE = className("md-scrollpos");
    private static final By TITLE_ON_TAB = xpath("//span[@itemprop='headline name']");
    private static final By TITLE_ON_TAB_MOBILE = tagName("h1");
    private static final By ARTICLE_IN_NEW_TAB = xpath("//div[contains(@class, 'article-title')]");
    private static final By ARTICLE_MOBILE = xpath(".//div[@class='md-mosaic-title']");

    //Please, set browser name and path to driver in ../resources/test.properties file!
    @Test
    @Description("Check first five articles on Delfi full and mobile versions")
    public void delfiPageTest() throws InterruptedException {
        int counter = 0;
        boolean result;

        Map<Integer, String> articlesMainWeb;
        Map<Integer, String> articlesTabWeb;
        Map<Integer, String> commentsTabWeb;

        Map<Integer, String> articlesMainMobile;
        Map<Integer, String> articlesTabMobile;

        List<WebElement> allArticles;
        List<WebElement> allArticlesMobile;

        do {
            driver.get(HOME_PAGE);
            driver.manage().window().maximize();

            allArticles = driver.findElements(TITLE);

            Map<Integer, WebElement> articles = new HashMap<>();
            for(int i = 0; i<=4; i++) {
                articles.put(i, allArticles.get(i));
            }

            articlesMainWeb = getTitlesAngCommentsFromMainPage(articles);

            articlesTabWeb = getTitleAngCommentsFromNewTab(articles);

            driver.get(MOBILE_VERSION);

            allArticlesMobile = driver.findElements(ARTICLE_MOBILE);

            Map<Integer, WebElement> articlesMobile = new HashMap<>();
            for(int i = 0; i<=4; i++) {
                articlesMobile.put(i, allArticlesMobile.get(i));
            }

            articlesMainMobile = getTitlesAngCommentsFromMainPageMobile(articlesMobile);

            articlesTabMobile = getTitleAngCommentsFromNewTabMobile(articlesMobile);

            result = articlesMainWeb.equals(articlesTabWeb) &&
                    articlesTabWeb.equals(articlesMainMobile) &&
                    articlesMainMobile.equals(articlesTabMobile);
            counter++;

        } while(!result && counter < 1);


        if (!result) {
            List<ArticleReview> errors = getDifferentTitles(articlesMainWeb, articlesTabWeb, articlesMainMobile, articlesTabMobile);
            System.out.println("Article numbers, that are not equal: ");
            System.out.println(errors);
        }

        assertTrue(result);

        closeAllTabs();
    }

    private Map<Integer, String> getTitlesAngCommentsFromMainPage(Map<Integer, WebElement> articles) {
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().findElement(TITLE_CLASS).getText() + getComments(e.getValue())));
    }

    private Map<Integer, String> getTitlesAngCommentsFromMainPageMobile(Map<Integer, WebElement> articles) {
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().findElement(TITLE_MOBILE).getText() + getMobileComments(e.getValue())));
    }

    private Map<Integer, String> getTitleAngCommentsFromNewTab(Map<Integer, WebElement> articles) {
        Map<Integer, String> result = new HashMap<>();
        articles.entrySet().forEach(a -> result.putAll(getTitlesAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB)));
        return result;
    }

    private Map<Integer, String> getCommentsCountFromTab(Map<Integer, WebElement> articles) {
        Map<Integer, String> result = new HashMap<>();
        articles.entrySet().forEach(a -> result.putAll(getTitlesAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB)));
        return result;
    }

    private Map<Integer, String> getTitleAngCommentsFromNewTabMobile(Map<Integer, WebElement> articles) {
        Map<Integer, String> result = new HashMap<>();
        articles.entrySet().forEach(a -> result.putAll(getTitlesAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB_MOBILE)));
        return result;
    }

    private void openInNewTab(WebElement link ) {
        Actions newTab = new Actions(driver);
        newTab.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).click(link).keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).build().perform();
        //((JavascriptExecutor)driver).executeScript("window.open();"); //open new tab
        //switchToTabByIdx(1);
//        driver.get(HOME_PAGE);
//        link.click();

    }

    private String getComments(WebElement article) {
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getText() : "(0)";
    }

    private String getMobileComments(WebElement mobileArticle) {
        List<WebElement> articleMobileComments = mobileArticle.findElements(COMMENT_COUNT_JOINED);
        return articleMobileComments.size() > 0 ? articleMobileComments.get(0).getText() : "(0)";
    }

    private Map<Integer, String> getTitlesAndCommentsFromArticlePage(int idx, WebElement article, By title) {
        Map<Integer, String> result = new HashMap<>();
        openInNewTab(article);
        wait.until(d -> (d.getWindowHandles().size() == 2));
        switchToTabByIdx(1);
        WebElement articleInNewTab = driver.findElement(ARTICLE_IN_NEW_TAB);
        result.put(idx, articleInNewTab.findElement(title).getText() + getComments(articleInNewTab));
        driver.close();
        switchToTabByIdx(0);
        return result;
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

    private List<ArticleReview> getDifferentTitles(Map<Integer, String> titlesWithComments, Map<Integer, String> titlesWithCommentsInArticleTab,
                                                   Map<Integer, String> titlesWithCommentsMobile, Map<Integer, String> titlesWithCommentsMobileInArticleTab) {
        List<ArticleReview> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (!(titlesWithComments.get(i).equals(titlesWithCommentsInArticleTab.get(i)) &&
                  titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsMobile.get(i)) &&
                  titlesWithCommentsMobile.get(i).equals(titlesWithCommentsMobileInArticleTab.get(i)))) {
                result.add(new ArticleReview(i, titlesWithComments.get(i), titlesWithCommentsInArticleTab.get(i), titlesWithCommentsMobile.get(i), titlesWithCommentsMobileInArticleTab.get(i)));
            }
        }

        return result;
    }
}
