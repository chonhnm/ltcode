package _3;

//Given a string, find the length of the longest substring without repeating characters.
//
//        Example 1:
//
//        Input: "abcabcbb"
//        Output: 3
//        Explanation: The answer is "abc", with the length of 3.

import java.util.HashMap;
import java.util.Map;

public class LongestSubString {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("bbbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring("a"));
        System.out.println(lengthOfLongestSubstring("abba"));
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        int result = 0;
        int cnt = 0;
        int start = 0;
        for (int idx = 0; idx < chars.length; idx++) {
            Integer oldIdx = map.get(chars[idx]);
            if (oldIdx == null || oldIdx < start) {
                cnt++;
                map.put(chars[idx], idx);
            } else {
                result = cnt > result ? cnt : result;
                map.put(chars[idx], idx);
                cnt = idx - oldIdx;
                start = oldIdx + 1;
            }
        }
        return cnt > result ? cnt : result;
    }

}
