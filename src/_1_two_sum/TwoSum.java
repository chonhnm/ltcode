package _1_two_sum;

//Given an array of integers, return indices of the two numbers such that they add up to a specific target.
//
//        You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
//        Example:
//
//        Given nums = [2, 7, 11, 15], target = 9,
//
//        Because nums[0] + nums[1] = 2 + 7 = 9,
//        return [0, 1].

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,11,15,2,2};
        int target = 26;
        int[] ints = twoSum3(nums, target);
        if (ints != null) {
            for (int anInt : ints) {
                System.out.print(anInt + ", ");
            }
        } else {
            System.out.println("null");
        }
    }

    private static int[] twoSum(int[] nums, int target) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(target - num);
        }
        for (int i = 0; i < nums.length; i++) {
            int index = list.indexOf(nums[i]);
            if (index != -1 && index != i) {
                return new int[] {i, index};
            }
        }
        return null;
    }

    private static int[] twoSum2(int[] nums, int target) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(target-nums[i], k -> new ArrayList<>()).add(i);
        }
        for (int i = 0; i < nums.length; i++) {
            List<Integer> list = map.get(nums[i]);
            if (list != null) {
                for (Integer idx : list) {
                    if (idx != i) {
                        return new int[] {i, idx};
                    }
                }
            }
        }
        return null;
    }

    private static int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer complement = map.get(nums[i]);
            if (complement != null && complement != i) {
                return new int[] {complement, i};
            } else {
                map.put(target-nums[i], i);
            }
        }
        return null;
    }

    public static int[] twoSumBase(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[] { i, map.get(complement) };
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }


}
