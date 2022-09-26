package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  private TwitterService twitterService;

  String hashTag = "IntTest";
  String text = "sometext" + hashTag + " " + System.currentTimeMillis();
  Double lat = 1d;
  Double lon = -1d;


  @Before
  public void setUp() throws Exception {
    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");
    System.out.println(CONSUMER_KEY +" | "+ CONSUMER_SECRET +" | "+ ACCESS_TOKEN +" | "+ TOKEN_SECRET);
    //set up dependency
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
    //pass dependency
    TwitterDao twitterDao = new TwitterDao(httpHelper);
    twitterService = new TwitterService(twitterDao);

  }

  @Test
  public void postTweet() {

    Tweet createTweet = TweetUtil.buildTweet(text,lon,lat);
    Tweet actualOutput = twitterService.postTweet(createTweet);
    assertEquals(createTweet.getText(),actualOutput.getText());
    assertEquals(createTweet.getCoordinates().getCoordinates().get(0),actualOutput.getCoordinates().getCoordinates().get(0));
    assertEquals(createTweet.getCoordinates().getCoordinates().get(1),actualOutput.getCoordinates().getCoordinates().get(1));
    assertEquals(2, actualOutput.getCoordinates().getCoordinates().size());

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


    Tweet createTweet = TweetUtil.buildTweet(text,lon,lat);
    Tweet post = twitterService.postTweet(createTweet);
    Tweet showingTest = twitterService.showTweet(post.getId_str(), fields);
    assertEquals(post.getText(),showingTest.getText());

  }

  @Test
  public void deleteTweets() {

    Tweet createTweet = TweetUtil.buildTweet(text,lon,lat);
    Tweet post = twitterService.postTweet(createTweet);
    List<Tweet> deleteTweet = twitterService.deleteTweets(new String[]{post.getId_str()});

    assertEquals(post.getId_str(),deleteTweet.get(0).getId_str());


  }

}