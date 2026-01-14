package com.example.quote_journal.repository;

import com.example.quote_journal.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    // Custom query methods (Spring generates the implementation automatically!)
    List<Quote> findByCategory(String category);
    List<Quote> findByIsFavorite(boolean isFavorite);
    List<Quote> findByAuthorContainingIgnoreCase(String author);
}