### 深度广度优先搜索 经典题型

**要点总结：<br/>
1、深度和广度优先都有递归与非递归两种写法。<br/>
2、深度优先非递归写法一般需要手动维护栈，使用非递归写法，可以避免栈溢出的情况<br/>
3、广度优先非递归写法一般使用队列来维护层级，每次遍历之前都需要取队列的 size，然后进行遍历<br/>
4、现实问题的情况一般可以映射为状态分叉，最后所有的状态形成状态树或者状态图<br/>
5、图的遍历一般需要维护一个 visited 集合，记录访问过得节点，防止重复访问<br/>
6、图的广度优先遍历可以是双向的，双向广度优先遍历能有效提高搜索效率，例题有：最小基因变化、单词接龙等<br/>
7、剪枝能排除没必要的搜索，显著提高搜索效率，做题时应该时刻注意是否可以剪枝，怎么正确高效的剪枝**

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

