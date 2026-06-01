package com.p7.soundlist.mapper;

import com.p7.soundlist.dtos.MusicRequestDto;
import com.p7.soundlist.dtos.MusicResponseDto;
import com.p7.soundlist.model.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicMapper {
    @Mapping(target = "playlist", ignore = true)
    Music toEntity(MusicRequestDto dto);

    @Mapping(source = "playlist.id", target = "playlistId")
    MusicResponseDto toDto(Music music);
}
