package delfi.pages;

import delfi.models.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.BaseFunctions;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

public class CommentsPageMobile {
    private BaseFunctions baseFunctions;

    private static final By TITLE_COMMENT_PAGE = className("comment-main-title-link");
    private static final By REG_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']");
    private static final By ANON_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']");
    private static final String COMMENTS = ": комментарии";

    private static final By MAIN_WRAPPER = id("md-wrapper");

    public CommentsPageMobile(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        baseFunctions.isPresent(MAIN_WRAPPER);
    }

    public Map<Integer, Article> getTitleAndCommentsWithIdx(int idx) {
        Map<Integer, Article> result = new HashMap<>();

        String fullTitle = baseFunctions.getElement(TITLE_COMMENT_PAGE).getText();
        String titleName = fullTitle.indexOf(COMMENTS) > 0 ? fullTitle.substring(0, fullTitle.indexOf(COMMENTS)) : fullTitle;
        String regComments = baseFunctions.getElement(REG_COMMENTS).getText();
        String anonComments = baseFunctions.getElement(ANON_COMMENTS).getText();
        int regCommentsValue = Integer.parseInt(regComments.substring(regComments.indexOf('(')+1, regComments.indexOf(')')));
        int anonCommentsValue = Integer.parseInt(anonComments.substring(anonComments.indexOf('(')+1, anonComments.indexOf(')')));
        int totalComments = regCommentsValue + anonCommentsValue;

        result.put(idx, new Article(titleName , totalComments));
        return result;
    }

    public Article getTitleAndComments() {
        String fullTitle = baseFunctions.getElement(TITLE_COMMENT_PAGE).getText();
        String titleName = fullTitle.indexOf(COMMENTS) > 0 ? fullTitle.substring(0, fullTitle.indexOf(COMMENTS)) : fullTitle;
        String regComments = baseFunctions.getElement(REG_COMMENTS).getText();
        String anonComments = baseFunctions.getElement(ANON_COMMENTS).getText();
        int regCommentsValue = Integer.parseInt(regComments.substring(regComments.indexOf('(')+1, regComments.indexOf(')')));
        int anonCommentsValue = Integer.parseInt(anonComments.substring(anonComments.indexOf('(')+1, anonComments.indexOf(')')));
        int totalComments = regCommentsValue + anonCommentsValue;

        return new Article(titleName , totalComments);
    }
}
