package com.itheima.coursedesign.Service;


import com.itheima.coursedesign.Graph.ClosenessCentrality;
import com.itheima.coursedesign.Graph.Graph;
import com.itheima.coursedesign.pojo.Edge;
import com.itheima.coursedesign.pojo.Vertex;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphAnalysisService {

    public Map<String, Object> analyzeByEdges(List<Edge> edges) {
        Graph graph = Graph.fromEdges(edges);

        Map<Vertex, Double> closeness = ClosenessCentrality.compute(graph);

        Vertex best = closeness.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        Map<String, Object> result = new HashMap<>();
        result.put("closeness", closeness);
        result.put("bestVertex", best);
        result.put("edges", graph.getAllEdges());
        return result;
    }

    // 示例：从文本文件中读取，格式：每行 "u v"
    public Map<String, Object> analyzeFromFile(InputStream in) throws IOException {
        List<Edge> edges = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length < 2) continue;
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                edges.add(new Edge(u, v, 1));
            }
        }
        return analyzeByEdges(edges);
    }

    // 从文本字符串解析，格式：每行 "u v"，用换行符分隔
    public Map<String, Object> analyzeFromText(String text) {
        List<Edge> edges = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+");
            if (parts.length < 2) continue;
            try {
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                edges.add(new Edge(u, v, 1));
            } catch (NumberFormatException e) {
                // 忽略无法解析的行
                continue;
            }
        }
        return analyzeByEdges(edges);
    }





}