package org.openapitools.model.modelFactory;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import org.openapitools.exception.ExpectedException400;
import org.openapitools.model.OperationType;

public class FavouritesCrudRequestBodyFactory {
    static FavouritesCrudRequestBodyFactory factoryInstance;
    private FavouritesCrudRequestBodyFactory(){}
    public static FavouritesCrudRequestBodyFactory getFactory(){
        if(factoryInstance==null) factoryInstance=new FavouritesCrudRequestBodyFactory();
        return factoryInstance;
    }
    
    @Getter
    @Setter
    public class CreateBody{
        private String newFavouritesName;
        private CreateBody(){}
    }
    @Getter
    @Setter
    public class ReadBody{
        private ReadBody(){}
    }
    @Getter
    @Setter
    public class UpdateBody{
        private String existingFavouritesName;
        private String newFavouritesName;
        private UpdateBody(){}
    }
    @Getter
    @Setter
    public class DeleteBody{
        private String existingFavouritesName;
        private DeleteBody(){}
    }
    
    public Object getUsableInstance(FavouritesCrudRequestBody rawBody, OperationType operation) throws Exception{
        switch(operation){
            case CREATE:
                CreateBody createBody=new CreateBody();
                if(!rawBody.hasSamePropertyOf(createBody)) throw new ExpectedException400("参数不匹配");
                BeanUtils.copyProperties(createBody,rawBody);
                return createBody;
            case READ:
                ReadBody readBody=new ReadBody();
                if(!rawBody.hasSamePropertyOf(readBody)) throw new ExpectedException400("参数不匹配");
                BeanUtils.copyProperties(readBody,rawBody);
                return readBody;
            case UPDATE:
                UpdateBody updateBody=new UpdateBody();
                if(!rawBody.hasSamePropertyOf(updateBody)) throw new ExpectedException400("参数不匹配");
                BeanUtils.copyProperties(updateBody,rawBody);
                return updateBody;
            case DELETE:
                DeleteBody deleteBody=new DeleteBody();
                if(!rawBody.hasSamePropertyOf(deleteBody)) throw new ExpectedException400("参数不匹配");
                BeanUtils.copyProperties(deleteBody,rawBody);
                return deleteBody;
            default:
                return null;
        }
    }
    
}
