package online.ahayujie.service.impl;

import online.ahayujie.pojo.TokenModel;
import online.ahayujie.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("redisTokenServiceImpl")
public class RedisTokenServiceImpl implements TokenService {

    // header token格式：userId_token

    private static final int TOKEN_EXPIRES_DAY = 5;

    private RedisTemplate<Long, String> redisTemplate;

    @Autowired
    public RedisTokenServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public TokenModel createToken(Long userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel tokenModel = new TokenModel(userId, token);
        redisTemplate.opsForValue().set(userId, token, TOKEN_EXPIRES_DAY, TimeUnit.DAYS);
        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel tokenModel) {
        String token = redisTemplate.opsForValue().get(tokenModel.getUserId());
        if (token == null || !token.equals(tokenModel.getToken())) {
            return false;
        }
        // 验证成功，重置token时限
        redisTemplate.opsForValue().set(tokenModel.getUserId(), tokenModel.getToken(), TOKEN_EXPIRES_DAY, TimeUnit.DAYS);
        return true;
    }

    @Override
    public TokenModel getTokenModel(String authentication) {
        String[] params = authentication.split("_");
        if (params.length != 2) {
            return null;
        }
        return new TokenModel(Long.parseLong(params[0]), params[1]);
    }

    @Override
    public void deleteToken(Long userId) {
        redisTemplate.delete(userId);
    }

    @Override
    public String getTokenString(TokenModel tokenModel) {
        return tokenModel.getUserId() + "_" + tokenModel.getToken();
    }
}
