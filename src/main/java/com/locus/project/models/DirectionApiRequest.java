package com.locus.project.models;

import lombok.*;

@ToString()
@EqualsAndHashCode()
public class DirectionApiRequest {
    @Getter
    @Setter
    @NonNull
    private String origin;

    @Getter
    @Setter
    @NonNull
    private String destination;

    @Getter
    @Setter
    @NonNull
    private String key;

    public DirectionApiRequest() {
    }

    public DirectionApiRequest(String origin, String destination, String key) {
        this.origin = origin;
        this.destination = destination;
        this.key = key;
    }
}