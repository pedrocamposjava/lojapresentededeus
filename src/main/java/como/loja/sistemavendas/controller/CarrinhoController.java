package como.loja.sistemavendas.controller;

import como.loja.sistemavendas.model.Produto;
import como.loja.sistemavendas.service.CarrinhoService;
import como.loja.sistemavendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String carrinho(Model model) {
        model.addAttribute("itens", carrinhoService.listarItens());
        model.addAttribute("total", carrinhoService.calcularTotal());
        model.addAttribute("produtos", produtoService.listarTodos());
        return "carrinho";
    }

    // AJUSTADO: Agora recebe os dados diretamente do clique do formulário HTML nativo
    @PostMapping("/adicionar")
    public String adicionar(@RequestParam("idProduto") Long id, @RequestParam("quantidade") int quantidade) {
        Produto produto = produtoService.buscarPorId(id);
        carrinhoService.adicionarProduto(produto, quantidade);
        return "redirect:/carrinho";
    }

    @GetMapping("/limpar")
    public String limpar() {
        carrinhoService.limparCarrinho();
        return "redirect:/carrinho";
    }
}