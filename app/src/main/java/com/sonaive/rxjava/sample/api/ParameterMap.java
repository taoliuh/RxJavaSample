package com.sonaive.rxjava.sample.api;

import android.util.Base64;

import com.sonaive.rxjava.sample.Config;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by liutao on 5/13/16.
 */
public class ParameterMap extends HashMap<String, String> {

    public ParameterMap transformMap() {
        SortedMap<String, String> sortedParams = new TreeMap<>(this);
        StringBuilder builder = new StringBuilder();
        try {
            String nonce = genOnceToken(Config.APP_SECRET, sortedParams);
            sortedParams.put("nonce", nonce);
            sortedParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return this;
        }
        boolean first = true;
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            String and = first ? "&" : "%26";
            first = false;
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            try {
                builder.append(and)
                        .append(encodeURIComponent(entry.getKey()))
                        .append("%3D")
                        .append(encodeURIComponent(entry.getValue()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return this;
            }
        }
        String signature = hash(builder.toString(), Config.APP_SECRET);
        clear();
        putAll(sortedParams);
        put("signature", signature);
        return this;
    }

    private String hash(String s, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretSpec = new SecretKeySpec(secret.getBytes(), mac.getAlgorithm());
            mac.init(secretSpec);
            byte[] digest = mac.doFinal(s.getBytes());
            return Base64.encodeToString(digest, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }

    private String genOnceToken(String appSecret, Map<String, String> sortedParams) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        StringBuilder paramString = new StringBuilder();
        for (String value : sortedParams.values()) {
            paramString.append(value);
        }
        String raw = appSecret + paramString.toString() + System.currentTimeMillis() + (new Random().nextInt(100) + 1);
        md.update(raw.getBytes("UTF-8"));
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * http://stackoverflow.com/questions/607176/java-equivalent-to-javascripts-encodeuricomponent-that-produces-identical-outpu
     */
    private String encodeURIComponent(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s, "UTF-8")
                .replace("+", "%20")
                .replace("%21", "!")
                .replace("%27", "'")
                .replace("%28", "(")
                .replace("%29", ")")
                .replace("%7E", "~");
    }
}
