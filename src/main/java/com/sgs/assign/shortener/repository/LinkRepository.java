package com.sgs.assign.shortener.repository;

import com.sgs.assign.shortener.Link;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAll();

    @Cacheable(value = "urlCash", key = "#url")
    Link findByUrl(String url);


    @CacheEvict(value = "urlCash", key = "#link.url")
    default void updateCacheAndSave(Link link) {
        this.save(link);
    }


    Link findByEncoded(String encoded);

}
