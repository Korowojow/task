package com.kg.task.utils;

import com.kg.task.entities.Car;
import com.kg.task.entities.Worker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeSearchCriteriaHelper {

    public Specification<Worker> getWorker(final EmployeeSearchCriteria request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getCarId() != null) {
                Join<Worker, Car> carIdJoin = root.join("cars");
                predicates.add(criteriaBuilder.equal(carIdJoin.get("carId"), request.getCarId()));
            }
            if (request.getFacility() != null) {
                predicates.add(criteriaBuilder.equal(root.get("facility"), request.getFacility()));
            }
            if (request.getPosition() != null) {
                predicates.add(criteriaBuilder.like(root.get("position").as(String.class), request.getPosition().name()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
