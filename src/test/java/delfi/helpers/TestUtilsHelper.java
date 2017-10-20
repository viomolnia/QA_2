package delfi.helpers;

import delfi.models.Article;
import delfi.models.ArticleReview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestUtilsHelper {

    public List<ArticleReview> getDifferentTitles(Map<Integer, Article> titlesWithComments, Map<Integer, Article> titlesWithCommentsInArticleTab,
                                                   Map<Integer, Article> titlesWithCommentsComment,
                                                   Map<Integer, Article> titlesWithCommentsMobile, Map<Integer, Article> titlesWithCommentsMobileInArticleTab,
                                                   Map<Integer, Article> titlesWithCommentsCommentMobile, int size) {
        List<ArticleReview> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            //if there are no comments for article, than there are no title and comments from comments page,
            //so we check if an article has zero comments and null on comments page
            if (hasZeroComments(titlesWithComments.get(i)) && hasZeroComments(titlesWithCommentsInArticleTab.get(i)) &&
                    hasZeroComments(titlesWithCommentsMobile.get(i)) && hasZeroComments(titlesWithCommentsMobileInArticleTab.get(i)) &&
                    titlesWithCommentsComment.get(i) == null && titlesWithCommentsCommentMobile.get(i) == null    ) {

                //if there are some mismatches, add an article to result
                if (!(titlesWithComments.get(i).equals(titlesWithCommentsInArticleTab.get(i)) &&
                        titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsMobile.get(i)) &&
                        titlesWithCommentsMobile.get(i).equals(titlesWithCommentsMobileInArticleTab.get(i)))) {

                    result.add(new ArticleReview(i, titlesWithComments.get(i), titlesWithCommentsInArticleTab.get(i),
                            titlesWithCommentsMobile.get(i), titlesWithCommentsMobileInArticleTab.get(i),
                            titlesWithCommentsComment.get(i), titlesWithCommentsCommentMobile.get(i)));
                }
            } else {

                //if an article has some comments, we check all the maps with articles
                if (!(titlesWithComments.get(i).equals(titlesWithCommentsInArticleTab.get(i)) &&
                        titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsMobile.get(i)) &&
                        titlesWithCommentsMobile.get(i).equals(titlesWithCommentsMobileInArticleTab.get(i)) &&
                        titlesWithCommentsInArticleTab.get(i).equals(titlesWithCommentsComment.get(i)) &&
                        titlesWithCommentsComment.get(i).equals(titlesWithCommentsCommentMobile.get(i)))) {

                    //i there are some mismatches, we add an article to result.
                    result.add(new ArticleReview(i, titlesWithComments.get(i), titlesWithCommentsInArticleTab.get(i),
                            titlesWithCommentsMobile.get(i), titlesWithCommentsMobileInArticleTab.get(i),
                            titlesWithCommentsComment.get(i), titlesWithCommentsCommentMobile.get(i)));
                }
            }
        }

        return result;
    }

    private boolean hasZeroComments(Article article) {
        return article.getComments() == 0;
    }
}
