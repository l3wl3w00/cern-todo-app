package ch.cern.todo.dal.entity;

import ch.cern.todo.config.Config;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TASK_CATEGORIES")
public class CategoryEntity {
    @Id
    @Column(name = "CATEGORY_ID",
            nullable = false,
            columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CATEGORY_NAME",
            nullable = false,
            unique = true,
            length = 100)
    private String name;

    static final int a = 100;
    @Column(name = "TASK_DESCRIPTION",
            length = 500)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryEntity that = (CategoryEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

