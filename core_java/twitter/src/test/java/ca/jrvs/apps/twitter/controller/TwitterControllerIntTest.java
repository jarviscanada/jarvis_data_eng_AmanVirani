package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  private TwitterDao dao;
  private TwitterController controller;
  String fields = "created_at,id,id_str,text,entities,coordinates,retweet_count,favorite_count,favorited,retweeted";
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
    dao = new TwitterDao(httpHelper);
    twitterService = new TwitterService(dao);
    controller = new TwitterController(twitterService);

  }

  @Test
  public void postTweet() {
    String coords = lon+":"+lat;
    String[] args = new String[]{"post",text,coords};
    Tweet postTweet = controller.postTweet(args);

    assertEquals(text,postTweet.getText());
    assertEquals(lon, postTweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, postTweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void showTweet() {
    String coords = lon+":"+lat;

    String[] args = new String[]{"post",text,coords};
    Tweet postTweet= controller.postTweet(args);

    String[] field = new String[]{"show", postTweet.getId_str(), fields};
    Tweet showTweet = controller.showTweet(field);
    assertEquals(postTweet.getText(), showTweet.getText());
  }

  @Test
  public void deleteTweet() {
    String coords = lon+":"+lat;

    String[] args = new String[]{"post",text,coords};
    Tweet postTweet= controller.postTweet(args);
    String[] delete = new String[]{"delete", postTweet.getId_str()};
    List<Tweet> deletedTweets= controller.deleteTweet(delete);
    assertEquals(text, deletedTweets.get(0).getText());
  }
}