package com.orkhan.library.specification;

import com.orkhan.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> title == null || title.isBlank() ? cb.conjunction() : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasPublicationYear(Integer year) {
        return (root, query, cb) -> year == null ? cb.conjunction() : cb.equal(root.get("publicationYear"), year);
    }
}