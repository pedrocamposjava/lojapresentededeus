package como.loja.sistemavendas.model;

import jakarta.persistence.*;

import java.util.Collection;

@SuppressWarnings("ALL")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    private String perfil; // ADMIN, CAIXA

    // getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @OneToMany(mappedBy = "usuario")
    private Collection<Venda> venda;

    public Collection<Venda> getVenda() {
        return venda;
    }

    public void setVenda(Collection<Venda> venda) {
        this.venda = venda;
    }
}