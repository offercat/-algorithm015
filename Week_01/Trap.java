//42. 接雨水

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