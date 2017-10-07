package delfi;

import com.sun.org.glassfish.gmbal.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.tagName;

public class ArticlesTest {

    private static final Logger LOGGER = LogManager.getLogger(ArticlesTest.class);

    private static final String DEFINED_TITLE = "Латвийским переработчикам не хватает молока, конкуренция очень жесткая";
    private int size = 1;

    //Please, set browser name and path to driver in ../resources/test.properties file!
    @Test
    @Description("Check first five articles on Delfi full and mobile versions")
    public void delfiPageTest() throws InterruptedException {
        LOGGER.info("Test checking first 5 Deli articles started...");
        BaseFunctionsDelfi base = new BaseFunctionsDelfi();
        MainPageWeb mainPage = new MainPageWeb();
        ArticlePageWeb articlePageWeb = new ArticlePageWeb();
        CommentsPageWeb commentsPageWeb = new CommentsPageWeb();

        CommentsPageCommon commentsPageCommon = new CommentsPageCommon();
        ArticlePageCommon articlePageCommon = new ArticlePageCommon();

        MainPageMobile mainPageMobile = new MainPageMobile();
        ArticlePageMobile articlePageMobile = new ArticlePageMobile();
        CommentsPageMobile commentsPageMobile = new CommentsPageMobile();

        int counter = 0;
        List<ArticleReview> result;
        int size = 5;

        List<WebElement> allArticles; //list of all articles from main page
        List<WebElement> allArticlesMobile; //list of all articles from mobile version

        do {
            base.goToUrl(BaseFunctionsDelfi.HOME_PAGE);

            //get all articles from main page
            allArticles = base.getDriver().findElements(MainPageWeb.TITLE);

            Map<Integer, WebElement> firstChosenArticles = new HashMap<>(); //first 5 articles from main page
            Map<Integer, String> commentsMainWebLinks = new HashMap<>(); //links to comments page for first five articles
            for(int i = 0; i < size; i++) {
                firstChosenArticles.put(i, allArticles.get(i));
                mainPage.getLinksToArticlesPages().put(i, allArticles.get(i).findElement(MainPageWeb.TITLE_CLASS1).getAttribute("href"));
                commentsMainWebLinks.put(i, base.getCommentsLink(allArticles.get(i), base));
            }

            //Saving titles and comments from main page web
            mainPage.setArticles(mainPage.getTitleAndComments(firstChosenArticles, MainPageWeb.TITLE_CLASS1, base, commentsPageCommon)); //getting first five titles with comments from main page

            //Saving titles and comments from article's page web
            articlePageWeb.setArticles(articlePageCommon.getTitlesAndComments(mainPage.getLinksToArticlesPages(), base.getDriver(), base, commentsPageCommon, articlePageCommon)); //getting titles and comments from article's page for first five articles

            //Saving titles and comments from comment's page web
            commentsPageWeb.setArticles(commentsPageCommon.getTitleAndComments(commentsMainWebLinks, base.getDriver(), commentsPageCommon)); //getting titles and comments from comments page for first five articles

            //switching to mobile version
            base.getDriver().get(BaseFunctionsDelfi.MOBILE_VERSION);

            allArticlesMobile = base.getDriver().findElements(ArticlePageMobile.ARTICLE1); //getting all articles from mobile main page

            Map<Integer, WebElement> firstChosenArticlesMobile = new HashMap<>(); //first five article titles with comments from main mobile page
            Map<Integer, String> articlesMobileLinks = new HashMap<>(); //first five article titles with comments from article's page of mobile version
            Map<Integer, String> commentsMainMobileLinks = new HashMap<>(); //first five articles with comments from comments page of mobile version
            for(int i = 0; i<size; i++) {
                firstChosenArticlesMobile.put(i, allArticlesMobile.get(i));
                articlesMobileLinks.put(i, allArticlesMobile.get(i).findElement(MainPageMobile.TITLE).getAttribute("href"));
                commentsMainMobileLinks.put(i, base.getCommentsLink(allArticlesMobile.get(i), base));

            }

            //Saving titles and comments from main page mobile
            mainPageMobile.setArtticles(mainPageMobile.getTitleAndComments(firstChosenArticlesMobile, MainPageMobile.TITLE, base, commentsPageCommon)); //getting first five titles with comments from main page of mobile version

            //Saving titles and comments from article's page mobile
            articlePageMobile.setArticles(articlePageMobile.getTitleAndComments(articlesMobileLinks, base.getDriver(), base, commentsPageCommon, articlePageCommon)); //getting titles and comments from article's page for first five articles of mobile version

            //Saving titles and comments from comment's page mobile
            commentsPageMobile.setArticles(commentsPageCommon.getTitleAndComments(commentsMainMobileLinks, base.getDriver(), commentsPageCommon)); //getting titles and comments from comments page for first five articles of mobile version

            //comparing articles and getting those items, which differ
            result = base.getDifferentTitles(mainPage.getArticles(), articlePageWeb.getArticles(), mainPageMobile.getArtticles(), articlePageMobile.getArticles(), commentsPageWeb.getArticles(), commentsPageMobile.getArticles(), size);
            counter++;

          //running test one more time if there are differences in articles. But no more, than three times.
        } while(result.size() > 0 && counter < 1);


        if (result.size() > 0) {
            System.out.println("Article numbers, that are not equal: ");
            System.out.println(result); //print articles, that differ
        }

        assertTrue(result.size() == 0);

        base.closeAllTabs();
        LOGGER.info("Test finished.");
    }

    @Test
    @Description("Check article by name defined in constant")
    public void articleByNameTest() throws InterruptedException {
        LOGGER.info("Test checking article by title name started...");
        int counter = 0;
        List<ArticleReview> result;
        BaseFunctionsDelfi base = new BaseFunctionsDelfi();
        MainPageWeb mainPage = new MainPageWeb();
        ArticlePageWeb articlePageWeb = new ArticlePageWeb();
        CommentsPageWeb commentsPageWeb = new CommentsPageWeb();

        CommentsPageCommon commentsPageCommon = new CommentsPageCommon();
        ArticlePageCommon articlePageCommon = new ArticlePageCommon();

        MainPageMobile mainPageMobile = new MainPageMobile();
        ArticlePageMobile articlePageMobile = new ArticlePageMobile();
        CommentsPageMobile commentsPageMobile = new CommentsPageMobile();
        Map<Integer, String> commentsMainWebLinks = new HashMap<>();

        do {
            base.getDriver().get(BaseFunctionsDelfi.HOME_PAGE);
            base.getDriver().manage().window().maximize();

            //get all articles from main page with different types of titles
            ((JavascriptExecutor) base.getDriver())
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");
            mainPage.setAllArticles1(base.getDriver().findElements(MainPageWeb.TITLE));
            mainPage.setAllArticles2(base.getDriver().findElements(MainPageWeb.TITLE2));
            ((JavascriptExecutor) base.getDriver())
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");
            mainPage.setAllArticles3(base.getDriver().findElements(MainPageWeb.TITLE3));
            mainPage.setAllArticles4(base.getDriver().findElements(MainPageWeb.TITLE4));
            mainPage.setAllArticles5(base.getDriver().findElements(MainPageWeb.TITLE5));
            mainPage.setAllArticles6(base.getDriver().findElements(MainPageWeb.TITLE6));
            mainPage.setAllArticles7(base.getDriver().findElements(MainPageWeb.TITLE7));

            //find article by title and update articlesMainWebLinks, commentsMainWebLinks, articlesMainWeb
            updateArticles(mainPage.getAllArticles1(), MainPageWeb.TITLE_CLASS1, mainPage, base, commentsPageCommon, commentsMainWebLinks);
            updateArticles(mainPage.getAllArticles2(), MainPageWeb.TITLE_CLASS2, mainPage, base, commentsPageCommon, commentsMainWebLinks);
            updateArticles(mainPage.getAllArticles3(), MainPageWeb.TITLE_CLASS2, mainPage, base, commentsPageCommon, commentsMainWebLinks);
            updateArticles(mainPage.getAllArticles4(), MainPageWeb.TITLE_CLASS2, mainPage, base, commentsPageCommon, commentsMainWebLinks);
            updateArticles(mainPage.getAllArticles5(), MainPageWeb.TITLE_CLASS2, mainPage, base, commentsPageCommon, commentsMainWebLinks);
            updateArticles(mainPage.getAllArticles6(), MainPageWeb.TITLE_CLASS2, mainPage, base, commentsPageCommon, commentsMainWebLinks);
            updateArticles(mainPage.getAllArticles7(), MainPageWeb.TITLE_CLASS2, mainPage, base, commentsPageCommon, commentsMainWebLinks);

            assertTrue(mainPage.getArticles().size() == 1);

            articlePageWeb.setArticles(articlePageCommon.getTitlesAndComments(mainPage.getLinksToArticlesPages(), base.getDriver(), base, commentsPageCommon, articlePageCommon)); //getting titles and comments from article's page

            commentsPageWeb.setArticles(commentsPageCommon.getTitleAndComments(commentsMainWebLinks, base.getDriver(), commentsPageCommon)); //getting titles and comments from comments page

            //switching to mobile version
            base.getDriver().get(BaseFunctionsDelfi.MOBILE_VERSION);

            ((JavascriptExecutor) base.getDriver())
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");

            //getting all articles from mobile main page for different types of titles
            List<WebElement> allArticlesMobile = base.getDriver().findElements(ArticlePageMobile.ARTICLE1);
            List<WebElement> allArticlesMobile2 = base.getDriver().findElements(ArticlePageMobile.ARTICLE2);

            Map<Integer, WebElement> articlesMobile = new HashMap<>();
            Map<Integer, String> articlesMobileLinks = new HashMap<>();
            Map<Integer, String> commentsMainMobileLinks = new HashMap<>();

            //find article by title and update articlesMobileLinks, commentsMainMobileLinks, articlesMainMobile
            for (WebElement anAllArticlesMobile : allArticlesMobile) {
                if (anAllArticlesMobile.findElement(MainPageMobile.TITLE).getText().equals(DEFINED_TITLE)) {
                    articlesMobile.put(0, anAllArticlesMobile);
                    articlesMobileLinks.put(0, anAllArticlesMobile.findElement(MainPageMobile.TITLE).getAttribute("href"));
                    commentsMainMobileLinks.put(0, base.getCommentsLink(anAllArticlesMobile, base));
                    mainPageMobile.setArtticles(mainPageMobile.getTitleAndComments(articlesMobile, MainPageMobile.TITLE, base, commentsPageCommon)); //getting first five titles with comments from main page of mobile version
                }
            }

            for (WebElement anAllArticlesMobile2 : allArticlesMobile2) {
                if (anAllArticlesMobile2.findElements(tagName("a")).get(0).getText().equals(DEFINED_TITLE)) {
                    articlesMobile.put(0, anAllArticlesMobile2);
                    articlesMobileLinks.put(0, anAllArticlesMobile2.findElements(tagName("a")).get(1).getAttribute("href"));
                    commentsMainMobileLinks.put(0, base.getCommentsLink(anAllArticlesMobile2, base));
                    mainPageMobile.setArtticles(mainPageMobile.getTitleAndComments(articlesMobile, base, commentsPageCommon)); //getting first five titles with comments from main page of mobile version

                }
            }

            assertTrue(articlesMobile.size() == 1);

            articlePageMobile.setArticles(articlePageMobile.getTitleAndComments(articlesMobileLinks, base.getDriver(), base, commentsPageCommon, articlePageCommon)); //getting title and comments from article's page of mobile version

            commentsPageMobile.setArticles(commentsPageCommon.getTitleAndComments(commentsMainMobileLinks, base.getDriver(), commentsPageCommon)); //getting titles and comments from comments page of mobile version

            //comparing articles and getting those items, which differ
            result = base.getDifferentTitles(mainPage.getArticles(), articlePageWeb.getArticles(),
                                        mainPageMobile.getArtticles(), articlePageMobile.
                                        getArticles(), commentsPageWeb.getArticles(),
                                        commentsPageMobile.getArticles(), size);
            counter++;

        //running test one more time if there are differences in articles. But no more, than three times.
        } while(result.size() > 0 && counter < 1);


        if (result.size() > 0) {
            System.out.println("Article numbers, that are not equal: ");
            System.out.println(result); //print articles, that differ
        }

        assertTrue(result.size() == 0);

        base.closeAllTabs();

        LOGGER.info("Test finished.");
    }

    private void updateArticles(List<WebElement> allArticles, By title, MainPageWeb mainPage, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon, Map<Integer, String> commentsMainWebLinks) {
        allArticles.stream().filter(allArticle -> allArticle.findElement(title).getText().equals(DEFINED_TITLE)).forEach(allArticle -> {
            assertTrue(MainPageWeb.matchingArticles.size() == 0);
            MainPageWeb.matchingArticles.put(0, allArticle);
            mainPage.getLinksToArticlesPages().put(0, allArticle.findElement(title).getAttribute("href"));
            commentsMainWebLinks.put(0, base.getCommentsLink(allArticle, base));
            mainPage.setArticles(mainPage.getTitleAndComments(MainPageWeb.matchingArticles, title, base, commentsPageCommon));
        });
    }
}
