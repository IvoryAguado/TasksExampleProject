package me.smorenburg.jira.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.Random;

import me.smorenburg.jira.BuildConfig;


/**
 * Created by ivory.aguado on 16/06/2016.
 */
public class EncryptionSuite {

    private static String seed = "";

private final static String TAG = "EncryptionSuite";

    public static void setSeed(String seed) {
        EncryptionSuite.seed = seed;
        Log.d(TAG, seed);
    }

    public synchronized static String decrypt(String toDecrypt) {
        if (!BuildConfig.DEBUG) {
            try {
                toDecrypt = AESCrypt.decrypt(seed, toDecrypt);
            } catch (@NonNull GeneralSecurityException | NullPointerException w) {
                Log.e(TAG, "Failed Decryption " + toDecrypt);
                return null;
            }
        }
        return toDecrypt;
    }

    public synchronized static String encrypt(String toEncrypt) {
        if (!BuildConfig.DEBUG) {
            try {
                toEncrypt = AESCrypt.encrypt(seed, toEncrypt);
            } catch (@NonNull GeneralSecurityException | NullPointerException w) {
                Log.e(TAG, "Failed Encryption " + toEncrypt);
                return null;
            }
        }
        return toEncrypt;
    }
    public static String getSaltString(int lenght) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < lenght) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

}
