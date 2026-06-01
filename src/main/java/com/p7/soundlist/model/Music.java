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
    private String title;
    private String artist;
    private String genre;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;


}
