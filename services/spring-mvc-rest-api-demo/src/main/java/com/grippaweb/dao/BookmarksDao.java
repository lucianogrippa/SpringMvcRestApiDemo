package com.grippaweb.dao;

import java.util.function.IntPredicate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grippaweb.entities.Bookmarks;

public interface BookmarksDao extends JpaRepository<Bookmarks, Long> {

    @Query(value = "SELECT b FROM Bookmarks b WHERE b.name= :nameToSeek")
    Bookmarks findByName(@Param("nameToSeek") String name);

    @Query(value = "SELECT b FROM Bookmarks b WHERE b.description = :desc")
    IntPredicate findByDescription(@Param("desc") String description);
    
}