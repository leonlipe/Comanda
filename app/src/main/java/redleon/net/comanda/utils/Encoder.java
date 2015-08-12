package redleon.net.comanda.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by leon on 12/08/15.
 */
public class Encoder {

    public static String encode(String reqdate, String token){
        String resultado = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(token.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(Base64.encodeToString(reqdate.getBytes(), Base64.DEFAULT).getBytes("UTF-8"));
            System.out.println(digest.toString());
            resultado = Base64.encodeToString(digest, Base64.DEFAULT);

        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();

        }catch(InvalidKeyException e){
            e.printStackTrace();

        }
        return resultado;
    }
}
