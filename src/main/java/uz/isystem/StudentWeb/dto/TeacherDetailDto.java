package uz.isystem.StudentWeb.dto;

import lombok.Getter;
import lombok.Setter;
import uz.isystem.StudentWeb.model.Group;
import uz.isystem.StudentWeb.model.User;

import java.util.List;

@Getter
@Setter
public class TeacherDetailDto {
    private List<Group> groupList;
    private List<List<User>> userList;
}
