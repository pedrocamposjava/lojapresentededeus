package como.loja.sistemavendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redireciona para o login por padrão para evitar conflito com o Interceptor
        return "login";
    }
}