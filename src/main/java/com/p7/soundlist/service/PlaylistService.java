package com.p7.soundlist.service;

import com.p7.soundlist.dtos.PlaylistPatchDto;
import com.p7.soundlist.dtos.PlaylistRequestDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.exception.BusinessException;
import com.p7.soundlist.mapper.PlaylistMapper;
import com.p7.soundlist.model.Playlist;
import com.p7.soundlist.repository.PlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper mapper;

    //select * from playlists
    public Page<PlaylistResponseDto> getAll(Pageable pageable){
        return playlistRepository.findAll(pageable).map(mapper::toDto);
    }

    //select * from playlists where id = ?
    public PlaylistResponseDto getById(Long id){
        var foundPlaylist = playlistRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Playlist não encontrada"));
        return mapper.toDto(foundPlaylist);
    }

    //save
    public PlaylistResponseDto create(PlaylistRequestDto playlistRequestDto){
        var playlist = mapper.toEntity(playlistRequestDto);
        var savedPlaylist = playlistRepository.save(playlist);
        return mapper.toDto(savedPlaylist);
    }

    //put
    public PlaylistResponseDto update(Long id, PlaylistRequestDto playlistRequestDto){
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));

        playlist.setName(playlistRequestDto.name());
        playlist.setDescription(playlistRequestDto.description());

        return mapper.toDto(playlistRepository.save(playlist));
    }

    //patch
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
        return mapper.toDto(playlistRepository.save(playlist));
    }

    //delete
    public void delete(Long id){
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));
        playlistRepository.delete(playlist);
    }
}
