package com.sf.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.config.ApiConfig;
import com.sf.service.AiService;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceimpl implements AiService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String voiceToText(String audioFilePath) throws Exception
    {
        try {
            // 使用硅基流动 API 进行语音转文字
            OkHttpClient client = new OkHttpClient();

            File audioFile = new File(audioFilePath);
            RequestBody fileBody = RequestBody.create(audioFile, MediaType.parse("audio/wav"));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", audioFile.getName(), fileBody)
                    .addFormDataPart("model", "FunAudioLLM/SenseVoiceSmall")
                    .build();


            Request request = new Request.Builder()
                    .url(ApiConfig.SILICONFLOW_BASE_URL + "/audio/transcriptions")
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + ApiConfig.getApiKey())
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // 解析返回的 JSON 获取转录文本
                // 根据实际 API 返回格式解析
                return parseTranscriptionResult(responseBody);
            } else {
                throw new RuntimeException("语音转文字失败: " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("语音转文字处理失败: " + e.getMessage());
        }
    }
    private String parseTranscriptionResult(String jsonResponse) {
        // 根据实际 API 返回格式解析 JSON
        // 示例解析（需根据实际返回格式调整）
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            return rootNode.get("text").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "解析转录结果失败";
        }
    }
    @Override
    public String analyzeVideo(String videoContent) throws Exception {
        return chatWithDeepSeek("请分析这段视频内容并给出总结: " + videoContent);
    }
    @Override
    public String chatWithDeepSeek(String userMessage) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(ApiConfig.SILICONFLOW_BASE_URL + "/chat/completions");

            // 设置请求头
            post.setHeader("Authorization", "Bearer " + ApiConfig.getApiKey());
            post.setHeader("Content-Type", "application/json");

            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", ApiConfig.DEEPSEEK_MODEL);
            requestBody.put("messages", createMessages(userMessage));

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            StringEntity entity = new StringEntity(jsonBody, "UTF-8");
            post.setEntity(entity);

            // 执行请求
            HttpResponse response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);

            return parseResponse(responseString);
        }
    }

    // 私有方法，不暴露为接口方法
    private Object[] createMessages(String userMessage) {
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        return new Object[]{userMsg};
    }

    // 私有方法，不暴露为接口方法
    private String parseResponse(String response) throws Exception {
        // 解析JSON响应并返回AI回复内容
        Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
        Object choices = responseMap.get("choices");
        if (choices instanceof List) {
            List<?> choicesList = (List<?>) choices;
            if (!choicesList.isEmpty()) {
                Map<String, Object> firstChoice = (Map<String, Object>) choicesList.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                if (message != null) {
                    Object content = message.get("content");
                    if (content != null) {
                        return (String) content;
                    }
                }
            }
        }
        return "无法获取AI回复";
    }
}
