package com.kg.task.dao;

import com.kg.task.entities.Car;
import com.kg.task.entities.Rental;
import org.hibernate.Criteria;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Repository
public interface RentalRepository extends PagingAndSortingRepository<Rental, Long> {

    @Override
    List<Rental> findAll();

    @Query("select a.car.carId from Rental a group by a.car.carId having count(distinct a.client.clientId)>10")
    List<Long> findAllCarsHavingOver10Rentals();

    @Query("select count(distinct a.car.carId) from Rental a where a.startDate between ?1 AND ?2")
    Integer countRentedCarsBetween(Date startDate, Date endDate);


}
