package com.p7.soundlist.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MusicRequestDto(
        @NotBlank(message = "O título da música é obrigatório")
        String title,

        @NotBlank(message = "O artista da música é obrigatório")
        String artist,

        String genre,

        @NotNull(message = "A duração da música é obrigatória")
        @Positive(message = "A duração da música deve ser um número positivo")
        Integer duration,

        @NotNull(message = "O ID da playlist é obrigatório")
        Long playlistId
) {
}
