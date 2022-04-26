package uz.isystem.StudentWeb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.dto.AuthDto;
import uz.isystem.StudentWeb.dto.RegistrationDto;
import uz.isystem.StudentWeb.dto.UserDto;
import uz.isystem.StudentWeb.exeption.ServerBadRequestException;
import uz.isystem.StudentWeb.model.User;
import uz.isystem.StudentWeb.model.UserType;
import uz.isystem.StudentWeb.repository.UserRepository;
import uz.isystem.StudentWeb.security.JwtTokenUtil;
import uz.isystem.StudentWeb.util.PasswordService;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final MailSenderService mailSenderService;
    private final UserTypeService userTypeService;

    @Value("${mailSendAddress}")
    private String address;

    public AuthService(UserRepository userRepository,
                       MailSenderService mailSenderService,
                       JwtTokenUtil jwtTokenUtil, UserTypeService userTypeService) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userTypeService = userTypeService;
    }

    public void register(RegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getCheckPassword())) {
            throw new ServerBadRequestException("Password invalid");
        }

        User user = new User();
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setPassword(PasswordService.generateMD5(dto.getPassword()));
        user.setStatus(false);
        user.setCreatedAt(LocalDateTime.now());
        UserType userType = userTypeService.getEntityByName("ROLE_USER");
        user.setUserTypeId(userType.getId());
        userRepository.save(user);
        String link = address + jwtTokenUtil.generateToken(user);

        String content = String.format("Please verify your data, click to link %s", link);
        try {
            mailSenderService.sendMail(content, dto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            userRepository.delete(user);
        }
    }

    public User verification(String token) {
        Optional<User> optional = userRepository.findById(Integer.valueOf(jwtTokenUtil.getId(token)));
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("Verification failed");
        }
        User user = optional.get();
        user.setStatus(true);
        userRepository.save(user);
        return user;
    }

    public UserDto auth(AuthDto dto) {
        String phone = dto.getPhone();
        String password= PasswordService.generateMD5(dto.getPassword());
        Optional<User> optional = userRepository.authorize(password, phone);
        if (optional.isEmpty()) {
            throw new ServerBadRequestException("User not found");
        }
        User user = optional.get();
        UserDto userDto = new UserDto();
        userDto.setPhone(userDto.getPhone());
        userDto.setToken(jwtTokenUtil.generateToken(user));
        return userDto;
    }

}
