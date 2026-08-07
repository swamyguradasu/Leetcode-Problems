class Solution {
    public String smallestNumber(String num, long t) {
        long temp = t;
        int reqA = 0, reqB = 0, reqC = 0, reqD = 0;
        
        // Step 1: Prime factorization of t
        while (temp % 2 == 0) { reqA++; temp /= 2; }
        while (temp % 3 == 0) { reqB++; temp /= 3; }
        while (temp % 5 == 0) { reqC++; temp /= 5; }
        while (temp % 7 == 0) { reqD++; temp /= 7; }
        
        // If t has prime factors other than 2, 3, 5, 7, it's impossible
        if (temp > 1) return "-1";

        // factor count for digits 0-9: {count of 2s, 3s, 5s, 7s}
        int[][] f = new int[10][4];
        f[2] = new int[]{1, 0, 0, 0};
        f[3] = new int[]{0, 1, 0, 0};
        f[4] = new int[]{2, 0, 0, 0};
        f[5] = new int[]{0, 0, 1, 0};
        f[6] = new int[]{1, 1, 0, 0};
        f[7] = new int[]{0, 0, 0, 1};
        f[8] = new int[]{3, 0, 0, 0};
        f[9] = new int[]{0, 2, 0, 0};

        int n = num.length();
        int[] pA = new int[n + 1];
        int[] pB = new int[n + 1];
        int[] pC = new int[n + 1];
        int[] pD = new int[n + 1];

        // Step 2: Calculate prefix factors up to the first '0'
        int zIdx = n;
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            if (d == 0 && zIdx == n) {
                zIdx = i;
            }
            if (zIdx == n) {
                pA[i + 1] = pA[i] + f[d][0];
                pB[i + 1] = pB[i] + f[d][1];
                pC[i + 1] = pC[i] + f[d][2];
                pD[i + 1] = pD[i] + f[d][3];
            }
        }

        // Check if the original number already satisfies the condition
        if (zIdx == n && pA[n] >= reqA && pB[n] >= reqB && pC[n] >= reqC && pD[n] >= reqD) {
            return num;
        }

        // Step 3: Search for the longest valid prefix
        int startI = Math.min(n - 1, zIdx);
        for (int i = startI; i >= 0; i--) {
            int cDig = num.charAt(i) - '0';
            
            // Try replacing num[i] with a strictly greater digit
            for (int dig = cDig + 1; dig <= 9; dig++) {
                int remA = Math.max(0, reqA - pA[i] - f[dig][0]);
                int remB = Math.max(0, reqB - pB[i] - f[dig][1]);
                int remC = Math.max(0, reqC - pC[i] - f[dig][2]);
                int remD = Math.max(0, reqD - pD[i] - f[dig][3]);

                // If the remaining required factors can fit in the leftover length
                if (minLen(remA, remB, remC, remD) <= n - 1 - i) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num.substring(0, i));
                    sb.append(dig);
                    sb.append(buildSuffix(remA, remB, remC, remD, n - 1 - i, f));
                    return sb.toString();
                }
            }
        }

        // Step 4: If no solution of the same length exists, build a longer one
        int reqLen = Math.max(n + 1, minLen(reqA, reqB, reqC, reqD));
        return buildSuffix(reqA, reqB, reqC, reqD, reqLen, f);
    }

    // Calculates the minimum number of digits needed to fulfill the remaining prime factors
    private int minLen(int a, int b, int c, int d) {
        int ans = a + b; 
        for (int k = 0; k <= 50; k++) { // k represents the number of '6's used
            int remA = Math.max(0, a - k);
            int remB = Math.max(0, b - k);
            
            // remaining 2s handled optimally with '8's (three 2s per digit)
            // remaining 3s handled optimally with '9's (two 3s per digit)
            int req = k + (remA + 2) / 3 + (remB + 1) / 2;
            ans = Math.min(ans, req);
        }
        return c + d + ans; // '5' and '7' inherently cost 1 digit each
    }

    // Greedily constructs the lexicographically smallest suffix of a fixed length
    private String buildSuffix(int a, int b, int c, int d, int len, int[][] f) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            for (int dig = 1; dig <= 9; dig++) {
                int nA = Math.max(0, a - f[dig][0]);
                int nB = Math.max(0, b - f[dig][1]);
                int nC = Math.max(0, c - f[dig][2]);
                int nD = Math.max(0, d - f[dig][3]);

                // Pick the first (smallest) digit that leaves a valid solvable state
                if (minLen(nA, nB, nC, nD) <= len - 1 - i) {
                    sb.append(dig);
                    a = nA; b = nB; c = nC; d = nD;
                    break;
                }
            }
        }
        return sb.toString();
    }
}