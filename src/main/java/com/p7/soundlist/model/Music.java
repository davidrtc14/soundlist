package com.p7.soundlist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tb_musics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String title;

    @NotBlank(message = "O artista é obrigatório")
    private String artist;

    @Column(length = 64)
    private String genre;

    @NotNull(message = "A duração é obrigatória")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
