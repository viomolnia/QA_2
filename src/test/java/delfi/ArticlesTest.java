package delfi;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.*;
import static utils.Utils.closeAllTabs;
import static utils.Utils.driver;

public class ArticlesTest {

    private static final String HOME_PAGE = "http://rus.delfi.lv/";
    private static final String MOBILE_VERSION = "http://m.rus.delfi.lv/";
    private static final String COMMENTS = ": комментарии";

//    private static final String HOME_PAGE = "http://delfi.lv/";
//    private static final String MOBILE_VERSION = "http://m.delfi.lv/";
//    private static final String COMMENTS = ": komentāri";

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
    private static final By TITLE_COMMENT_PAGE = className("comment-main-title-link");
    private static final By REG_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']");
    private static final By ANON_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']");

    private static final String ZERO = "(0)";

    //Please, set browser name and path to driver in ../resources/test.properties file!
    @Test
    @Description("Check first five articles on Delfi full and mobile versions")
    public void delfiPageTest() throws InterruptedException {
        int counter = 0;
        List<ArticleReview> result;

        Map<Integer, String> articlesMainWeb; //article titles and comments from main page
        Map<Integer, String> articlesTabWeb; //article titles and comments from article's page (after clicking on title)
        Map<Integer, String> articlesCommentWeb; //article titles and comments from comments page (after clicking on comments)

        Map<Integer, String> articlesMainMobile; //article titles and comments from main page of mobile version
        Map<Integer, String> articlesTabMobile; //article titles and comments from article's page (after clicking on title)
        Map<Integer, String> articlesCommentMobile; //article titles and comments from comments page (after clicking on comments)

        List<WebElement> allArticles; //list of all articles from main page
        List<WebElement> allArticlesMobile; //list of all articles from mobile version

        do {
            driver.get(HOME_PAGE);
            driver.manage().window().maximize();

            //get all articles from main page
            allArticles = driver.findElements(TITLE);

            Map<Integer, WebElement> articles = new HashMap<>(); //first 5 articles from main page
            Map<Integer, String> articlesMainWebLinks = new HashMap<>(); //links to article's page for first five articles
            Map<Integer, String> commentsMainWebLinks = new HashMap<>(); //links to comments page for first five articles
            for(int i = 0; i<=4; i++) {
                articles.put(i, allArticles.get(i));
                articlesMainWebLinks.put(i, allArticles.get(i).findElement(TITLE_CLASS).getAttribute("href"));
                commentsMainWebLinks.put(i, getCommentsLink(allArticles.get(i)));
            }

            articlesMainWeb = getTitleAndCommentsFromMainPage(articles); //getting first five titles with comments from main page

            articlesTabWeb = getTitleAndCommentsFromNewTab(articlesMainWebLinks); //getting titles and comments from article's page for first five articles

            articlesCommentWeb = getTitleAndCommentsFromCommentPage(commentsMainWebLinks); //getting titles and comments from comments page for first five articles

            //switching to mobile version
            driver.get(MOBILE_VERSION);

            allArticlesMobile = driver.findElements(ARTICLE_MOBILE); //getting all articles from mobile main page

            Map<Integer, WebElement> articlesMobile = new HashMap<>(); //first five article titles with comments from main mobile page
            Map<Integer, String> articlesMobileLinks = new HashMap<>(); //first five article titles with comments from article's page of mobile version
            Map<Integer, String> commentsMainMobileLinks = new HashMap<>(); //first five articles with comments from comments page of mobile version
            for(int i = 0; i<=4; i++) {
                articlesMobile.put(i, allArticlesMobile.get(i));
                articlesMobileLinks.put(i, allArticlesMobile.get(i).findElement(TITLE_MOBILE).getAttribute("href"));
                commentsMainMobileLinks.put(i, getCommentsLink(allArticlesMobile.get(i)));

            }

            articlesMainMobile = getTitleAndCommentsFromMainPageMobile(articlesMobile); //getting first five titles with comments from main page of mobile version

            articlesTabMobile = getTitleAndCommentsFromNewTabMobile(articlesMobileLinks); //getting titles and comments from article's page for first five articles of mobile version

            articlesCommentMobile = getTitleAndCommentsFromCommentPage(commentsMainMobileLinks); //getting titles and comments from comments page for first five articles of mobile version

            //comparing articles and getting those items, which differ
            result = getDifferentTitles(articlesMainWeb, articlesTabWeb, articlesMainMobile, articlesTabMobile, articlesCommentWeb, articlesCommentMobile);
            counter++;

          //running test one more time if there are differences in articles. But no more, than three times.
        } while(result.size() > 0 && counter < 1);


        if (result.size() > 0) {
            System.out.println("Article numbers, that are not equal: ");
            System.out.println(result); //print articles, that differ
        }

        assertTrue(result.size() == 0);

        closeAllTabs();
    }

    private Map<Integer, String> getTitleAndCommentsFromMainPage(Map<Integer, WebElement> articles) {
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().findElement(TITLE_CLASS).getText() + getComments(e.getValue())));
    }

    private Map<Integer, String> getTitleAndCommentsFromMainPageMobile(Map<Integer, WebElement> articles) {
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().findElement(TITLE_MOBILE).getText() + getMobileComments(e.getValue())));
    }

    private Map<Integer, String> getTitleAndCommentsFromNewTab(Map<Integer, String> articlesLinks) {
        Map<Integer, String> result = new HashMap<>();
        articlesLinks.entrySet().forEach(a -> result.putAll(getTitleAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB)));
        return result;
    }

    private Map<Integer, String> getTitleAndCommentsFromCommentPage(Map<Integer, String> commentsLinks) {
        Map<Integer, String> result = new HashMap<>();
        commentsLinks.entrySet().stream()
                .filter(a -> !a.getValue().equals(ZERO))
                .forEach(a -> result.putAll(getTitleAndCommentsFromCommentPage(a.getKey(), a.getValue(), TITLE_COMMENT_PAGE, REG_COMMENTS, ANON_COMMENTS)));
        return result;
    }

    private Map<Integer, String> getTitleAndCommentsFromNewTabMobile(Map<Integer, String> articlesLink) {
        Map<Integer, String> result = new HashMap<>();
        articlesLink.entrySet().forEach(a -> result.putAll(getTitleAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB_MOBILE)));
        return result;
    }

    private String getComments(WebElement article) {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getText() : ZERO;
    }

    private String getCommentsLink(WebElement article) {
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getAttribute("href") : ZERO;
    }

    private String getMobileComments(WebElement mobileArticle) {
        List<WebElement> articleMobileComments = mobileArticle.findElements(COMMENT_COUNT_JOINED);
        return articleMobileComments.size() > 0 ? articleMobileComments.get(0).getText() : ZERO;
    }

    private Map<Integer, String> getTitleAndCommentsFromArticlePage(int idx, String articleTitle, By title) {
        Map<Integer, String> result = new HashMap<>();
        driver.get(articleTitle);

        //there was an age check pop-up shown for a few articles
        skipAgeCheck();
        WebElement articleInNewTab = driver.findElement(ARTICLE_IN_NEW_TAB);
        result.put(idx, articleInNewTab.findElement(title).getText() + getComments(articleInNewTab));
        return result;
    }

    private void skipAgeCheck() {
        if (driver.findElements(className("yrscheck-yes-input")).size() > 0) {
            driver.findElements(className("yrscheck-yes-input")).get(0).click();
        }
    }

    private Map<Integer, String> getTitleAndCommentsFromCommentPage(int idx, String commentLink, By title, By reg, By anon) {
        Map<Integer, String> result = new HashMap<>();

        driver.get(commentLink);
        String fullTitle = driver.findElement(title).getText();
        String titleName = fullTitle.indexOf(COMMENTS) > 0 ? fullTitle.substring(0, fullTitle.indexOf(COMMENTS)) : fullTitle;
        String regComments = driver.findElement(reg).getText();
        String anonComments = driver.findElement(anon).getText();
        int regCommentsValue = Integer.parseInt(regComments.substring(regComments.indexOf('(')+1, regComments.indexOf(')')));
        int anonCommentsValue = Integer.parseInt(anonComments.substring(anonComments.indexOf('(')+1, anonComments.indexOf(')')));
        int totalComments = regCommentsValue + anonCommentsValue;

        result.put(idx, titleName + "(" + totalComments + ")");
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

    //we compare all six lists with articles
    private List<ArticleReview> getDifferentTitles(Map<Integer, String> titlesWithComments, Map<Integer, String> titlesWithCommentsInArticleTab,
                                                   Map<Integer, String> titlesWithCommentsMobile, Map<Integer, String> titlesWithCommentsMobileInArticleTab,
                                                   Map<Integer, String> titlesWithCommentsComment, Map<Integer, String> titlesWithCommentsCommentMobile) {
        List<ArticleReview> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            //if there are no comments for article, than there are no title and comments from comments page,
            //so we check if an article has zero comments and null on comments page
            if (getCommentsFromArticle(titlesWithComments.get(i)) == 0 && getCommentsFromArticle(titlesWithCommentsInArticleTab.get(i)) == 0 &&
                getCommentsFromArticle(titlesWithCommentsMobile.get(i)) == 0 && getCommentsFromArticle(titlesWithCommentsMobileInArticleTab.get(i)) == 0
                && titlesWithCommentsComment.get(i) == null && titlesWithCommentsCommentMobile.get(i) == null    ) {

                //if there are some mismatches, add an article to result
                if (!(titlesWithComments.get(i).equals(titlesWithCommentsInArticleTab.get(i)) &&
                        titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsMobile.get(i)) &&
                        titlesWithCommentsMobile.get(i).equals(titlesWithCommentsMobileInArticleTab.get(i)))) {

                        result.add(new ArticleReview(i, titlesWithComments.get(i), titlesWithCommentsInArticleTab.get(i),
                                titlesWithCommentsMobile.get(i), titlesWithCommentsMobileInArticleTab.get(i),
                                titlesWithCommentsComment.get(i), titlesWithCommentsCommentMobile.get(i)));
                }
            } else {

                //if an article has some comments, we check all the maps with articles
                if (!(titlesWithComments.get(i).equals(titlesWithCommentsInArticleTab.get(i)) &&
                  titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsMobile.get(i)) &&
                  titlesWithCommentsMobile.get(i).equals(titlesWithCommentsMobileInArticleTab.get(i)) &&
                  titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsComment.get(i)) &&
                  titlesWithCommentsComment.get(i).equals(titlesWithCommentsCommentMobile.get(i)))) {

                    //i there are some mismatches, we add an article to result.
                    result.add(new ArticleReview(i, titlesWithComments.get(i), titlesWithCommentsInArticleTab.get(i),
                            titlesWithCommentsMobile.get(i), titlesWithCommentsMobileInArticleTab.get(i),
                            titlesWithCommentsComment.get(i), titlesWithCommentsCommentMobile.get(i)));
                }
            }
        }

        return result;
    }

    private int getCommentsFromArticle(String article) {
        String reversedTitle = new StringBuilder(article).reverse().toString();
        String comments = reversedTitle.substring(reversedTitle.indexOf(')') + 1, reversedTitle.indexOf('('));
        return Integer.parseInt(new StringBuilder(comments).reverse().toString());
    }

}
