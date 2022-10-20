package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.JsonParser;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  String tweetJsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1097607853932564480,\n"
      + "   \"id_str\":\"1097607853932564480\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "      \"hashtags\":[],"
      + "      \"user_mentions\":[]"
      + "   },\n"
      + "   \"coordinates\": {"
      + "           \"coordinates\" : [ 10.1, 10.1 ],\n"
      + "           \"type\" : \"Point\"\n"
      + "   },\n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";

  @Test
  public void postTweet() throws Exception {
    String hashTag = "UnitTest";
    String text = "sometext" + hashTag + " " + System.currentTimeMillis();
    Double lat = 1d;
    Double lon = -1d;

    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try{
      dao.create((TweetUtil.buildTweet(text,lon, lat)));
      fail();
    }catch(RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJSON(tweetJsonStr,Tweet.class);

    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, lon, lat));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void showTweet() throws IOException {

    String hashTag = "#abc";
    String text = "sometext" + hashTag + " " + System.currentTimeMillis();
    Double lon = -1d;
    Double lat = 1d;
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
    try{
      dao.findById("1097607853932564480");
      fail();
    }
    catch (Exception e){
      assertTrue(true);
    }


    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao= spy(dao);
    Tweet expectedTweet= JsonParser.toObjectFromJSON(tweetJsonStr,Tweet.class);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
    Tweet tweet=spyDao.findById("1097607853932564480");
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    assertEquals("1097607853932564480",tweet.getId_str());
  }

  @Test
  public void deleteTweet() throws IOException {
    String hashTag = "#abc";
    String text = "@someone some text" + hashTag + " " + System.currentTimeMillis();
    Double lon = -1d;
    Double lat = 1d;
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try{
      dao.deleteById("1097607853932564480");
      fail();
    }
    catch (Exception e){
      assertTrue(true);
    }


    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao= spy(dao);
    Tweet expectedTweet= JsonParser.toObjectFromJSON(tweetJsonStr,Tweet.class);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
    Tweet tweet=spyDao.deleteById("1097607853932564480");
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    assertEquals("1097607853932564480",tweet.getId_str());
  }

}