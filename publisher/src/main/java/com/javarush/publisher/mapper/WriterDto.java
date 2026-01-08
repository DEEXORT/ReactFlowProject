package com.javarush.reactflow.mapper;

import com.javarush.reactflow.model.writer.Writer;
import com.javarush.reactflow.model.writer.WriterRequestTo;
import com.javarush.reactflow.model.writer.WriterResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WriterDto {
    Writer fromDto(WriterRequestTo writerRequestTo);
    WriterResponseTo toDto(Writer writer);
}
