package como.loja.sistemavendas.service;

import como.loja.sistemavendas.model.Produto;
import como.loja.sistemavendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ProdutoService {

    @Autowired
    private  ProdutoRepository repository;

    // LISTAR TODOS OS PRODUTOS
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    // SALVAR OU ATUALIZAR PRODUTO
    public Produto salvar(Produto produto) {

        return repository.save(produto);
    }

    // DELETAR PRODUTO POR ID
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    // TOTAL DE PRODUTOS
    public  long totalProdutos() {
        return repository.count();
    }

    // VALOR TOTAL DO ESTOQUE
    public BigDecimal valorTotalEstoque() {

        return repository.findAll()
                .stream()
                .map(p ->
                        p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // BUSCAR POR ID (ÚTIL PARA EDITAR DEPOIS)
    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}