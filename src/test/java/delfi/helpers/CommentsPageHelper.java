package delfi.helpers;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.BaseFunctions;

import java.util.HashMap;
import java.util.Map;

public abstract class CommentsPageHelper {
    private BaseFunctions baseFunctions;
    private final Logger LOGGER = LogManager.getLogger(CommentsPageHelper.class);
    private static final String ZERO = "(0)";

    public CommentsPageHelper(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
    }

    public Map<Integer, Article> getTitleAndComments(Map<Integer, String> commentsLinks) {
        LOGGER.info("Getting title and comments from comments page of web version");

        Map<Integer, Article> result = new HashMap<>();
        commentsLinks.entrySet().stream()
                .filter(a -> !a.getValue().equals(ZERO))
                .forEach(a -> result.putAll(getTitleAndComments(a.getKey(), a.getValue())));

        return result;
    }

    private Map<Integer, Article> getTitleAndComments(int idx, String commentLink) {
        Map<Integer, Article> result = new HashMap<>();

        baseFunctions.goToUrl(commentLink);
        String fullTitle = baseFunctions.getElement(getTitleCommentPage()).getText();
        String titleName = fullTitle.indexOf(getComments()) > 0 ? fullTitle.substring(0, fullTitle.indexOf(getComments())) : fullTitle;
        String regComments = baseFunctions.getElement(getRegComments()).getText();
        String anonComments = baseFunctions.getElement(getAnonComments()).getText();
        int regCommentsValue = Integer.parseInt(regComments.substring(regComments.indexOf('(')+1, regComments.indexOf(')')));
        int anonCommentsValue = Integer.parseInt(anonComments.substring(anonComments.indexOf('(')+1, anonComments.indexOf(')')));
        int totalComments = regCommentsValue + anonCommentsValue;

        result.put(idx, new Article(titleName , totalComments));
        return result;
    }

    protected abstract By getTitleCommentPage();
    protected abstract String getComments();
    protected abstract By getRegComments();
    protected abstract By getAnonComments();
}
