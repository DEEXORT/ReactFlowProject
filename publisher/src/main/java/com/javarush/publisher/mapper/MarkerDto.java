package com.javarush.reactflow.mapper;

import com.javarush.reactflow.model.marker.Marker;
import com.javarush.reactflow.model.marker.MarkerRequestTo;
import com.javarush.reactflow.model.marker.MarkerResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarkerDto {
    @Mapping(target = "topics", ignore = true)
    Marker fromDto(MarkerRequestTo markerDto);

    MarkerResponseTo toDto(Marker marker);
}
