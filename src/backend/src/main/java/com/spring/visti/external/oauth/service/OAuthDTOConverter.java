package com.spring.visti.external.oauth.service;

import java.util.Map;

public interface OAuthDTOConverter<T> {
    T convertToDto(Map<String, String> params);
}