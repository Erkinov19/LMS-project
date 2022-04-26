package uz.isystem.StudentWeb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.isystem.StudentWeb.model.User;
import uz.isystem.StudentWeb.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        System.out.println("Custom User details");
        Optional<User> optional = userRepository.findByPhoneAndDeletedAtIsNull(phone);
        if (optional.isEmpty()){
            throw new UsernameNotFoundException("Phone number not found");
        }
        return new CustomUserDetail(optional.get());
    }
}

