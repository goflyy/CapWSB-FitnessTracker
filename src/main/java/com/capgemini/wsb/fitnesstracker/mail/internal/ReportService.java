package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.sql.Date;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final EmailSender emailSender;

    public void generateAndSendReports() {
        List<User> users = userRepository.findAll();
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        Date startDate = Date.valueOf(firstDayOfMonth); // Konwersja do java.sql.Date
        Date endDate = Date.valueOf(lastDayOfMonth);    // Konwersja do java.sql.Date
    

        for (User user : users) {
            List<Training> userTrainings = trainingRepository.findAllByUserAndStartTimeBetween(user, startDate, endDate);
            sendMonthlyReport(user, userTrainings);
        }
    }

    private void sendMonthlyReport(User user, List<Training> trainings) {
        int totalTrainings = trainings.size();
        String subject = "Miesięczne podsumowanie treningów";
        String content = String.format(
                "Cześć %s %s,\n\n" +
                "W ostatnim miesiącu zarejestrowałeś(-aś) %d treningów.\n" +
                "Gratulacje i trzymaj tak dalej!\n\n" +
                "Twój zespół Fitness Tracker",
                user.getFirstName(), user.getLastName(), totalTrainings);

        EmailDto email = new EmailDto(user.getEmail(), subject, content);
        emailSender.send(email);
    }
}
