package uz.isystem.StudentWeb.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.isystem.StudentWeb.model.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private Integer id;
    private final String password;
    private final String phone;
    private final Boolean status;
    private final List<GrantedAuthority> authorityList;

    public CustomUserDetail(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.phone = user.getUsername();
        this.status = user.getStatus();
        this.authorityList = List.of(new SimpleGrantedAuthority(user.getUserType().getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

    public Integer getId(){
        return this.id;
    }
    @Override
    public String toString() {
        return "CustomUserDetail{" +
                "password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", authorityList=" + authorityList +
                '}';
    }
}
