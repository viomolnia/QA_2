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
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DelfiAtricleByTitleTest {
    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = LogManager.getLogger(DelfiAtricleByTitleTest.class);
    private static final String DELFI_MAIN_PAGE_WEB_URL = "http://rus.delfi.lv";
    private static final String DELFI_MAIN_PAGE_MOBILE_URL = "http://m.rus.delfi.lv";
    private static final String DEFINED_TITLE = "Чем заняться в Риге в выходные: афиша мероприятий";


    @Test
    @Description("Check article by name defined in constant")
    public void delfiPageObjectTest() {
        List<ArticleReview> result;
        int rounds = 0;
        int precision = 3;

        do {

            LOGGER.info("Open main page on web version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_WEB_URL);
            MainPageWeb mainPageWeb = new MainPageWeb(baseFunctions);
            List<ArticleWrapperWeb> allArticlesWeb = mainPageWeb.getAllArticlesWrappers();

            LOGGER.info("Get article from web main page by title defined in constant variable");
            ArticleWrapperWeb matchingArticleWeb = mainPageWeb.getArticleWrapperByTitle(allArticlesWeb, DEFINED_TITLE);

            LOGGER.info("Get link to article's web page");
            String linkToArticlesPage = mainPageWeb.extractLinksToArticlePagesByTitle(matchingArticleWeb);

            LOGGER.info("Get link to comments's web page");
            String linkToCommentsPage = mainPageWeb.extractLinksToCommentsPageByTitle(matchingArticleWeb);

            LOGGER.info("Get title and comments from article from main web page");
            Article articleFromMainPage = mainPageWeb.extractArticle(matchingArticleWeb);

            LOGGER.info("Get title and comments from article's web page");
            baseFunctions.goToUrl(linkToArticlesPage);
            ArticlePageWeb articlePageWeb = new ArticlePageWeb(baseFunctions);
            Article articleFromArticlePage = articlePageWeb.extractArticle();

            LOGGER.info("Get title and comments from comments's web page");
            baseFunctions.goToUrl(linkToCommentsPage);
            CommentsPageWeb commentsPageWeb = new CommentsPageWeb(baseFunctions);
            Article articleFromCommentsPage = commentsPageWeb.extractArticle();

            LOGGER.info("Open main page on mobile version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_MOBILE_URL);


            LOGGER.info("Get article from web main page by title defined in constant variable");
            MainPageMobile mainPageMobile = new MainPageMobile(baseFunctions);
            List<ArticleWrapperMobile> articlesMobile = mainPageMobile .getAllArticlesWrappers();

            LOGGER.info("Get article from mobile main page by title defined in constant variable");
            ArticleWrapperMobile matchingArticleMobile = mainPageMobile.getMatchingArticleWrapper(articlesMobile, DEFINED_TITLE);

            LOGGER.info("Get links to article's web page");
            String linkToArticlePageMobile = mainPageMobile.extractLinksToArticlesMobileByTitle(matchingArticleMobile);

            LOGGER.info("Get links to comments' web page");
            String commentsMainLinksMobile = mainPageMobile.extractLinksToCommentsPageByTitle(matchingArticleMobile);

            LOGGER.info("Get title and comments from article from main mobile page");
            Article articleFromMainPageMobile = mainPageMobile.getTitleAndComments(matchingArticleMobile);

            LOGGER.info("Get title and comments from article from article's mobile page");
            baseFunctions.goToUrl(linkToArticlePageMobile);
            ArticlePageMobile articlePageMobile = new ArticlePageMobile(baseFunctions);
            Article articleFromArticlePageMobile = articlePageMobile.getTitlesAndComments();

            LOGGER.info("Get title and comments from article from comments' mobile page");
            baseFunctions.goToUrl(commentsMainLinksMobile);
            CommentsPageMobile commentsPageMobile = new CommentsPageMobile(baseFunctions);
            Article articleFromCommentsPageMobile = commentsPageMobile.getTitleAndComments();

            LOGGER.info("Check title and comments similarity");
            result = getDifferentTitles(articleFromMainPage, articleFromArticlePage,
                    articleFromCommentsPage, articleFromMainPageMobile,
                    articleFromArticlePageMobile, articleFromCommentsPageMobile);
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

    private List<ArticleReview> getDifferentTitles(Article titlesWithComments, Article titlesWithCommentsInArticleTab,
                                                   Article titlesWithCommentsComment,
                                                   Article titlesWithCommentsMobile, Article titlesWithCommentsMobileInArticleTab,
                                                   Article titlesWithCommentsCommentMobile) {
        List<ArticleReview> result = new ArrayList<>();

            //if there are no comments for article, than there are no title and comments from comments page,
            //so we check if an article has zero comments and null on comments page
            if (hasZeroComments(titlesWithComments) && hasZeroComments(titlesWithCommentsInArticleTab) &&
                    hasZeroComments(titlesWithCommentsMobile) && hasZeroComments(titlesWithCommentsMobileInArticleTab) &&
                    titlesWithCommentsComment == null && titlesWithCommentsCommentMobile == null    ) {

                //if there are some mismatches, add an article to result
                if (!(titlesWithComments.equals(titlesWithCommentsInArticleTab) &&
                        titlesWithCommentsInArticleTab.equals(titlesWithCommentsMobile) &&
                        titlesWithCommentsMobile.equals(titlesWithCommentsMobileInArticleTab))) {

                    result.add(new ArticleReview(0, titlesWithComments, titlesWithCommentsInArticleTab,
                            titlesWithCommentsMobile, titlesWithCommentsMobileInArticleTab,
                            titlesWithCommentsComment, titlesWithCommentsCommentMobile));
                }
            } else {

                //if an article has some comments, we check all the maps with articles
                if (!(titlesWithComments.equals(titlesWithCommentsInArticleTab) &&
                        titlesWithCommentsInArticleTab.equals(titlesWithCommentsMobile) &&
                        titlesWithCommentsMobile.equals(titlesWithCommentsMobileInArticleTab) &&
                        titlesWithCommentsInArticleTab.equals(titlesWithCommentsComment) &&
                        titlesWithCommentsComment.equals(titlesWithCommentsCommentMobile))) {

                    //if there are some mismatches, we add an article to result.
                    result.add(new ArticleReview(0, titlesWithComments, titlesWithCommentsInArticleTab,
                            titlesWithCommentsMobile, titlesWithCommentsMobileInArticleTab,
                            titlesWithCommentsComment, titlesWithCommentsCommentMobile));
                }
            }

        return result;
    }

    private boolean hasZeroComments(Article article) {
        return article.getComments() == 0;
    }
}
