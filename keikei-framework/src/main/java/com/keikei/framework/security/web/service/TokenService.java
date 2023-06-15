package com.keikei.framework.security.web.service;

import com.keikei.common.constants.CacheConstants;
import com.keikei.common.constants.Constants;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.LoginBody;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.common.utils.ServletUtils;
import com.keikei.common.utils.StringUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenService {
    @Value("${token.secret}")
    private String secret;
    @Autowired
    private RedisCache redisCache;

    @Value("${token.expireTime}")
    private Long expireTime;

    public LoginUser getLoginUser(HttpServletRequest request) {
        String inputToken = getRequestToken(request.getHeader(Constants.TOKEN_HEADER));
        if (StringUtils.isNotEmpty(inputToken)) {
            try {
                Claims claims = parseToken(inputToken);
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String redis_token_key = getTokenKey(uuid);
                LoginUser loginUser = redisCache.getRedisObject(redis_token_key);
                return loginUser;
            } catch (Exception e) {
                log.error("获取登录LoginUser失败,{}", e.getMessage());
            }
        }
        return null;
    }

    private String getRequestToken(String header) {
        if(!StringUtils.isEmpty(header)&&header.startsWith(Constants.BEARER)){
            return header.replace(Constants.BEARER,"");
        }
        return null;
    }

    private String getTokenKey(String uuid) {
        return CacheConstants.LOGIN_TOKEN_KEY + uuid;
    }

    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public void checkTimeOut(LoginUser loginUser) {
        Long expireTime = loginUser.getExpireTime();
        Long nowTime = System.currentTimeMillis();
        if (nowTime - expireTime > 0) {
            RefreshToken(loginUser);
        }
    }

    /**
     * 刷新token/创建token
     *
     * @param loginUser
     */
    private void RefreshToken(LoginUser loginUser) {
        loginUser.setExpireTime(System.currentTimeMillis()+expireTime*10000);
        String redis_token_key = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(redis_token_key,loginUser,expireTime, TimeUnit.MINUTES);
    }

    public String createToken(LoginUser loginUser) {
        String uuid = UUID.randomUUID().toString();
        loginUser.setToken(uuid);
        Map<String,Object> map = new HashMap<>();
        map.put(Constants.LOGIN_USER_KEY,uuid);
        setLoginUserProperties(loginUser);
        RefreshToken(loginUser);
        return createToken(map);
    }

    private void setLoginUserProperties(LoginUser loginUser) {
        UserAgent agent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        loginUser.setBrowser(String.valueOf(agent.getBrowser()));
        loginUser.setOs(String.valueOf(agent.getOperatingSystem()));
    }


    private String createToken(Map<String,Object> map) {
        return Jwts.builder().setClaims(map).signWith(SignatureAlgorithm.HS512,secret).compact();
    }
}
