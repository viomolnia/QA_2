package delfi;

import java.util.Map;

class CommentsPageWeb {
    private Map<Integer, Article> articles;

    public Map<Integer, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }
}