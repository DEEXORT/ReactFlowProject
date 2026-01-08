package com.javarush.publisher.mapper;

import com.javarush.publisher.model.marker.Marker;
import com.javarush.publisher.model.marker.MarkerRequestTo;
import com.javarush.publisher.model.marker.MarkerResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarkerDto {
    @Mapping(target = "topics", ignore = true)
    Marker fromDto(MarkerRequestTo markerDto);

    MarkerResponseTo toDto(Marker marker);
}
