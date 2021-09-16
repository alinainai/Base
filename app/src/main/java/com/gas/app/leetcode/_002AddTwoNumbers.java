package com.gas.app.leetcode;

import java.util.HashMap;
import java.util.Map;

public class _002AddTwoNumbers {

    public static void main(String[] args) {
        System.out.println( lengthOfLongestSubstring("abcabcbb"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int max = 0;
        char[] chars = s.toCharArray();
        int index = 0;
        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            if(map.containsKey(chars[i]) && map.get(chars[i])>=index){
                int size= i-index;
                max = Math.max(max,size);
                index = map.get(chars[i])+1;
            }
            map.put(chars[i],i);
        }
        return max;
    }
}
