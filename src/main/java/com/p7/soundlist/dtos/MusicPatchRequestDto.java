package com.p7.soundlist.dtos;

public record MusicPatchRequestDto(
        String title,
        String artist,
        String genre,
        Integer duration,
        Long playlistId
) {
}
