package com.p7.soundlist.service;

import com.p7.soundlist.dtos.MusicResponseDto;
import com.p7.soundlist.dtos.PlaylistPatchDto;
import com.p7.soundlist.dtos.PlaylistRequestDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.exception.BusinessException;
import com.p7.soundlist.mapper.PlaylistMapper;
import com.p7.soundlist.model.Playlist;
import com.p7.soundlist.repository.PlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final MusicService musicService;

    //select * from playlists
    public Page<PlaylistResponseDto> getAll(Pageable pageable){
        var playlists = playlistRepository.findAll(pageable);
        return playlists.map(playlistMapper::toDto);
    }

    //select * from playlists where id = ?
    public PlaylistResponseDto getById(Long id){
        var foundPlaylist = playlistRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Playlist não encontrada"));
        return playlistMapper.toDto(foundPlaylist);
    }

    //select * from musics where playlistId = ?
    public Page<MusicResponseDto> getMusicsByPlaylist(Long id, Pageable pageable) {
        playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada com id: " + id));

        return musicService.getByPlaylistId(id, pageable);
    }

    //save
    @Transactional
    public PlaylistResponseDto create(PlaylistRequestDto playlistRequestDto){
        var playlist = playlistMapper.toEntity(playlistRequestDto);
        var savedPlaylist = playlistRepository.save(playlist);
        return playlistMapper.toDto(savedPlaylist);
    }

    //put
    @Transactional
    public PlaylistResponseDto update(Long id, PlaylistRequestDto playlistRequestDto){
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));

        playlist.setName(playlistRequestDto.name());
        playlist.setDescription(playlistRequestDto.description());

        return playlistMapper.toDto(playlistRepository.save(playlist));
    }

    //patch
    @Transactional
    public  PlaylistResponseDto patch(Long id, PlaylistPatchDto playlistPatchDto){
        if (playlistPatchDto.name() == null && playlistPatchDto.description() == null) {
            throw new BusinessException("Ao menos um campo deve ser informado para atualização");
        }

        if (playlistPatchDto.name() != null && playlistPatchDto.name().isBlank()) {
            throw new BusinessException("Nome não pode ser vazio");
        }

        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));
        if (playlistPatchDto.name() != null) {
            playlist.setName(playlistPatchDto.name());
        }
        if (playlistPatchDto.description() != null) {
            playlist.setDescription(playlistPatchDto.description());
        }
        return playlistMapper.toDto(playlistRepository.save(playlist));
    }

    //delete
    @Transactional
    public void delete(Long id){
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));
        playlistRepository.delete(playlist);
    }
}
