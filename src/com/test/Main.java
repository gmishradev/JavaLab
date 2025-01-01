package com.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

      //  System.out.println("Hello world!");

        //Input: logs = [[0,5],[1,2],[0,2],[0,5],[1,3],[1,4] k = 5
        //
        //Output: [0,1,1,0,0]
       /*// 0 - 5,2
                1-2,3,
         (uid –uam)
         k =1 to 5
          0 – 2
          1- 3
          2-2


          3-4
 */

        UserTime[] userTimes = new UserTime[6];
        UserTime userTime1 = new UserTime(0,5);
        UserTime userTime2 = new UserTime(1,2);
        UserTime userTime3 = new UserTime(0,2);
        UserTime userTime4 = new UserTime(0,5);
        UserTime userTime5 = new UserTime(1,3);
        UserTime userTime6 = new UserTime(1,4);
        userTimes[0] =userTime1;
        userTimes[1] =userTime2;
        userTimes[2] =userTime3;
        userTimes[3] =userTime4;
        userTimes[4] =userTime5;
        userTimes[5] =userTime6;




        sol(userTimes,5);
    }
    static class UserTime{

        int id, time;
        UserTime(int id, int time)
        {
            this.id =id;
            this.time =time;
        }

        public int getId()
        {
            return id;
        }
    }

    public static  void sol(UserTime[] userTimes, int k)
    {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for(UserTime userTime: userTimes)
        {
            if(map.containsKey(userTime.getId()))
            {
                map.get(userTime.getId()).add(userTime.time);
            }
            else
            {
                Set<Integer> set = new HashSet<>();
                set.add(userTime.time);
                map.put(userTime.id, set);
            }
        }

        for(int i=0;i<k;i++)
        {
            if(map.containsKey(i)) {
                System.out.print(map.get(i));
            }
            else
            {
                System.out.print(0);
            }
        }

    }
}