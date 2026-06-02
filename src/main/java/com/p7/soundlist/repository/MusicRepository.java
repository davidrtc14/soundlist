package com.p7.soundlist.repository;

import com.p7.soundlist.model.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findByPlaylistId(Long playlistId, Pageable pageable);
}
