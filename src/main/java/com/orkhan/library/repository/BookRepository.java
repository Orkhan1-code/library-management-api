package com.orkhan.library.repository;

import com.orkhan.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByPublicationYearGreaterThan(Integer year);
    List<Book> findByCategoriesName(String categoryName);
    
    @Query("""
            SELECT b
            FROM Book b
            JOIN b.author a
            WHERE LOWER(a.fullName)
            LIKE LOWER(CONCAT('%', :authorName, '%'))
            """)
            
    List<Book> findBooksByAuthorName(@Param("authorName") String authorName);
            
    @EntityGraph(attributePaths = {
        "author",
        "categories"
        })
        
    @Query("SELECT b FROM Book b")
    List<Book> findAllWithAuthorAndCategories();
}
