package com.kg.task.services;

import com.kg.task.entities.Car;
import com.kg.task.entities.Facility;
import com.kg.task.entities.Worker;
import com.kg.task.utils.WorkerPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:drop.sql","classpath:schema.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql")})
public class FacilityServiceTest {

    @Autowired
    FacilityService facilityService;

    @Autowired
    WorkerService workerService;

    @Autowired
    CarService carService;

    @Transactional
    @Test
    public void newFacilitySaved_Successfully(){
        //given
        Facility facility = new Facility("MICKIEWICZA","00-709709707");
        //when
        facilityService.addFacility(facility);
        //then
        assertTrue(facilityService.findAll().contains(facility));
    }

    @Test
    public void editFacilitySaved_Successfully(){
        //given
        Facility facility = new Facility("MICKIEWICZA","00-709709707");
        Facility addedFacility = facilityService.addFacility(facility);
        //when
        addedFacility.setAddress("SLOWACKIEGO");
        facilityService.updateFacility(addedFacility);
        //then
        assertEquals("SLOWACKIEGO",facilityService.findFacilityById(facility.getFacilityId()).getAddress());
    }

    @Test
    @Transactional
    public void addWorkerToFacility(){
        //given
        Facility facility = new Facility("MICKIEWICZA","00-709709707");
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker2 = new Worker(WorkerPosition.ACCOUNTANT);
        Facility addedFacility = facilityService.addFacility(facility);
        Worker addedWorker1 = workerService.addWorker(worker1);
        Worker addedWorker2 = workerService.addWorker(worker2);
        //when
        facilityService.addWorkerToFacility(addedWorker1,addedFacility);
        facilityService.addWorkerToFacility(addedWorker2,addedFacility);
        //then
        assertTrue(addedFacility.getWorkers().contains(worker1));
        assertTrue(addedFacility.getWorkers().contains(worker2));
    }

    @Test
    @Transactional
    public void removeWorkerFromFacility(){
        //given
        Facility facility = new Facility("MICKIEWICZA","00-709709707");
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker2 = new Worker(WorkerPosition.ACCOUNTANT);
        Facility addedFacility = facilityService.addFacility(facility);
        Worker addedWorker1 = workerService.addWorker(worker1);
        Worker addedWorker2 = workerService.addWorker(worker2);
        facilityService.addWorkerToFacility(addedWorker1,addedFacility);
        facilityService.addWorkerToFacility(addedWorker2,addedFacility);
        //when
        facilityService.removeWorkerFromFacility(addedWorker1,addedFacility);
        //then
        assertTrue(addedFacility.getWorkers().contains(worker2));
        assertFalse(addedFacility.getWorkers().contains(worker1));
    }

    @Test
    @Transactional
    public void findAllWorkers(){
        //given
        Facility facility = new Facility("MICKIEWICZA","00-709709707");
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker2 = new Worker(WorkerPosition.ACCOUNTANT);
        Facility addedFacility = facilityService.addFacility(facility);
        Worker addedWorker1 = workerService.addWorker(worker1);
        Worker addedWorker2 = workerService.addWorker(worker2);
        facilityService.addWorkerToFacility(addedWorker1,addedFacility);
        facilityService.addWorkerToFacility(addedWorker2,addedFacility);
        List<Worker> expected = new LinkedList<>();
        expected.add(worker1);
        expected.add(worker2);
        //when
        List<Worker> actual = facilityService.findAllWorkers(addedFacility);
        //then
        assertEquals(actual,expected);
    }

    @Test
    @Transactional
    public void findAllWorkersByCarAndFacility(){
        Worker worker0 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        workerService.addWorker(worker0);
        workerService.addWorker(worker1);
        Car car1 = new Car("SEDAN","GREEN", new Date(2000, Calendar.MAY,4),"2000l","2000KMH","2000","HUNDAI");
        Car car2 = new Car("COMBI","GREEN", new Date(2000, Calendar.MAY,4),"2000l","2000KMH","2000","HUNDAI");
        Car car3 = new Car("SEDAN","GREEN", new Date(2000, Calendar.MAY,4),"2000l","2000KMH","2000","TOYOTA");
        carService.addCar(car1);
        carService.addCar(car2);
        carService.addCar(car3);
        Facility facility1 = new Facility("MICKIEWICZA","00-709709707");
        Facility facility2 = new Facility("SIENKIEWICZA","01-709709707");
        facilityService.addFacility(facility1);
        facilityService.addFacility(facility2);
        carService.addWorkerToCar(worker0,car1);
        carService.addWorkerToCar(worker1,car2);
        facilityService.addWorkerToFacility(worker0,facility1);
        facilityService.addWorkerToFacility(worker1,facility1);
        List<Worker> expected1 = new LinkedList<>();
        List<Worker> expected2 = new LinkedList<>();
        expected1.add(worker0);
        expected2.add(worker1);
        //when 
        List<Worker> actual1 = facilityService.findAllWorkersByCarAndFacility(car1,facility1);
        List<Worker> actual2 = facilityService.findAllWorkersByCarAndFacility(car2,facility1);
        //then
        assertEquals(actual1,expected1);
        assertEquals(actual2,expected2);
    }

    @Transactional
    @Test
    public void deleteFacilityById(){
        //given
        Facility facility1 = new Facility("MICKIEWICZA","00-709709707");
        Facility facility2 = new Facility("SIENKIEWICZA","01-709709707");
        Facility addedFacility1 = facilityService.addFacility(facility1);
        facilityService.addFacility(facility2);
        //when
        facilityService.deleteFacility(addedFacility1.getFacilityId());
        //then
        assertFalse(facilityService.findAll().contains(facility1));
        assertTrue(facilityService.findAll().contains(facility2));
    }
}
