package como.loja.sistemavendas.repository;

import como.loja.sistemavendas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}