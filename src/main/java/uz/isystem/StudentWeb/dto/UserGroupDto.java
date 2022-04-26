package uz.isystem.StudentWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {
    private Integer id;
    private UserDto user;
    @NotNull
    private Integer userId;
    private GroupTypeDto groupTypeDto;
    @NotNull
    private Integer groupId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
