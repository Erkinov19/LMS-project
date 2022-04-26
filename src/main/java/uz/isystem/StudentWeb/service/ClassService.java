package uz.isystem.StudentWeb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.dto.ClassDto;
import uz.isystem.StudentWeb.exeption.ServerBadRequestException;
import uz.isystem.StudentWeb.model.Class;
import uz.isystem.StudentWeb.repository.ClassRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    public ClassDto get(Integer id){
        Class entity =getEntity(id);
        ClassDto dto = convertEntityToDto(entity, new ClassDto());
        log.info("Bu yerda class olinvotti {}", dto);
        return dto;
    }

    public boolean create(ClassDto classDto){
        Class entity = new Class();
        convertDtoToEntity(classDto, entity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(true);
        LocalDate localDate =classDto.getDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        String name = String.format("%s.%s", day, month);
        entity.setName(name);
        classRepository.save(entity);
        return true;
    }


    public boolean update(Integer id, ClassDto classDto){
        Class entity = getEntity(id);
        classDto.setUpdatedAt(LocalDateTime.now());
        convertDtoToEntity(classDto, entity);
        classRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id){
        Class entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        classRepository.save(entity);
        return true;
    }

    public Class getEntity(Integer id){
        Optional<Class> optional = classRepository.findById(id);
        if (optional.isEmpty()){
            throw new ServerBadRequestException("Class not found");
        }
        return optional.get();
    }

    public List<ClassDto> getAll(){
        List<Class> classList = classRepository.findAll();
        if (classList.isEmpty()){
            throw new ServerBadRequestException("Classes not found");
        }
        return classList.stream()
                .map(entity -> convertEntityToDto(entity, new ClassDto()))
                .collect(Collectors.toList());
    }



    public ClassDto convertEntityToDto(Class entity, ClassDto dto){
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRoomId(entity.getRoomId());
        dto.setUserGroupId(entity.getUserGroupId());
        dto.setAttendanceTypeId(entity.getAttendanceTypeId());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        return dto;
    }

    public ClassDto convertDtoToEntity(ClassDto dto,Class entity){
        dto.setRoomId(entity.getRoomId());
        dto.setUserGroupId(entity.getUserGroupId());
        dto.setAttendanceTypeId(entity.getAttendanceTypeId());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }

}
