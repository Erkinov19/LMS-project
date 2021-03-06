package uz.isystem.StudentWeb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.dto.UserDto;
import uz.isystem.StudentWeb.dto.UserFilterDto;
import uz.isystem.StudentWeb.dto.UserTypeDto;
import uz.isystem.StudentWeb.exeption.ServerBadRequestException;
import uz.isystem.StudentWeb.model.User;
import uz.isystem.StudentWeb.model.UserType;
import uz.isystem.StudentWeb.repository.UserRepository;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserTypeService userTypeService;

    public UserService(UserRepository userRepository, UserTypeService userTypeService) {
        this.userRepository = userRepository;
        this.userTypeService = userTypeService;
    }

    //Main functions
    public UserDto get(Integer id) {
        User user = getEntity(id);
        UserDto userDto = new UserDto();
        UserTypeDto userTypeDto = userTypeService.get(user.getUserTypeId());
        userDto.setUserType(userTypeDto);
        userDto = convertEntityToDto(user, userDto);
        //TODO: all fields set
        log.info("Get user {}", userDto);
        return userDto;
    }

    public UserDto create(UserDto dto) {
        Optional<User> optional = userRepository.findByUserIdAndPhoneAndDeletedAtIsNull(dto.getUserId(), dto.getPhone());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("User already exist!");
        }
        userTypeService.getEntity(dto.getUserTypeId()); // checking user type available
        User user = new User();
        user.setUserTypeId(dto.getUserTypeId());
        user.setUserId(dto.getUserId());
        user.setChatId(dto.getChatId());
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setStatus(true);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        dto.setId(user.getId());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        log.info("Create user {}", dto);
        return dto;
    }

    public Boolean update(Integer userId, User user) {
        // TODO: user to userDto
        User old = getEntity(userId);
        userTypeService.getEntity(user.getUserTypeId());
        user.setId(userId);
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(old.getCreatedAt());
        user.setStatus(old.getStatus());
        userRepository.save(user);
        log.info("Update user {}", user);
        return true;
    }

    public Boolean delete(Integer userId) {
        User user = getEntity(userId);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("Delete user {}", user);
        return true;
    }


    public List<User> getAll() {
        List<User> userList = userRepository.findAllByDeletedAtIsNull();
        for (User user : userList) {
            user.setUserType(userTypeService.getEntity(user.getUserTypeId()));
        }
        return userList;
    }

    public List<UserDto> page(Integer page, Integer size) {
        List<UserDto> userDtoList = new LinkedList<>();

        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        for (User user : userPage) {
            UserDto dto = new UserDto();
            convertEntityToDto(user, dto);
            userDtoList.add(dto);
        }
        return userDtoList;
    }

    public List<UserDto> filter(UserFilterDto dto) {
        String sortBy = dto.getSortBy();
        if (sortBy == null) {
            sortBy = "createdAt";
        }

        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<javax.persistence.criteria.Predicate> predicates = new LinkedList<>();
        Specification<User> specification = (((root, query, criteriaBuilder) -> {
            if (dto.getFirstname() != null) {
                predicates.add(criteriaBuilder.equal(root.get("firstname"), dto.getFirstname()));
            }
            if (dto.getLastname() != null) {
                predicates.add(criteriaBuilder.equal(root.get("lastname"), dto.getLastname()));
            }
            if (dto.getUserTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userTypeId"), dto.getUserTypeId()));
            }
            if (dto.getPhone() != null) {
                predicates.add(criteriaBuilder.like(root.get("phone"), dto.getPhone()));
            }
            if (dto.getMinCreateDate() != null && dto.getMaxCreateDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("createdAt"), dto.getMinCreateDate(), dto.getMaxCreateDate()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
        Page<User> userPage = userRepository.findAll(specification, pageable);
        List<UserDto> userDtoList = new LinkedList<>();
        for (User user : userPage) {
            UserDto userDto = new UserDto();
            convertEntityToDto(user, userDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }


    //Secondary functions

    public User getEntity(Integer id) {

        Optional<User> optional = userRepository.findByIdAndDeletedAtNull(id);
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("User not found");
        }
        return optional.get();
    }

    public UserDto convertEntityToDto(User entity, UserDto dto) {
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());
        dto.setPhone(entity.getPhone());
        dto.setUsername(entity.getUsername());
        dto.setUserId(entity.getUserId());
        dto.setChatId(entity.getChatId());
        dto.setUserTypeId(entity.getUserTypeId());

        UserTypeDto userTypeDto = new UserTypeDto();
        userTypeService.convertEntityToDto(entity.getUserType(), userTypeDto);
        dto.setUserType(userTypeDto);

        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());

        return dto;
    }

    public User convertDtoToEntity(UserDto dto, User entity) {
        entity.setUserId(dto.getUserId());
        entity.setChatId(dto.getChatId());
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setUsername(dto.getUsername());
        entity.setPhone(dto.getPhone());
        entity.setUserTypeId(dto.getUserTypeId());

        return entity;
    }

}
