package com.sf.service;

/**
 * 批量音频提取服务接口
 */
public interface BatchAudioService {
    
    /**
     * 批量提取videolook目录下所有视频的音频
     * @return 转换成功的文件数量
     */
    int batchExtractAudioFromVideos();
}