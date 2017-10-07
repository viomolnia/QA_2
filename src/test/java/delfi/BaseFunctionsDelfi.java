package delfi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BaseFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.By.className;

public class BaseFunctionsDelfi extends BaseFunctions {
    static final String HOME_PAGE = "http://rus.delfi.lv/";
    static final String MOBILE_VERSION = "http://m.rus.delfi.lv/";
    static final String COMMENTS = ": комментарии";

//    static final String HOME_PAGE = "http://delfi.lv/";
//    static final String MOBILE_VERSION = "http://m.delfi.lv/";
//    static final String COMMENTS = ": komentāri";

    static final By COMMENT_COUNT = className("comment-count");
    static final By COMMENT_COUNT_JOINED = className("commentCount");
    static final By TECH_COMMENT_COUNT = className("tech-comment-count");

    By getCorrectCommentLocator(WebElement element) {
        if (element.findElements(BaseFunctionsDelfi.COMMENT_COUNT).size() > 0) {
            return BaseFunctionsDelfi.COMMENT_COUNT;
        } else if (element.findElements(BaseFunctionsDelfi.COMMENT_COUNT_JOINED).size() > 0){
            return BaseFunctionsDelfi.COMMENT_COUNT_JOINED;
        } else {
            return BaseFunctionsDelfi.TECH_COMMENT_COUNT;
        }
    }

    void skipAgeCheck(WebDriver driver) {
        if (driver.findElements(className("yrscheck-yes-input")).size() > 0) {
            driver.findElements(className("yrscheck-yes-input")).get(0).click();
        }
    }

    //we compare all six lists with articles
    List<ArticleReview> getDifferentTitles(Map<Integer, Article> titlesWithComments, Map<Integer, Article> titlesWithCommentsInArticleTab,
                                                   Map<Integer, Article> titlesWithCommentsMobile, Map<Integer, Article> titlesWithCommentsMobileInArticleTab,
                                                   Map<Integer, Article> titlesWithCommentsComment, Map<Integer, Article> titlesWithCommentsCommentMobile, int size) {
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

    String getCommentsLink(WebElement article, BaseFunctionsDelfi base) {
        List<WebElement> comments = article.findElements(base.getCorrectCommentLocator(article));
        return comments.size() > 0 ? comments.get(0).getAttribute("href") : CommentsPageCommon.ZERO;
    }
}
