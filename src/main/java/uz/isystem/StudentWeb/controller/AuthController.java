package uz.isystem.StudentWeb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.StudentWeb.dto.AuthDto;
import uz.isystem.StudentWeb.dto.RegistrationDto;
import uz.isystem.StudentWeb.dto.UserDto;
import uz.isystem.StudentWeb.model.User;
import uz.isystem.StudentWeb.service.AuthService;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthDto dto){
        UserDto token = authService.auth(dto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto dto){
        authService.register(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validation/{token}")
    public ResponseEntity<?> validate(@PathVariable("token") String token){
        User user = authService.verification(token);
        return ResponseEntity.ok(user);
    }
}
