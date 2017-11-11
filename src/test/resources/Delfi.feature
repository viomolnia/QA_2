Feature: Testing Delfi page

  Scenario: First Scenario
    Given Open DELFI main page on web version
    And Get first 5 articles from main web page
    When The title of article is Аболтиня сравнила себя с жертвой "расстрельной тройки" времен СССР
    Then Find the article by title
    And Get comments count from matching article from web page
    When Open DELFI main page on mobile version
    And Get first 5 articles from main mobile page
    Then Find article with the same title from mobile web page
    When Get comments count from matching article from mobile page
    Then Comments count web and comments count mobile are equal
