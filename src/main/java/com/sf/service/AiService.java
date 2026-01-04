package com.sf.service;

public interface AiService {
    String chatWithDeepSeek(String userMessage) throws Exception;
    String analyzeVideo(String videoContent) throws Exception;
    String voiceToText(String audioFilePath) throws Exception;
}
