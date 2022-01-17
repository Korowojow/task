package com.kg.task.services;

import com.kg.task.dao.CarRepository;
import com.kg.task.dao.WorkerRepository;
import com.kg.task.entities.Car;
import com.kg.task.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final WorkerRepository workerRepository;
    private final RentalService rentalService;

    @Autowired
    public CarService(CarRepository carRepository, WorkerRepository workerRepository, RentalService rentalService) {
        this.carRepository = carRepository;
        this.workerRepository = workerRepository;
        this.rentalService = rentalService;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    //dodaj samochód
    @Transactional
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    //zmien dane samochodu
    @Transactional
    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    //usun samochód
    public void deleteCar(long id) {
        rentalService.removeAllCarRentals(id);
        carRepository.delete(findCarById(id));
    }

    public Car findCarById(long id) {
        Car car = carRepository.findCarByCarId(id);
        return carRepository.findCarByCarId(id);
    }

    //znajdz po opiekunie
    @Transactional
    public List<Car> findAllCarsByWorker(Worker worker) {
        return carRepository.findAll().stream().filter(car -> car.getWorkers().contains(worker)).collect(Collectors.toList());
    }

    //przypisanie opiekuna do samochodu
    @Transactional
    public Car addWorkerToCar(Worker worker, Car car) {
        if (car.getWorkers() == null) {
            List<Worker> workers = new LinkedList<>();
            workers.add(worker);
            car.setWorkers(workers);
        } else {
            car.getWorkers().add(worker);
        }
        if (worker.getCars() == null) {
            List<Car> cars = new LinkedList<>();
            cars.add(car);
            worker.setCars(cars);
        } else {
            worker.getCars().add(car);
        }
        workerRepository.save(worker);
        return carRepository.save(car);
    }

    //znalezienie po typie i marce
    @Transactional
    public List<Car> findAllByTypeAndCarMake(String type, String carMake) {
        return carRepository.findAllByTypeAndCarMake(type, carMake);
    }

}
