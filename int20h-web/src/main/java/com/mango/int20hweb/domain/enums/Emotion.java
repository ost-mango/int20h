package com.mango.int20hweb.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Emotion {
    @JsonProperty("sadness")
    SADNESS,
    @JsonProperty("neutral")
    NEUTRAL,
    @JsonProperty("disgust")
    DISGUST,
    @JsonProperty("anger")
    ANGER,
    @JsonProperty("surprise")
    SURPRISE,
    @JsonProperty("fear")
    FEAR,
    @JsonProperty("happiness")
    HAPPINESS
}
