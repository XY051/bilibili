package com.sf.Maping;

import com.sf.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AiController {

    @Autowired
    private AiService aiService;

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> chat(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();

        try {
            String userMessage = request.getParameter("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                result.put("success", false);  // 添加此行
                result.put("error", "消息不能为空");
                return result;
            }

            String aiResponse = aiService.chatWithDeepSeek(userMessage);
          //  String aiResponse = "测试";
            result.put("success", true);
            result.put("response", aiResponse);

        } catch (Exception e) {
            result.put("success", false);  // 添加此行
            result.put("error", "AI服务调用失败: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/voiceToText", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> voiceToText(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();

        try {
            String userMessage = request.getParameter("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                result.put("success", false);  // 添加此行
                result.put("error", "消息不能为空");
                return result;
            }

            String aiResponse = aiService.voiceToText(userMessage);
            //  String aiResponse = "测试";
            result.put("success", true);
            result.put("response", aiResponse);

        } catch (Exception e) {
            result.put("success", false);  // 添加此行
            result.put("error", "AI服务调用失败: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // 处理视频评论AI分析
    @RequestMapping(value = "/videoAnalysis", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> analyzeVideo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            String videoContent = request.getParameter("content");
            String analysisPrompt = "请分析这段视频内容并给出总结: " + videoContent;

            String analysis = aiService.chatWithDeepSeek(analysisPrompt);
            result.put("success", true);
            result.put("analysis", analysis);

        } catch (Exception e) {
            result.put("error", "视频分析失败: " + e.getMessage());
        }

        return result;
    }
}
