package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserOlderThanDto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<TrainingDTO> getTrainingsByUser(Long userId) {
        return trainingRepository.findAllByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TrainingDTO> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId).map(this::mapToDTO);
    }

    private TrainingDTO mapToDTO(Training training) {
    TrainingDTO dto = new TrainingDTO();
    dto.setId(training.getId());
    
    UserDto userDto = new UserDto(
            training.getUser().getId(),
            training.getUser().getFirstName(),
            training.getUser().getLastName(),
            training.getUser().getBirthdate(),
            training.getUser().getEmail()
    );
    
    dto.setUser(userDto);
    
    dto.setStartTime(training.getStartTime());
    dto.setEndTime(training.getEndTime());
    dto.setActivityType(training.getActivityType());
    dto.setDistance(training.getDistance());
    dto.setAverageSpeed(training.getAverageSpeed());

    return dto;
}

@Override
    public List<TrainingDTO> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAllByActivityType(activityType).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    

}
