package delfi;

import org.openqa.selenium.By;

import java.util.Map;

import static org.openqa.selenium.By.xpath;

class ArticlePageWeb extends ArticlePageCommon{

    static final By ARTICLE_IN_NEW_TAB = xpath("//*[contains(@class, 'article-title')]");
    static final By ARTICLE_IN_NEW_TAB2 = xpath("//*[contains(@class, 'comments-about-title')]");
    static final By TITLE = xpath("//span[@itemprop='headline name']");

    private Map<Integer, Article> articles;

    Map<Integer, Article> getArticles() {
        return articles;
    }

    void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }
}
