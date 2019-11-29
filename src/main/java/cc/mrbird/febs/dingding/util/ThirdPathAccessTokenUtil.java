package cc.mrbird.febs.dingding.util;

import cc.mrbird.febs.dingding.config.Constant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiServiceGetCorpTokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiServiceGetCorpTokenResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import static cc.mrbird.febs.dingding.config.URLConstant.URL_GET_TOKKEN;

public class ThirdPathAccessTokenUtil {
    private static final Logger bizLogger = LoggerFactory.getLogger(ThirdPathAccessTokenUtil.class);
    public static String getThirdPathToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/service/get_corp_token");
            OapiServiceGetCorpTokenRequest req = new OapiServiceGetCorpTokenRequest();
            req.setAuthCorpid("dingc365fcabbf733c3535c2f4657eb6378f");
            //OapiServiceGetCorpTokenResponse response = client.execute(req,"suiteKey","suiteSecrect", "suiteTicket");
            OapiServiceGetCorpTokenResponse response = client.execute(req,"suiteKey","suiteSecrect", "suiteTicket");

            String accessToken = response.getAccessToken();
            return accessToken;
        } catch (ApiException e) {
            bizLogger.error("getThirdPathToken failed", e);
            throw new RuntimeException();
        }

    }
}
