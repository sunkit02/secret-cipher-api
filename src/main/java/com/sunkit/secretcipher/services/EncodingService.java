package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.models.DecodingResult;
import com.sunkit.secretcipher.models.EncodingResult;

public interface EncodingService {
    EncodingResult encodeWithBytes(String rawInput, String key);
    DecodingResult decodeWithBytes(String encodedInput, String key);

}
