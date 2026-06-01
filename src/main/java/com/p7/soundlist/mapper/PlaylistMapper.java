package com.p7.soundlist.mapper;

import com.p7.soundlist.dtos.PlaylistRequestDto;
import com.p7.soundlist.dtos.PlaylistResponseDto;
import com.p7.soundlist.model.Playlist;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    Playlist toEntity(PlaylistRequestDto dto);

    PlaylistResponseDto toDto(Playlist playlist);
}
