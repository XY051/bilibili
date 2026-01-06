package com.sf.service;

import com.sf.entity.jsonEntity;

public interface JsonService {
    boolean saveJsonInfo(jsonEntity jsonEntity);
    jsonEntity getJsonByAudioId(int audioId);
    jsonEntity getJsonByJsonPath(String jsonPath);
}