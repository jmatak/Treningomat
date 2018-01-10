package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
    Training findTrainingById(Integer id);

    @Query(value = "SELECT COUNT(DISTINCT training) FROM training " +
            "JOIN training_attendants ON training.id = trainings_id " +
            "WHERE training_group_id IN (SELECT id FROM training_group WHERE trainer_id = :trainerId) " +
            "AND attendants_id = :attendantId", nativeQuery = true)
    Integer countTrainingsWhereAttendantAndTrainerAre(@Param("attendantId")Integer attendantId,@Param("trainerId")Integer trainerId);

    List<Training> findTrainingsByTrainingGroupInAndEndsAtAfter(List<TrainingGroup> groups, Date date);

    List<Training> findTrainingsByTrainingGroupInAndStartAtBefore(List<TrainingGroup> groups, Date date);

    List<Training> findTrainingsByAttendantsContainsAndStartAtBefore(Attendant attendant, Date date);
}
