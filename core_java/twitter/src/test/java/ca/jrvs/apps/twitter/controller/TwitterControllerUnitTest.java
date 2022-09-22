package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  String hashTag = "IntTest";
  String text = "sometext" + hashTag + " " + System.currentTimeMillis();
  Double lat = 1d;
  Double lon = -1d;
  String fields = "created_at,id,id_str,text,entities,coordinates,retweet_count,favorite_count,favorited,retweeted";
  @Mock
  TwitterService twitterService;

  @InjectMocks
  TwitterController twitterController;

  @Test
  public void postTweet() {
    String coords = lon+":"+lat;
    Tweet newTweet = TweetUtil.buildTweet(text,lon,lat);
    when(twitterService.postTweet(any())).thenReturn(newTweet);
    String[] args = new String[]{"post",text,coords};
    Tweet post = twitterController.postTweet(args);
    assertNotNull(post);
    assertEquals(newTweet.getText(),post.getText());
  }

  @Test
  public void showTweet() {
    when(twitterService.showTweet(any(),any())).thenReturn(new Tweet());
    String[] args = new String[]{"show","123456789",fields};
    Tweet show = twitterController.showTweet(args);
    assertNotNull(show);
  }

  @Test
  public void deleteTweet() {
    List<Tweet> tweetList = new ArrayList<>();
    String[] ids = new String[]{"123456789","987654321"};
    String[] args = new String[]{"delete", String.valueOf(ids)};
    when(twitterService.deleteTweets(any())).thenReturn(tweetList);
    List<Tweet> deleteTweets = twitterController.deleteTweet(args);
    assertEquals(0,deleteTweets.size());

  }
}