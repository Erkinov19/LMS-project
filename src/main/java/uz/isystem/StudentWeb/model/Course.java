package uz.isystem.StudentWeb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private boolean status;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    @Column (name = "updated_date")
    private LocalDateTime updatedAt;

    @Column (name = "deleted_date")
    private LocalDateTime deletedAt;
}
