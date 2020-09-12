### 递归-回溯 经典题型归纳
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
