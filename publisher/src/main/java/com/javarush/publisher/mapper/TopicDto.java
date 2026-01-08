package com.javarush.reactflow.mapper;

import com.javarush.reactflow.model.marker.Marker;
import com.javarush.reactflow.model.topic.Topic;
import com.javarush.reactflow.model.topic.TopicRequestTo;
import com.javarush.reactflow.model.topic.TopicResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TopicDto {
    @Mapping(source = "writerId", target = "writer.id")
    @Mapping(source = "markers", target = "markers", qualifiedByName = "stringListToMarkerList")
    Topic fromDto (TopicRequestTo topicRequestTo);

    @Mapping(source = "writer.id", target = "writerId")
    @Mapping(source = "markers", target = "markers", qualifiedByName = "markerListToStringList")
    TopicResponseTo toDto (Topic topicResponseTo);

    @Named("stringListToMarkerList")
    default List<Marker> stringListToMarkerList (List<String> stringList){
        if (stringList == null) return null;
        return stringList.stream().map(this::stringToMarker).collect(Collectors.toList());
    }

    default Marker stringToMarker (String string){
        if (string == null) return null;
        return Marker.builder().name(string).build();
    }

    @Named("markerListToStringList")
    default String[] markerListToStringList (List<Marker> markerList){
        if (markerList == null) return null;
        String[] stringArray = new String[markerList.size()];
        for (int i = 0; i < markerList.size(); i++) {
            stringArray[i] = markerList.get(i).getName();
        }
        return stringArray;
    }

}
