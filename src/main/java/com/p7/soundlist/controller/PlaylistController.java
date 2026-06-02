package com.p7.soundlist.controller;

import com.p7.soundlist.dtos.MusicResponseDto;
import com.p7.soundlist.dtos.PlaylistPatchDto;
import com.p7.soundlist.dtos.PlaylistRequestDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.service.PlaylistService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
@Tag(
        name = "Playlists",
        description = "Operações relacionadas às playlists"
)
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

    @GetMapping("/{id}/musics")
    public ResponseEntity<Page<MusicResponseDto>> getMusicsByPlaylistId(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(playlistService.getMusicsByPlaylist(id, pageable));
    }

    @PostMapping
    public ResponseEntity<PlaylistResponseDto> createPlaylist(@RequestBody @Valid PlaylistRequestDto playlistRequestDto) {
        var createdPlaylist = playlistService.create(playlistRequestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPlaylist.id())
                .toUri();

        return ResponseEntity.created(uri).body(createdPlaylist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> updatePlaylist(@PathVariable Long id, @RequestBody @Valid PlaylistRequestDto playlistRequestDto){
        return ResponseEntity.ok(playlistService.update(id, playlistRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaylistResponseDto> patchPlaylist(@PathVariable Long id, @RequestBody PlaylistPatchDto playlistPatchDto){
        return ResponseEntity.ok(playlistService.patch(id, playlistPatchDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id){
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
