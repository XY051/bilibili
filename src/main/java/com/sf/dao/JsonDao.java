package com.sf.dao;

import com.sf.entity.jsonEntity;

public interface JsonDao {
    boolean saveJsonInfo(jsonEntity jsonEntity);
    jsonEntity getJsonByAudioId(int audioId);
    jsonEntity getJsonByJsonPath(String jsonPath);
}