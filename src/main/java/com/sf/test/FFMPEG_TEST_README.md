# FFmpeg 测试说明

## 测试目的
此测试类用于验证FFmpeg在项目中的可用性和基本功能。

## 测试内容
1. 检查FFmpeg是否正确安装和配置
2. 测试基本编码器功能
3. 测试视频转音频转换功能

## 如何运行测试
```bash
mvn exec:java -D"exec.mainClass"="com.sf.test.FFmpegTest"
```

## 测试结果说明
- 如果显示 "FFmpeg可用: 是"，表示FFmpeg已正确配置
- 如果要测试视频转音频功能，需要提供一个测试视频文件（如test_video.mp4）
- 测试会尝试在项目根目录下查找可能的视频文件

## FFmpeg安装要求
根据用户偏好和项目需求，FFmpeg需要正确安装并配置到系统PATH中，以便JAVE库能够访问。

## 注意事项
- 确保FFmpeg已正确安装并添加到系统PATH
- 如果要测试转换功能，需要提供一个视频文件
- 测试会生成输出音频文件，注意磁盘空间

## 依赖项
- JAVE库 (Java Audio Video Encoder)
- FFmpeg native binaries