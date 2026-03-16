package com.itheima.coursedesign.Graph;

import com.itheima.coursedesign.pojo.Vertex;
import java.util.HashMap;
import java.util.Map;

public class ClosenessCentrality {
    public static Map<Vertex, Double> compute(Graph graph){
        Map<Vertex, Double> result = new HashMap<>();
        int n = graph.getAllVertex().size();

        for (Vertex v : graph.getAllVertex()) {
            Map<Vertex, Integer> distance = graph.bfs(v);
            double sum = 0;
            boolean unreachable = false;

            for (Map.Entry<Vertex, Integer> entry : distance.entrySet()) {
                Vertex u = entry.getKey();
                int d = entry.getValue();

                if (u.equals(v)) {
                    continue; // 跳过自己
                }
                if (d == Integer.MAX_VALUE) { // 不可达
                    unreachable = true;
                    break;
                }
                sum += d;
            }

            double closeness;
            if (unreachable || sum == 0) {
                closeness = 0.0; // 非连通时设为 0
            } else {
                closeness = (double) (n - 1) / sum; // 经典定义
            }
            result.put(v, closeness);
        }
        return result;
    }
}
