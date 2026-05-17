package como.loja.sistemavendas.controller;

import como.loja.sistemavendas.model.Produto;
import como.loja.sistemavendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping("/produtos")
    public String listar(Model model) {
        model.addAttribute("produtos", service.listarTodos());
        return "lista-produtos";
    }

    @GetMapping("/produtos/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        return "form-produto";
    }

    @PostMapping("/produtos/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        service.salvar(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/editar/{id}") // Corrigido: Adicionada a barra inicial
    public String editar(@PathVariable Long id, Model model){
        Produto produto = service.buscarPorId(id);
        model.addAttribute("produto", produto);
        return "form-produto";
    }
}