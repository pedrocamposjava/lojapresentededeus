package como.loja.sistemavendas.service;

import como.loja.sistemavendas.model.Usuario;
import como.loja.sistemavendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario autenticar(String login, String senha) {

        Usuario user = repository.findByLogin(login);

        if (user != null && user.getSenha().equals(senha)) {
            return user;
        }

        return null;
    }
}