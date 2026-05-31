package com.p7.soundlist.controller;

import com.p7.soundlist.dtos.PlaylistRequestDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<Page<PlaylistResponseDto>> getAllPlaylists(Pageable pageable) {
        var playlists = playlistService.getAll(pageable);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> getPlaylistById(@PathVariable Long id) {
        PlaylistResponseDto playlistResponse = playlistService.getById(id);
        return ResponseEntity.ok(playlistResponse);
    }

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody @Valid PlaylistRequestDto playlistRequestDto) {
        var createdPlaylist = playlistService.create(playlistRequestDto);
        return ResponseEntity.ok(createdPlaylist);
    }
}
