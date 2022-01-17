package com.kg.task.services;

import com.kg.task.entities.Car;
import com.kg.task.entities.Facility;
import com.kg.task.entities.Worker;
import com.kg.task.utils.EmployeeSearchCriteria;
import com.kg.task.utils.WorkerPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:drop.sql","classpath:schema.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql")})
public class WorkerServiceTest {

    @Autowired
    WorkerService workerService;

    @Autowired
    FacilityService facilityService;

    @Autowired
    CarService carService;

    @Transactional
    @Test
    public void findByCriteriaTest(){
        Car car = new Car("COMBI", "GREEN", new Date(2000, Calendar.MAY, 4), "2000l", "2000KMH", "2000", "HUNDAI");
        carService.addCar(car);
        Facility facility = new Facility("MICKIEWICZA","00-709709707");
        facilityService.addFacility(facility);
        Worker worker0 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker1 = new Worker(WorkerPosition.ACCOUNTANT);
        Worker worker2 = new Worker(WorkerPosition.SELLER);
        Worker worker3 = new Worker(WorkerPosition.SELLER);
        Worker worker4 = new Worker(WorkerPosition.SELLER);
        workerService.addWorker(worker0);
        workerService.addWorker(worker1);
        workerService.addWorker(worker2);
        workerService.addWorker(worker3);
        workerService.addWorker(worker4);
        facilityService.addWorkerToFacility(worker0,facility);
        facilityService.addWorkerToFacility(worker3,facility);
        carService.addWorkerToCar(worker0, car);
        EmployeeSearchCriteria employeeSearchCriteriaOnlyPosition = new EmployeeSearchCriteria();
        employeeSearchCriteriaOnlyPosition.setPosition(WorkerPosition.ACCOUNTANT);
        EmployeeSearchCriteria employeeSearchCriteriaAllCriteria = new EmployeeSearchCriteria();
        employeeSearchCriteriaAllCriteria.setPosition(WorkerPosition.ACCOUNTANT);
        employeeSearchCriteriaAllCriteria.setCarId(car.getCarId());
        employeeSearchCriteriaAllCriteria.setFacility(facility);

        //when
        HashSet<Worker> actualWorkersOnlyPosition = workerService.findWorkerByCriteria(employeeSearchCriteriaOnlyPosition);
        HashSet<Worker> actualWorkersAllCriteria = workerService.findWorkerByCriteria(employeeSearchCriteriaAllCriteria);
        //then
        //--------------------------ONLY POSITION------------------------------
        assertTrue(actualWorkersOnlyPosition.contains(worker0));
        assertTrue(actualWorkersOnlyPosition.contains(worker1));
        assertFalse(actualWorkersOnlyPosition.contains(worker2));
        assertFalse(actualWorkersOnlyPosition.contains(worker3));
        assertFalse(actualWorkersOnlyPosition.contains(worker4));
        //------------------------ALL CRITERIA---------------------------------
        assertTrue(actualWorkersAllCriteria.contains(worker0));
    }
}
