package online.ahayujie.service;

import online.ahayujie.pojo.TokenModel;

public interface TokenService {

    /**
     * 创建一个token
     * @param userId
     * @return
     */
    TokenModel createToken(Long userId);

    /**
     * 检查token有效性
     * @param tokenModel
     * @return true则有效，否则无效
     */
    boolean checkToken(TokenModel tokenModel);

    /**
     * 从字符串中解析出 token
     * @param authentication 加密的字符串
     * @return 解析成功返回TokenModel类型，否则返回null
     */
    TokenModel getTokenModel(String authentication);

    /**
     * 删除token
     * @param userId
     */
    void deleteToken(Long userId);

    /**
     * 从TokenModel中生成token字符串
     * @param tokenModel
     * @return
     */
    String getTokenString(TokenModel tokenModel);

}
