// 208. 实现 Trie (前缀树)

class Trie {

    private TrieNode root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        char[] array = word.toCharArray();
        TrieNode cur = root;
        for (char c : array) {
            int i = c - 'a';
            if (cur.children[i] == null) {
                cur.children[i] = new TrieNode();
            }
            cur = cur.children[i];
        }
        cur.isWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        char[] array = word.toCharArray();
        TrieNode cur = root;
        for (char c : array) {
            int i = c - 'a';
            if (cur.children[i] == null) return false;
            cur = cur.children[i];
        }
        return cur.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        char[] array = prefix.toCharArray();
        TrieNode cur = root;
        for (char c : array) {
            int i = c - 'a';
            if (cur.children[i] == null) return false;
            cur = cur.children[i];
        }
        return cur != null;
    }

    private static class TrieNode {
        private boolean isWord;
        private TrieNode[] children = new TrieNode[26];
    }
}