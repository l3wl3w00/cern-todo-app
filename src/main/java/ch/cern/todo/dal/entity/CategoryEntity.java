package ch.cern.todo.dal.entity;

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
    @Column(name = "CATEGORY_ID", nullable = false, columnDefinition = "NUMBER")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false,  unique = true, columnDefinition = "VARCHAR2(100 BYTE)")
    private String name;

    @Column(name = "TASK_DESCRIPTION", columnDefinition = "VARCHAR2(500 BYTE)")
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

