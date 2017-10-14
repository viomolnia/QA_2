package delfi;

import com.sun.org.glassfish.gmbal.Description;
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

public class DelfiFirstFiveArticlesTest {
    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = LogManager.getLogger(DelfiFirstFiveArticlesTest.class);
    private static final String DELFI_MAIN_PAGE_WEB_URL = "http://rus.delfi.lv";
    private static final String DELFI_MAIN_PAGE_MOBILE_URL = "http://m.rus.delfi.lv";


    @Test
    @Description("Check first five articles for matching titles and comments on main page, article's page and comments page")
    public void delfiPageObjectTest() {
        List<ArticleReview> result;
        int size = 5;
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
            List<WebElement> firstChosenArticles = mainPageWeb.extractArticles1(size);

            LOGGER.info("Get links to article's web page and comments web page");
            Map<Integer, String> linksToArticlesPages = mainPageWeb.extractLinksToArticlePages(firstChosenArticles);
            Map<Integer, String> commentsMainWebLinks = mainPageWeb.extractLinksToCommentsPage(firstChosenArticles);

            LOGGER.info("Get articles and titles from web main page, article web page and comments web page");
            Map<Integer, Article> articlesFromMainPage = mainPageWeb.extractTitleWithComments(firstChosenArticles);
            Map<Integer, Article> articlesFromArticlePage = articlePageWeb.getTitlesAndComments(linksToArticlesPages);
            Map<Integer, Article> articlesFromCommentsPage = commentsPageWeb.getTitleAndComments(commentsMainWebLinks);

            LOGGER.info("Open main page on mobile version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_MOBILE_URL);

            List<WebElement> allArticlesMobile = mainPageMobile.extractArticles1(size);

            LOGGER.info("Get links to article's web page and comments web page");
            Map<Integer, Article> articlesFromMainPageMobile = mainPageMobile.extractTitleWithComments(allArticlesMobile);
            Map<Integer, String> linksToArticlesPagesMobile = mainPageMobile.extractLinksToArticlesMobile(allArticlesMobile);
            Map<Integer, String> commentsMainWebLinksMobile = mainPageMobile.extractLinksToCommentsPage(allArticlesMobile);

            LOGGER.info("Get articles and titles from mobile main page, article mobile page and comments mobile page");
            Map<Integer, Article> articlesFromArticlePageMobile = articlePageMobile.getTitlesAndComments(linksToArticlesPagesMobile);
            Map<Integer, Article> articlesFromCommentsPageMobile = commentsPageMobile.getTitleAndComments(commentsMainWebLinksMobile);

            LOGGER.info("Check title and comments similarity");
            result = getDifferentTitles(articlesFromMainPage, articlesFromArticlePage, articlesFromCommentsPage, articlesFromMainPageMobile, articlesFromArticlePageMobile, articlesFromCommentsPageMobile, 1);
            rounds ++;

        } while (result.size() > 0 && rounds <= precision);

        LOGGER.info("Print result if titles and comments are different");
        if (result.size() > 0) {
            System.out.println(result);
        }

        assertTrue(result.size() == 0);

        baseFunctions.closeAllTabs();

        LOGGER.info("Test is successful");

    }

    private List<ArticleReview> getDifferentTitles(Map<Integer, Article> titlesWithComments, Map<Integer, Article> titlesWithCommentsInArticleTab,
                                                   Map<Integer, Article> titlesWithCommentsComment,
                                                   Map<Integer, Article> titlesWithCommentsMobile, Map<Integer, Article> titlesWithCommentsMobileInArticleTab,
                                                   Map<Integer, Article> titlesWithCommentsCommentMobile, int size) {
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

    private boolean hasZeroComments(Article article) {
        return article.getComments() == 0;
    }
}
