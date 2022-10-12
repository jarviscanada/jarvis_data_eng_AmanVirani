package ca.jrvs.practice.codingChallenge;

public class climbStairs {

  public int dynamicClimbStairs(int n) {
    if(n<=2){
      return n;
    } else{
      int[] dpClimb=new int[n+3];
      dpClimb[0] = 0;
      dpClimb[1] = 1;
      dpClimb[2]=2;
      for(int i=3; i<=n; i++)
      {
        dpClimb[i] = dpClimb[i-1] + dpClimb[i-2];
      }
      return dpClimb[n];
    }
  }

}
