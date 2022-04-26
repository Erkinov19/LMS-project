package uz.isystem.StudentWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.dto.RoomDto;
import uz.isystem.StudentWeb.exeption.ServerBadRequestException;
import uz.isystem.StudentWeb.model.Room;
import uz.isystem.StudentWeb.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public RoomDto get(Integer id) {
        Room entity = getEntity(id);
        RoomDto dto = new RoomDto();
        convertEntityToDto(entity, dto);
        return dto;
    }

    public boolean create(RoomDto dto){
        Room entity = new Room();
        convertDtoToEntity(dto, entity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(true);
        roomRepository.save(entity);
        return true;
    }

    public boolean update(Integer id, RoomDto dto){
        Room entity = getEntity(id);
        convertDtoToEntity(dto, entity);
        entity.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(entity);
        return true;
    }

    public boolean delete(Integer id){
        Room entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        roomRepository.save(entity);
        return true;
    }

    public List<RoomDto> getAll(){
        List<Room> roomList = roomRepository.findAll();
        if (roomList.isEmpty()){
            throw new ServerBadRequestException("Room not found");
        }
        return roomList
                .stream()
                .map(room -> convertEntityToDto(room, new RoomDto()))
                .collect(Collectors.toList());
    }

    public Room getEntity(Integer id) {
        Optional<Room> optional = roomRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("Room not found");
        }
        return optional.get();
    }

    public RoomDto convertEntityToDto(Room entity, RoomDto dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public Room convertDtoToEntity(RoomDto dto, Room entity) {
        entity.setName(dto.getName());
        return entity;
    }
}
