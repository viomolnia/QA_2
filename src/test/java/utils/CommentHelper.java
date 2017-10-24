package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.google.common.base.CharMatcher.digit;
import static java.lang.Integer.valueOf;

public class CommentHelper {
    private static final Logger LOGGER = LogManager.getLogger(delfi.delfi_demo.CommentHelper.class);

    public Integer extractCommentCountFromString(String textInput) {
        LOGGER.info("Extract comments from string");
        return valueOf(digit().retainFrom(textInput));
    }
}
