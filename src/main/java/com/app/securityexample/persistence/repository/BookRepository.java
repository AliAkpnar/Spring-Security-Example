package com.app.securityexample.persistence.repository;

import com.app.securityexample.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long>
{

}
