package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

  /**
   * Big O: O(n^2)
   * Justification: looping the list twice so its n^2
   */
  public int[] twoSum(int[] nums, int target) {
    /**
     * exactly one solution
     */
    int[] resultNum = new int[2];
    for (int i=0;i<nums.length;i++){
      for(int j=i+1;j<nums.length;j++){
        int expectedNums = nums[i] + nums[j];
        if(expectedNums == target){
          resultNum[0] = i;
          resultNum[1] = j;
          return resultNum;
        }
      }
    }
    return resultNum;
  }

  public int[] optimisedTwoSum(int[] nums, int target) {
    Map<Integer,Integer> map = new HashMap<Integer, Integer>();
    for(int i=0;i<nums.length;i++){
      int resultNums = target - nums[i];
      if(map.containsKey(resultNums)){
        return new int[] {map.get(resultNums),i};
      }
      map.put(nums[i],i);
    }
    return null;
  }

}
