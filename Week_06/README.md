### 动态规划总结

**1、动态规划的核心思想分为两步：寻找分治+最优子结构<br/>
2、动态规划和递推或者分治没有根本上的区别（关键看有无最优子结构），他们之间的共性都是需要找到重复子问题，差异性是动态规划
需要寻找最优子结构，中途可以淘汰次优解<br/>
3、a.列出状态转移方程存储中间状态，所谓最优子结构（递推；自底向上），b.思考如何初始化<br/>
4、对于拿到一道新题要有一个系统的思维路程,比如三角形最短路径和<br/>
a.思维I: 暴力求解 n层遍历，每次遍历左右 复杂度2^n<br/>
b.思维II: DP：1、寻找重读性 2、定义状态数组并初始化 3、列出状态转移方程**

#### 一、最长公共子序列
https://leetcode-cn.com/problems/longest-common-subsequence/<br/>
```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                if (text1.charAt(i) == text2.charAt(j)) dp[i + 1][j + 1] = dp[i][j] + 1;
                else dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
            }
        }
        return dp[text1.length()][text2.length()];
    }
}
```
----
#### 二、三角形最小路径和
https://leetcode-cn.com/problems/triangle/description/<br/>
```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 0) return 0;
        int[][] dp = new int[triangle.size() + 1][triangle.size() + 1];
        for (int i = triangle.size() - 1; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }
}
```
----
#### 三、最大子序和
https://leetcode-cn.com/problems/maximum-subarray/<br/>
```java
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
```
----
#### 四、乘积最大子数组
https://leetcode-cn.com/problems/maximum-product-subarray/description/<br/>
```java
class Solution {
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        int max = nums[0], min = nums[0], result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = max;
            max = Math.max(nums[i], Math.max(nums[i] * max, nums[i] * min));
            min = Math.min(nums[i], Math.min(nums[i] * temp, nums[i] * min));
            result = Math.max(result, max);
        }
        return result;
    }
}
```
----
#### 五、零钱兑换
https://leetcode-cn.com/problems/coin-change/description/<br/>
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (coins.length == 0) return 0;
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }
}
```
----
#### 六、打家劫舍
https://leetcode-cn.com/problems/house-robber/<br/>
```java
class Solution {
    public int rob(int[] nums) {
        int prev1 = 0, prev2 = 0;
        for (int num : nums) {
            int temp = prev1;
            prev1 = Math.max(num + prev2, prev1);
            prev2 = temp;
        }
        return prev1;
    }
}
```
----
#### 七、打家劫舍 II
https://leetcode-cn.com/problems/house-robber-ii/description/<br/>
```java
class Solution {
    public int rob(int[] nums) {
        if (nums.length <= 1) return nums.length == 0 ? 0 : nums[0];
        return Math.max(rob(nums, 0, nums.length - 2), rob(nums, 1, nums.length - 1));
    }

    public int rob(int[] nums, int low, int high) {
        int prev1 = 0, prev2 = 0;
        for (int i = low; i <= high; i++) {
            int temp = prev1;
            prev1 = Math.max(prev1, prev2 + nums[i]);
            prev2 = temp;
        }
        return prev1;
    }
}
```
----
#### 八、买卖股票的最佳时机
https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/<br/>
```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length == 0) return 0;
        int i0 = 0;
        int i1 = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            i0 = Math.max(i0, i1 + prices[i]);
            i1 = Math.max(i1, -prices[i]);
        }
        return i0;
    }
}
```
----
#### 九、买卖股票的最佳时机 II
https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/<br/>
```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) return 0;
        int i0 = 0, i1 = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            i0 = Math.max(i0, i1 + prices[i]);
            i1 = Math.max(i1, i0 - prices[i]);
        }
        return i0;
    }
}
```
----
#### 十、买卖股票的最佳时机 III
https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/<br/>
```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length <= 1) return 0;
        int i10 = 0, i11 = -prices[0];
        int i20 = 0, i21 = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            i20 = Math.max(i20, i21 + prices[i]);
            i21 = Math.max(i21, i10 - prices[i]);
            i10 = Math.max(i10, i11 + prices[i]);
            i11 = Math.max(i11, - prices[i]);
        }
        return i20;
    }
}
```
----
#### 十一、卖股票的最佳时机 IV
https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iv/<br/>
```java
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices.length <= 1) return 0;
        if (k > prices.length / 2) return maxProfit(prices);
        int[][] dp = new int[k + 1][2];
        for (int i = 0; i <= k; i++) {
            dp[i][0] = 0;
            dp[i][1] = -prices[0];
        }
        for (int i = 1; i < prices.length; i++) {
            for (int j = k; j >= 1; j--) {
                dp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i]);
                dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i]);
            }
        }
        return dp[k][0];
    }

    private int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(prices[i] - prices[i - 1], 0) + max;
        }
        return max;
    }
}
```
----
#### 十二、最佳买卖股票时机含冷冻期
https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/<br/>
```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length < 2) return 0;
        int iPre = 0, i0 = 0, i1 = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            int temp = i0;
            i0 = Math.max(i0, i1 + prices[i]);
            i1 = Math.max(i1, iPre - prices[i]);
            iPre = temp;
        }
        return i0;
    }
}
```
----
#### 十三、买卖股票的最佳时机含手续费
https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/<br/>
```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        if (prices.length <= 1) return 0;
        int i0 = 0, i1 = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            i0 = Math.max(i0, i1 + prices[i] - fee);
            i1 = Math.max(i1, i0 - prices[i]);
        }
        return i0;
    }
}
```