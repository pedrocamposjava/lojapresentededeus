package como.loja.sistemavendas.controller;

import como.loja.sistemavendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/test-db")
    public String test() {
        return usuarioRepository.findAll().toString();
    }
}