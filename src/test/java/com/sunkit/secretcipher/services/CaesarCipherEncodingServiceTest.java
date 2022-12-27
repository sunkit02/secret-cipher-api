package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.models.DecodingResult;
import com.sunkit.secretcipher.models.EncodingResult;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThat;

class CaesarCipherEncodingServiceTest {

    private final CaesarCipherEncodingService underTest =
            new CaesarCipherEncodingService();

    @Test
    void canEncodeWithBytes() {
        // given
        String rawString = "abcdefg";
        String key = "1,2,3";
        // when
        EncodingResult encodingResult = underTest.encodeWithBytes(rawString, key);

        // then
        assertThat(encodingResult.encodedString()).isEqualTo("bdfegih");
        assertThat(encodingResult.encodedBytes()).isEqualTo("bdfegih".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void canDecodeWithBytes() {
        // given
        String encodedString = "bdfegih";
        String key = "1,2,3";
        // when
        DecodingResult decodingResult = underTest.decodeWithBytes(encodedString, key);

        // then
        assertThat(decodingResult.decodedString()).isEqualTo("abcdefg");
        assertThat(decodingResult.decodedBytes()).isEqualTo("abcdefg".getBytes(StandardCharsets.UTF_8));
    }
}