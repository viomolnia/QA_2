package delfi;

public class ArticleReview {
    private int idx;
    private String titleWithCommentsFromMainPage;
    private String titleWithCommentsFromArticlePage;
    private String titleWithCommentsFromMainPageMobile;
    private String titleWithCommentsFromArticlePageMobile;

    public ArticleReview(int idx, String titleWithCommentsFromMainPage, String titleWithCommentsFromArticlePage, String titleWithCommentsFromMainPageMobile, String titleWithCommentsFromArticlePageMobile) {
        this.idx = idx;
        this.titleWithCommentsFromMainPage = titleWithCommentsFromMainPage;
        this.titleWithCommentsFromArticlePage = titleWithCommentsFromArticlePage;
        this.titleWithCommentsFromMainPageMobile = titleWithCommentsFromMainPageMobile;
        this.titleWithCommentsFromArticlePageMobile = titleWithCommentsFromArticlePageMobile;
    }

    @Override
    public String toString() {
        return "{" +
                "[" + idx + "]: \n"
                + titleWithCommentsFromMainPage + ", \n"
                + titleWithCommentsFromArticlePage + ", \n"
                + titleWithCommentsFromMainPageMobile + ", \n"
                + titleWithCommentsFromArticlePageMobile + "} \n";
    }
}
