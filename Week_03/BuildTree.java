// 105. 从前序与中序遍历序列构造二叉树

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLen = preorder.length;
        int inLen = inorder.length;
        if (preLen != inLen) return null;
        Map<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            cache.put(inorder[i], i);
        }
        return buildTree(preorder, 0, preLen - 1, cache, inorder, 0, inLen - 1);
    }

    private TreeNode buildTree(int[] preorder, int preLeft, int preRight, Map<Integer, Integer> cache, int[] inorder, int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) return null;
        int rootVal = preorder[preLeft];
        TreeNode root = new TreeNode(rootVal);
        int pIndex = cache.get(rootVal);

        root.left = buildTree(preorder, preLeft + 1, pIndex - inLeft + preLeft, cache, inorder, inLeft, pIndex - 1);
        root.right = buildTree(preorder, pIndex - inLeft + preLeft + 1, preRight, cache, inorder, pIndex + 1, inRight);
        return root;
    }
}