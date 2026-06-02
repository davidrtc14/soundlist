package com.p7.soundlist.controller;

import com.p7.soundlist.dtos.MusicPatchRequestDto;
import com.p7.soundlist.dtos.MusicRequestDto;
import com.p7.soundlist.dtos.MusicResponseDto;
import com.p7.soundlist.service.MusicService;

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
@RequestMapping("/musics")
@RequiredArgsConstructor
@Tag(
        name = "Músicas",
        description = "Operações relacionadas às músicas"
)
public class MusicController {
    private final MusicService musicService;

    @GetMapping
    public ResponseEntity<Page<MusicResponseDto>> getAllMusics(Pageable pageable) {
        var songs = musicService.getAll(pageable);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicResponseDto> getMusicById(@PathVariable Long id) {
        MusicResponseDto musicResponse = musicService.getById(id);
        return ResponseEntity.ok(musicResponse);
    }

    @PostMapping
    public ResponseEntity<MusicResponseDto> addMusic(@RequestBody @Valid MusicRequestDto musicRequestDto){
        var newSong = musicService.add(musicRequestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSong.id())
                .toUri();

        return ResponseEntity.created(uri).body(newSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicResponseDto> updateMusic(@PathVariable Long id, @RequestBody @Valid MusicRequestDto musicRequestDto){
        return ResponseEntity.ok(musicService.update(id, musicRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MusicResponseDto> patchMusic(@PathVariable Long id, @RequestBody MusicPatchRequestDto musicPatchRequestDto){
        return ResponseEntity.ok(musicService.patch(id, musicPatchRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long id){
        musicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
