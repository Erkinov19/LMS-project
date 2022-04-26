package uz.isystem.StudentWeb.dto;

import lombok.Getter;
import lombok.Setter;
import uz.isystem.StudentWeb.model.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class ClassDto {
    private Integer id;
    private Room room;
    private Integer roomId;
    private UserGroupDto userGroup;
    private Integer userGroupId;
    private AttendanceTypeDto attendanceType;
    private Integer attendanceTypeId;
    private String name;
    private LocalDate date;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
