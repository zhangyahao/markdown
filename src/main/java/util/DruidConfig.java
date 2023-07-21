package util;
import com.alibaba.druid.filter.config.ConfigTools;
/**
 * Druid
 */
public class DruidConfig extends ConfigTools{
    public static void main(String[] args) throws Exception{
        String[] arr = genKeyPair(521);

        String privateKey = arr[0];
        System.out.println("privateKey:+"+privateKey);

        String publicKey = arr[1];
        System.out.println("publicKey:+"+publicKey);

        String pass = "rckp@ssw0rd";
        String cipherTex = encrypt(privateKey, pass);
        System.out.println("加密结果+"+cipherTex);

        System.out.println("解密结果："+decrypt(publicKey,cipherTex));
    }
}
