package org.openapitools.service;

import lombok.Getter;
import org.openapitools.entity.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SceneFilterMachineImpl implements SceneFilterMachine<SceneFilterMachineImpl.SceneListContainer,SceneFilterMachineImpl.FilterMachine>{
    @Autowired
    ElasticsSearchServiceImpl elasticsSearchService;

    //不提供set方法和构造器，想要获取可用的container实例必须通过SceneFilterMachineImpl
    @Getter
    public class SceneListContainer{
        private List<Scene> list;
        private SceneListContainer(List<Scene> list){
            this.list=list;
        }
    }

    public class FilterMachine{

        @Getter
        private String currentPhrase;
        private boolean enableFirst;private Integer first;
        private boolean enableAfter;private Integer after;
        private boolean enableKeyword;private String keyword;
        private boolean enableIsPublic;
        private boolean enableTag;private String tag;

        private FilterMachine(){
            this.currentPhrase="NOT_START_YET";
            this.enableFirst=false;this.first=10;
            this.enableAfter=false;this.after=0;
            this.enableKeyword=false;this.keyword="";
            this.enableTag=false;this.tag="";
            this.enableIsPublic=false;
        }

        public FilterMachine switchFirst(boolean enable){
            this.enableFirst=enable;
            return this;
        }
        public FilterMachine setFirst(Integer first){
            if(first!=null) this.first=first;
            return this;
        }
        public FilterMachine switchAfter(boolean enable){
            this.enableAfter=enable;
            return this;
        }
        public FilterMachine setAfter(Integer after){
            if(after!=null) this.after=after;
            return this;
        }
        public FilterMachine switchKeyword(boolean enable){
            this.enableKeyword=enable;
            return this;
        }
        public FilterMachine setKeyword(String keyword){
            if(keyword!=null) this.keyword=keyword;
            return this;
        }
        public FilterMachine switchTag(boolean enable){
            this.enableTag=enable;
            return this;
        }
        public FilterMachine setTag(String tag){
            if(tag!=null) this.tag=tag;
            return this;
        }
        public FilterMachine switchIsPublic(boolean enable){
            this.enableIsPublic=enable;
            return this;
        }

        public void start(){
            currentPhrase="START";
        }
        public void shutDown() {currentPhrase="OVER";}

        public boolean isRunning(SceneListContainer returnSceneList) throws Exception {
            switch(currentPhrase){
                case "NOT_START_YET":
                    return false;
                case "START":
                    currentPhrase="FILTER_BY_AFTER";
                    return true;
                case "FILTER_BY_AFTER":
                    if(enableAfter){
                        returnSceneList.list=returnSceneList.list.stream().filter((scene -> {
                            return returnSceneList.list.indexOf(scene)>=this.after;
                        })).collect(Collectors.toList());
                    }
                    currentPhrase="FILTER_BY_KEYWORD";
                    return true;
                case "FILTER_BY_KEYWORD":
                    if(enableKeyword){
                        List<Scene> sceneListInES=elasticsSearchService.searchScenesList(keyword);
                        returnSceneList.list=returnSceneList.list.stream().filter(scene -> {
                            for(Scene sceneInES:sceneListInES){
                                if(sceneInES.getSceneId()==scene.getSceneId()){
                                    System.err.println("accept"+scene.getSceneId());
                                    return true;
                                }
                            }
                            return false;
                        }).collect(Collectors.toList());
                    }
                    currentPhrase="FILTER_BY_TAG";
                    return true;
                case "FILTER_BY_TAG":
                    if(enableTag){
                        returnSceneList.list=returnSceneList.list.stream().filter(scene -> {
                            return scene.getTagsList().contains(this.tag);
                        }).collect(Collectors.toList());
                    }
                    currentPhrase="FILTER_BY_IS_PUBLIC";
                    return true;
                case "FILTER_BY_IS_PUBLIC":
                    if(enableIsPublic){
                        returnSceneList.list=returnSceneList.list.stream().filter((scene -> {
                            return scene.getSceneIsPublic() == 1;
                        })).collect(Collectors.toList());
                    }
                    currentPhrase="FILTER_BY_FIRST";
                    return true;
                case "FILTER_BY_FIRST":
                    if(enableFirst){
                        returnSceneList.list=returnSceneList.list.stream().filter((scene -> {
                            return returnSceneList.list.indexOf(scene)<this.first;
                        })).collect(Collectors.toList());
                    }
                    currentPhrase="OVER";
                    return true;
                case "OVER":
                    return false;
                default:
                    throw new IllegalStateException("运行异常");
            }
        }

    }

    public SceneListContainer getSceneListContainer(List<Scene> list){return new SceneListContainer(list);}
    public FilterMachine getFilterMachine(){
        return new FilterMachine();
    }

}
