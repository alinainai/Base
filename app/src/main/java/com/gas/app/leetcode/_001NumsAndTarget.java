package com.gas.app.leetcode;

import java.util.HashMap;
import java.util.Map;

public class _001NumsAndTarget {

    public static void main(String[] args) {

    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> targetMap = new HashMap<>();
        int[] result = new int[2];
        for(int i = 0;i< nums.length;i++){
            if(targetMap.isEmpty()){
                targetMap.put(nums[i],i);
            }else{
                if(targetMap.containsKey(target - nums[i])){
                    //noinspection ConstantConditions
                    result[0] = targetMap.get(target - nums[i]);
                    result[1] = i;
                }else{
                    targetMap.put(nums[i],i);
                }
            }
        }
       return result;
    }

}
