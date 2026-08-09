class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[][] memo = new int[n][n + 1];
        int[] suffixSum = new int[n];
        
        // Precompute suffix sums
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }
        
        return helper(piles, memo, suffixSum, 0, 1);
    }
    
    private int helper(int[] piles, int[][] memo, int[] suffixSum, int i, int m) {
        // Base cases
        if (i == piles.length) return 0;
        // If we can take all remaining piles, take them all
        if (i + 2 * m >= piles.length) return suffixSum[i];
        
        // Return memoized result if calculated
        if (memo[i][m] != 0) return memo[i][m];
        
        int minOpponent = Integer.MAX_VALUE;
        
        // Explore all possible moves for X (1 to 2M)
        for (int x = 1; x <= 2 * m; x++) {
            minOpponent = Math.min(minOpponent, helper(piles, memo, suffixSum, i + x, Math.max(m, x)));
        }
        
        // Max stones current player can get = Total remaining - Min stones opponent gets
        memo[i][m] = suffixSum[i] - minOpponent;
        return memo[i][m];
    }
}