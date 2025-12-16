package org.openapitools.service;

import org.openapitools.entity.Scene;
import org.openapitools.entity.SceneHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SceneFilterMachine<Container,Filter> {
    Container getSceneListContainer(List<Scene> list);
    Filter getFilterMachine();
}
