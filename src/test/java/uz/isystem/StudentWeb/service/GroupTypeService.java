package uz.isystem.StudentWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.dto.GroupTypeDto;
import uz.isystem.StudentWeb.exeption.ServerBadRequestException;
import uz.isystem.StudentWeb.model.GroupType;
import uz.isystem.StudentWeb.repository.GroupTypeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupTypeService {
    @Autowired
    private GroupTypeRepository groupTypeRepository;

    public GroupTypeDto create(GroupTypeDto groupTypeDto) {
        GroupType groupType = new GroupType();
        groupType.setName(groupTypeDto.getName());
        groupType.setCreatedAt(LocalDateTime.now());
        groupType.setStatus(true);

        groupTypeRepository.save(groupType);

        groupTypeDto.setCreatedAt(groupType.getCreatedAt());
        groupTypeDto.setStatus(groupType.getStatus());
        groupTypeDto.setId(groupType.getId());
        return groupTypeDto;
    }

    public GroupTypeDto get(Integer id) {
        GroupType groupType = getEntity(id);
        GroupTypeDto groupTypeDto = new GroupTypeDto();
        groupTypeDto.setName(groupType.getName());
        groupTypeDto.setStatus(groupType.getStatus());
        return groupTypeDto;
    }

    public GroupTypeDto update(Integer id, GroupTypeDto groupTypeDto) {
        GroupType groupType = getEntity(id);
        groupType.setName(groupTypeDto.getName());
        groupType.setUpdatedAt(LocalDateTime.now());

        groupTypeRepository.save(groupType);
        groupTypeDto.setCreatedAt(groupType.getCreatedAt());
        groupTypeDto.setUpdatedAt(groupType.getUpdatedAt());
        groupTypeDto.setStatus(groupType.getStatus());
        groupTypeDto.setId(groupType.getId());
        return groupTypeDto;
    }

    public boolean delete(Integer id) {
        GroupType groupType = getEntity(id);
        groupType.setDeletedAt(LocalDateTime.now());
        groupTypeRepository.save(groupType);
        return true;
    }

    public List<GroupTypeDto> getAll() {
        List<GroupType> groupTypeList = groupTypeRepository.findAll();
        return groupTypeList.stream().map(
                        groupType -> {
                            GroupTypeDto groupTypeDto = new GroupTypeDto();
                            groupTypeDto.setName(groupType.getName());
                            groupTypeDto.setId(groupType.getId());
                            return groupTypeDto;
                        })
                .collect(Collectors.toList());
    }

    public GroupType getEntity(Integer id) {
        Optional<GroupType> optional = groupTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("Group type not found");
        }
        return optional.get();
    }
}
