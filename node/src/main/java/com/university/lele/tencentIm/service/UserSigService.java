package com.university.lele.tencentIm.service;

import com.tencentyun.TLSSigAPIv2;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserSigService {

    //eJyrVgrxCdZLrSjILEpVsjIzMLEwMNABi5WlFilZKRnpGShB*MUp2YkFBZkpSlaGJgYGpqYWRuaWEJnMlNS8ksy0TLAGC0sDIITpyUwHCrmaBntUOadbFLlWOZpnBRU4V5RElHiUlhgXZ4SZpYSVJ4Z4FZc6pbsHFprYQjWWZOYCnWNoZmJqYWpmYWRZCwDprDEF
    //eJyrVgrxCdZLrSjILEpVsjIzMLEwMNABi5WlFilZKRnpGShB*MUp2YkFBZkpSlaGJgYGpqYWRuaWEJnMlNS8ksy0TLAGC0sDIITpyUwHCrkYhlhWGVYZFuV6GBabl4R6OLvna4dFBhcVO2c5OkWZaJcGh2S45JnlVQXaQjWWZOYCnWNoZmJqYWpmYWZYCwC3mi-7

   // @Value("${IMConfig.sdkAppId}")
    private long sdkAppId=1400558279;
   // @Value("{$IMConfig.secretKey}")
    private String secretKey="dd3303a6a9badd77d7695c6481be6d1092851ee890d062ba510ac9fadd997b42";
    private long expirce=60*60*24*7;

    /**
     * f返回帐号所需要的UserSig
     * @param username
     * @return
     */
    public Result generateUserSig(String username) {
        TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppId, secretKey);
        String genSig = api.genSig(username, expirce);
        if (!genSig.isEmpty()) {
            return Result.success("返回usersig成功", genSig);
        } else {

            return Result.error(Code.USERSIG_ERR, "返回usersig错误");
        }
    }


}
