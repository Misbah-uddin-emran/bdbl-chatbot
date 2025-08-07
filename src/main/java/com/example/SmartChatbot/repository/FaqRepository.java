package com.example.SmartChatbot.repository;


import com.example.SmartChatbot.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findTop10ByOrderByIdDesc();
    @Query(value = "SELECT * FROM faq ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Faq> findRandomFaqs(@Param("limit") int limit);
}