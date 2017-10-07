package delfi;

public class ArticleReview {
    private int idx;
    private Article titleWithCommentsFromMainPage;
    private Article titleWithCommentsFromArticlePage;
    private Article titleWithCommentsFromMainPageMobile;
    private Article titleWithCommentsFromArticlePageMobile;
    private Article titleWithCommentsFromCommentsPage;
    private Article titleWithCommentsFromCommentsPageMobile;

    public ArticleReview(int idx, Article titleWithCommentsFromMainPage,
                         Article titleWithCommentsFromArticlePage,
                         Article titleWithCommentsFromMainPageMobile,
                         Article titleWithCommentsFromArticlePageMobile,
                         Article titleWithCommentsFromCommentsPage,
                         Article titleWithCommentsFromCommentsPageMobile) {
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
                + "Main age Web:          " + articleToString(titleWithCommentsFromMainPage) + ", \n"
                + "Article's Page Web:    " + articleToString(titleWithCommentsFromArticlePage) + ", \n"
                + "Main page Mobile:      " + articleToString(titleWithCommentsFromMainPageMobile) + ", \n"
                + "Article's page Mobile: " + articleToString(titleWithCommentsFromArticlePageMobile) + ", \n"
                + "Comments page Web:     " + articleToString(titleWithCommentsFromCommentsPage) + ", \n"
                + "Comments page Mobile:  " + articleToString(titleWithCommentsFromCommentsPageMobile) + "} \n";
    }

    private String articleToString(Article article) {
        return article == null ? "[]" : article.toString();
    }
}
