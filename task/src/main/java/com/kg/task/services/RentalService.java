package com.kg.task.services;

import com.kg.task.dao.RentalRepository;
import com.kg.task.entities.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    List<Long> findAllCarsHavingOver10Rentals(){
      return rentalRepository.findAllCarsHavingOver10Rentals();
    };

    Integer countRentedCarsBetween(Date startDate, Date endDate){
       return rentalRepository.countRentedCarsBetween(startDate,endDate);
    };

    Rental addRental(Rental rental){
        return rentalRepository.save(rental);
    }

    List<Rental> findAll(){
     return rentalRepository.findAll();
    }

    void removeAllCarRentals(long carId){
        rentalRepository.deleteAll(rentalRepository.findAll().stream().filter(rental -> rental.getCar().getCarId() == carId).collect(Collectors.toList()));
    }
}
