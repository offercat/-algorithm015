// 91. 解码方法

class Solution {
    public int numDecodings(String s) {
        char[] array = s.toCharArray();
        int[] dp = new int[array.length + 1];
        dp[array.length] = 1;
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] != '0') {
                dp[i] = dp[i + 1];
                if (i < array.length - 1 && (array[i] == '1' || array[i] == '2' && array[i + 1] < '7')) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }
}