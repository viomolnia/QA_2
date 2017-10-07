package delfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.tagName;

class MainPageMobile {
    private static final Logger LOGGER = LogManager.getLogger(CommentsPageCommon.class);

    static final By TITLE = className("md-scrollpos");

    private Map<Integer, Article> artticles = new HashMap<>();

    public Map<Integer, Article> getArtticles() {
        return artticles;
    }

    public void setArtticles(Map<Integer, Article> artticles) {
        this.artticles = artticles;
    }

    Map<Integer, Article> getTitleAndComments(Map<Integer, WebElement> articles, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon) {
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Article(e.getValue().findElements(tagName("a")).get(0).getText(), commentsPageCommon.getComments(e.getValue(), base))));
    }

    Map<Integer, Article> getTitleAndComments(Map<Integer, WebElement> articles, By title, BaseFunctionsDelfi base, CommentsPageCommon commentsPageCommon) {
        LOGGER.info("Titles and comments extracted from main page of mobile version...");
        return articles.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Article(e.getValue().findElement(title).getText(), commentsPageCommon.getComments(e.getValue(), base))));
    }
}
