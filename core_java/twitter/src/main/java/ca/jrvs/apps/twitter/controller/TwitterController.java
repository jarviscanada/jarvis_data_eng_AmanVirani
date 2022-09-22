package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.List;
import org.springframework.util.StringUtils;

public class TwitterController implements Controller{

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {
    if(args.length !=3){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    String tweet_txt = args[1];
    String coord = args[2];
    String[] coordArray = coord.split(COORD_SEP);
    if(coordArray.length != 2 || StringUtils.isEmpty(tweet_txt)){
      throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_txt\" \"latitude:longitude\"");
    }
    Double lat,lon = null;
    try{
      lon = Double.parseDouble(coordArray[0]);
      lat = Double.parseDouble(coordArray[1]);
    } catch (Exception e){
      throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_txt\" \"latitude:longitude\"",e);
    }
    Tweet postTweet = TweetUtil.buildTweet(tweet_txt,lon,lat);
    return service.postTweet(postTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if(args.length < 2){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"id\"");
    }
    String id = args[1];
    String[] fields;
    if(args.length > 2){
      fields = args[2].split(COMMA);
    } else {
      fields = new String[0];
    }
    return service.showTweet(id,fields);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if(args.length > 2){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"id1,id2,id3...\"");
    }
    String[] ids = args[1].split(COMMA);
    return service.deleteTweets(ids);
  }
}
