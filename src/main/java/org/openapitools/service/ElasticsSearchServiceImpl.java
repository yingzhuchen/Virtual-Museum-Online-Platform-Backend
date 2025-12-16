package org.openapitools.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.openapitools.dao.SceneDao;
import org.openapitools.dao.UserDao;
import org.openapitools.entity.Scene;
import org.openapitools.entity.User;
import org.openapitools.exception.ExpectedException404;
import org.springframework.beans.factory.annotation.Autowired;
import org.openapitools.exception.ExpectedException400;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticsSearchServiceImpl implements ElasticsSearchService{

    @Autowired
    RestHighLevelClient client;

    @Autowired
    SceneDao sceneDao;

    @Autowired
    UserDao userDao;

    @Override
    public void createSceneIndex() throws Exception {

        //7.10.1版本下索引必须是小写，否则会抛出异常
        String index = "scene";

        GetIndexRequest checkRequest = new GetIndexRequest(index);
        if(client.indices().exists(checkRequest, RequestOptions.DEFAULT)) throw new ExpectedException400("该索引已存在");

        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 3)
                .put("number_of_replicas", 1);

        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder();
        mappingBuilder.startObject()
                .startObject("properties")
                    .startObject("sceneId").field("type", "integer").endObject()
                    .startObject("sceneCreatorId").field("type", "integer").endObject()
                    .startObject("sceneCreateTime").field("type", "date").endObject()
                    .startObject("sceneName")
                        .field("type", "text")
                        //.field("analyzer", "ik_smart")
                    .endObject()
                    .startObject("sceneUrl").field("type", "text").endObject()
                    .startObject("sceneDescription")
                        .field("type", "text")
                        //.field("analyzer", "ik_smart")
                    .endObject()
                    .startObject("collaborators").field("type", "text").endObject()
                    .startObject("sceneIsPublic").field("type", "integer").endObject()
                .endObject()
        .endObject();

        CreateIndexRequest request = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(mappingBuilder);

        client.indices().create(request, RequestOptions.DEFAULT);

    }

    @Override
    public void createUserIndex() throws IOException {

        //7.10.1版本下索引必须是小写，否则会抛出异常
        String index = "user";

        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 3)
                .put("number_of_replicas", 1);

        XContentBuilder mappingBuilder = XContentFactory.jsonBuilder();
        mappingBuilder.startObject()
                .startObject("properties")
                    .startObject("userId").field("type", "integer").endObject()
                    .startObject("userName").field("type", "text").endObject()
                .endObject()
        .endObject();

        CreateIndexRequest request = new CreateIndexRequest(index)
                .settings(settings)
                .mapping(mappingBuilder);

        client.indices().create(request, RequestOptions.DEFAULT);

    }

    @Override
    public void updateSceneDoc() throws Exception{

        String index = "scene";
        String documentId;

        List<Scene> scenesList=sceneDao.findAll();
        for(Scene scene:scenesList){
            documentId=String.valueOf(scene.getSceneId());
            UpdateRequest request = new UpdateRequest(index,documentId);

            String json= JSON.toJSONString(scene);
            IndexRequest indexRequest=new IndexRequest(index,documentId);
            indexRequest.source(json, XContentType.JSON);
            //7.10.1提供了对老版本6.5.4的兼容
            request.doc(indexRequest);
            request.docAsUpsert(true); // 如果文档不存在，则将其插入为新文档
            //TODO 修复因磁盘空间不足导致的异常
            UpdateResponse updateResponse=client.update(request, RequestOptions.DEFAULT);
        }
    }

    @Override
    public void updateUserDoc() throws Exception{

        String index = "user";
        String documentId;

        List<User> usersList=userDao.findAll();
        for(User user:usersList){
            documentId=String.valueOf(user.getUserId());
            UpdateRequest request = new UpdateRequest(index,documentId);

            String json= JSON.toJSONString(user);
            IndexRequest indexRequest=new IndexRequest(index,documentId);
            indexRequest.source(json, XContentType.JSON);
            //7.10.1提供了对老版本6.5.4的兼容
            request.doc(indexRequest);
            request.docAsUpsert(true); // 如果文档不存在，则将其插入为新文档
            //TODO 修复因磁盘空间不足导致的异常
            UpdateResponse updateResponse=client.update(request, RequestOptions.DEFAULT);
        }
    }

    @Override
    public List<Scene> searchScenesList(String keyword) throws Exception{

        String index = "scene";
        SearchRequest request = new SearchRequest(index);

        //TODO 启用ik中文插件，参考EsScene
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("sceneName", keyword))
                .should(QueryBuilders.matchQuery("sceneDescription", keyword)));

        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.err.println(response.toString());

        // ES返回的数据太冗杂，只拿取_source中的数据
        JSONObject jsonResponse = JSONObject.parseObject(response.toString());
        JSONObject hits = jsonResponse.getJSONObject("hits");
        JSONArray hitsData = hits.getJSONArray("hits");
        //if (hitsData.isEmpty()) throw new ExpectedException404("该搜索不存在！");
        List<Scene> ansList = new ArrayList<>();
        for (Object obj : hitsData) {
            JSONObject jsonObj = (JSONObject) obj;
            JSONObject source = jsonObj.getJSONObject("_source");
            ansList.add(source.toJavaObject(Scene.class));
        }

        return ansList;

    }

    @Override
    public List<User> searchUsersList(String keyword) throws Exception{

        String index = "user";
        SearchRequest request = new SearchRequest(index);

        //TODO 启用ik中文插件，参考EsScene
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("userName", keyword)));

        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.err.println(response.toString());

        // ES返回的数据太冗杂，只拿取_source中的数据
        JSONObject jsonResponse = JSONObject.parseObject(response.toString());
        JSONObject hits = jsonResponse.getJSONObject("hits");
        JSONArray hitsData = hits.getJSONArray("hits");
        List<User> ansList = new ArrayList<>();
        if (hitsData.isEmpty()) return ansList;
        for (Object obj : hitsData) {
            JSONObject jsonObj = (JSONObject) obj;
            JSONObject source = jsonObj.getJSONObject("_source");
            ansList.add(source.toJavaObject(User.class));
        }

        return ansList;

    }

}
