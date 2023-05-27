package ch.cern.todo.dal.entity;

import ch.cern.todo.bll.dto.TaskDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static ch.cern.todo.bll.dto.TaskDTO.MAX_NAME_LENGTH;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TASKS")
public class TaskEntity {
    static final String NAME_TYPE = "VARCHAR2(" + MAX_NAME_LENGTH + "BYTE)";

    @Id
    @Column(name = "TASK_ID", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TASK_NAME",
            nullable = false,
            columnDefinition = "VARCHAR2(${constraints.maxTaskNameLength} BYTE)")
    private String name;

    @Column(name = "TASK_DESCRIPTION",
            columnDefinition =  "VARCHAR2(${constraints.maxTaskDescriptionLength} BYTE)")
    private String description;

    @Column(name = "DEADLINE", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate deadline;

    @JoinColumn(name = "CATEGORY_ID", nullable = false, columnDefinition = "NUMBER")
    @ManyToOne
    private CategoryEntity category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskEntity that = (TaskEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
