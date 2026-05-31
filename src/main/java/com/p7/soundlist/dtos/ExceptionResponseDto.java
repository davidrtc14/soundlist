package com.p7.soundlist.dtos;

import java.time.LocalDateTime;
import java.util.Map;

public record ExceptionResponseDto(
        String status,
        Map<String, String> errors,
        LocalDateTime localDateTime
) {
}
