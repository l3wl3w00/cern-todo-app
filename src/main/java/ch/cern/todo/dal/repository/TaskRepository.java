package ch.cern.todo.dal.repository;

import ch.cern.todo.dal.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
