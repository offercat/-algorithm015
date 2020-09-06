// 49. 字母异位词分组

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return Collections.emptyList();
        Map<String, List<String>> map = new HashMap<>();
        for (String s: strs) {
            char[] array = s.toCharArray();
            Arrays.sort(array);
            String key = String.valueOf(array);
            if (!map.containsKey(key)) map.put(key, new ArrayList<>());
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
}