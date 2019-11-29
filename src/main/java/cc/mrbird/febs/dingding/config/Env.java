package cc.mrbird.febs.dingding.config;


/**
 * 第三方企业应用接入时的常量定义
 */
public class Env {

    /**
     * 企业corpid
     */
    public static final String CORP_ID = "ding827934143c10773835c2f4657eb6378f";

    /**
     * 应用agentId
     */
    public static final String AGENT_ID = "286587669";

    /**
     * 应用的appkey
     */
    public static final String APP_KEY = "dingwcukqem1pjyw5gfl";

    /**
     * 应用的appsecret
     */
    public static final String APP_SECRET = "l-q3Jmnto89N_epKXBwWoZ4zGP1LXSWlGySba0d9HI9mH3kanywBccOFRVd-0nTH";

    /**
     * 登录的ACCESSKEY
     */
    public static final String ACCESSKEY = "dingoakvxpd9icyeewvolr";
    /**
     * 登录的ACCESSSECRET
     */
    public static final String ACCESSSECRET = "wzMnAn6ZVc8gcabZT3rXEsGcAMRGO6DixPoIxw4Vq4K--d9aXtMs19iMmF4O0ZRX";

    /**
     * 回调host
     */
    public static final String CALLBACK_URL_HOST = "";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "123456"
     */
	public static final String TOKEN = "123456";

    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
	public static final String ENCODING_AES_KEY = "abcdefghijabcdefghijabcdefghijabcdefghij123";

    /**
     * DING API地址
     */
    public static final String OAPI_HOST = "https://oapi.dingtalk.com";

    /**
     * 删除企业回调接口url
     */
    public static final String DELETE_CALLBACK = "https://oapi.dingtalk.com/call_back/delete_call_back";

    /**
     * 注册企业回调接口url
     */
    public static final String REGISTER_CALLBACK = "https://oapi.dingtalk.com/call_back/register_call_back";

    /**
     * 企业应用后台地址，用户管理后台免登使用
     */
    public static final String OA_BACKGROUND_URL = "";

    public static final String SSO_Secret = "";
}
