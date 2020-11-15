### 递归-回溯
**技巧总结：<br/>
1、一般现在主方法中构造结果，作为参数传递给递归方法<br/>
2、需要用一个集合 path 进行回溯，可以用List、Stack、Deque来做回溯，他们都有相应的方法删除Last元素<br/>
3、通常需要一个 level 变量记录当前所在的位置，没深入一层递归，需要将 level 加一并传入下一层<br/>
4、递归方法的写法有两种，一种是递归内加一层循环，来迭代处理所有当前 level 的所有情况；
另一种是纯粹的递归写法，只关心单个元素添加或者不添加，不断的递归尝试并回溯<br/>
5、构造结果时需要构造一个新的 ArrayList 对象，不能直接用 path 当做结果，因为 path 是引用，当返回上一层回溯时，
path就会改变，导致最后输出空集合的情况<br/>
6、也可以使用 clone 新 path 集合的方式避免回溯操作，但是这样，每一次递归都会构造新的 path 对象，比较消耗内存<br/>
7、如果需要处理的集合中有重复元素（一般为某一类题的 II 版本），对原集合进行排序不能忘记。
然后在递归中对 i 与 i - 1 元素值是否相同进行剪枝<br/>**

### 深度广度优先搜索
**要点总结：<br/>
1、深度和广度优先都有递归与非递归两种写法。<br/>
2、深度优先非递归写法一般需要手动维护栈，使用非递归写法，可以避免栈溢出的情况<br/>
3、广度优先非递归写法一般使用队列来维护层级，每次遍历之前都需要取队列的 size，然后进行遍历<br/>
4、现实问题的情况一般可以映射为状态分叉，最后所有的状态形成状态树或者状态图<br/>
5、图的遍历一般需要维护一个 visited 集合，记录访问过得节点，防止重复访问<br/>
6、图的广度优先遍历可以是双向的，双向广度优先遍历能有效提高搜索效率，例题有：最小基因变化、单词接龙等<br/>
7、剪枝能排除没必要的搜索，显著提高搜索效率，做题时应该时刻注意是否可以剪枝，怎么正确高效的剪枝**


### 动态规划总结
**1、动态规划的核心思想分为两步：寻找分治+最优子结构<br/>
2、动态规划和递推或者分治没有根本上的区别（关键看有无最优子结构），他们之间的共性都是需要找到重复子问题，差异性是动态规划
需要寻找最优子结构，中途可以淘汰次优解<br/>
3、a.列出状态转移方程存储中间状态，所谓最优子结构（递推；自底向上），b.思考如何初始化<br/>
4、对于拿到一道新题要有一个系统的思维路程,比如三角形最短路径和<br/>
a.思维I: 暴力求解 n层遍历，每次遍历左右 复杂度2^n<br/>
b.思维II: DP：1、寻找重读性 2、定义状态数组并初始化 3、列出状态转移方程**

### 经典题型：

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
#### 3、爬楼梯
https://leetcode-cn.com/problems/climbing-stairs/
<br/>**解题思路：用数组来保存斐波拉契序列
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(n)**
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
----
### 二、链表
#### 1、反转链表
https://leetcode-cn.com/problems/reverse-linked-list/
<br/>**解题思路：利用pre节点驱动反转
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while(cur != null) {
            ListNode a = cur.next;
            cur.next = pre;
            pre = cur;
            cur = a;
        }
        return pre;
    }
}
```
----
#### 2、两两交换链表中的节点
https://leetcode-cn.com/problems/swap-nodes-in-pairs/
<br/>**解题思路：理清节点交换思路即可
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode self = new ListNode(-1);
        ListNode prev = self;
        prev.next = head;
        ListNode cur = head;
        while(cur != null && cur.next != null) {
            ListNode first = cur.next;
            ListNode second = cur;
            cur = cur.next.next;

            prev.next = first;
            first.next = second;
            second.next = cur;

            prev = second;
        }
        return self.next;
    }
}
```
----
#### 3、K 个一组翻转链表
https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
<br/>**解题思路：综合链表反转，理清节点交换思路即可
<br/>时间复杂度：O(n)
<br/>空间复杂度：O(1)**
```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode self = new ListNode(-1);
        self.next = head;
        ListNode pre = self;
        ListNode end = self;

        while (end.next != null) {
            for (int i=0; i<k && end != null; i++) {
                end = end.next;
            }
            if (end == null) break;
            ListNode next = end.next;
            end.next = null;
            ListNode start = pre.next;
            pre.next = reverse(start);
            start.next = next;
            pre = start;
            end = start;
        }
        return self.next;
    }

    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
```


#### 1、子集
https://leetcode-cn.com/problems/subsets/<br/>
**循环处理单层**
```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        // 构造结果
        List<List<Integer>> result = new ArrayList<>();
        // path 可以用栈 Stack
        dfs(0, nums, new Stack<>(), result);
        return result;
    }
    
    // 循环处理单层
    private void dfs(int index, int[] nums, Stack<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path));
        for (int i = index; i < nums.length; i++) {
            path.push(nums[i]);
            dfs(i + 1, nums, path, result);
            path.pop();
        }
    }
}
```
**单个处理与尝试**
```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // path 可以用栈 List
        dfs(0, nums, new ArrayList<>(), result);
        return result;
    }

    // 单个处理与尝试
    private void dfs(int index, int[] nums, List<Integer> path, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        dfs(index + 1, nums, path, result);
        path.add(nums[index]);
        dfs(index + 1, nums, path, result);
        path.remove(path.size() - 1);
    }
}
```
----
#### 2、子集 II
https://leetcode-cn.com/problems/subsets-ii/<br/>
```java
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 排序，将相同元素放在一起
        Arrays.sort(nums);
        dfs(0, nums, new Stack<>(), result);
        return result;
    }

    private void dfs(int index, int[] nums, Stack<Integer> path, List<List<Integer>> result) {
        result.add(new ArrayList<>(path));
        for (int i = index; i < nums.length; i++) {
            // 剪枝，对比 nums[i] 与 nums[i - 1]
            if (i > index && nums[i] == nums[i - 1]) continue;
            path.push(nums[i]);
            dfs(i + 1, nums, path, result);
            path.pop();
        }
    }
}
```
----
#### 3、全排列
https://leetcode-cn.com/problems/permutations/<br/>
```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 0) return result;
        dfs(nums, new boolean[nums.length], new Stack<>(), result);
        return result;
    }

    private void dfs(int[] nums, boolean[] used, Stack<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i=0; i<nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            path.push(nums[i]);
            dfs(nums, used, path, result);
            used[i] = false;
            path.pop();
        }
    }
}
```
----
#### 4、全排列 II
https://leetcode-cn.com/problems/permutations-ii/<br/>
```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        dfs(nums, new boolean[nums.length], new Stack<>(), result);
        return result;
    }

    private void dfs(int[] nums, boolean[] used, Stack<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i=0; i<nums.length; i++) {
            if (used[i]) continue;
            // 剪枝，对比 nums[i] 与 nums[i - 1]
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            used[i] = true;
            path.push(nums[i]);
            dfs(nums, used, path, result);
            used[i] = false;
            path.pop();
        }
    }
}
```
----
#### 5、组合
https://leetcode-cn.com/problems/combinations/<br/>
**循环处理单层**
```java
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (n <= 0 || n < k) return result;
        Deque<Integer> deque = new ArrayDeque<>();
        dfs(n, k, 1, deque, result);
        return result;
    }

    private void dfs(int n, int k, int index, Deque<Integer> deque, List<List<Integer>> result) {
        if (deque.size() == k) {
            result.add(new ArrayList<>(deque));
            return;
        }
        for (int i = index; i <= n - (k - deque.size()) + 1; i++) {
            deque.addLast(i);
            dfs(n, k, i + 1, deque, result);
            deque.removeLast();
        }
    }
}
```
**单个处理与尝试**
```java
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        dfs(1, n, k, path, result);
        return result;
    }

    private void dfs(int i, int n, int k, Deque<Integer> path, List<List<Integer>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (i > n - k + 1) return;
        dfs(i + 1, n, k, path, result);
        path.add(i);
        dfs(i + 1, n, k - 1, path, result);
        path.pollLast();
    }
}
```


#### 1、括号生成
https://leetcode-cn.com/problems/generate-parentheses/<br/>
```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generate(0, 0, n, "", result);
        return result;
    }

    private void generate(int left, int right, int n, String path, List<String> result) {
        if (left == n && right == n) result.add(path);
        else {
            if (left < n) generate(left + 1, right, n, path + "(", result);
            if (right < left) generate(left, right + 1, n, path + ")", result);
        }
    }
}
```
----
#### 2、岛屿数量
https://leetcode-cn.com/problems/number-of-islands/<br/>
```java
class Solution {
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfsMarking(grid, i, j);
                }
            }
        }
        return count;
    }

    private void dfsMarking(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') return;
        grid[i][j] = '0';
        dfsMarking(grid, i + 1, j);
        dfsMarking(grid, i - 1, j);
        dfsMarking(grid, i, j + 1);
        dfsMarking(grid, i, j - 1);
    }
}
```
----
#### 3、最小基因变化
https://leetcode-cn.com/problems/minimum-genetic-mutation/<br/>
```java
class Solution {
    public int minMutation(String start, String end, String[] bank) {
        Set<String> bankSet = new HashSet<>();
        for (String b : bank) bankSet.add(b);
        if (!bankSet.contains(end)) return -1;

        Set<String> visited = new HashSet<>();
        visited.add(start);
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        char[] charArray = new char[]{'A', 'C', 'G', 'T'};

        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                char[] array = cur.toCharArray();
                for (int j = 0; j < array.length; j++) {
                    char old = array[j];
                    for (char c : charArray) {
                        array[j] = c;
                        String newStr = new String(array);
                        if (newStr.equals(end)) return count + 1;
                        if (bankSet.contains(newStr) && !visited.contains(newStr)) {
                            visited.add(newStr);
                            queue.add(newStr);
                        }
                    }
                    array[j] = old;
                }
            }
            count++;
        }
        return -1;
    }
}
```
----
#### 4、单词接龙
https://leetcode-cn.com/problems/word-ladder/<br/>
```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Set<String> start = new HashSet<>();
        Set<String> end = new HashSet<>();
        start.add(beginWord);
        end.add(endWord);
        dict.remove(beginWord);
        dict.remove(endWord);

        int length = 1;
        while (!start.isEmpty()) {
            Set<String> next = new HashSet<>();
            for (String word: start) {
                char[] array = word.toCharArray();
                for (int i = 0; i < array.length; i++) {
                    char old = array[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        array[i] = c;
                        String target = new String(array);
                        if (end.contains(target)) return length + 1;
                        if (dict.contains(target)) {
                            next.add(target);
                            dict.remove(target);
                        }
                    }
                    array[i] = old;
                }
            }
            length++;
            start = next.size() < end.size() ? next : end;
            end = start == next ? end : next;
        }
        return 0;
    }
}
```
----
#### 5、单词接龙 II
https://leetcode-cn.com/problems/word-ladder-ii/<br/>
```java
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return result;

        Map<String, List<String>> graph = new HashMap<>();
        Set<String> start = new HashSet<>();
        Set<String> end = new HashSet<>();
        Set<String> visited = new HashSet<>();
        start.add(beginWord);
        end.add(endWord);

        boolean found = false;
        boolean backward = false;
        while (!start.isEmpty()) {
            Set<String> next = new HashSet<>();
            for (String word : start) {
                visited.add(word);
                char[] array = word.toCharArray();
                for (int i = 0; i < array.length; i++) {
                    char old = array[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) continue;
                        array[i] = c;
                        String nextWord = String.valueOf(array);
                        if (!dict.contains(nextWord)) continue;
                        if (end.contains(nextWord)) found = true;
                        if (!visited.contains(nextWord) && !start.contains(nextWord)) {
                            next.add(nextWord);

                            String parent = backward ? nextWord : word;
                            String child = backward ? word : nextWord;
                            if (!graph.containsKey(parent)) {
                                graph.put(parent, new ArrayList<>());
                            }
                            graph.get(parent).add(child);
                        }
                    }
                    array[i] = old;
                }
            }
            if (found) break;
            if (next.size() < end.size()) {
                start = next;
            } else {
                start = end;
                end = next;
                backward = !backward;
            }
        }

        if (!found) return result;
        backtrack(beginWord, endWord, new Stack<>(), graph, result);
        return result;
    }

    private void backtrack(String beginWord, String endWord, Stack<String> stack, 
                           Map<String, List<String>> graph,  List<List<String>> result) {
        stack.push(beginWord);
        if (beginWord.equals(endWord)) result.add(new ArrayList<>(stack));
        else if (graph.containsKey(beginWord)) {
            for (String next : graph.get(beginWord)) {
                backtrack(next, endWord, stack, graph, result);
            }
        }
        stack.pop();
    }
}
```

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