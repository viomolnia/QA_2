package delfi;

import com.sun.org.glassfish.gmbal.Description;
import delfi.models.Article;
import delfi.models.ArticleReview;
import delfi.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import utils.ArticleWrapperMobile;
import utils.ArticleWrapperWeb;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class DelfiFirstFiveArticlesTest {

    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = LogManager.getLogger(DelfiFirstFiveArticlesTest.class);
    private static final String DELFI_MAIN_PAGE_WEB_URL = "http://rus.delfi.lv";
    private static final String DELFI_MAIN_PAGE_MOBILE_URL = "http://m.rus.delfi.lv";
    private static final String ZERO = "(0)";

    @Test
    @Description("Check first five articles for matching titles and comments on main page, article's page and comments page")
    public void delfiPageObjectTest() {
        List<ArticleReview> result;
        int size = 5;
        int rounds = 0;
        int precision = 1;

        do {
            LOGGER.info("Open main page on web version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_WEB_URL);
            MainPageWeb mainPageWeb = new MainPageWeb(baseFunctions);

            LOGGER.info("Get first five articles from main web page");
            List<ArticleWrapperWeb> firstArticlesBySize = mainPageWeb.getFirstArticlesBySize(size);

            LOGGER.info("Get links to article's page for first five articles");
            Map<Integer, String> linksToArticlesPages = mainPageWeb.extractLinksToArticlePages(firstArticlesBySize);

            LOGGER.info("Get links to comments' page for first five articles");
            Map<Integer, String> commentsMainWebLinks = mainPageWeb.extractLinksToCommentsPage(firstArticlesBySize);

            LOGGER.info("Get title and comments for article from main web page for first five articles");
            Map<Integer, Article> articlesFromMainPage = mainPageWeb.extractArticle(firstArticlesBySize);

            LOGGER.info("Get title and comments for article from article's page for first five articles");
            Map<Integer, Article> articlesFromArticlePage = new HashMap<>();
            linksToArticlesPages.forEach((key, value) -> {
                baseFunctions.goToUrl(value);
                ArticlePageWeb articlePageWeb = new ArticlePageWeb(baseFunctions);
                articlesFromArticlePage.putAll(articlePageWeb.getTitleAndComments(key));
            });

            LOGGER.info("Get title and comments for article from comments' page for first five articles");
            Map<Integer, Article> articlesFromCommentsPage = new HashMap<>();
            commentsMainWebLinks.forEach((key, value) -> {
                if (value.equals(ZERO)) {
                    articlesFromCommentsPage.put(key, null);
                } else {
                    baseFunctions.goToUrl(value);
                    CommentsPageWeb commentsPageWeb = new CommentsPageWeb(baseFunctions);
                    articlesFromCommentsPage.putAll(commentsPageWeb.extractArticle(key));
                }
            });

            LOGGER.info("Open main page on mobile version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_MOBILE_URL);
            MainPageMobile mainPageMobile = new MainPageMobile(baseFunctions);

            LOGGER.info("Get first five articles from main mobile page");
            List<ArticleWrapperMobile> firstArticlesBySizeMobile = mainPageMobile.getFirstArticlesBySize(size);

            LOGGER.info("Get links to article's page for first five articles from web page");
            Map<Integer, String> linksToArticlesPagesMobile = mainPageMobile.extractLinksToArticlesMobile(firstArticlesBySizeMobile);

            LOGGER.info("Get links to comments' page for first five articles from web page");
            Map<Integer, String> commentsMainWebLinksMobile = mainPageMobile.extractLinksToCommentsPage(firstArticlesBySizeMobile);

            LOGGER.info("Get titles and comments for first five articles from main web page");
            Map<Integer, Article> articlesFromMainPageMobile = mainPageMobile.extractTitleWithComments(firstArticlesBySizeMobile);

            LOGGER.info("Get title and comments from article's page for first five articles");
            Map<Integer, Article> articlesFromArticlePageMobile = new HashMap<>();
            linksToArticlesPagesMobile.forEach((key, value) -> {
                baseFunctions.goToUrl(value);
                ArticlePageMobile articlePageMobile = new ArticlePageMobile(baseFunctions);
                articlesFromArticlePageMobile.putAll(articlePageMobile.getTitleAndComments(key));
            });

            LOGGER.info("Get title and comments from comments' page for first five articles");
            Map<Integer, Article> articlesFromCommentsPageMobile = new HashMap<>();
            commentsMainWebLinksMobile.forEach((key, value) -> {
                if (value.equals(ZERO)) {
                    articlesFromCommentsPageMobile.put(key, null);
                } else {
                    baseFunctions.goToUrl(value);
                    CommentsPageMobile commentsPageMobile = new CommentsPageMobile(baseFunctions);
                    articlesFromCommentsPageMobile.putAll(commentsPageMobile.getTitleAndCommentsWithIdx(key));
                }
            });

            LOGGER.info("Check title and comments similarity");
            result = getDifferentTitles(articlesFromMainPage, articlesFromArticlePage, articlesFromCommentsPage, articlesFromMainPageMobile, articlesFromArticlePageMobile, articlesFromCommentsPageMobile, size);
            rounds ++;

        } while (result.size() > 0 && rounds < precision);

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
