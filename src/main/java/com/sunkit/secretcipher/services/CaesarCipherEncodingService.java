package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.models.DecodingResult;
import com.sunkit.secretcipher.models.EncodingResult;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class CaesarCipherEncodingService implements EncodingService {

    private final Charset ENCODING_CHAR_SET = StandardCharsets.UTF_8;

    @Override
    public EncodingResult encodeWithBytes(String rawInput, String key) {
        Queue<Integer> keyQueue = parseKey(key);
        byte[] bytes = rawInput.trim().getBytes(ENCODING_CHAR_SET);
        for (int i = 0; i < bytes.length; i++) {
            // get key for current byte and return key to queue
            Integer encodeKey = keyQueue.poll();
            keyQueue.offer(encodeKey);

            // ensuring that new byte produced is valid
            if (encodeKey == null) encodeKey = 0;
            int currByte = bytes[i] + 128;
            currByte += encodeKey;
            if (currByte > 255) currByte = (currByte - 255) % 256 - 1;
            else if (currByte < 0) currByte = 255 + (currByte % 256) + 1;
            currByte -= 128;
            // assign new encoded byte to byte array
            bytes[i] = (byte) currByte;
        }

        return EncodingResult.of(new String(bytes, ENCODING_CHAR_SET), bytes);
    }

    @Override
    public DecodingResult decodeWithBytes(String encodedInput, String key) {
        Queue<Integer> keyQueue = parseKey(key);
        byte[] bytes = encodedInput.trim().getBytes(ENCODING_CHAR_SET);
        for (int i = 0; i < bytes.length; i++) {
            // get key for current byte and return key to queue
            Integer encodeKey = keyQueue.poll();
            keyQueue.offer(encodeKey);

            // ensure that new byte produced is valid
            if (encodeKey == null) encodeKey = 0;
            int currByte = bytes[i] + 128;
            currByte -= encodeKey;
            if (currByte > 255) currByte = (currByte - 255) % 256 - 1;
            else if (currByte < 0) currByte = 255 + (currByte % 256) + 1;
            currByte -= 128;
            // assign new decoded byte to byte array
            bytes[i] = (byte) currByte;
        }
        return DecodingResult.of(new String(bytes, ENCODING_CHAR_SET), bytes);
    }

//    public DecodingResult encodeByChar(String rawInput, String key) {
//        Queue<Integer> keyQueue = parseKey(key);
//        char[] chars = new char[rawInput.length()];
//        rawInput.getChars(0, rawInput.length(), chars, 0);
//        return null;
//    }

    private Queue<Integer> parseKey(String key) {
        // if key type is num array then tries to parse it as such
        // defaults to string key if an exception occurs
        try {
            // method will early return if parsed key successfully
            return parseNumArrayKey(key);
        } catch (NumberFormatException ignored) {
        }
        return parseStringKey(key);
    }

    private Queue<Integer> parseStringKey(String key) {
        Queue<Integer> keyQueue = new LinkedList<>();
        for (int i = 0; i < key.length(); i++) {
            keyQueue.add((int) key.charAt(i));
        }
        return keyQueue;
    }

    private Queue<Integer> parseNumArrayKey(String key) throws NumberFormatException {
        Queue<Integer> keyQueue = new LinkedList<>();
        for (String k : key.split(",")) {
            keyQueue.add(Integer.valueOf(k));
        }
        return keyQueue;
    }
}
