// 127. 单词接龙

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