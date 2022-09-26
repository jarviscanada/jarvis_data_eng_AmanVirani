package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  String hashTag = "UnitTest";
  String text = "sometext" + hashTag + " " + System.currentTimeMillis();
  Double lat = 1d;
  Double lon = -1d;


  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService twitterService;

  @Test
  public void postTweet() {

    Tweet tweet = new Tweet();
    tweet.setText(text);
    when(dao.create(any())).thenReturn(tweet);
    Tweet posting = twitterService.postTweet(TweetUtil.buildTweet(text,lon,lat));
    assertNotNull(posting);
    assertEquals(tweet.getText(),posting.getText());
  }

  @Test
  public void showTweet() {
    String[] fields = {
        "created_at",
        "id",
        "id_str",
        "text",
        "entities",
        "coordinates",
        "retweet_count",
        "favorite_count",
        "favorited",
        "retweeted"
    };
    Tweet tweet = new Tweet();
    tweet.setId_str("10000");
    tweet.setText("sometext");
    when(dao.findById(any())).thenReturn(tweet);
    Tweet show = twitterService.showTweet(tweet.getId_str(), fields);
    assertEquals("sometext", show.getText());
  }

  @Test
  public void deleteTweets() {
    Tweet tweet = new Tweet();
    tweet.setText(text);
    String[] ids = {"10000"};

    when(dao.deleteById(any())).thenReturn(tweet);
    List<Tweet> tweets = twitterService.deleteTweets(ids);
    assertEquals(tweet.getText(),tweets.get(0).getText());

  }
}