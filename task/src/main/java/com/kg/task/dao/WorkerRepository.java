package com.kg.task.dao;

import com.kg.task.entities.Worker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    @Override
    List<Worker> findAll();

    Worker findWorkerByWorkerId(long id);

   HashSet<Worker> findAll(Specification<Worker> criteria);

}
