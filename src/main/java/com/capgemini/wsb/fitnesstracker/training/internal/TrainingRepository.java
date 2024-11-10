package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByUserId(Long userId);
    List<Training> findAllByActivityType(ActivityType activityType);
}
