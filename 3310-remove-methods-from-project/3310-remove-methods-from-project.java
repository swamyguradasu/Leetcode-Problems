class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] inv : invocations) {
            adj[inv[0]].add(inv[1]);
        }
        
        boolean[] suspicious = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(k);
        suspicious[k] = true;
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : adj[curr]) {
                if (!suspicious[next]) {
                    suspicious[next] = true;
                    queue.offer(next);
                }
            }
        }
        
        boolean canRemove = true;
        for (int u = 0; u < n; u++) {
            if (!suspicious[u]) {
                for (int v : adj[u]) {
                    if (suspicious[v]) {
                        canRemove = false;
                        break;
                    }
                }
            }
            if (!canRemove) break;
        }
        
        List<Integer> result = new ArrayList<>();
        if (canRemove) {
            for (int i = 0; i < n; i++) {
                if (!suspicious[i]) {
                    result.add(i);
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                result.add(i);
            }
        }
        
        return result;
    }
}