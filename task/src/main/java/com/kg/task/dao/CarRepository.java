package com.kg.task.dao;

import com.kg.task.entities.Car;
import com.kg.task.entities.Worker;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
    @Override
    List<Car> findAll();

    Car findCarByCarId(long id);

    List<Car> findAllByTypeAndCarMake(String type, String carMake);
}
