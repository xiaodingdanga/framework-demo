package com.lx.framework.demo1.heyingyun;

import com.ecloud.sdk.config.Config;
import com.ecloud.sdk.ecs.v1.Client;
import com.ecloud.sdk.ecs.v1.model.VmChangeRequest;
import com.ecloud.sdk.ecs.v1.model.VmChangeResponse;

import java.util.*;

public class Ecloud {

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKey
     * @param secretKey
     * @param poolId
     * @return Client
     */
    private static Client createClient(String accessKey, String secretKey,
                                       String poolId) {
        Config config = new Config();
        config.setAccessKey(accessKey);
        config.setSecretKey(secretKey);
        config.setPoolId(poolId);

        return new Client(config);
    }

    public static void main(String[] args) {
        String accessKey = "A2REOZSJWM4SDC4AC1P6";
        String secretKey = "UfS0kGMGdAEri9FyoWylalXFTRGEIsesyfOOs1HE";
        String poolId = "CIDC-RP-2006";
        Client client = Ecloud.createClient(accessKey, secretKey,
                poolId);
        VmChangeRequest vmChangeRequest = new VmChangeRequest();
        VmChangeResponse result = client.vmChange(vmChangeRequest);
    }
}
