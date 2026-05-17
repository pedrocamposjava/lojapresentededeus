package como.loja.sistemavendas.repository;

import como.loja.sistemavendas.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    // 🌟 Cria a query automática para deletar os itens filtrando pelo ID da venda pai
    @Transactional
    void deleteByVendaId(Long vendaId);
}