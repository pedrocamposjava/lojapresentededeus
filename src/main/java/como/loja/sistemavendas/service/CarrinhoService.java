package como.loja.sistemavendas.service;

import como.loja.sistemavendas.model.CarrinhoItem;
import como.loja.sistemavendas.model.Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CarrinhoService {

    private List<CarrinhoItem> itens = new ArrayList<>();

    public void adicionarProduto(Produto produto, int quantidade) {

        for (CarrinhoItem item : itens) {
            if (Objects.equals(item.getProduto().getId(), produto.getId())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }

        itens.add(new CarrinhoItem(produto, quantidade));
    }

    public List<CarrinhoItem> getItens() {
        return itens;
    }

    public List<CarrinhoItem> listarItens() {
        return itens;
    }

    // CORRIGIDO (BigDecimal)
    public BigDecimal calcularTotal() {

        return itens.stream()
                .map(CarrinhoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void limparCarrinho() {
        itens.clear();
    }
}