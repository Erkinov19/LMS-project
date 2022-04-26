package uz.isystem.StudentWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.dto.AttendanceTypeDto;
import uz.isystem.StudentWeb.exeption.ServerBadRequestException;
import uz.isystem.StudentWeb.model.AttendanceType;
import uz.isystem.StudentWeb.repository.AttendanceTypeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceTypeService {
    @Autowired
    private AttendanceTypeRepository attendanceTypeRepository;

    public AttendanceTypeDto get(Integer id) {
        AttendanceType attendanceType = getEntity(id);
        AttendanceTypeDto attendanceTypeDto = new AttendanceTypeDto();
        convertEntityToDto(attendanceType, attendanceTypeDto);
        return attendanceTypeDto;
    }

    public boolean create(AttendanceTypeDto attendanceTypeDto){
        AttendanceType attendanceType = new AttendanceType();
        convertDtoToEntity(attendanceTypeDto, attendanceType);
        attendanceType.setCreatedAt(LocalDateTime.now());
        attendanceType.setStatus(true);
        attendanceTypeRepository.save(attendanceType);
        return true;
    }

    public boolean update(Integer id, AttendanceTypeDto attendanceTypeDto){
        AttendanceType attendanceType = getEntity(id);
        convertDtoToEntity(attendanceTypeDto, attendanceType);
        attendanceType.setUpdatedAt(LocalDateTime.now());
        attendanceTypeRepository.save(attendanceType);
        return true;
    }

    public boolean delete(Integer id){
        AttendanceType attendanceType = getEntity(id);
        attendanceType.setDeletedAt(LocalDateTime.now());
        attendanceTypeRepository.save(attendanceType);
        return true;
    }

    public List<AttendanceTypeDto> getAll(){
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAll();
        if (attendanceTypeList.isEmpty()){
            throw new ServerBadRequestException("Attendances not found");
        }
        return attendanceTypeList
                .stream()
                .map(attendanceType -> convertEntityToDto(attendanceType, new AttendanceTypeDto()))
                .collect(Collectors.toList());
    }

    public AttendanceType getEntity(Integer id) {
        Optional<AttendanceType> optional = attendanceTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("Attendance type not found");
        }
        return optional.get();
    }

    public AttendanceTypeDto convertEntityToDto(AttendanceType entity, AttendanceTypeDto dto) {
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public AttendanceType convertDtoToEntity(AttendanceTypeDto dto, AttendanceType entity) {
        entity.setName(dto.getName());
        return entity;
    }

}
