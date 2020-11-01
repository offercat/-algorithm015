// 32. 最长有效括号

class Solution {
    public int longestValidParentheses(String s) {
        int[] dp = new int[s.length()];
        char[] array = s.toCharArray();
        int max = 0;
        for (int i = 1; i < s.length(); i++) {
            if (array[i] == ')' && i - dp[i - 1] > 0 && array[i - dp[i - 1] - 1] == '(') {
                dp[i] = 2 + dp[i - 1] + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0);
                max = Math.max(max, dp[i]);
            }
        }
        return max;
    }
}