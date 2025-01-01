package com.test;

import java.util.*;
import java.util.stream.Collectors;

class SOL {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Map.Entry<Integer,Integer>> entryList = new ArrayList<>(map.entrySet());
        //sort set by map.entry value
        entryList.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        entryList.forEach(System.out::println);
       Map<Integer, Integer>map1=  entryList.stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey, (e1, e2) -> e1, LinkedHashMap::new));
       return entryList.stream().limit(k).mapToInt(Map.Entry::getKey).toArray();

      //  return new int[4];


    }

    public static void main(String[] args) {
       int[] nums = {1,2,3,3,4,3,1,1,2,2,3}; int k = 2;
       int res[] = topKFrequent(nums,k);

       for(int m:res)
       {
           System.out.println(m);
       }
    }
}