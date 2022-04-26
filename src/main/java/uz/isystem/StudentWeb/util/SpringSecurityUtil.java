package uz.isystem.StudentWeb.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.isystem.StudentWeb.security.CustomUserDetail;

public class SpringSecurityUtil {
    public static Integer getUsedId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        return userDetail.getId();
    }
}
