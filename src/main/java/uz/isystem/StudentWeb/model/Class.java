package uz.isystem.StudentWeb.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import uz.isystem.StudentWeb.dto.AttendanceTypeDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = ("classes"))
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = ("room_id"), insertable = false, updatable = false)
    private Room room;

    @Column(name = ("room_id"))
    private Integer roomId;

    @ManyToOne
    @JoinColumn(name = ("user_group_id"), insertable = false,updatable = false)
    private UserGroup userGroup;

    @Column(name = ("user_group_id"))
    private Integer userGroupId;

    @ManyToOne
    @JoinColumn(name = ("attendance_type_id"), insertable = false, updatable = false)
    private AttendanceType attendanceType;

    @Column(name = ("attendance_type_id"))
    private Integer attendanceTypeId;

    @Column(name = ("name"))
    private String name;

    @Column(name = ("date"))
    private LocalDate date;

    @Column(name = ("status"))
    private Boolean status;

    @Column(name = ("created_at"))
    private LocalDateTime createdAt;

    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
}






















