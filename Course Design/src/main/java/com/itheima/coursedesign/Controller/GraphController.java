package com.itheima.coursedesign.Controller;

import com.itheima.coursedesign.Service.GraphAnalysisService;
import com.itheima.coursedesign.pojo.Edge;
import com.itheima.coursedesign.pojo.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    private GraphAnalysisService graphAnalysisService;

    @PostMapping("/analyzeFromFile")
    public Map<String, Object> analyzeFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> serviceResult = graphAnalysisService.analyzeFromFile(file.getInputStream());
        return buildEchartsResult(serviceResult);
    }

    // 通过文本输入分析，接收JSON格式：{"data": "1 2\n2 3\n..."}
    @PostMapping("/analyzeFromText")
    public Map<String, Object> analyzeFromText(@RequestBody Map<String, String> request) {
        String text = request.get("data");
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("输入数据不能为空");
        }
        Map<String, Object> serviceResult = graphAnalysisService.analyzeFromText(text);
        return buildEchartsResult(serviceResult);
    }

    // 构造 ECharts 需要的节点/边数据
    private Map<String, Object> buildEchartsResult(Map<String, Object> serviceResult) {
        Map<Vertex, Double> closenessMap = (Map<Vertex, Double>) serviceResult.get("closeness");
        Vertex bestVertex = (Vertex) serviceResult.get("bestVertex");
        List<Edge> edgeList = (List<Edge>) serviceResult.get("edges");

        List<Map<String, Object>> nodes = closenessMap.entrySet().stream().map(entry -> {
            Map<String, Object> node = new HashMap<>();
            Vertex v = entry.getKey();
            double closeness = entry.getValue();
            node.put("name", String.valueOf(v.getId()));
            node.put("value", closeness);
            node.put("symbolSize", 20 + closeness * 50);
            node.put("itemStyle", Map.of(
                    "color", v.equals(bestVertex) ? "red" : "blue"
            ));
            return node;
        }).collect(Collectors.toList());

        List<Map<String, Object>> links = edgeList.stream().map(e -> {
            Map<String, Object> link = new HashMap<>();
            link.put("source", String.valueOf(e.getStart()));
            link.put("target", String.valueOf(e.getEnd()));
            return link;
        }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("nodes", nodes);
        result.put("links", links);
        result.put("bestVertex", bestVertex != null ? bestVertex.getId() : null);
        return result;
    }






}