package com.capgemini.wsb.fitnesstracker.training.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingProvider trainingProvider;

    public TrainingController(TrainingProvider trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByUser(@PathVariable Long userId) {
        List<TrainingDTO> trainings = trainingProvider.getTrainingsByUser(userId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        List<TrainingDTO> trainings = trainingProvider.getTrainingsByActivityType(activityType);
        return ResponseEntity.ok(trainings);
    }

}
