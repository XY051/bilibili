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
    
    // 生成全信息JSON文件
    @RequestMapping(value = "/generateFullJson", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> generateFullJson(HttpServletRequest request, HttpServletResponse response) {
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
            
            String aiResponse = aiService.generateFullInfoJson(videoId, videoPath);
            result.put("success", true);
            result.put("response", aiResponse);

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "生成全信息JSON失败: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    // 分析视频并结合JSON信息
    @RequestMapping(value = "/analyzeVideoWithJson", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> analyzeVideoWithJson(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            String videoPath = request.getParameter("videoPath");
            String videoIdStr = request.getParameter("videoId");
            String question = request.getParameter("question");
            
            if (videoPath == null || videoPath.trim().isEmpty() || videoIdStr == null || videoIdStr.trim().isEmpty() || question == null || question.trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "视频路径、视频ID和问题都不能为空");
                return result;
            }
            
            int videoId = Integer.parseInt(videoIdStr);
            
            // 1. 首先生成全信息JSON
            String jsonResult = aiService.generateFullInfoJson(videoId, videoPath);
            if (jsonResult.contains("失败")) {
                result.put("success", false);
                result.put("error", "生成JSON信息失败: " + jsonResult);
                return result;
            }
            
            // 2. 读取生成的JSON文件内容 - 使用与AiServiceimpl中一致的路径构建方式
            String videoFileName = new java.io.File(videoPath).getName();
            String videoNameWithoutExt = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
            // 对URL编码的文件名进行解码，处理中文文件名
            videoNameWithoutExt = java.net.URLDecoder.decode(videoNameWithoutExt, "UTF-8");
            
            // 构建与AiServiceimpl中一致的路径 - 使用json_all目录保存完整信息JSON
            String projectPath = System.getProperty("user.dir");
            if (projectPath == null || !projectPath.contains("bilibili")) {
                // 如果在开发环境，尝试构建路径
                projectPath = "C:/Course/Third/JavaMvc/bilibili/bilibili";
            }
            String webappPath = projectPath + java.io.File.separator + "src" + java.io.File.separator + "main" + java.io.File.separator + "webapp";
            String fullJsonDir = webappPath + java.io.File.separator + "static" + java.io.File.separator + "json_all";
            String fullJsonPath = fullJsonDir + java.io.File.separator + videoNameWithoutExt + "_full.json";
            
            String fullJsonContent = "";
            
            java.io.File jsonFile = new java.io.File(fullJsonPath);
            if (jsonFile.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(jsonFile, "UTF-8");
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append("\n");
                }
                scanner.close();
                fullJsonContent = sb.toString();
            } else {
                result.put("success", false);
                result.put("error", "JSON文件不存在: " + fullJsonPath + "，请先生成全信息JSON文件。生成的路径为: " + jsonResult);
                return result;
            }
            
            // 3. 将JSON内容和用户问题一起发送给AI
            String aiPrompt = "根据以下视频信息分析：\n\n视频JSON信息：\n" + fullJsonContent + "\n\n用户问题：\n" + question + "\n\n请根据JSON中的视频信息回答用户的问题。";
            String aiResponse = aiService.chatWithDeepSeek(aiPrompt);
            
            result.put("success", true);
            result.put("response", aiResponse);

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "分析视频失败: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}