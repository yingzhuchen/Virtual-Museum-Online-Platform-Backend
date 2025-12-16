package org.openapitools.resolver;

import org.apache.commons.validator.routines.UrlValidator;
import org.openapitools.dao.*;
import org.openapitools.entity.*;
import org.openapitools.entity.User;
import org.openapitools.exception.*;
import org.openapitools.model.*;
import org.openapitools.model.modelFactory.*;
import org.openapitools.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.LocalDate;
import java.util.*;
import javax.transaction.Transactional;

@Service
@Transactional
@org.springframework.transaction.annotation.Transactional
public class UserResolverImpl implements UserResolver {

    @Autowired
    UserDao userDao;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JedisServiceImpl jedisService;


    @Autowired
    ElasticsSearchServiceImpl elasticsSearchService;

    @Autowired
    UserSubDao userSubDao;

    @Autowired
    SceneHistoryDao sceneHistoryDao;

    @Override//更新用户信息
    public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest req, long userId) throws ExpectedException {
        User existUser = userDao.findByUserIdAndUserIsDeleted(userId, 0);
        if (existUser == null) {
            throw new ExpectedException400("找不到对应用户");
        }

        String[] argumentsList = {
                "name",
                "avatarUrl",
                "nickName",
                "motto",
                "gender",
                "birthday",
                "address",
                "topSceneIdList"
        };
        Iterator<String> iterator = Arrays.stream(argumentsList).iterator();
        //处理用户子表
        UserSub userSub;
        if (!userSubDao.findByUserId(userId).isPresent()) {
            userSub = new UserSub();
            userSub.setUserId(userId);
            userSubDao.save(userSub);
        } else {
            userSub = userSubDao.findByUserId(userId).get();
        }

        //查看每一个变量
        while (iterator.hasNext()) {
            String key = iterator.next();
            switch (key) {
                case "name":
                    String name = req.getName();
                    if (name == null) break;
                    else if (name.length() > 15) throw new ExpectedException400("用户名过长");
                    existUser.setUserName(name);
                    break;
                case "avatarUrl":
                    String url = req.getAvatarUrl();
                    UrlValidator urlValidator = new UrlValidator();
                    if (url == null) break;
                    else if (!urlValidator.isValid(url)) throw new ExpectedException400("头像URL不合法");
                    existUser.setAvatarUrl(url);
                    break;
                case "nickName":
                    String nickName = req.getNickName();
                    if (nickName == null) break;
                    if (nickName.length() > 15) {
                        throw new ExpectedException400("用户昵称过长");
                    }
                    userSub.setNickName(nickName);
                    break;
                case"motto":
                    String motto = req.getMotto();
                    if (motto == null)break;
                    if (motto.length() > 30) {
                        throw new ExpectedException400("用户座签名过长");
                    }
                    userSub.setMotto(motto);
                    break;
                case "gender":
                    UpdateUserInfoRequest.GenderEnum gender = req.getGender();
                    if (gender == null) break;
                    switch (gender) {
                        case MALE:
                            userSub.setGender(1);
                            break;
                        case FEMALE:
                            userSub.setGender(2);
                            break;
                        case UNKOWN:
                            userSub.setGender(3);
                            break;
                    }
                    break;
                case "birthday":
                    LocalDate birthday = (req.getBirthday() == null) ? null : LocalDate.parse(req.getBirthday());
                    if (birthday == null) break;
                    else if (birthday.isAfter(LocalDate.now())) throw new ExpectedException400("生日不能是未来日期");
                    userSub.setBirthday(birthday);
                    break;
                case "address":
                    String address = req.getAddress();
                    if (address == null) break;
                    else if (address.length() > 200) throw new ExpectedException400("地址过长");
                    userSub.setAddress(address);
                    break;
                case"topSceneIdList":
                    List<TopSceneId> topSceneIdList = req.getTopSceneIdList();
                    if (topSceneIdList == null) break;
                    else if (topSceneIdList.size() > 3) throw new ExpectedException400("最多只能设置3个置顶场景");
                    else {
                        userService.editTopSceneIdList(userId, topSceneIdList);
                    }
                    break;
                default:
                    break;
            }
        }
        userDao.save(existUser);
        userSubDao.save(userSub);

        UpdateUserInfoResponse resp = new UpdateUserInfoResponse();
        resp.setUser(userDao.findByUserIdAndUserIsDeleted(userId, 0), userSubDao.findByUserId(userId).get());
        return resp;

    }

    @Override
    public UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, long userId) throws ExpectedException400 {
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = updatePasswordRequest.getNewPassword();
        User existUser = userDao.findByUserIdAndUserIsDeleted(userId, 0);
        if (existUser == null) {
            throw new ExpectedException400("用户不存在");
        }

        userService.updatePassword(existUser, oldPassword, newPassword);

        String newToken = userService.generateToken(existUser);

        UpdatePasswordResponse resp = new UpdatePasswordResponse();
        resp.setToken(newToken);
        resp.setUserId((int) userService.getUserById(userId).getUserId());
        resp.setUserName(userService.getUserById(userId).getUserName());
        return resp;
    }

    @Override
    public ListUserInfoResponse getUserListByName(String name) throws Exception {
        //List<User> queryUserList = userService.getUserListByName(name);
        List<User> queryUserList = elasticsSearchService.searchUsersList(name);
        ListUserInfoResponse resp=new ListUserInfoResponse();
        if(queryUserList==null)  return resp;
        int totalCount = 0;
        List<GetUserInfoResponse> getUserInfoResponseList = new ArrayList<>();
        for (User user : queryUserList) {
            GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
            getUserInfoResponse.setId((int) user.getUserId());
            getUserInfoResponse.setName(user.getUserName());
            getUserInfoResponse.setAvatarUrl(user.getAvatarUrl());
            //TODO 数据库内有很多脏数据，例如搜索test，会出现大量无role映射的情况，后面改...
            try {
                getUserInfoResponse.setRole(UserRole.fromValue(userService.getUserRoleById(user.getUserId())));
            } catch (Exception e) {
                continue;
            }
            totalCount++;
            getUserInfoResponseList.add(getUserInfoResponse);
        }
        resp.setTotalCount(totalCount);
        resp.setData(getUserInfoResponseList);
        return resp;
    }

    @Override
    public ListSceneHistroyResponse getUserSceneHistory(NativeWebRequest request, long userId) throws Exception {
        int first = request.getParameter("first") != null
                ? Integer.parseInt(request.getParameter("first"))
                : 10;
        int after = request.getParameter("after") != null
                ? Integer.parseInt(request.getParameter("after"))
                : 0;
        int count;
        List<SceneHistory> listFromRedis=new ArrayList<>();
        try{//服务降级
            listFromRedis = jedisService.getSceneHistory(first, after, userId);
            count = listFromRedis.size();
        }catch(Exception e){count=0;}
        List<SceneHistory> listFromMySQL = new ArrayList<>();
        if (count < first) {
            Pageable pageable = PageRequest.of(0, first - count);
            listFromMySQL = sceneHistoryDao.findByUserIdOrderBySceneAccessTimeDesc(userId, pageable);
        }
        ListSceneHistroyResponse listResp = new ListSceneHistroyResponse();
        List<SceneHistoryResponse> respList = new ArrayList<>();
        for (SceneHistory h : listFromRedis) {
            SceneHistoryResponse sceneHistoryResponse = new SceneHistoryResponse();
            sceneHistoryResponse.setSceneHistory(h);
            respList.add(sceneHistoryResponse);
        }
        for (SceneHistory h : listFromMySQL) {
            SceneHistoryResponse sceneHistoryResponse = new SceneHistoryResponse();
            sceneHistoryResponse.setSceneHistory(h);
            respList.add(sceneHistoryResponse);
        }
        listResp.setData(respList);
        listResp.setTotalCount(respList.size());
        return listResp;
    }
    @Override
    public GetUserInfoResponse getUserSubInfo(long userid){
        GetUserInfoResponse resp = new GetUserInfoResponse();

        User user = userDao.findByUserIdAndUserIsDeleted(userid,0);
        resp.setId((int) user.getUserId());
        resp.setName(user.getUserName());
        resp.setRole(UserRole.fromValue(userService.getUserRoleById(user.getUserId())));
        resp.setAvatarUrl(user.getAvatarUrl());
        resp.setTopSceneIdList(user.getTopSceneId());

        UserSub userSub=userSubDao.findByUserId(userid).orElse(null);
        if(userSub!=null) {
            resp.setNickName(userSub.getNickName());
            resp.setMotto(userSub.getMotto());
            resp.setGender(userSub.getGender());
            resp.setBirthday(String.valueOf(userSub.getBirthday()));
            resp.setAddress(userSub.getAddress());
        }
        return resp;
    }

    @Override
    public FavouritesListResponse favouritesCRUD(FavouritesCrudRequest favouritesCrudRequest, long userId) throws Exception {
        FavouritesListResponse resp=new FavouritesListResponse();

        FavouritesCrudRequestBodyFactory factory=FavouritesCrudRequestBodyFactory.getFactory();
        FavouritesCrudRequestBody rawBody=favouritesCrudRequest.getBody();
        switch(favouritesCrudRequest.getOperation().getValue()){
            case "Create":
                FavouritesCrudRequestBodyFactory.CreateBody createBody=
                        (FavouritesCrudRequestBodyFactory.CreateBody)factory.getUsableInstance(rawBody,OperationType.CREATE);
                String newFavouritesNameCreate=createBody.getNewFavouritesName();
                jedisService.createSceneFavourites(newFavouritesNameCreate,userId);
                break;
            case "Read":
//                FavouritesCrudRequestBodyFactory.ReadBody readBody=
//                        (FavouritesCrudRequestBodyFactory.ReadBody)factory.getUsableInstance(rawBody,OperationType.READ);
                if(rawBody!=null) throw new ExpectedException400("参数不匹配");
                break;
            case "Update":
                FavouritesCrudRequestBodyFactory.UpdateBody updateBody=
                        (FavouritesCrudRequestBodyFactory.UpdateBody)factory.getUsableInstance(rawBody,OperationType.UPDATE);
                String existingFavouritesNameUpdate=updateBody.getExistingFavouritesName();
                String newFavouritesNameUpdate=updateBody.getNewFavouritesName();
                jedisService.updateSceneFavourites(existingFavouritesNameUpdate,newFavouritesNameUpdate,userId);
                break;
            case "Delete":
                FavouritesCrudRequestBodyFactory.DeleteBody deleteBody=
                        (FavouritesCrudRequestBodyFactory.DeleteBody)factory.getUsableInstance(rawBody,OperationType.DELETE);
                String existingFavouritesNameDelete=deleteBody.getExistingFavouritesName();
                jedisService.deleteSceneFavourites(existingFavouritesNameDelete,userId);
                break;
            default:
                break;
        }

        List<String> favouritesList=jedisService.readSceneFavourites(userId);
        resp.setTotalCount(favouritesList.size());
        resp.setFavouritesList(favouritesList);
        return resp;
    }

    @Override
    public FavoriteSceneListResponse favoriteSceneCRUD(FavoriteSceneCrudRequest favoriteSceneCrudRequest, long userId) throws Exception {
        FavoriteSceneListResponse resp=new FavoriteSceneListResponse();

        FavoriteSceneCrudRequestBodyFactory factory=FavoriteSceneCrudRequestBodyFactory.getFactory();
        FavoriteSceneCrudRequestBody rawBody=favoriteSceneCrudRequest.getBody();
        List<Long> favoriteSceneList=new ArrayList<>();
        switch(favoriteSceneCrudRequest.getOperation().getValue()){
            case "Create":
                FavoriteSceneCrudRequestBodyFactory.CreateBody createBody=
                        (FavoriteSceneCrudRequestBodyFactory.CreateBody)factory.getUsableInstance(rawBody,OperationType.CREATE);
                String existingFavouritesNameCreate=createBody.getExistingFavouritesName();
                Integer sceneIdCreate=createBody.getSceneId();
                jedisService.pushFavoriteScene(existingFavouritesNameCreate,sceneIdCreate,userId);
                favoriteSceneList=jedisService.findAllFavoriteScene(existingFavouritesNameCreate,userId);
                break;
            case "Read":
                FavoriteSceneCrudRequestBodyFactory.ReadBody readBody=
                        (FavoriteSceneCrudRequestBodyFactory.ReadBody)factory.getUsableInstance(rawBody,OperationType.READ);
                String existingFavouritesNameRead=readBody.getExistingFavouritesName();
                favoriteSceneList=jedisService.findAllFavoriteScene(existingFavouritesNameRead,userId);
                break;
            case "Update":
                FavoriteSceneCrudRequestBodyFactory.UpdateBody updateBody=
                        (FavoriteSceneCrudRequestBodyFactory.UpdateBody)factory.getUsableInstance(rawBody,OperationType.UPDATE);
                String existingFavouritesNameUpdate=updateBody.getExistingFavouritesName();
                List<Integer> sceneIdListUpdate=updateBody.getSceneIdList();
                jedisService.replaceFavoriteSceneList(existingFavouritesNameUpdate,sceneIdListUpdate,userId);
                favoriteSceneList=jedisService.findAllFavoriteScene(existingFavouritesNameUpdate,userId);
                break;
            case "Delete":
                FavoriteSceneCrudRequestBodyFactory.DeleteBody deleteBody=
                        (FavoriteSceneCrudRequestBodyFactory.DeleteBody)factory.getUsableInstance(rawBody,OperationType.DELETE);
                String existingFavouritesNameDelete=deleteBody.getExistingFavouritesName();
                Integer sceneIdDelete=deleteBody.getSceneId();
                jedisService.popFavoriteScene(existingFavouritesNameDelete,sceneIdDelete,userId);
                favoriteSceneList=jedisService.findAllFavoriteScene(existingFavouritesNameDelete,userId);
                break;
            default:
                break;
        }

        resp.setTotalCount(favoriteSceneList.size());
        resp.favoriteList(favoriteSceneList);
        return resp;
    }

}
