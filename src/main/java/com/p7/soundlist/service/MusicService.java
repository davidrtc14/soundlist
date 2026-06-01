package com.p7.soundlist.service;

import com.p7.soundlist.dtos.MusicPatchRequestDto;
import com.p7.soundlist.dtos.MusicRequestDto;
import com.p7.soundlist.dtos.MusicResponseDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.exception.BusinessException;
import com.p7.soundlist.mapper.MusicMapper;
import com.p7.soundlist.model.Music;
import com.p7.soundlist.model.Playlist;
import com.p7.soundlist.repository.MusicRepository;
import com.p7.soundlist.repository.PlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;
    private final MusicMapper mapper;

    //select * from music
    public Page<MusicResponseDto> getAll(Pageable pageable){
        var songs = musicRepository.findAll(pageable);

        if(songs.stream().count() == 0){
            throw new EntityNotFoundException("Nenhuma música encontrada");
        }

        return songs.map(mapper::toDto);
    }

    //select * from music where id = ?
    public MusicResponseDto getById(Long id){
        var selectedSong = musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Música não encontrada"));
        return mapper.toDto(selectedSong);
    }

    //save
    public MusicResponseDto add(MusicRequestDto musicRequestDto){
        Playlist playlist = playlistRepository.findById(musicRequestDto.playlistId()).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));

        Music music = mapper.toEntity(musicRequestDto);
        music.setPlaylist(playlist);
        return mapper.toDto(musicRepository.save(music));
    }

    //put
    public MusicResponseDto update(Long id, MusicRequestDto musicRequestDto){
        Music music = musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Música não encontrada"));
        Playlist playlist = playlistRepository.findById(musicRequestDto.playlistId()).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));

        music.setTitle(musicRequestDto.title());
        music.setArtist(musicRequestDto.artist());
        music.setGenre(musicRequestDto.genre());
        music.setDuration(musicRequestDto.duration());
        music.setPlaylist(playlist);

        return mapper.toDto(musicRepository.save(music));
    }

    //patch
    public MusicResponseDto patch(Long id, MusicPatchRequestDto musicPatchRequestDto){
        Music music = musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Música não encontrada"));

        if(musicPatchRequestDto.title() == null && musicPatchRequestDto.artist() == null && musicPatchRequestDto.genre() == null && musicPatchRequestDto.duration() == null && musicPatchRequestDto.playlistId() == null){
            throw new BusinessException("Ao menos um campo deve ser informado para atualização");
        }

        if (musicPatchRequestDto.title() != null) {
            music.setTitle(musicPatchRequestDto.title());
        }
        if (musicPatchRequestDto.artist() != null) {
            music.setArtist(musicPatchRequestDto.artist());
        }
        if (musicPatchRequestDto.genre() != null) {
            music.setGenre(musicPatchRequestDto.genre());
        }
        if (musicPatchRequestDto.duration() != null) {
            music.setDuration(musicPatchRequestDto.duration());
        }
        if (musicPatchRequestDto.playlistId() != null) {
            Playlist playlist = playlistRepository.findById(musicPatchRequestDto.playlistId()).orElseThrow(() -> new EntityNotFoundException("Playlist não encontrada"));
            music.setPlaylist(playlist);
        }

        return mapper.toDto(musicRepository.save(music));
    }

    //delete
    public void delete(Long id){
        Music music = musicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Música não encontrada"));
        musicRepository.delete(music);
    }
}
