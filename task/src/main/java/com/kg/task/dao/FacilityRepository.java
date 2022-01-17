package com.kg.task.dao;

import com.kg.task.entities.Facility;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends PagingAndSortingRepository<Facility, Long> {

    @Override
    List<Facility> findAll();

    Facility findFacilityByFacilityId(long id);

}
