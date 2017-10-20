package delfi.pages;

import delfi.helpers.CommentsPageHelper;
import org.openqa.selenium.By;
import utils.BaseFunctions;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.xpath;

public class CommentsPageWeb extends CommentsPageHelper{

    private static final By TITLE_COMMENT_PAGE = className("comment-main-title-link");
    private static final By REG_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']");
    private static final By ANON_COMMENTS = xpath(".//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']");
    private static final String COMMENTS = ": комментарии";

    public CommentsPageWeb(BaseFunctions baseFunctions) {
        super(baseFunctions);
    }


    @Override
    protected By getTitleCommentPage() {
        return TITLE_COMMENT_PAGE;
    }

    @Override
    protected String getComments() {
        return COMMENTS;
    }

    @Override
    protected By getRegComments() {
        return REG_COMMENTS;
    }

    @Override
    protected By getAnonComments() {
        return ANON_COMMENTS;
    }
}
