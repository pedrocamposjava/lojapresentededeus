package como.loja.sistemavendas.service;

import como.loja.sistemavendas.model.*;
import como.loja.sistemavendas.repository.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoService carrinhoService;
    private final ItemVendaRepository itemVendaRepository;
    private final HttpSession session;

    // Construtor atualizado recebendo os repositórios necessários
    public VendaService(VendaRepository vendaRepository,
                        ProdutoRepository produtoRepository,
                        CarrinhoService carrinhoService,
                        ItemVendaRepository itemVendaRepository,
                        HttpSession session) {

        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.carrinhoService = carrinhoService;
        this.itemVendaRepository = itemVendaRepository;
        this.session = session;
    }

    // 🌟 LISTAR TODAS VENDAS (Modificado para evitar Erro 500 / Lazy Loading)
    @Transactional(readOnly = true)
    public List<Venda> listarTodas() {
        List<Venda> vendas = vendaRepository.findAll();

        // Percorre as vendas e força o Hibernate a carregar os itens e os produtos associados
        for (Venda venda : vendas) {
            if (venda.getItens() != null) {
                venda.getItens().forEach(item -> {
                    if (item.getProduto() != null) {
                        item.getProduto().getNome(); // Toca no nome para tirar o produto do modo "fantasma" (Proxy)
                    }
                });
            }
        }
        return vendas;
    }

    // TOTAL DE VENDAS (dashboard)
    public long totalVendas() {
        return vendaRepository.count();
    }

    // TOTAL VENDIDO (dashboard)
    public BigDecimal totalVendido() {
        return vendaRepository.findAll()
                .stream()
                .map(Venda::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // MÉTODO: Deleta todos os itens associados a essa venda
    @Transactional
    public void deletarItensDaVenda(Long idVenda) {
        itemVendaRepository.deleteByVendaId(idVenda);
    }

    // MÉTODO: Deleta a venda principal do banco
    @Transactional
    public void deletarVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    // FINALIZAR VENDA
    @Transactional
    public Venda finalizarVenda(Usuario usuario) {
        var itensCarrinho = carrinhoService.listarItens();

        if (itensCarrinho.isEmpty()) {
            throw new IllegalStateException("Carrinho vazio.");
        }

        Venda venda = new Venda();
        venda.setItens(new ArrayList<>());

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado != null) {
            venda.setUsuario(usuarioLogado);
        }

        BigDecimal total = BigDecimal.ZERO;

        for (CarrinhoItem item : itensCarrinho) {
            Produto produto = item.getProduto();

            if (produto.getQuantidade() < item.getQuantidade()) {
                throw new IllegalStateException("Estoque insuficiente");
            }

            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);

            BigDecimal subtotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(item.getQuantidade());
            itemVenda.setPrecoUnitario(produto.getPreco());
            itemVenda.setSubtotal(subtotal);
            itemVenda.setVenda(venda);

            venda.getItens().add(itemVenda);
            total = total.add(subtotal);
        }

        venda.setTotal(total);
        Venda vendaSalva = vendaRepository.save(venda);
        carrinhoService.limparCarrinho();

        return vendaSalva;
    }
}