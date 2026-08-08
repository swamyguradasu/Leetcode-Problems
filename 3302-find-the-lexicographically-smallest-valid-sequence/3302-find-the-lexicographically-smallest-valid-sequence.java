class Solution {
    public int[] validSequence(String word1, String word2) {
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();
        int n = w1.length;
        int m = w2.length;
        
        // rightMatch[i] stores the maximum length of a suffix of word2 
        // that can be perfectly matched in word1[i...n-1]
        int[] rightMatch = new int[n + 1];
        int j = m - 1;
        
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && w1[i] == w2[j]) {
                j--;
            }
            rightMatch[i] = m - 1 - j;
        }
        
        int[] ans = new int[m];
        int count = 0;
        boolean changed = false;
        j = 0;
        
        // Greedily pick the earliest possible indices
        for (int i = 0; i < n && j < m; i++) {
            if (w1[i] == w2[j]) {
                // Exact match: always optimally safe to take
                ans[count++] = i;
                j++;
            } else if (!changed && rightMatch[i + 1] >= m - 1 - j) {
                // Mismatch, but we haven't used our change yet AND 
                // the remaining string can perfectly satisfy the rest of word2.
                changed = true;
                ans[count++] = i;
                j++;
            }
        }
        
        // If we successfully mapped all required characters, return the array
        if (count == m) {
            return ans;
        }
        
        return new int[0];
    }
}