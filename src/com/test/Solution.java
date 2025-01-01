package com.test;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public static int lengthOfLongestSubstring(String s) {

        Map<Character, Integer> map = new HashMap();
        if (s.length() == 1) return 1;
        int n = s.length();
        int start = 0;
        String res = "";
        int longest = 0;
        int resL = 0;
        while (start < n) {
            if (!map.containsKey(s.charAt(start))) {
                res = res + s.charAt(start);
                map.put(s.charAt(start), 1);
                resL = res.length();
            } else {
                resL = res.length();
                String newRes = res.substring(res.indexOf(s.charAt(start)) + 1, resL) + s.charAt(start);
                System.out.println(newRes);
                String remove = res.substring(0, res.indexOf(s.charAt(start)));

                System.out.println(remove);
                int l = remove.length() - 1;
                while (l >= 0) {
                    map.remove(remove.charAt(l--));
                }
                longest = Math.max(longest, resL);
                res = newRes;
            }
            start++;

        }


        return longest;
    }

    public static void main(String[] args) {
        String s = "tmmzuxt";
        System.out.println(lengthOfLongestSubstring(s));
    }
}