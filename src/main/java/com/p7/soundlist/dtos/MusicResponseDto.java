package com.p7.soundlist.dtos;

public record MusicResponseDto(
        Long id,
        String title,
        String artist,
        String genre,
        Integer duration,
        Long playlistId
) {
}
