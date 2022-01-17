package com.kg.task.services;

import com.kg.task.dao.FacilityRepository;
import com.kg.task.dao.WorkerRepository;
import com.kg.task.entities.Car;
import com.kg.task.entities.Facility;
import com.kg.task.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public FacilityService(FacilityRepository facilityRepository, WorkerRepository workerRepository) {
        this.facilityRepository = facilityRepository;
        this.workerRepository = workerRepository;
    }

    public List<Facility> findAll() {
        return facilityRepository.findAll();
    }

    //dodaj placowke
    @Transactional
    public Facility addFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    //zmien dane placowki
    @Transactional
    public Facility updateFacility(Facility facility) {
        return facilityRepository.save(facility);
    }

    public Facility findFacilityById(long id) {
        return facilityRepository.findFacilityByFacilityId(id);
    }

    //usun placówke
    public void deleteFacility(Long id) {
        facilityRepository.delete(findFacilityById(id));
    }

    //dodaj pracownika do placówki
    @Transactional
    public Facility addWorkerToFacility(Worker worker, Facility facility) {
        if (facility.getWorkers() == null) {
            List<Worker> workers = new LinkedList<>();
            workers.add(worker);
            facility.setWorkers(workers);
        } else {
            facility.getWorkers().add(worker);
        }
        worker.setFacility(facility);
        workerRepository.save(worker);
        return facilityRepository.save(facility);
    }

    //usun pracownika z placówki
    @Transactional
    public Facility removeWorkerFromFacility(Worker worker, Facility facility) {
        facility.getWorkers().remove(worker);
        worker.setFacility(null);
        return facilityRepository.save(facility);
    }

    //wyszukaj wszystkich pracownikow placowki
    public List<Worker> findAllWorkers(Facility facility) {
        return facility.getWorkers();
    }

    //wyszukaj pracownikow placowki ktorzy opiekuja sie danym samochodem
    @Transactional
    public List<Worker> findAllWorkersByCarAndFacility(Car car, Facility facility) {
        List<Worker> facilityWorkers = facility.getWorkers();
        List<Worker> carWorkers = car.getWorkers();
        return facilityWorkers.stream().filter(carWorkers::contains).collect(Collectors.toList());
    }

}
