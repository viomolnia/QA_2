package delfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.xpath;

class CommentsPageCommon {
    private static final Logger LOGGER = LogManager.getLogger(CommentsPageCommon.class);

    private static final By TITLE_COMMENT_PAGE = className("comment-main-title-link");
    private static final By REG_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']");
    private static final By ANON_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']");
    static final String ZERO = "(0)";

    Integer getComments(WebElement article, BaseFunctionsDelfi base) {
        //Sometimes there ate 'comment-count' class name for the comments, but sometimes - 'commentCount',
        // (also once there was 'tech-comment-count' classname noticed), so we check for correct class name for comments
        List<WebElement> comments = article.findElements(base.getCorrectCommentLocator(article));
        String result = comments.size() > 0 ? comments.get(0).getText() : CommentsPageCommon.ZERO;
        result = result.substring(result.indexOf('(') + 1, result.indexOf(')'));
        return Integer.parseInt(result);
    }

    private Map<Integer, Article> getTitleAndComments(int idx, String commentLink, By title, By reg, By anon, WebDriver driver) {
        Map<Integer, Article> result = new HashMap<>();

        driver.get(commentLink);
        String fullTitle = driver.findElement(title).getText();
        String titleName = fullTitle.indexOf(BaseFunctionsDelfi.COMMENTS) > 0 ? fullTitle.substring(0, fullTitle.indexOf(BaseFunctionsDelfi.COMMENTS)) : fullTitle;
        String regComments = driver.findElement(reg).getText();
        String anonComments = driver.findElement(anon).getText();
        int regCommentsValue = Integer.parseInt(regComments.substring(regComments.indexOf('(')+1, regComments.indexOf(')')));
        int anonCommentsValue = Integer.parseInt(anonComments.substring(anonComments.indexOf('(')+1, anonComments.indexOf(')')));
        int totalComments = regCommentsValue + anonCommentsValue;

        result.put(idx, new Article(titleName , totalComments));
        return result;
    }

    Map<Integer, Article> getTitleAndComments(Map<Integer, String> commentsLinks, WebDriver driver, CommentsPageCommon commentsPageCommon) {
        LOGGER.info("Getting title and comments from comments page...");

        Map<Integer, Article> result = new HashMap<>();
        commentsLinks.entrySet().stream()
                .filter(a -> !a.getValue().equals(CommentsPageCommon.ZERO))
                .forEach(a -> result.putAll(commentsPageCommon.getTitleAndComments(a.getKey(), a.getValue(), CommentsPageCommon.TITLE_COMMENT_PAGE, CommentsPageCommon.REG_COMMENTS, CommentsPageCommon.ANON_COMMENTS, driver)));

        LOGGER.info("Title and comments extracted from comments page...");
        return result;
    }
}
