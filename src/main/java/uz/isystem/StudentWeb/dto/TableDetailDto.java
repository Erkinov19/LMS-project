package uz.isystem.StudentWeb.dto;

import lombok.Getter;
import lombok.Setter;
import uz.isystem.StudentWeb.model.Class;

import java.util.List;

@Getter
@Setter
public class TableDetailDto {
    private String firstname;
    private String lastname;
    private Integer userGroupId;
    private List<Class> classList;
}
