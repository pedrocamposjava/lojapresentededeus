package como.loja.sistemavendas.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@SuppressWarnings("ALL")
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    public Long getId() { // Alterado para Long (objeto)
        return id;
    }

    public void setId(Long id) { // Alterado para Long (objeto)
        this.id = id;
    }

    // ... restante dos getters e setters permanecem iguais
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}