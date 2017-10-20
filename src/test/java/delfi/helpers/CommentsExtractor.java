package delfi.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.By.className;

public class CommentsExtractor {

    private static final Logger LOGGER = LogManager.getLogger(CommentsExtractor.class);


    private static final String ZERO = "(0)";
    private static final By COMMENT_COUNT = className("comment-count");
    private static final By COMMENT_COUNT_JOINED = className("commentCount");
    private static final By TECH_COMMENT_COUNT = className("tech-comment-count");

    public Integer getComments(WebElement article) {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments

        LOGGER.info("Get comments count");
        List<WebElement> comments = article.findElements(getCorrectCommentLocator(article));
        String result = comments.size() > 0 ? comments.get(0).getText() : ZERO;
        result = result.substring(result.indexOf('(') + 1, result.indexOf(')'));
        return Integer.parseInt(result);
    }

    public By getCorrectCommentLocator(WebElement element) {
        if (element.findElements(COMMENT_COUNT).size() > 0) {
            return COMMENT_COUNT;
        } else if (element.findElements(COMMENT_COUNT_JOINED).size() > 0){
            return COMMENT_COUNT_JOINED;
        } else {
            return TECH_COMMENT_COUNT;
        }
    }
}
