## 【LeetCode 经典题注】数组 & 链表
### 一、数组
#### 1、两数之和
https://leetcode-cn.com/problems/two-sum/
<br/>**解题思路：使用map作为缓存
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (cache.containsKey(complement)) {
                return new int[] { cache.get(complement), i };
            }
            cache.put(nums[i], i);
        }
        return null;
    }
}
```
----
#### 2、三数之和
https://leetcode-cn.com/problems/3sum/
<br/>**解题思路：夹逼法
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public int climbStairs(int n) {
        if (n == 1) return 1;
        int[] a = new int[n+1];
        a[1] = 1;
        a[2] = 2;
        for (int i=3; i<=n; i++) {
            a[i] = a[i-1] + a[i-2];
        }
        return a[n];
    }
}
```
----
#### 3、爬楼梯
https://leetcode-cn.com/problems/climbing-stairs/
<br/>**解题思路：用数组来保存斐波拉契序列
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(n)**
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int k=0; k<nums.length-2; k++) {
            if (nums[k] > 0) break;
            if (k > 0 && nums[k] == nums[k - 1]) continue;
            int i = k+1, j = nums.length-1;
            while (i < j) {
                int sum = nums[k] + nums[i] + nums[j];
                if (sum < 0) {
                    while (i < j && nums[i] == nums[++i]);
                } else if (sum > 0) {
                    while (i < j && nums[j] == nums[--j]);
                } else {
                    result.add(Arrays.asList(nums[k], nums[i], nums[j]));
                    while (i < j && nums[i] == nums[++i]);
                    while (i < j && nums[j] == nums[--j]);
                }
            }
        }
        return result;
    }
}
```
----
#### 4、合并两个有序数组
https://leetcode-cn.com/problems/merge-sorted-array/
<br/>**解题思路：双指针+从后往前复制
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            nums1[p--] = nums1[p1] > nums2[p2] ? nums1[p1--] : nums2[p2--];
        }
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }
}
```
----
#### 5、移动零
https://leetcode-cn.com/problems/move-zeroes/submissions/
<br/>**解题思路：双指针法
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public void moveZeroes(int[] nums) {
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                if (i != j) {
                    nums[i] = 0;
                }
                j++;
            }
        }
    }
}
```
----
#### 6、盛最多水的容器
https://leetcode-cn.com/problems/container-with-most-water/
<br/>**解题思路：夹逼法
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public int maxArea(int[] height) {
        int max = 0;
        for (int i=0, j=height.length-1; i<j; ) {
            int minHeight = height[i] < height[j] ? height[i++] : height[j--];
            max = Math.max(max, minHeight * (j - i + 1));
        }
        return max;
    }
}
```
----
#### 7、滑动窗口最大值
https://leetcode-cn.com/problems/sliding-window-maximum/
<br/>**解题思路：双端队列维持最大值
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(k)**
```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> window = new LinkedList<>();
        for (int i=0; i<nums.length; i++) {
            if (i >= k && window.peekLast() <= i - k) {
                window.pollLast();
            }
            while (!window.isEmpty() && nums[window.peekFirst()] <= nums[i]) {
                window.pollFirst();
            }
            window.addFirst(i);
            if (i >= k - 1) {
                result[i - k + 1] = nums[window.peekLast()];
            }
         }
         return result;
    }
}
```
----
#### 8、柱状图中最大的矩形
https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
<br/>**解题思路：使用栈来记录左右边界，然后求面积最大值
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(n)**
```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);
        Stack<Integer> stack = new Stack<>();
        for (int i=0; i<n; i++) {
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                right[stack.pop()] = i;
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        int ans = 0;
        for (int i=0; i<n; i++) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }
}
```
----
#### 9、接雨水
https://leetcode-cn.com/problems/trapping-rain-water/
<br/>**和上一题（柱状图中最大的矩形）类似，使用栈来记录左右边界
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(n)**
```java
class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int ans = 0;
        for (int i=0; i<height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int top = stack.pop();
                if (stack.isEmpty()) break;
                int instance = i - stack.peek() - 1;
                int hei = Math.min(height[i], height[stack.peek()]) - height[top];
                ans += instance * hei;
            }
            stack.push(i);
        }
        return ans;
    }
}
```