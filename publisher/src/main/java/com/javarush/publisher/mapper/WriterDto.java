package com.javarush.publisher.mapper;

import com.javarush.publisher.model.writer.Writer;
import com.javarush.publisher.model.writer.WriterRequestTo;
import com.javarush.publisher.model.writer.WriterResponseTo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WriterDto {
    Writer fromDto(WriterRequestTo writerRequestTo);
    WriterResponseTo toDto(Writer writer);
}
