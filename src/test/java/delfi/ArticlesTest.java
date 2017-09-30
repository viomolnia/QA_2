package delfi;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry;
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
    private static final By TITLE2 = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TITLE3 = xpath(".//div[contains(@class, 'content-half-block')]");
    private static final By TITLE4 = xpath(".//div[contains(@class, 'content-third-block')]");
    private static final By TITLE5 = xpath(".//div[contains(@class, 'content-twothirds-block')]");
    private static final By TITLE6 = xpath(".//div[contains(@class, 'article-full-image')]");
    private static final By TITLE7 = xpath(".//div[contains(@class, 'article-link publishTime')]");
    private static final By TITLE_CLASS = className("top2012-title");
    private static final By TITLE_CLASS2 = className("article-title");
    private static final By TITLE_MOBILE = className("md-scrollpos");
    private static final By TITLE_ON_TAB = xpath("//span[@itemprop='headline name']");
    private static final By TITLE_ON_TAB_MOBILE = tagName("h1");
    private static final By ARTICLE_IN_NEW_TAB = xpath("//*[contains(@class, 'article-title')]");
    private static final By ARTICLE_IN_NEW_TAB2 = xpath("//*[contains(@class, 'comments-about-title')]");
    private static final By ARTICLE_MOBILE = xpath(".//div[@class='md-mosaic-title']");
    private static final By ARTICLE_MOBILE2 = xpath(".//div[@class='md-specialblock-headline-title']");
    private static final By TITLE_COMMENT_PAGE = className("comment-main-title-link");
    private static final By REG_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']");
    private static final By ANON_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']");
    private static final String ZERO = "(0)";

    //final String DEFINED_TITLE = "В авариях в Латвии пострадали 18 человек";
    static final String DEFINED_TITLE = "Демограф: Латвия экономит на молодых и многодетных семьях";
    int size = 1;

    static Map<Integer, Map<String, Integer>> articlesMainWeb = new HashMap<>(); //article titles and comments from main page
    static Map<Integer, Map<String, Integer>> articlesTabWeb; //article titles and comments from article's page (after clicking on title)
    static Map<Integer, Map<String, Integer>> articlesCommentWeb; //article titles and comments from comments page (after clicking on comments)

    static Map<Integer, Map<String, Integer>> articlesMainMobile = null; //article titles and comments from main page of mobile version
    static Map<Integer, Map<String, Integer>> articlesTabMobile; //article titles and comments from article's page (after clicking on title)
    static Map<Integer, Map<String, Integer>> articlesCommentMobile; //article titles and comments from comments page (after clicking on comments)

    static List<WebElement> allArticles; //list of all articles from main page
    static List<WebElement> allArticles2; //list of all articles from main page
    static List<WebElement> allArticles3; //list of all articles from main page
    static List<WebElement> allArticles4; //list of all articles from main page
    static List<WebElement> allArticles5; //list of all articles from main page
    static List<WebElement> allArticles6; //list of all articles from main page
    static List<WebElement> allArticles7; //list of all articles from main page
    static List<WebElement> allArticlesMobile; //list of all articles from mobile version
    static List<WebElement> allArticlesMobile2; //list of all articles from mobile version

    static Map<Integer, WebElement> articles = new HashMap<>(); //first 5 articles from main page
    static Map<Integer, String> articlesMainWebLinks = new HashMap<>(); //links to article's page for first five articles
    static Map<Integer, String> commentsMainWebLinks = new HashMap<>(); //links to comments page for first five articles

    //Please, set browser name and path to driver in ../resources/test.properties file!
    @Test
    @Description("Check first five articles on Delfi full and mobile versions")
    public void delfiPageTest() throws InterruptedException {
        int counter = 0;
        List<ArticleReview> result;
        int size = 5;

        Map<Integer, Map<String, Integer>> articlesMainWeb; //article titles and comments from main page
        Map<Integer, Map<String, Integer>> articlesTabWeb; //article titles and comments from article's page (after clicking on title)
        Map<Integer, Map<String, Integer>> articlesCommentWeb; //article titles and comments from comments page (after clicking on comments)

        Map<Integer, Map<String, Integer>> articlesMainMobile; //article titles and comments from main page of mobile version
        Map<Integer, Map<String, Integer>> articlesTabMobile; //article titles and comments from article's page (after clicking on title)
        Map<Integer, Map<String, Integer>> articlesCommentMobile; //article titles and comments from comments page (after clicking on comments)

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
            for(int i = 0; i<size; i++) {
                articles.put(i, allArticles.get(i));
                articlesMainWebLinks.put(i, allArticles.get(i).findElement(TITLE_CLASS).getAttribute("href"));
                commentsMainWebLinks.put(i, getCommentsLink(allArticles.get(i)));
            }

            articlesMainWeb = getTitleAndCommentsFromMainPage(articles, TITLE_CLASS); //getting first five titles with comments from main page

            articlesTabWeb = getTitleAndCommentsFromNewTab(articlesMainWebLinks); //getting titles and comments from article's page for first five articles

            articlesCommentWeb = getTitleAndCommentsFromCommentPage(commentsMainWebLinks); //getting titles and comments from comments page for first five articles

            //switching to mobile version
            driver.get(MOBILE_VERSION);

            allArticlesMobile = driver.findElements(ARTICLE_MOBILE); //getting all articles from mobile main page

            Map<Integer, WebElement> articlesMobile = new HashMap<>(); //first five article titles with comments from main mobile page
            Map<Integer, String> articlesMobileLinks = new HashMap<>(); //first five article titles with comments from article's page of mobile version
            Map<Integer, String> commentsMainMobileLinks = new HashMap<>(); //first five articles with comments from comments page of mobile version
            for(int i = 0; i<size; i++) {
                articlesMobile.put(i, allArticlesMobile.get(i));
                articlesMobileLinks.put(i, allArticlesMobile.get(i).findElement(TITLE_MOBILE).getAttribute("href"));
                commentsMainMobileLinks.put(i, getCommentsLink(allArticlesMobile.get(i)));

            }

            articlesMainMobile = getTitleAndCommentsFromMainPageMobile(articlesMobile, TITLE_MOBILE); //getting first five titles with comments from main page of mobile version

            articlesTabMobile = getTitleAndCommentsFromNewTabMobile(articlesMobileLinks); //getting titles and comments from article's page for first five articles of mobile version

            articlesCommentMobile = getTitleAndCommentsFromCommentPage(commentsMainMobileLinks); //getting titles and comments from comments page for first five articles of mobile version

            //comparing articles and getting those items, which differ
            result = getDifferentTitles(articlesMainWeb, articlesTabWeb, articlesMainMobile, articlesTabMobile, articlesCommentWeb, articlesCommentMobile, size);
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

    @Test
    @Description("Check article by name defined in constant")
    public void articleByNameTest() throws InterruptedException {
        int counter = 0;
        List<ArticleReview> result;

        do {
            driver.get(HOME_PAGE);
            driver.manage().window().maximize();

            //get all articles from main page with different types of titles
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");
            allArticles = driver.findElements(TITLE);
            allArticles2 = driver.findElements(TITLE2);
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");
            allArticles3 = driver.findElements(TITLE3);
            allArticles4 = driver.findElements(TITLE4);
            allArticles5 = driver.findElements(TITLE5);
            allArticles6 = driver.findElements(TITLE6);
            allArticles7 = driver.findElements(TITLE7);

            //find article by title and update articlesMainWebLinks, commentsMainWebLinks, articlesMainWeb
            updateArticles(allArticles, TITLE_CLASS);
            updateArticles(allArticles2, TITLE_CLASS2);
            updateArticles(allArticles3, TITLE_CLASS2);
            updateArticles(allArticles4, TITLE_CLASS2);
            updateArticles(allArticles5, TITLE_CLASS2);
            updateArticles(allArticles6, TITLE_CLASS2);
            updateArticles(allArticles7, TITLE_CLASS2);

            assertTrue(articles.size() == 1);

            articlesTabWeb = getTitleAndCommentsFromNewTab(articlesMainWebLinks); //getting titles and comments from article's page

            articlesCommentWeb = getTitleAndCommentsFromCommentPage(commentsMainWebLinks); //getting titles and comments from comments page

            //switching to mobile version
            driver.get(MOBILE_VERSION);

            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");

            //getting all articles from mobile main page for different types of titles
            allArticlesMobile = driver.findElements(ARTICLE_MOBILE);
            allArticlesMobile2 = driver.findElements(ARTICLE_MOBILE2);

            Map<Integer, WebElement> articlesMobile = new HashMap<>(); //title with comments from main mobile page
            Map<Integer, String> articlesMobileLinks = new HashMap<>(); //title with comments from article's page of mobile version
            Map<Integer, String> commentsMainMobileLinks = new HashMap<>(); //article with comments from comments page of mobile version

            //find article by title and update articlesMobileLinks, commentsMainMobileLinks, articlesMainMobile
            for (int i = 0; i < allArticlesMobile.size(); i++) {
                if(allArticlesMobile.get(i).findElement(TITLE_MOBILE).getText().equals(DEFINED_TITLE)) {
                    articlesMobile.put(0, allArticlesMobile.get(i));
                    articlesMobileLinks.put(0, allArticlesMobile.get(i).findElement(TITLE_MOBILE).getAttribute("href"));
                    commentsMainMobileLinks.put(0, getCommentsLink(allArticlesMobile.get(i)));
                    articlesMainMobile = getTitleAndCommentsFromMainPageMobile(articlesMobile, TITLE_MOBILE); //getting first five titles with comments from main page of mobile version
                }
            }

            for (int i = 0; i < allArticlesMobile2.size(); i++) {
                if(allArticlesMobile2.get(i).findElements(tagName("a")).get(0).getText().equals(DEFINED_TITLE)) {
                    articlesMobile.put(0, allArticlesMobile2.get(i));
                    articlesMobileLinks.put(0, allArticlesMobile2.get(i).findElements(tagName("a")).get(1).getAttribute("href"));
                    commentsMainMobileLinks.put(0, getCommentsLink(allArticlesMobile2.get(i)));
                    articlesMainMobile = getTitleAndCommentsFromMainPageMobile(articlesMobile); //getting first five titles with comments from main page of mobile version

                }
            }

            assertTrue(articlesMobile.size() == 1);

            articlesTabMobile = getTitleAndCommentsFromNewTabMobile(articlesMobileLinks); //getting title and comments from article's page of mobile version

            articlesCommentMobile = getTitleAndCommentsFromCommentPage(commentsMainMobileLinks); //getting titles and comments from comments page of mobile version

            //comparing articles and getting those items, which differ
            result = getDifferentTitles(articlesMainWeb, articlesTabWeb, articlesMainMobile, articlesTabMobile, articlesCommentWeb, articlesCommentMobile, size);
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

    private void updateArticles(List<WebElement> allArticles, By title) {
        allArticles.stream().filter(allArticle -> allArticle.findElement(title).getText().equals(DEFINED_TITLE)).forEach(allArticle -> {
            assertTrue(articles.size() == 0);
            articles.put(0, allArticle);
            articlesMainWebLinks.put(0, allArticle.findElement(title).getAttribute("href"));
            commentsMainWebLinks.put(0, getCommentsLink(allArticle));
            articlesMainWeb = getTitleAndCommentsFromMainPage(articles, title);
        });
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromMainPage(Map<Integer, WebElement> articles, By locator) {
        return articles.entrySet().stream()
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.toMap(e ->e.getValue().findElement(locator).getText(), e -> getComments(e.getValue()))));
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromMainPageMobile(Map<Integer, WebElement> articles, By title) {
        return articles.entrySet().stream()
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.toMap(e -> e.getValue().findElement(title).getText(), e -> getComments(e.getValue()))));
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromMainPageMobile(Map<Integer, WebElement> articles) {
        return articles.entrySet().stream()
                .collect(Collectors.groupingBy(Entry::getKey, Collectors.toMap(e -> e.getValue().findElements(tagName("a")).get(0).getText(), e -> getComments(e.getValue()))));
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromNewTab(Map<Integer, String> articlesLinks) {
        Map<Integer, Map<String, Integer>> result = new HashMap<>();
        articlesLinks.entrySet().forEach(a -> result.putAll(getTitleAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB)));
        return result;
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromCommentPage(Map<Integer, String> commentsLinks) {
        Map<Integer, Map<String, Integer>> result = new HashMap<>();
        commentsLinks.entrySet().stream()
                .filter(a -> !a.getValue().equals(ZERO))
                .forEach(a -> result.putAll(getTitleAndCommentsFromCommentPage(a.getKey(), a.getValue(), TITLE_COMMENT_PAGE, REG_COMMENTS, ANON_COMMENTS)));
        return result;
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromNewTabMobile(Map<Integer, String> articlesLink) {
        Map<Integer, Map<String, Integer>> result = new HashMap<>();
        articlesLink.entrySet().forEach(a -> result.putAll(getTitleAndCommentsFromArticlePage(a.getKey(), a.getValue(), TITLE_ON_TAB_MOBILE)));
        return result;
    }

    private Integer getComments(WebElement article) {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        String result = comments.size() > 0 ? comments.get(0).getText() : ZERO;
        result = result.substring(result.indexOf('(') + 1, result.indexOf(')'));
        return Integer.parseInt(result);
    }

    private String getCommentsLink(WebElement article) {
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getAttribute("href") : ZERO;
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromArticlePage(int idx, String articleTitle, By title) {
        Map<Integer, Map<String, Integer>> result = new HashMap<>();
        driver.get(articleTitle);

        //there was an age check pop-up shown for a few articles
        skipAgeCheck();
        WebElement articleInNewTab;

        if (driver.findElements(ARTICLE_IN_NEW_TAB).size() > 0) {
            articleInNewTab = driver.findElement(ARTICLE_IN_NEW_TAB);
        } else {
            articleInNewTab = driver.findElement(ARTICLE_IN_NEW_TAB2);
        }

        String titleText;
        if (articleInNewTab.findElements(title).size() > 0) {
            titleText = articleInNewTab.findElement(title).getText();
        } else {
            titleText = articleInNewTab.getText();
        }
        result.put(idx, new HashMap<String, Integer>(){{ put(titleText, getComments(articleInNewTab));}});
        return result;
    }

    private void skipAgeCheck() {
        if (driver.findElements(className("yrscheck-yes-input")).size() > 0) {
            driver.findElements(className("yrscheck-yes-input")).get(0).click();
        }
    }

    private Map<Integer, Map<String, Integer>> getTitleAndCommentsFromCommentPage(int idx, String commentLink, By title, By reg, By anon) {
        Map<Integer, Map<String, Integer>> result = new HashMap<>();

        driver.get(commentLink);
        String fullTitle = driver.findElement(title).getText();
        String titleName = fullTitle.indexOf(COMMENTS) > 0 ? fullTitle.substring(0, fullTitle.indexOf(COMMENTS)) : fullTitle;
        String regComments = driver.findElement(reg).getText();
        String anonComments = driver.findElement(anon).getText();
        int regCommentsValue = Integer.parseInt(regComments.substring(regComments.indexOf('(')+1, regComments.indexOf(')')));
        int anonCommentsValue = Integer.parseInt(anonComments.substring(anonComments.indexOf('(')+1, anonComments.indexOf(')')));
        int totalComments = regCommentsValue + anonCommentsValue;

        result.put(idx, new HashMap<String, Integer>(){{ put(titleName , totalComments);}});
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
    private List<ArticleReview> getDifferentTitles(Map<Integer, Map<String, Integer>> titlesWithComments, Map<Integer, Map<String, Integer>> titlesWithCommentsInArticleTab,
                                                   Map<Integer, Map<String, Integer>> titlesWithCommentsMobile, Map<Integer, Map<String, Integer>> titlesWithCommentsMobileInArticleTab,
                                                   Map<Integer, Map<String, Integer>> titlesWithCommentsComment, Map<Integer, Map<String, Integer>> titlesWithCommentsCommentMobile, int size) {
        List<ArticleReview> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            //if there are no comments for article, than there are no title and comments from comments page,
            //so we check if an article has zero comments and null on comments page
            if (hasZeroComments(titlesWithComments.get(i)) && hasZeroComments(titlesWithCommentsInArticleTab.get(i)) &&
                hasZeroComments(titlesWithCommentsMobile.get(i)) && hasZeroComments(titlesWithCommentsMobileInArticleTab.get(i)) &&
                 titlesWithCommentsComment.get(i) == null && titlesWithCommentsCommentMobile.get(i) == null    ) {

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

    private boolean hasZeroComments(Map<String, Integer> pair) {
        return pair.values().stream().findFirst().get() == 0;
    }
}
