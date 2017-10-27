package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.tagName;

public class ArticleWrapperMobile {
    private final utils.BaseFunctions baseFunctions;
    private final WebElement webElement;
    private CommentHelper commentHelper = new CommentHelper();

    private static final By TITLE_CLASS1 = className("md-scrollpos");

    private static final By COMMENT_COUNT = className("comment-count");
    private static final By COMMENT_COUNT_JOINED = className("commentCount");
    private static final By TECH_COMMENT_COUNT = className("tech-comment-count");
    private static final String ZERO = "(0)";

    public ArticleWrapperMobile (utils.BaseFunctions baseFunctions, WebElement webElement) {
        this.baseFunctions = baseFunctions;
        this.webElement = webElement;
    }

    public String getTitleFromArticle() {
        return webElement.findElements(TITLE_CLASS1).size() > 0 ? webElement.findElement(TITLE_CLASS1).getText() : "";
    }

    public String getLinkToArticle() {
        return webElement.findElement(TITLE_CLASS1).getAttribute("href");
    }

    public String getLinkToArticleByTag() {
        return webElement.findElements(tagName("a")).get(0).getAttribute("href");
    }

    public String getCommentsLink() {
        List<WebElement> comments = webElement.findElements(getCorrectCommentLocator(webElement));
        return comments.size() > 0 ? comments.get(0).getAttribute("href") : ZERO;
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

    public Integer getComments() {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments
        List<WebElement> comments = webElement.findElements(getCorrectCommentLocator(webElement));
        String result = comments.size() > 0 ? comments.get(0).getText() : ZERO;
        if (result.equals("")) result = ZERO;
        return commentHelper.extractCommentCountFromString(result);
    }

}
