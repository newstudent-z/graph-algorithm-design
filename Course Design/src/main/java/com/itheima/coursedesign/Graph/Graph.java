package com.itheima.coursedesign.Graph;

import com.itheima.coursedesign.pojo.Edge;
import com.itheima.coursedesign.pojo.Vertex;
import java.util.*;

public class Graph {
    private Map<Vertex, List<Vertex>> graph;

    public Graph(){
        graph = new HashMap<>();
    }


    // 添加顶点
    public void addVertex(Vertex v){
        if(!graph.containsKey(v)){
            graph.put(v, new ArrayList<>());
        }
    }
    // 无向图
    public void addEdge(Vertex v, Vertex w){
        addVertex(v);
        addVertex(w);
        graph.get(v).add(w);
        graph.get(w).add(v);
    }

    //遍历所有顶点
    public Set<Vertex> getAllVertex(){
        return graph.keySet();
    }

    //获取所有边
    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        Set<String> seen = new HashSet<>(); // 防止无向边重复

        for (Map.Entry<Vertex, List<Vertex>> entry : graph.entrySet()) {
            Vertex from = entry.getKey();
            for (Vertex to : entry.getValue()) {
                String key = Math.min(from.getId(), to.getId())
                        + "-" + Math.max(from.getId(), to.getId());
                if (!seen.contains(key)) {
                    seen.add(key);
                    edges.add(new Edge(from.getId(), to.getId(), 1));
                }
            }
        }
        return edges;
    }


    public Map<Vertex, Integer> bfs(Vertex start){
        Map<Vertex, Integer> distance = new HashMap<>();
        for (Vertex v: graph.keySet()){
            distance.put(v, Integer.MAX_VALUE);
        }
        distance.put(start, 0);

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()){
            Vertex curVertex = queue.poll();
            for(Vertex nextVertex : graph.get(curVertex)){
                if(distance.get(nextVertex) == Integer.MAX_VALUE){
                    distance.put(nextVertex, distance.get(curVertex) + 1);
                    queue.offer(nextVertex);
                }
            }
        }
        return distance;
    }


    //通过边集合构建图
    public static Graph fromEdges(List<Edge> edges) {
        Graph g = new Graph();
        for (Edge e : edges) {
            g.addEdge(new Vertex(e.getStart()), new Vertex(e.getEnd()));
        }
        return g;
    }
}
