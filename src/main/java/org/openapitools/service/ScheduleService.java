package org.openapitools.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public interface ScheduleService {
    Future<Boolean> startCleanThread(String targetFile);
    void autoClean() throws Exception;
    void autoUpdateESDoc() throws Exception;
    void autoGenerateRecommendDat() throws Exception;
    void IBDat() throws Exception;
    void UBDat() throws Exception;
}
