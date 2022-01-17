package com.kg.task.services;

import com.kg.task.entities.Car;
import com.kg.task.entities.Client;
import com.kg.task.entities.Rental;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:drop.sql","classpath:schema.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql")})
public class RentalServiceTest {

    @Autowired
    RentalService rentalService;

    @Autowired
    CarService carService;

    @Test
    @Transactional
    public void findAllCarsHavingOver10Rentals(){
        //given
        Car car1 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car2 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Client client1 = new Client("ROZANA", new Date(), 1000);
        Client client2 = new Client("ROZANA", new Date(), 1000);
        Client client3 = new Client("ROZANA", new Date(), 1000);
        Client client4 = new Client("ROZANA", new Date(), 1000);
        Client client5 = new Client("ROZANA", new Date(), 1000);
        Client client6 = new Client("ROZANA", new Date(), 1000);
        Client client7 = new Client("ROZANA", new Date(), 1000);
        Client client8 = new Client("ROZANA", new Date(), 1000);
        Client client9 = new Client("ROZANA", new Date(), 1000);
        Client client10 = new Client("ROZANA", new Date(), 1000);
        Client client11 = new Client("ROZANA", new Date(), 1000);
        carService.addCar(car1);
        carService.addCar(car2);
        Rental rental1 = new Rental(new Date(), new Date(), 1000);
        Rental rental2 = new Rental(new Date(), new Date(), 1000);
        Rental rental3 = new Rental(new Date(), new Date(), 1000);
        Rental rental4 = new Rental(new Date(), new Date(), 1000);
        Rental rental5 = new Rental(new Date(), new Date(), 1000);
        Rental rental6 = new Rental(new Date(), new Date(), 1000);
        Rental rental7 = new Rental(new Date(), new Date(), 1000);
        Rental rental8 = new Rental(new Date(), new Date(), 1000);
        Rental rental9 = new Rental(new Date(), new Date(), 1000);
        Rental rental10 = new Rental(new Date(), new Date(), 1000);
        Rental rental11 = new Rental(new Date(), new Date(), 1000);
        rental1.setCar(car1);
        rental2.setCar(car1);
        rental3.setCar(car1);
        rental4.setCar(car1);
        rental5.setCar(car1);
        rental6.setCar(car1);
        rental7.setCar(car1);
        rental8.setCar(car1);
        rental9.setCar(car1);
        rental10.setCar(car1);
        rental11.setCar(car1);
        rental1.setClient(client1);
        rental2.setClient(client2);
        rental3.setClient(client3);
        rental4.setClient(client4);
        rental5.setClient(client5);
        rental6.setClient(client6);
        rental7.setClient(client7);
        rental8.setClient(client8);
        rental9.setClient(client9);
        rental10.setClient(client10);
        rental11.setClient(client11);
        rentalService.addRental(rental1);
        rentalService.addRental(rental2);
        rentalService.addRental(rental3);
        rentalService.addRental(rental4);
        rentalService.addRental(rental5);
        rentalService.addRental(rental6);
        rentalService.addRental(rental7);
        rentalService.addRental(rental8);
        rentalService.addRental(rental9);
        rentalService.addRental(rental10);
        rentalService.addRental(rental11);
        Rental rental12 = new Rental(new Date(), new Date(), 1000);
        rental12.setCar(car2);
        rental12.setClient(client1);
        rentalService.addRental(rental2);
        //when
        List<Long> actualCars = rentalService.findAllCarsHavingOver10Rentals();
        //then
        assertTrue(actualCars.contains(car1.getCarId()));
    }

    @Transactional
    @Test
    public void countRentedCarsBetween(){
        //given
        Date startDate = new Date(2000, Calendar.MAY,4);
        Date endDate =  new Date(2022, Calendar.MAY,4);
        Car car1 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car2 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        carService.addCar(car1);
        carService.addCar(car2);
        Rental rental1 = new Rental(new Date(2001, Calendar.MAY,4), new Date(), 1000);
        Rental rental2 = new Rental(new Date(2050, Calendar.MAY,4), new Date(), 1000);
        Rental rental3 = new Rental(new Date(2019, Calendar.MAY,4), new Date(), 1000);
        rental1.setCar(car1);
        rental2.setCar(car2);
        rental3.setCar(car1);
        rentalService.addRental(rental1);
        rentalService.addRental(rental2);
        rentalService.addRental(rental3);

        //when
        int actual = rentalService.countRentedCarsBetween(startDate,endDate);
        //then
        assertEquals(1,actual);

    }

}
