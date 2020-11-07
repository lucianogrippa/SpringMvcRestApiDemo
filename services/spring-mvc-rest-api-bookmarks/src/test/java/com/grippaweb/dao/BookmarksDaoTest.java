package com.grippaweb.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.grippaweb.entities.Bookmarks;

@TestPropertySource(properties = {"app.profile=testcase"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookmarksDaoTest {
    @Autowired
    BookmarksDao bookmarksDao;
    
    String bookmarkName = "bookmark test";
    
    
    
    @Test
    public void A_insertNewBookmark() {
	Bookmarks bookmark = new Bookmarks();
	
	bookmark.setName(bookmarkName);
	
	bookmark.setDescription("the bookmark description");
	
	bookmark.setLink("http://grippaweb.eu");
	
	bookmarksDao.save(bookmark);
	
	assertThat(bookmarksDao
		.findByName(bookmarkName)).isNotNull();
    }
    
    @Test
    public void B_updateBookmark() {
	String expected ="updated test description "+System.currentTimeMillis();
	
	Bookmarks bookmark = bookmarksDao.findByName(bookmarkName);
	
	bookmark.setDescription(expected);
	
	bookmarksDao.save(bookmark);
	
	assertThat(bookmarksDao
		.findByDescription(expected)).isNotNull();
    }
    
    @Test
    public void C_deleteBookmark() {
	Bookmarks bookmark = bookmarksDao.findByName(bookmarkName);
	bookmarksDao.delete(bookmark);
	
	assertThat(bookmarksDao
		.findByName(bookmarkName)).isNull();
    }
}