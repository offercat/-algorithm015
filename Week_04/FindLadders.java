// 126. 单词接龙 II

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