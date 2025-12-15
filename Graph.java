package com.itheima.coursedesign.Graph;

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
    // 添加顶点（通过ID）
    public void addVertex(Integer id) {
        Vertex v = new Vertex(id);
        addVertex(v);
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



}
