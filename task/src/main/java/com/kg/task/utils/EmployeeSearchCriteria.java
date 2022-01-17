package com.kg.task.utils;

import com.kg.task.entities.Car;
import com.kg.task.entities.Facility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSearchCriteria {
    private Facility facility;
    private Long carId;
    private WorkerPosition position;
}
