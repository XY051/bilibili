package com.sf.service;

public interface AiService {
    String chatWithDeepSeek(String userMessage) throws Exception;
    String analyzeVideo(String videoContent) throws Exception;
    String voiceToText(String audioFilePath) throws Exception;
    String processVideoToTextWithJsonCheck(int videoId, String videoPath) throws Exception;
    String checkVideoToTextStatus(int videoId, String videoPath) throws Exception;
    String generateFullInfoJson(int videoId, String videoPath) throws Exception;
}
