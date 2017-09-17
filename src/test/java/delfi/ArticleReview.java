package delfi;

public class ArticleReview {
    private int idx;
    private String titleWithCommentsFromMainPage;
    private String titleWithCommentsFromArticlePage;
    private String titleWithCommentsFromMainPageMobile;
    private String titleWithCommentsFromArticlePageMobile;
    private String titleWithCommentsFromCommentsPage;
    private String titleWithCommentsFromCommentsPageMobile;

    public ArticleReview(int idx, String titleWithCommentsFromMainPage, String titleWithCommentsFromArticlePage,
                         String titleWithCommentsFromMainPageMobile, String titleWithCommentsFromArticlePageMobile,
                         String titleWithCommentsFromCommentsPage, String titleWithCommentsFromCommentsPageMobile) {
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
                + titleWithCommentsFromMainPage + ", \n"
                + titleWithCommentsFromArticlePage + ", \n"
                + titleWithCommentsFromMainPageMobile + ", \n"
                + titleWithCommentsFromArticlePageMobile + ", \n"
                + titleWithCommentsFromCommentsPage + ", \n"
                + titleWithCommentsFromCommentsPageMobile + "} \n";
    }
}
