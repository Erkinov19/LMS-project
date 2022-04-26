package uz.isystem.StudentWeb.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError (HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null){
            Integer code = Integer.valueOf(status.toString());
            if (code.equals(HttpStatus.NOT_FOUND.value())){
                return "error/404";
            }
            if (code.equals(HttpStatus.INTERNAL_SERVER_ERROR.value())){
                return "error/500";
            }
        }
        return "error";
    }
}