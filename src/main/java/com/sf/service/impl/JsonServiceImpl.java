package com.sf.service.impl;

import com.sf.dao.JsonDao;
import com.sf.entity.jsonEntity;
import com.sf.service.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonServiceImpl implements JsonService {

    @Autowired
    private JsonDao jsonDao;

    @Override
    public boolean saveJsonInfo(jsonEntity jsonEntity) {
        return jsonDao.saveJsonInfo(jsonEntity);
    }

    @Override
    public jsonEntity getJsonByAudioId(int audioId) {
        return jsonDao.getJsonByAudioId(audioId);
    }

    @Override
    public jsonEntity getJsonByJsonPath(String jsonPath) {
        return jsonDao.getJsonByJsonPath(jsonPath);
    }
}