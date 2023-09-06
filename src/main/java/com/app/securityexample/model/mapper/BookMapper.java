package com.app.securityexample.model.mapper;

import com.app.securityexample.model.BookRequest;
import com.app.securityexample.persistence.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper
{
    //BookRequest toBookRequest(BookEntity bookEntity);
    BookEntity toBook(BookRequest bookRequest);

    void updateBookEntityFromRequest(BookRequest bookRequest, @MappingTarget BookEntity bookEntity);
}
