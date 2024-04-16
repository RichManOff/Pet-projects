package com.example.provence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

@Getter
@Setter
@Builder
public class ImageDataDTO {
    private final byte[] imageBytes;
    private final MediaType mediaType;
}
