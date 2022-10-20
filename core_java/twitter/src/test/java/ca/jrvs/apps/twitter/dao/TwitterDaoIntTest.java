package ca.jrvs.apps.twitter.dao;

import static java.awt.SystemColor.text;
import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao dao;
  Double lon = -1d;
  Double lat = 1d;
  String hashtag = "#create_test";
  String text = "sometext"+hashtag+" "+System.currentTimeMillis();

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
    this.dao = new TwitterDao(httpHelper);

  }

  @Test
  public void create() throws Exception {

    Tweet postTweet;

    try {
      postTweet = TweetUtil.buildTweet(text,lon,lat);
    }catch (Exception e){
      throw new Exception("Unable to create tweet with coordinate", e);
    }

    Tweet tweet = dao.create(postTweet);

    assertEquals(text, tweet.getText());
    //assertNotNull(tweet.getCoordinates());
    assertEquals(2,tweet.getCoordinates().getCoordinates().size());
    assertEquals(lon,tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat,tweet.getCoordinates().getCoordinates().get(1));
    //assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void findById() {
    String id = "1571641036807589889";
    String expectedText = "sometext#create_test 1663543397375";
    Tweet tweet = dao.findById(id);

    assertEquals(expectedText, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    //assertTrue(hashtag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void deleteById() throws Exception {
    Tweet tweet;

    try {
      tweet = TweetUtil.buildTweet(text,lon,lat);
    }catch (Exception e){
      throw new Exception("Unable to create tweet with coordinate in del part", e);
    }

    Tweet createTweet = dao.create(tweet);

    String id = createTweet.getId_str();
    Tweet delTweet = dao.deleteById(id);

    assertNotNull(delTweet.getCoordinates());
    assertEquals(2, delTweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, delTweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, delTweet.getCoordinates().getCoordinates().get(1));
  }
}