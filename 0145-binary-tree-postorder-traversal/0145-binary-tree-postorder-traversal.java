/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result =new ArrayList<>();
        Stack<TreeNode> stack=new Stack<>();
        TreeNode current =root;
        TreeNode LastVisited = null;
        while (current !=null || !stack.isEmpty()) {
            if(current !=null){
                stack.push(current);
                current = current.left;
            }else {
                TreeNode peekNode = stack.peek();
                if(peekNode.right!=null && LastVisited != peekNode.right){
                    current=peekNode.right;
                }else{
                    result.add(peekNode.val);
                    LastVisited = stack.pop();
                }
            }
        }
        return result;
    }
}