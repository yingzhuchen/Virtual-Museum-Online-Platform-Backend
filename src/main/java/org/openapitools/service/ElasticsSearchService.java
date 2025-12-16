package org.openapitools.service;

import org.openapitools.entity.Scene;
import org.openapitools.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElasticsSearchService {

    //创建场景索引
    void createSceneIndex() throws Exception;

    //创建用户索引
    void createUserIndex() throws Exception;

    void updateSceneDoc() throws Exception;

    void updateUserDoc() throws Exception;

    List<Scene> searchScenesList(String keyword) throws Exception;

    List<User> searchUsersList(String keyword) throws Exception;

}
