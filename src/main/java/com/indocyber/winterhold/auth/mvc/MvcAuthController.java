package com.indocyber.winterhold.auth.mvc;
import com.indocyber.winterhold.dtos.auth.AuthRegisterDto;
import com.indocyber.winterhold.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MvcAuthController {
    private final AuthService authService;

    public MvcAuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping("register")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("/auth/register");
        mv.addObject("dto", AuthRegisterDto.builder().build());
        return mv;
    }

    @PostMapping("register")
    public ModelAndView postRegister(AuthRegisterDto dto) {
        if (authService.checkAccount(dto)) {
            authService.register(dto);
            return new ModelAndView("redirect:/login");
        } else {
            return new ModelAndView("redirect:/register");

        }
    }

    @GetMapping("login")
    public ModelAndView login() {
        var mv = new ModelAndView("auth/login");
        return mv;
    }

}
