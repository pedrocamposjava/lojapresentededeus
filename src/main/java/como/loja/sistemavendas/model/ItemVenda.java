package como.loja.sistemavendas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_venda") // Garante o nome correto da tabela deste item no banco
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🌟 CORREÇÃO 1: Aponta explicitamente para a coluna id da sua tabela "produtos" (plural)
    @ManyToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private Produto produto;

    private int quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal subtotal;

    // 🌟 CORREÇÃO 2: Proteção caso sua tabela de vendas no MySQL chame-se "vendas" (plural)
    @ManyToOne
    @JoinColumn(name = "venda_id", referencedColumnName = "id")
    private Venda venda;

    // ... Seus getters e setters permanecem exatamente os mesmos abaixo daqui!
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }
}