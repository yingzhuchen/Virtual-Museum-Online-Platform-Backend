package org.openapitools.configuration;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Policy;
import com.tencent.cloud.Response;
import com.tencent.cloud.Statement;
import com.tencent.cloud.cos.util.Jackson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.TreeMap;

@Configuration
@PropertySource("classpath:/application.properties")
public class TencentCloudConfig {

    @Value("${TencentCOS.secretId}")
    String secretId;

    @Value("${TencentCOS.secretKey}")
    String secretKey;

    private BasicSessionCredentials configCred() throws Exception {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        config.put("secretId", secretId);
        config.put("secretKey", secretKey);
        config.put("durationSeconds", 3600);//per hour
        config.put("bucket", "demo-bucket-1325569882");
        config.put("region", "ap-guangzhou");

        Policy policy = new Policy();
        Statement statement = new Statement();
        // 声明设置的结果是允许操作
        statement.setEffect("allow");
        statement.addActions(new String[]{
                //授权所有
                "cos:*"
        });
        statement.addResources(new String[]{
                "qcs::cos:ap-guangzhou:uid/1325569882:demo-bucket-1325569882/*"});
        // 可以添加多条statement到 policy
        policy.addStatement(statement);
        config.put("policy", Jackson.toJsonPrettyString(policy));

        Response response = CosStsClient.getCredential(config);
        String tmpSecretId=response.credentials.tmpSecretId;
        String tmpSecretKey=response.credentials.tmpSecretKey;
        String sessionToken=response.credentials.sessionToken;
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        return cred;
    }

    @Bean
    public COSClient createCOSClient() throws Exception {
        BasicSessionCredentials cred = configCred();
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    @Scheduled(fixedDelay = 3600000)//per hour
    public void updateClient() throws Exception {
        COSClient cosClient=createCOSClient();
        BasicSessionCredentials cred = configCred();
        cosClient.setCOSCredentials(cred);
    }

}
