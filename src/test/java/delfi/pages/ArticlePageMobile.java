package delfi.pages;

import delfi.helpers.ArticlePageHelper;
import org.openqa.selenium.By;
import utils.BaseFunctions;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;

public class ArticlePageMobile extends ArticlePageHelper{

    private static final By TITLE = tagName("h1");
    private static final By ARTICLE_IN_NEW_TAB = xpath("//*[contains(@class, 'article-title')]");
    private static final By ARTICLE_IN_NEW_TAB2 = xpath("//*[contains(@class, 'comments-about-title')]");

    public ArticlePageMobile(BaseFunctions baseFunctions) {
        super(baseFunctions);
    }

    @Override
    protected By getTitle() {
        return TITLE;
    }

    @Override
    protected By getArticleInNewTab() {
        return ARTICLE_IN_NEW_TAB;
    }

    @Override
    protected By getArticleInNewTab2() {
        return ARTICLE_IN_NEW_TAB2;
    }
}
