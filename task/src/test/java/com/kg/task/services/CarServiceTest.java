package com.kg.task.services;

import com.kg.task.dao.RentalRepository;
import com.kg.task.entities.Car;
import com.kg.task.entities.Rental;
import com.kg.task.entities.Worker;
import com.kg.task.utils.WorkerPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:drop.sql","classpath:schema.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql")})
public class CarServiceTest {

    @Autowired
    RentalService rentalService;

    @Autowired
    CarService carService;

    @Autowired
    WorkerService workerService;

    @Transactional
    @Test
    public void newCarSaved_Successfully() {
        //given
        Car car = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        //when
        carService.addCar(car);
        //then
        assertTrue(carService.findAll().contains(car));
    }

    @Test
    public void editCarSaved_Successfully() {
        //given
        Car car = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car addedCar = carService.addCar(car);
        //when
        addedCar.setType("SEDAN");
        carService.updateCar(addedCar);
        //then
        assertEquals("SEDAN", carService.findCarById(car.getCarId()).getType());
    }

    @Transactional
    @Test
    public void findCarByTypeAndCarMake_Successfully() {
        //given
        Car car0 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car1 = new Car("SEDAN", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car2 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car3 = new Car("SEDAN", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "TOYOTA");
        carService.addCar(car0);
        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);
        //when
        int actual1 = carService.findAllByTypeAndCarMake("SEDAN", "HUNDAI").size();
        int actual2 = carService.findAllByTypeAndCarMake("COMBI", "HUNDAI").size();
        //then
        assertEquals(1, actual1);
        assertEquals(2, actual2);
    }

    @Test
    @Transactional
    public void addWorkerToCar() {
        //given
        Car car = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker2 = new Worker(WorkerPosition.ACCOUNTANT);
        Car addedCar = carService.addCar(car);
        Worker addedWorker1 = workerService.addWorker(worker1);
        Worker addedWorker2 = workerService.addWorker(worker2);
        //when
        carService.addWorkerToCar(addedWorker1, addedCar);
        carService.addWorkerToCar(addedWorker2, addedCar);
        //then
        assertTrue(addedCar.getWorkers().contains(worker1));
        assertTrue(addedCar.getWorkers().contains(worker2));
    }

    @Test
    @Transactional
    public void deleteCarById() {
        //given
        Car car1 = new Car("COMBI", "BLUE", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car2 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car addedCar1 = carService.addCar(car1);
        carService.addCar(car2);
        //when
        carService.deleteCar(addedCar1.getCarId());
        //then
        assertFalse(carService.findAll().contains(car1));
        assertTrue(carService.findAll().contains(car2));
    }

    @Test
    @Transactional
    public void findCarByWorker_Successfully() {
        //given
        Worker worker0 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker2 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker3 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker4 = new Worker(WorkerPosition.ACCOUNTANT);
        workerService.addWorker(worker0);
        workerService.addWorker(worker1);
        workerService.addWorker(worker2);
        workerService.addWorker(worker3);
        workerService.addWorker(worker4);
        List<Worker> allWorkers = new ArrayList<>();
        List<Worker> twoWorkers = new ArrayList<>();
        List<Worker> fourWorkers = new ArrayList<>();
        twoWorkers.add(worker0);
        twoWorkers.add(worker1);
        fourWorkers.add(worker0);
        fourWorkers.add(worker1);
        fourWorkers.add(worker2);
        fourWorkers.add(worker3);
        allWorkers.add(worker0);
        allWorkers.add(worker1);
        allWorkers.add(worker2);
        allWorkers.add(worker3);
        allWorkers.add(worker4);
        Car car1 = new Car("SEDAN", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car2 = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        Car car3 = new Car("SEDAN", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "TOYOTA");
        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);
        car1.setWorkers(allWorkers);
        car2.setWorkers(fourWorkers);
        car3.setWorkers(twoWorkers);
        //when
        int actual1 = carService.findAllCarsByWorker(worker0).size();
        int actual2 = carService.findAllCarsByWorker(worker3).size();
        int actual3 = carService.findAllCarsByWorker(worker4).size();

        //then
        assertEquals(3, actual1);
        assertEquals(2, actual2);
        assertEquals(1, actual3);
    }

    @Test
    @Transactional
    public void deleteOnCascadeCar(){
        //given
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
        carService.deleteCar(car1.getCarId());
        //then
        assertFalse(rentalService.findAll().contains(rental1));
        assertTrue(rentalService.findAll().contains(rental2));
        assertFalse(rentalService.findAll().contains(rental3));

    }

    //-------------------------------------------------------------------------------------------------------
    //----------------------------------OPTIMISTIC LOCKING TESTS---------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testVersionedOptimisticLocking1() {
        // given
        Car car1 = new Car("SEDAN", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        carService.addCar(car1);
        // when
        doAnUpdate(car1);
        // then
        // exception
    }

    @Transactional
    public void doAnUpdate(Car car1) {
        doUpdateOneMoreTime(car1);
        car1.setType("COMBI");
        carService.updateCar(car1);
    }

    @Transactional
    public void doUpdateOneMoreTime(Car car1) {
        car1.setType("TIR");
        carService.updateCar(car1);
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testVersionedOptimisticLocking2() {
        // given
        Car car1 = new Car("SEDAN", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        carService.addCar(car1);
        // when
        car1.setCarMake("TOYOTA");
        carService.updateCar(car1);
        car1.setCarMake("HONDA");
        carService.updateCar(car1);
        // then
        // exception
    }
}
