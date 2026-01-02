package com.sf.Maping;

import com.sf.service.BatchAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/batchaudio")
public class BatchAudioController {

    @Autowired
    private BatchAudioService batchAudioService;

    /**
     * 批量提取音频接口
     */
    @RequestMapping(value = "/batchExtractAudio", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> batchExtractAudio() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            int successCount = batchAudioService.batchExtractAudioFromVideos();
            result.put("success", true);
            result.put("message", "批量音频提取完成");
            result.put("convertedCount", successCount);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "批量音频提取失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
}