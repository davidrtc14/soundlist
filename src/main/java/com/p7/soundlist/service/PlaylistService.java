package com.p7.soundlist.service;

import com.p7.soundlist.dtos.PlaylistRequestDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.mapper.PlaylistMapper;
import com.p7.soundlist.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper mapper;

    //select * from playlists
    public Page<PlaylistResponseDto> getAll(Pageable pageable){
        return playlistRepository.findAll(pageable).map(mapper::toDto);
    }

    //save
    public PlaylistResponseDto create(PlaylistRequestDto playlistRequestDto){
        var playlist = mapper.toEntity(playlistRequestDto);
        var savedPlaylist = playlistRepository.save(playlist);
        return mapper.toDto(savedPlaylist);
    }
}
