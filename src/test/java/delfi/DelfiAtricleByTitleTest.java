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
            MainPageWeb mainPageWeb = new MainPageWeb(baseFunctions);
            ArticlePageWeb articlePageWeb = new ArticlePageWeb(baseFunctions);
            CommentsPageWeb commentsPageWeb = new CommentsPageWeb(baseFunctions);

            MainPageMobile mainPageMobile = new MainPageMobile(baseFunctions);
            ArticlePageMobile articlePageMobile = new ArticlePageMobile(baseFunctions);
            CommentsPageMobile commentsPageMobile = new CommentsPageMobile(baseFunctions);

            LOGGER.info("Open main page on web version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_WEB_URL);

            LOGGER.info("Get article from web main page by title defined in constant variable");
            ArticleWrapperWeb matchingArticleWeb = mainPageWeb.getArticleWrapperByTitle(DEFINED_TITLE);

            LOGGER.info("Get links to article's web page and comments web page");
            String linkToArticlesPage = mainPageWeb.extractLinksToArticlePagesByTitle(matchingArticleWeb);
            String linkToCommentsPage = mainPageWeb.extractLinksToCommentsPageByTitle(matchingArticleWeb);

            LOGGER.info("Get articles and titles from web main page, article web page and comments web page");
            Article articleFromMainPage = mainPageWeb.extractTitleWithComments(matchingArticleWeb);
            Article articleFromArticlePage = articlePageWeb.getTitlesAndComments(linkToArticlesPage);
            Article articleFromCommentsPage = commentsPageWeb.getTitleAndComments(linkToCommentsPage);

            LOGGER.info("Open main page on mobile version");
            baseFunctions.goToUrl(DELFI_MAIN_PAGE_MOBILE_URL);

            LOGGER.info("Get article from web main page by title defined in constant variable");
            ArticleWrapperMobile matchingArticleMobile = mainPageMobile.getMatchingArticleWrapper(DEFINED_TITLE);

            LOGGER.info("Get links to article's web page and comments web page");
            String linkToArticlePageMobile = mainPageMobile.extractLinksToArticlesMobileByTitle(matchingArticleMobile);
            String commentsMainbLinksMobile = mainPageMobile.extractLinksToCommentsPageByTitle(matchingArticleMobile);

            LOGGER.info("Get articles and titles from mobile main page, article mobile page and comments mobile page");
            Article articleFromMainPageMobile = mainPageMobile.getTitleAndComments(matchingArticleMobile);
            Article articleFromArticlePageMobile = articlePageMobile.getTitlesAndComments(linkToArticlePageMobile);
            Article articleFromCommentsPageMobile = commentsPageMobile.getTitleAndComments(commentsMainbLinksMobile);


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
