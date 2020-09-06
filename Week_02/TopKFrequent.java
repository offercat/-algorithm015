// 347. 前 K 个高频元素

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> cache = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            if (!cache.containsKey(nums[i])) cache.put(nums[i], 0);
            cache.put(nums[i], cache.get(nums[i]) + 1);
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> cache.get(a) - cache.get(b));
        cache.forEach((key, count) -> {
            if (queue.size() < k) {
                queue.add(key);
            } else if (cache.get(queue.peek()) < count) {
                queue.remove();
                queue.add(key);
            }
        });
        int[] result = new int[k];
        for (int i=k-1; i>=0; i--) {
            result[i] = queue.poll();
        }
        return result;
    }
}