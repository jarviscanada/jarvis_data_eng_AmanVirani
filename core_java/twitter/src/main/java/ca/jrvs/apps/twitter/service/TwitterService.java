package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TwitterService implements Service{

  private CrdDao dao;

  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {

    validatePostTweet(tweet);

    return (Tweet) dao.create(tweet);
  }

  private void validatePostTweet(Tweet tweet) {

    int tweetLength = tweet.getText().length();
    Coordinates coordinates = tweet.getCoordinates();

    if(tweetLength > 140 || tweetLength < 1){
      throw new IllegalArgumentException("Tweet length should be less than 140 Characters.");
    }
    if(coordinates != null){
      if(coordinates.getCoordinates()!= null){
        if(coordinates.getCoordinates().get(0) > 180.0 || coordinates.getCoordinates().get(0) < -180.0 ||
        coordinates.getCoordinates().get(0) > 90.0 || coordinates.getCoordinates().get(1) < -90.0) {
          throw new IllegalArgumentException("Coordinates out of range: Long range -180 to 180 , Lat range -90 to 90");
        }
      }
    }
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {

    if(!(validateId(id))) {
      throw new IllegalArgumentException("Invalid Id");
    }

    return (Tweet) dao.findById(id);
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {

    List<Tweet> tweetsList = new ArrayList<>();

    for(String id: ids){
      if(!(validateId(id))){
        throw new IllegalArgumentException("Invalid id");
      } else {
        tweetsList.add((Tweet) dao.deleteById(id));
      }
    }

    return tweetsList;
  }

  public boolean validateId(String id){
    if(id == null) {
      return false;
    }
    try{
      Long longId= Long.parseLong(id);
    } catch (NumberFormatException e){
      return false;
    }
    return true;
  }
}
