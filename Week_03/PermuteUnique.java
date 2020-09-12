// 47. 全排列 II

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
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            used[i] = true;
            path.push(nums[i]);
            dfs(nums, used, path, result);
            used[i] = false;
            path.pop();
        }
    }
}