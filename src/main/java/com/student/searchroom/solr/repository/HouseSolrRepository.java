package com.student.searchroom.solr.repository;

import com.student.searchroom.solr.entity.HouseSolr;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface HouseSolrRepository extends SolrCrudRepository<HouseSolr, String> {

    @Query("?0")
    List<HouseSolr> search(String keyword);
}
