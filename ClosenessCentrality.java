package com.itheima.coursedesign.Graph;

import com.itheima.coursedesign.pojo.Vertex;
import java.util.HashMap;
import java.util.Map;

public class ClosenessCentrality {
    public static Map<Vertex, Double> compute(Graph graph){
        Map<Vertex, Double> result = new HashMap<>();
        int n = graph.getAllVertex().size();

        for(Vertex v : graph.getAllVertex()){
            Map<Vertex, Integer> distance = graph.bfs(v);
            double sum = 0;
            for(int num : distance.values()){
                sum += num;
            }
            double CloseNess = (double)(n-1)/sum;
            result.put(v, CloseNess);
        }
        return result;
    }
}
