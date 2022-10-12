package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.StringHolder;

public class mergeTwoListsTest {

  mergeTwoLists mergeLists;

  @Before
  public void setUp() throws Exception {
    mergeLists = new mergeTwoLists();
  }

  @Test
  public void mergeTwoLists() {
    ListNode list1 = new ListNode(1,new ListNode(2,new ListNode(4)));
    ListNode list2= new ListNode(1,new ListNode(3,new ListNode(4)));
    String output = "1,1,2,3,4";
    ListNode actual = mergeLists.mergeTwoLists(list1,list2);
    String actualString = new String(actual.toString());
  }
}