package com.example.texttoemotion;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.texttoemotion.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Constants {
    public static  byte[] getBytes(Uri uri, Context c) throws IOException {
        InputStream inputStream =   c.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public  static Calendar return_hour_to_zero(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    public static Boolean  checkPassword(String password){
        Pattern passwordPattern=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
        return  passwordPattern.matcher(password).matches();

    }
    public static void waitTask(Task<Object> task,String Tag){
        try {
            Tasks.await(task);
        }catch (Exception e){
            Log.d(Tag,"error while trying to wait :"+e.getMessage());
        }
    }
    public static String encryptMsg(String message, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SecretKeySpec secret;
        secret = new SecretKeySpec(key.getBytes(), "AES/ECB/PKCS5Padding");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }
    public static String decryptMsg(String cipherText, String key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        SecretKeySpec secret;
        secret = new SecretKeySpec(key.getBytes(), "AES/ECB/PKCS5Padding");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] decode = Base64.decode(cipherText, Base64.NO_WRAP);
        String decryptString = new String(cipher.doFinal(decode), "UTF-8");
        return decryptString;
    }


}
