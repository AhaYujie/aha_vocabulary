package online.ahayujie.aha_vocabulary_app.utils;

/**
 * 密码工具类
 *
 * @author aha
 */
public class PasswordUtils {

    private static final int PASSWORD_MIN_LENGTH = 6;

    public static void checkPassword(String password) throws IllegalArgumentException {
        if ("".equals(password)) {
            throw new IllegalArgumentException("密码为空");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new IllegalArgumentException("密码长度小于6位");
        }
    }

}
