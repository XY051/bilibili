package com.sf.config;

import org.springframework.stereotype.Component;

@Component
public class ApiConfig {
    // 硅基流动API配置
    public static final String SILICONFLOW_BASE_URL = "https://api.siliconflow.cn/v1";
    public static final String DEEPSEEK_MODEL = "deepseek-ai/DeepSeek-V3.2-Exp";
    public static final String TELEAI_MODEL = "TeleAI/TeleSpeechASR";
    // 从环境变量或配置文件获取API密钥
    public static String getApiKey() {
        return "sk-tyqheluqazibzukhozjuikolzmoqhjdmyalyniimrlomcrrv"; // 或从配置文件读取
    }
}