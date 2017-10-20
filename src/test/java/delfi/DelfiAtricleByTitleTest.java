package delfi;

import com.sun.org.glassfish.gmbal.Description;
import delfi.helpers.TestUtilsHelper;
import delfi.models.Article;
import delfi.models.ArticleReview;
import delfi.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class DelfiAtricleByTitleTest {
    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = LogManager.getLogger(DelfiAtricleByTitleTest.class);
    private static final String DELFI_MAIN_PAGE_WEB_URL = "http://rus.delfi.lv";
    private static final String DELFI_MAIN_PAGE_MOBILE_URL = "http://m.rus.delfi.lv";
    private static final String DEFINED_TITLE = "Валдис Валтерс о себе, патриотизме и тусовщиках";

    @Test
    @Description("Check article by name defined in constant")
    public void delfiPageObjectTest() {
        List<ArticleReview> result;
        TestUtilsHelper testUtilsHelper = new TestUtilsHelper();
        int rounds = 0;
        int precision = 3;

        do {
            MainPageWeb mainPageWeb = new MainPageWeb(baseFunctions);
            ArticlePageWeb articlePageWeb = new ArticlePageWeb(baseFunctions);
            CommentsPageWeb commentsPageWeb = new CommentsPageWeb(baseFunctions);

            MainPageMobile mainPageMobile = new MainPageMobile(baseFunctions);
            ArticlePageMobile articlePageMobile = new ArticlePageMobile(baseFunctions);
            CommentsPageMobile commentsPageMobile = new CommentsPageMobile(baseFunctions);

            LOGGER.info("Open main page on web version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_WEB_URL);

            LOGGER.info("Get all articles from web main page");
            List<WebElement> allArticlesWeb = new ArrayList<>();

            allArticlesWeb.addAll(mainPageWeb.extractArticles1());
            allArticlesWeb.addAll(mainPageWeb.extractArticles2());
            allArticlesWeb.addAll(mainPageWeb.extractArticles3());
            allArticlesWeb.addAll(mainPageWeb.extractArticles4());
            allArticlesWeb.addAll(mainPageWeb.extractArticles5());
            allArticlesWeb.addAll(mainPageWeb.extractArticles6());
            allArticlesWeb.addAll(mainPageWeb.extractArticles7());

            LOGGER.info("Get article from web main page by title defined in constant variable");
            Map<Integer, WebElement> matchingArticlesWeb = mainPageWeb.extractArticlesMatchingByTitle(allArticlesWeb, DEFINED_TITLE);

            LOGGER.info("Assert that there is exactly one article matching defined title");
            assertTrue(matchingArticlesWeb.size() == 1);

            LOGGER.info("Get links to article's web page and comments web page");
            Map<Integer, String> linksToArticlesPages = mainPageWeb.extractLinksToArticlePagesByTitle(allArticlesWeb, DEFINED_TITLE);
            Map<Integer, String> commentsMainWebLinks = mainPageWeb.extractLinksToCommentsPageByTitle(allArticlesWeb, DEFINED_TITLE);

            LOGGER.info("Get articles and titles from web main page, article web page and comments web page");
            Map<Integer, Article> articlesFromMainPage = mainPageWeb.extractTitleWithComments(allArticlesWeb, DEFINED_TITLE, matchingArticlesWeb);
            Map<Integer, Article> articlesFromArticlePage = articlePageWeb.getTitlesAndComments(linksToArticlesPages);
            Map<Integer, Article> articlesFromCommentsPage = commentsPageWeb.getTitleAndComments(commentsMainWebLinks);

            LOGGER.info("Open main page on mobile version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_MOBILE_URL);

            List<WebElement> allArticlesMobile = new ArrayList<>();
            allArticlesMobile.addAll(mainPageMobile.extractArticles1());
            allArticlesMobile.addAll(mainPageMobile.extractArticles2());

            LOGGER.info("Get article from mobile main page by title defined in constant variable");
            Map<Integer, WebElement> matchingArticlesMobile = mainPageMobile.extractArticlesMatchingByTitle(allArticlesMobile, DEFINED_TITLE);

            LOGGER.info("Assert that there is exactly one article matching defined title");
            assertTrue(matchingArticlesMobile.size() == 1);

            LOGGER.info("Get links to article's web page and comments web page");
            Map<Integer, String> linksToArticlesPagesMobile = mainPageMobile.extractLinksToArticlesMobileByTitle(allArticlesMobile, DEFINED_TITLE);
            Map<Integer, String> commentsMainWebLinksMobile = mainPageMobile.extractLinksToCommentsPageByTitle(allArticlesMobile, DEFINED_TITLE);

            LOGGER.info("Get articles and titles from mobile main page, article mobile page and comments mobile page");
            Map<Integer, Article> articlesFromMainPageMobile = mainPageMobile.extractTitleWithComments(allArticlesMobile, DEFINED_TITLE, matchingArticlesMobile);
            Map<Integer, Article> articlesFromArticlePageMobile = articlePageMobile.getTitlesAndComments(linksToArticlesPagesMobile);
            Map<Integer, Article> articlesFromCommentsPageMobile = commentsPageMobile.getTitleAndComments(commentsMainWebLinksMobile);


            LOGGER.info("Check title and comments similarity");
            result = testUtilsHelper.getDifferentTitles(articlesFromMainPage, articlesFromArticlePage, articlesFromCommentsPage, articlesFromMainPageMobile, articlesFromArticlePageMobile, articlesFromCommentsPageMobile, 1);
            rounds++;

        } while (result.size() > 0 && rounds <= precision);

        LOGGER.info("Print result if titles and comments are different");
        if (result.size() > 0) {
            System.out.println(result);
        }

        assertTrue(result.size() == 0);

        baseFunctions.closeAllTabs();

        LOGGER.info("Test is successful");

    }
}
