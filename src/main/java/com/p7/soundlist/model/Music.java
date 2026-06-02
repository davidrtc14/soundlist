package com.p7.soundlist.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(length = 64)
    private String genre;

    @Column(nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
