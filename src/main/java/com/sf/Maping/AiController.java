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
    
    // 处理视频音频转文字请求
    @RequestMapping(value = "/videoToText", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> videoToText(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();

        try {
            String videoPath = request.getParameter("videoPath");
            String videoIdStr = request.getParameter("videoId");
            
            if (videoPath == null || videoPath.trim().isEmpty() || videoIdStr == null || videoIdStr.trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "视频路径和视频ID不能为空");
                return result;
            }
            
            int videoId = Integer.parseInt(videoIdStr);
            
            String aiResponse = aiService.processVideoToTextWithJsonCheck(videoId, videoPath);
            result.put("success", true);
            result.put("response", aiResponse);

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "视频转文字服务调用失败: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // 检查视频转文字状态
    @RequestMapping(value = "/checkVideoToTextStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkVideoToTextStatus(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            String videoPath = request.getParameter("videoPath");
            String videoIdStr = request.getParameter("videoId");
            
            if (videoPath == null || videoPath.trim().isEmpty() || videoIdStr == null || videoIdStr.trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "视频路径和视频ID不能为空");
                return result;
            }
            
            int videoId = Integer.parseInt(videoIdStr);
            
            String status = aiService.checkVideoToTextStatus(videoId, videoPath);
            result.put("success", true);
            result.put("response", status);

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "检查视频转文字状态失败: " + e.getMessage());
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
