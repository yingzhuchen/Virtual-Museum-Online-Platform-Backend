package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets AnnotationType
 */
public enum AnnotationType {

    INTRODUCTION("introduction"),
    TRANSMISSION("transmission"),
    NAVIGATION("navigation"),

    OTHER("other");

    private String value;

    AnnotationType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static AnnotationType fromValue(String value) {
        for (AnnotationType b : AnnotationType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

