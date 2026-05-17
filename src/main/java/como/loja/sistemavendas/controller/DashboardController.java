package como.loja.sistemavendas.controller;

import como.loja.sistemavendas.service.ProdutoService;
import como.loja.sistemavendas.service.VendaService;
import jakarta.servlet.http.HttpSession; // Importação necessária para gerenciar a sessão
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private VendaService vendaService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {

        // 🌟 Define que os valores começam INVISÍVEIS (true) no primeiro acesso
        if (session.getAttribute("ocultarValores") == null) {
            session.setAttribute("ocultarValores", true);
        }

        // Seus atributos originais do banco de dados
        model.addAttribute("totalProdutos", produtoService.totalProdutos());
        model.addAttribute("valorEstoque", produtoService.valorTotalEstoque());
        model.addAttribute("totalVendas", vendaService.totalVendas());
        model.addAttribute("totalVendido", vendaService.totalVendido());

        // Passa o estado da sessão para o Thymeleaf aplicar as máscaras na tela
        model.addAttribute("valoresOcultos", session.getAttribute("ocultarValores"));

        return "dashboard";
    }

    // Rota responsável por inverter o olho (Mostrar / Ocultar) nativamente
    @GetMapping("/dashboard/alternar-visibilidade")
    public String alternarVisibilidade(HttpSession session) {
        Boolean ocultar = (Boolean) session.getAttribute("ocultarValores");

        if (ocultar == null) {
            session.setAttribute("ocultarValores", false);
        } else {
            session.setAttribute("ocultarValores", !ocultar);
        }

        return "redirect:/dashboard";
    }
}