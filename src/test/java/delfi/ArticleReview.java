package delfi;

import java.util.Map;

public class ArticleReview {
    private int idx;
    private Map<String, Integer> titleWithCommentsFromMainPage;
    private Map<String, Integer> titleWithCommentsFromArticlePage;
    private Map<String, Integer> titleWithCommentsFromMainPageMobile;
    private Map<String, Integer> titleWithCommentsFromArticlePageMobile;
    private Map<String, Integer> titleWithCommentsFromCommentsPage;
    private Map<String, Integer> titleWithCommentsFromCommentsPageMobile;

    public ArticleReview(int idx, Map<String, Integer> titleWithCommentsFromMainPage, Map<String, Integer> titleWithCommentsFromArticlePage,
                         Map<String, Integer> titleWithCommentsFromMainPageMobile, Map<String, Integer> titleWithCommentsFromArticlePageMobile,
                         Map<String, Integer> titleWithCommentsFromCommentsPage, Map<String, Integer> titleWithCommentsFromCommentsPageMobile) {
        this.idx = idx;
        this.titleWithCommentsFromMainPage = titleWithCommentsFromMainPage;
        this.titleWithCommentsFromArticlePage = titleWithCommentsFromArticlePage;
        this.titleWithCommentsFromMainPageMobile = titleWithCommentsFromMainPageMobile;
        this.titleWithCommentsFromArticlePageMobile = titleWithCommentsFromArticlePageMobile;
        this.titleWithCommentsFromCommentsPage = titleWithCommentsFromCommentsPage;
        this.titleWithCommentsFromCommentsPageMobile = titleWithCommentsFromCommentsPageMobile;
    }

    @Override
    public String toString() {
        return "{" +
                "[" + idx + "]: \n"
                + titleWithCommentsFromMainPage.toString() + ", \n"
                + titleWithCommentsFromArticlePage.toString() + ", \n"
                + titleWithCommentsFromMainPageMobile.toString() + ", \n"
                + titleWithCommentsFromArticlePageMobile.toString() + ", \n"
                + titleWithCommentsFromCommentsPage.toString() + ", \n"
                + titleWithCommentsFromCommentsPageMobile.toString() + "} \n";
    }
}
