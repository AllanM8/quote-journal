package com.example.quote_journal.controller;

import com.example.quote_journal.model.Quote;
import com.example.quote_journal.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    // GET all quotes
    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    // GET quote by ID
    @GetMapping("/{id}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable Long id) {
        Optional<Quote> quote = quoteRepository.findById(id);
        return quote.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Create new quote
    @PostMapping
    public ResponseEntity<Quote> createQuote(@RequestBody Quote quote) {
        Quote savedQuote = quoteRepository.save(quote);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuote);
    }

    // PUT - Update existing quote
    @PutMapping("/{id}")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quoteDetails) {
        Optional<Quote> quoteOptional = quoteRepository.findById(id);

        if (quoteOptional.isPresent()) {
            Quote quote = quoteOptional.get();
            quote.setText(quoteDetails.getText());
            quote.setAuthor(quoteDetails.getAuthor());
            quote.setPersonalNote(quoteDetails.getPersonalNote());
            quote.setCategory(quoteDetails.getCategory());
            quote.setFavorite(quoteDetails.isFavorite());

            Quote updatedQuote = quoteRepository.save(quote);
            return ResponseEntity.ok(updatedQuote);
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE quote
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        if (quoteRepository.existsById(id)) {
            quoteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET favorites
    @GetMapping("/favorites")
    public List<Quote> getFavoriteQuotes() {
        return quoteRepository.findByIsFavorite(true);
    }

    // GET by category
    @GetMapping("/category/{category}")
    public List<Quote> getQuotesByCategory(@PathVariable String category) {
        return quoteRepository.findByCategory(category);
    }
}