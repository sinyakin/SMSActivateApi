package com.sms_activate.util;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * An object keys and values in URL.
 * This class is designed for use as a drop-in replacement create url parameter string yourself.
 */
public class QueryStringBuilder {
    /**
     * Map parameter URL.
     */
    private Map<String, Object> parameterMap;

    /**
     * Constructor QueryStringBuilder with initialize values.
     *
     * @param key   name parameter url (unique and not be null).
     * @param value value parameter url (can be duplicate and not be null).
     */
    public QueryStringBuilder(@NotNull String key, @NotNull Object value) {
        this(new HashMap<String, Object>() {{
            put(key, value);
        }});
    }

    /**
     * Constructor QueryStringBuilder with initialize values.
     *
     * @param parameterMap key value the to url parameter.
     */
    public QueryStringBuilder(@NotNull Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /**
     * Appends the specified strings (key, value) to parameter URL.
     *
     * @param key   key with which the specified value is to be associated (not be null).
     * @param value value to be associated with the specified key.
     */
    public QueryStringBuilder append(@NotNull String key, Object value) {
        this.parameterMap.put(key, value);
        return this;
    }

    /**
     * Builds the http query string.
     *
     * @return http query string.
     */
    public String build() {
        return this.parameterMap.entrySet().stream().map(x -> {
            Object value = x.getValue();
            String key = x.getKey();

            if (value != null) {
                return String.join("=", key, value.toString());
            }

            return "";
        }).collect(Collectors.joining("&"));
    }


    /**
     * Returns a url parameter string.
     *
     * @return url parameter string.
     */
    @Override
    public String toString() {
        return build();
    }
}
