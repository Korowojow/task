package com.kg.task.services;

import com.kg.task.dao.WorkerRepository;
import com.kg.task.entities.Worker;
import com.kg.task.utils.EmployeeSearchCriteriaHelper;
import com.kg.task.utils.EmployeeSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    private final EmployeeSearchCriteriaHelper employeeSearchCriteriaHelper;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, EmployeeSearchCriteriaHelper employeeSearchCriteriaHelper) {
        this.workerRepository = workerRepository;
        this.employeeSearchCriteriaHelper = employeeSearchCriteriaHelper;
    }

    @Transactional
    public Worker addWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public HashSet<Worker> findWorkerByCriteria(EmployeeSearchCriteria employeeSearchCriteria){
        return workerRepository.findAll(employeeSearchCriteriaHelper.getWorker(employeeSearchCriteria));
    };
}
