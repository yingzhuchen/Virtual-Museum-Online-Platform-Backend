package org.openapitools.service;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.openapitools.exception.ExpectedException404;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SceneRecommenderServiceImpl implements SceneRecommenderService {
    @Autowired JedisServiceImpl jedisService;
    @Autowired ScheduleServiceImpl scheduleService;
    @Override
    public List<Long> itemBasedRecommend(long sceneId) throws Exception {
        List<Long> ansList=new ArrayList<>();
        File file = new File("dat/IBRecommend.dat");
        if(!file.exists()) scheduleService.IBDat();
        DataModel dataModel = dataModel = new FileDataModel(file);
        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        List<RecommendedItem> recommendedItemList = recommender.mostSimilarItems(sceneId,6);
        for (RecommendedItem recommendedItem : recommendedItemList) {
            ansList.add(recommendedItem.getItemID());
        }
        return ansList;
    }
    @Override
    public List<Long> userBasedRecommender(long userId) throws Exception{
        File file = new File("dat/UBRecommend.dat");
        if(!file.exists()) scheduleService.UBDat();
        DataModel dataModel = new FileDataModel(file);
        UserSimilarity userSimilarity = new UncenteredCosineSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, userSimilarity);
        List<RecommendedItem> recommendedItemList = recommender.recommend(userId,6);
        List<Long> ansList=new ArrayList<>();
        for(RecommendedItem recommendedItem:recommendedItemList){
            ansList.add(recommendedItem.getItemID());
        }
        return ansList;
    }
    public List<Long> recommendByMostPopular(int number) throws Exception {
        return jedisService.getMostPopularSceneList(number);
    }
}
