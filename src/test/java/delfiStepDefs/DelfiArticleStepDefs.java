package delfiStepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import delfi.models.Article;
import delfi.pages.MainPageMobile;
import delfi.pages.MainPageWeb;
import utils.ArticleWrapperMobile;
import utils.ArticleWrapperWeb;
import utils.BaseFunctions;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DelfiArticleStepDefs {

    private BaseFunctions baseFunctions = new BaseFunctions();
    private MainPageWeb mainPageWeb;
    private MainPageMobile mainPageMobile;
    private int commentsCountFromWebVersion;
    private int commentsCountFromMobileVersion;
    private String articleTitle;
    private List<ArticleWrapperWeb> articlesWeb;
    private List<ArticleWrapperMobile> articlesMobile;
    private ArticleWrapperWeb matchingArticleWeb;
    private ArticleWrapperMobile matchingArticleMobile;

    @Given("Open DELFI main page on web version")
    public void go_to_main_url_web() {
        baseFunctions.goToUrl("http://rus.delfi.lv");
        mainPageWeb = new MainPageWeb(baseFunctions);
    }

    @Given("Get first ([1-5]) articles from main web page")
    public void get_first_articles_from_main_page_web(int size) {
        articlesWeb = mainPageWeb.getFirstArticlesBySize(size);
    }

    @When("The title of article is (.*)")
    public void title_is(String title) {
        articleTitle = title;
    }

    @Then("Find the article by title")
    public void find_article() {
    }

    @Given("Get comments count from matching article from web page")
    public void get_comments_count_web() {
        matchingArticleWeb = mainPageWeb.getArticleWrapperByTitle(articlesWeb, articleTitle);
        Article articleFromWebPage = mainPageWeb.extractArticle(matchingArticleWeb);
        commentsCountFromWebVersion = articleFromWebPage.getComments();
        System.out.println("Comments count from web version: " + commentsCountFromWebVersion);
    }

    @When("Open DELFI main page on mobile version")
    public void go_to_main_url_mobile() {
        baseFunctions.goToUrl("http://m.rus.delfi.lv");
        mainPageMobile = new MainPageMobile(baseFunctions);
    }

    @Given("Get first ([1-5]) articles from main mobile page")
    public void get_first_articles_from_main_page_mobile(int size) {
        articlesMobile = mainPageMobile.getFirstArticlesBySize(size);
    }

    @Then("Find article with the same title from mobile web page")
    public void get_article_with_the_same_title_from_mobile() {
        matchingArticleMobile = mainPageMobile.getMatchingArticleWrapper(articlesMobile, articleTitle);
    }

    @When("Get comments count from matching article from mobile page")
    public void get_comments_count_mobile() {
        Article articleFromMobilePage = mainPageMobile.getTitleAndComments(matchingArticleMobile);
        commentsCountFromMobileVersion = articleFromMobilePage.getComments();
        System.out.println("Comments count from mobile version: " + commentsCountFromMobileVersion);
    }

    @Then("Comments count web and comments count mobile are equal")
    public void assert_web_comments_and_mobile_comments() {
        assertEquals(commentsCountFromWebVersion, commentsCountFromMobileVersion);
    }
}


