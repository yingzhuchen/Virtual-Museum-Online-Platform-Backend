package org.openapitools.resolver;

import org.openapitools.exception.ExpectedException;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

@Service
public interface UserResolver {
    UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest, long userId) throws ExpectedException;
    UpdatePasswordResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, long userId) throws ExpectedException400;
    ListUserInfoResponse getUserListByName(String name) throws Exception;
    ListSceneHistroyResponse getUserSceneHistory(NativeWebRequest request,long userId) throws Exception;
    GetUserInfoResponse getUserSubInfo(long userid);
    FavouritesListResponse favouritesCRUD(FavouritesCrudRequest favouritesCrudRequest,long userId) throws Exception;
    FavoriteSceneListResponse favoriteSceneCRUD(FavoriteSceneCrudRequest favoriteSceneCrudRequest,long userId) throws Exception;
}
