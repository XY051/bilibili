package com.sf.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sf.config.ApiConfig;
import com.sf.dao.JsonDao;
import com.sf.entity.jsonEntity;
import com.sf.service.AiService;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceimpl implements AiService {
    private static final Logger logger = LoggerFactory.getLogger(AiServiceimpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    private com.sf.service.Audioservice audioService;
    
    @Autowired
    private com.sf.service.Danmuservice danmuService;
    
    // Note: messageService was removed as it was not being used
    
    @Autowired
    private com.sf.service.UserListService userListService;
    
    @Autowired
    private com.sf.dao.JsonDao jsonDao;

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

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // 解析返回的 JSON 获取转录文本
                    // 根据实际 API 返回格式解析
                    return parseTranscriptionResult(responseBody);
                } else {
                    throw new RuntimeException("语音转文字失败: " + response.message());
                }
            }
        } catch (Exception e) {
            logger.error("语音转文字处理失败", e);
            throw new RuntimeException("语音转文字处理失败: " + e.getMessage());
        }
    }
    private String parseTranscriptionResult(String jsonResponse) {
        // 根据实际 API 返回格式解析 JSON
        // 示例解析（需根据实际返回格式调整）
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 配置安全选项以防止反序列化漏洞
            mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            JsonNode rootNode = mapper.readTree(jsonResponse);
            return rootNode.get("text").asText();
        } catch (Exception e) {
            logger.error("解析转录结果失败", e);
            return "解析转录结果失败";
        }
    }
    
    public String processVideoToText(String videoPath) throws Exception {
        try {
            // 1. 从视频路径获取视频文件名（不含扩展名）
            String videoFileName = new File(videoPath).getName();
            String videoNameWithoutExt = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
            
            // 2. 检查JSON文件是否存在
            String jsonDir = "src/main/webapp/static/json";
            String jsonPath = jsonDir + "/" + videoNameWithoutExt + ".json";
            File jsonFile = new File(jsonPath);
            
            // 3. 如果JSON文件存在，直接返回提示信息
            if (jsonFile.exists()) {
                return "该视频已转换过文字，JSON文件已存在";
            }
            
            // 4. 如果JSON文件不存在，先提取音频
            String audioDir = "src/main/webapp/static/audio";
            String audioPath = audioDir + "/" + videoNameWithoutExt + ".mp3";
            
            // 确保音频目录存在
            File audioDirectory = new File(audioDir);
            if (!audioDirectory.exists()) {
                boolean created = audioDirectory.mkdirs();
                if (!created) {
                    logger.warn("无法创建音频目录: {}", audioDir);
                }
            }
            
            // 检查音频文件是否存在，如果不存在则提取音频
            File audioFile = new File(audioPath);
            if (!audioFile.exists()) {
                // 提取音频的逻辑
                if (!extractAudioFromVideo(videoPath, audioPath)) {
                    throw new RuntimeException("提取音频失败");
                }
            }
            
            // 5. 将音频转为文字
            String transcription = voiceToText(audioPath);
            
            // 6. 将转录结果保存为JSON文件
            saveTranscriptionToJson(transcription, jsonPath);
            
            // 7. 返回成功信息
            return "音频转文字成功，JSON文件已保存到: " + jsonPath;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("处理视频转文字失败: " + e.getMessage());
        }
    }
    
    private boolean extractAudioFromVideo(String videoPath, String audioPath) {
        try {
            // 检查FFmpeg是否可用
            try {
                Encoder encoder = new Encoder();
                System.out.println("FFmpeg可用，开始转换...");
            } catch (Exception e) {
                System.out.println("FFmpeg不可用，请检查JAVE库配置: " + e.getMessage());
                e.printStackTrace();
                return false;
            }

            File source = new File(videoPath);
            File target = new File(audioPath);

            // 确保目标目录存在
            File targetDir = target.getParentFile();
            if (targetDir != null && !targetDir.exists()) {
                boolean created = targetDir.mkdirs();
                if (!created) {
                    System.out.println("无法创建音频目录: " + targetDir.getAbsolutePath());
                    return false;
                }
                System.out.println("已创建音频目录: " + targetDir.getAbsolutePath());
            }
            
            System.out.println("视频源路径: " + source.getAbsolutePath());
            System.out.println("音频目标路径: " + target.getAbsolutePath());
            
            System.out.println("源文件大小: " + source.length() + " 字节");

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame"); // 使用MP3编码

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(audio);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            
            System.out.println("音频转换完成");

            // 检查音频文件是否成功生成
            if (!target.exists() || target.length() == 0) {
                System.out.println("音频文件未成功生成或文件大小为0: " + target.getAbsolutePath());
                return false;
            }
            
            System.out.println("音频提取成功: " + target.getAbsolutePath());
            return true;
        } catch (EncoderException e) {
            System.out.println("音频转换失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("音频提取过程中发生未知错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void saveTranscriptionToJson(String transcription, String jsonPath) throws IOException {
        // 创建JSON目录（如果不存在）
        File jsonFile = new File(jsonPath);
        File jsonDir = jsonFile.getParentFile();
        if (jsonDir != null && !jsonDir.exists()) {
            boolean created = jsonDir.mkdirs();
            if (created) {
                System.out.println("创建JSON目录成功: " + jsonDir.getAbsolutePath());
            } else {
                System.out.println("创建JSON目录失败: " + jsonDir.getAbsolutePath());
            }
        }
        
        // 写入JSON文件
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(transcription);
            System.out.println("JSON文件写入成功: " + jsonFile.getAbsolutePath());
        }
    }
    @Override
    public String analyzeVideo(String videoContent) throws Exception {
        return chatWithDeepSeek("请分析这段视频内容并给出总结: " + videoContent);
    }
    // 用于检查视频对应的JSON文件是否存在，并进行音频转文字处理
    @Override
    public String processVideoToTextWithJsonCheck(int videoId, String videoPath) throws Exception {
        try {
            // 1. 从视频路径获取视频文件名（不含扩展名）
            String videoFileName = new File(videoPath).getName();
            String videoNameWithoutExt = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
            
            // 2. 检查JSON文件是否存在
            String jsonDir = "src/main/webapp/static/json";
            String jsonPath = jsonDir + "/" + videoNameWithoutExt + ".json";
            // 使用完整路径来检查文件是否存在
            String checkJsonPath = getWebAppPath() + File.separator + "static" + File.separator + "json" + File.separator + videoNameWithoutExt + ".json";
            File jsonFile = new File(checkJsonPath);
            
            // 3. 检查数据库中是否已存在对应音频ID的JSON记录
            jsonEntity existingJson = jsonDao.getJsonByJsonPath("/static/json/" + videoNameWithoutExt + ".json");
            
            // 4. 如果JSON文件存在且数据库中有记录，直接返回提示信息
            if (jsonFile.exists() && existingJson != null) {
                return "该视频已转换过文字，JSON文件已存在";
            }
            
            // 5. 如果JSON文件不存在，先提取音频
            String audioDir = "src/main/webapp/static/audio";
            String audioPath = audioDir + "/" + videoNameWithoutExt + ".mp3";
            
            // 确保音频目录存在
            File audioDirectory = new File(audioDir);
            if (!audioDirectory.exists()) {
                boolean created = audioDirectory.mkdirs();
                if (!created) {
                    logger.warn("无法创建音频目录: {}", audioDir);
                }
            }
            
            // 检查音频文件是否存在，如果不存在则提取音频
            File audioFile = new File(audioPath);
            if (!audioFile.exists()) {
                // 处理视频路径 - 将相对路径转换为绝对路径
                String absoluteVideoPath = buildAbsolutePath(getWebAppPath(), videoPath);
                
                // 检查视频文件是否存在
                File videoFile = new File(absoluteVideoPath);
                if (!videoFile.exists()) {
                    // 如果在webapp路径下没找到，尝试项目根路径
                    String projectPath = getProjectPath();
                    if (!projectPath.endsWith(File.separator)) {
                        projectPath += File.separator;
                    }
                    String altPath = projectPath + videoPath.substring(1).replace("/", File.separator);
                    videoFile = new File(altPath);
                    if (videoFile.exists()) {
                        absoluteVideoPath = altPath;
                    }
                }
                
                if (!videoFile.exists()) {
                    // 最后尝试直接拼接src/main/webapp路径
                    String directPath = "src" + File.separator + "main" + File.separator + "webapp" + videoPath.replace("/", File.separator);
                    videoFile = new File(directPath);
                    if (videoFile.exists()) {
                        absoluteVideoPath = directPath;
                    }
                }
                
                if (!videoFile.exists()) {
                    throw new RuntimeException("视频文件不存在: " + absoluteVideoPath);
                }
                
                // 提取音频的逻辑
                if (!extractAudioFromVideo(absoluteVideoPath, audioPath)) {
                    throw new RuntimeException("提取音频失败");
                }
            }
            
            // 6. 将音频转为文字
            String transcription = voiceToText(audioPath);
            
            // 7. 将转录结果保存为JSON文件
            String fullJsonPath = getWebAppPath() + File.separator + "static" + File.separator + "json" + File.separator + videoNameWithoutExt + ".json";
            saveTranscriptionToJson(transcription, fullJsonPath);
            
            // 8. 创建或获取音频记录
            // 首先检查数据库中是否存在对应的音频记录
            com.sf.entity.audioEntity audioInfo = null;
            
            // 尝试获取已存在的音频记录
            audioInfo = audioService.getAudioByVideoId(videoId);
            
            if (audioInfo == null) {
                // 如果没有找到音频记录，则创建一个新的
                audioInfo = new com.sf.entity.audioEntity();
                audioInfo.setVideo_id(videoId);
                audioInfo.setAudio_name(videoNameWithoutExt + ".mp3");
                audioInfo.setAudio_path("/static/audio/" + videoNameWithoutExt + ".mp3");
                audioInfo.setAudio_size(audioFile.length());
                audioInfo.setCreate_time(new java.util.Date());
                
                // 保存音频信息到数据库
                audioService.saveAudioInfo(audioInfo);
            }
            
            // 确保音频信息已保存，获取最新的音频ID
            if (audioInfo.getAudio_id() == 0) {
                // 如果audioInfo是新创建的，可能还没有ID，需要重新查询
                audioInfo = audioService.getAudioByVideoId(videoId);
            }
            
            // 9. 创建JSON记录并保存到数据库
            // 重新创建jsonFile对象以获取正确的文件大小
            File actualJsonFile = new File(fullJsonPath);
            jsonEntity jsonEntity = new jsonEntity();
            jsonEntity.setAudioId(audioInfo != null ? audioInfo.getAudio_id() : 0);
            jsonEntity.setJsonName(videoNameWithoutExt + ".json");
            jsonEntity.setJsonPath("/static/json/" + videoNameWithoutExt + ".json");
            jsonEntity.setJsonSize(actualJsonFile.length());
            
            boolean saved = jsonDao.saveJsonInfo(jsonEntity);
            
            // 10. 返回成功信息
            if (saved) {
                return "音频转文字成功，JSON文件已保存到: " + fullJsonPath + "，数据库记录已创建";
            } else {
                return "音频转文字成功，但数据库记录保存失败";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("处理视频转文字失败: " + e.getMessage());
        }
    }
    
    // 仅检查视频对应的JSON文件是否存在，不执行转换
    @Override
    public String checkVideoToTextStatus(int videoId, String videoPath) throws Exception {
        try {
            // 1. 从视频路径获取视频文件名（不含扩展名）
            String videoFileName = new File(videoPath).getName();
            String videoNameWithoutExt = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
            
            // 2. 检查JSON文件是否存在
            String jsonDir = "src/main/webapp/static/json";
            String jsonPath = jsonDir + "/" + videoNameWithoutExt + ".json";
            // 使用完整路径来检查文件是否存在
            String checkStatusJsonPath = getWebAppPath() + File.separator + "static" + File.separator + "json" + File.separator + videoNameWithoutExt + ".json";
            File jsonFile = new File(checkStatusJsonPath);
            
            // 3. 检查数据库中是否已存在对应音频ID的JSON记录
            jsonEntity existingJson = jsonDao.getJsonByJsonPath("/static/json/" + videoNameWithoutExt + ".json");
            
            // 4. 如果JSON文件存在且数据库中有记录，返回已存在信息
            if (jsonFile.exists() && existingJson != null) {
                return "该视频已转换过文字，JSON文件已存在";
            } else if (jsonFile.exists()) {
                // 如果文件存在但数据库中没有记录
                return "检测到JSON文件，但数据库记录不完整";
            } else if (existingJson != null) {
                // 如果数据库中有记录但文件不存在
                return "数据库中存在记录，但JSON文件不存在";
            } else {
                // 如果两者都不存在
                return "该视频尚未转换过文字，可以进行转换";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("检查视频转文字状态失败: " + e.getMessage());
        }
    }
    
    // 获取项目webapp路径
    private String getWebAppPath() {
        // 优先使用src/main/webapp路径以符合用户偏好
        String projectPath = System.getProperty("user.dir");
        if (projectPath == null || !projectPath.contains("bilibili")) {
            // 如果在开发环境，尝试构建路径
            projectPath = "C:/Course/Third/JavaMvc/bilibili/bilibili";
        }
        return projectPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp";
    }
    
    // 获取项目根路径
    private String getProjectPath() {
        String projectPath = System.getProperty("user.dir");
        if (projectPath == null || !projectPath.contains("bilibili")) {
            projectPath = "C:/Course/Third/JavaMvc/bilibili/bilibili";
        }
        return projectPath;
    }
    
    // Java 8 兼容的文件读取方法
    private String readFileContent(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (content.length() > 0) {
                    content.append("\n");
                }
                content.append(line);
            }
        }
        return content.toString();
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
    
    @Override
    public String generateFullInfoJson(int videoId, String videoPath) throws Exception {
        try {
            // 1. 从视频路径获取视频文件名（不含扩展名）
            String videoFileName = new File(videoPath).getName();
            String videoNameWithoutExt = videoFileName.substring(0, videoFileName.lastIndexOf('.'));
            
            // 2. 读取原始的视频转文字JSON文件
            String jsonDir = "src/main/webapp/static/json";
            String jsonPath = jsonDir + "/" + videoNameWithoutExt + ".json";
            File jsonFile = new File(jsonPath);
            
            String videoTranscript;
            if (jsonFile.exists()) {
                // 读取现有JSON内容 - 使用Java 8兼容方式
                videoTranscript = readFileContent(jsonFile);
                if (videoTranscript == null) {
                    videoTranscript = "";
                }
            } else {
                // 如果没有转文字文件，先进行视频转文字
                String audioDir = "src/main/webapp/static/audio";
                String audioPath = audioDir + "/" + videoNameWithoutExt + ".mp3";
                
                // 确保音频目录存在
                File audioDirectory = new File(audioDir);
                if (!audioDirectory.exists()) {
                    audioDirectory.mkdirs();
                }
                
                // 检查音频文件是否存在，如果不存在则提取音频
                File audioFile = new File(audioPath);
                if (!audioFile.exists()) {
                    // 处理视频路径 - 将相对路径转换为绝对路径
                    String absoluteVideoPath = buildAbsolutePath(getWebAppPath(), videoPath);
                    
                    // 检查视频文件是否存在
                    File videoFile = new File(absoluteVideoPath);
                    if (!videoFile.exists()) {
                        // 如果在webapp路径下没找到，尝试项目根路径
                        String projectPath = getProjectPath();
                        if (!projectPath.endsWith(File.separator)) {
                            projectPath += File.separator;
                        }
                        String altPath = projectPath + videoPath.substring(1).replace("/", File.separator);
                        videoFile = new File(altPath);
                        if (videoFile.exists()) {
                            absoluteVideoPath = altPath;
                        }
                    }
                    
                    if (!videoFile.exists()) {
                        // 最后尝试直接拼接src/main/webapp路径
                        String directPath = "src" + File.separator + "main" + File.separator + "webapp" + videoPath.replace("/", File.separator);
                        videoFile = new File(directPath);
                        if (videoFile.exists()) {
                            absoluteVideoPath = directPath;
                        }
                    }
                    
                    if (!videoFile.exists()) {
                        throw new RuntimeException("视频文件不存在: " + absoluteVideoPath);
                    }
                    
                    // 提取音频的逻辑
                    if (!extractAudioFromVideo(absoluteVideoPath, audioPath)) {
                        throw new RuntimeException("提取音频失败");
                    }
                }
                
                // 将音频转为文字
                videoTranscript = voiceToText(audioPath);
                
                // 将转录结果保存为JSON文件
                saveTranscriptionToJson(videoTranscript, jsonPath);
            }
            
            // 3. 获取弹幕数据
            List<com.sf.entity.Danmu> danmuList = danmuService.readDanmuByVid(videoId);
            
            // 确保弹幕列表不为null
            if (danmuList == null) {
                danmuList = java.util.Collections.emptyList();
            }
            
            // 4. 获取评论数据
            // 通过查询数据库获取与视频相关的评论
            List<com.sf.entity.messageEntity> messageList = userListService.messagelist(String.valueOf(videoId));
            
            // 确保评论列表不为null
            if (messageList == null) {
                messageList = java.util.Collections.emptyList();
            }
            
            // 5. 构建完整信息JSON
            ObjectMapper mapper = new ObjectMapper();
            // 设置JSON格式化选项
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            
            // 格式化视频转录文本 - 每达到一定长度就换行
            String formattedVideoTranscript = formatTextWithLineBreaks(videoTranscript, 80);
            
            // 格式化弹幕列表 - 每个弹幕单独一行
            List<Map<String, Object>> formattedDanmuList = new ArrayList<>();
            if (danmuList != null) {
                for (com.sf.entity.Danmu danmu : danmuList) {
                    Map<String, Object> danmuMap = new HashMap<>();
                    danmuMap.put("id", danmu.getVid());
                    danmuMap.put("time", danmu.getDtime());
                    danmuMap.put("text", danmu.getContent());
                    danmuMap.put("color", danmu.getColor());
                    danmuMap.put("type", danmu.getPosition());
                    danmuMap.put("size", danmu.getDsize());
                    danmuMap.put("uid", danmu.getVid());
                    danmuMap.put("create_time", danmu.getDtime());
                    formattedDanmuList.add(danmuMap);
                }
            }
            
            // Format comment list - each comment on a separate line
            List<Map<String, Object>> formattedMessageList = new ArrayList<>();
            if (messageList != null) {
                for (com.sf.entity.messageEntity message : messageList) {
                    Map<String, Object> messageMap = new HashMap<>();
                    messageMap.put("messageId", message.getMessageID());
                    messageMap.put("messageText", message.getMessage());
                    messageMap.put("messageTime", message.getMessageTime());
                    messageMap.put("messageName", message.getMessageuserName());
                    messageMap.put("messageHead", message.getMessageHand());
                    messageMap.put("messageUid", message.getMessageuserID());
                    formattedMessageList.add(messageMap);
                }
            }
            
            Map<String, Object> fullInfo = new HashMap<>();
            fullInfo.put("videoTranscript", formattedVideoTranscript);
            fullInfo.put("danmuList", formattedDanmuList);
            fullInfo.put("messageList", formattedMessageList);
            
            // 6. Create json_all directory (if not exists) - According to user preference, save full info JSON to json_all directory
            String webappPath = getWebAppPath();
            String fullJsonDir = webappPath + File.separator + "static" + File.separator + "json_all";
            File fullJsonDirectory = new File(fullJsonDir);
            if (!fullJsonDirectory.exists()) {
                boolean created = fullJsonDirectory.mkdirs();
                if (created) {
                    logger.info("Successfully created json_all directory: {}", fullJsonDirectory.getAbsolutePath());
                } else {
                    logger.warn("Failed to create json_all directory or directory already exists: {}", fullJsonDirectory.getAbsolutePath());
                }
            }
            
            // 7. Generate full information JSON file
            String fullJsonPath = fullJsonDir + File.separator + videoNameWithoutExt + "_full.json";
            String fullJsonContent = mapper.writeValueAsString(fullInfo);
            
            // 8. Write full information JSON file - This will overwrite the file if it exists
            try (FileWriter writer = new FileWriter(fullJsonPath)) {
                writer.write(fullJsonContent);
                System.out.println("Successfully wrote full information JSON file: " + fullJsonPath);
            }
            
            return "Full information JSON generated successfully, file saved to: " + fullJsonPath;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate full information JSON: " + e.getMessage());
        }
    }
    
    // Format text, add line break when reaching specified length
    private String formatTextWithLineBreaks(String text, int maxLength) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        int currentLength = 0;
        
        for (char c : text.toCharArray()) {
            if (currentLength >= maxLength && (c == ' ' || c == '.' || c == ',' || c == '。' || c == '，')) {
                result.append('\n');
                currentLength = 0;
            } else {
                result.append(c);
                currentLength++;
            }
        }
        
        return result.toString();
    }
    
    // 提取构建绝对路径的公共方法
    private String buildAbsolutePath(String basePath, String relativePath) {
        String absolutePath;
        if (relativePath.startsWith("/")) {
            // 移除开头的斜杠并替换所有斜杠为文件分隔符
            absolutePath = basePath + File.separator + relativePath.substring(1).replace("/", File.separator);
        } else {
            absolutePath = basePath + File.separator + relativePath.replace("/", File.separator);
        }
        return absolutePath;
    }
}
