package como.loja.sistemavendas.controller;

import como.loja.sistemavendas.model.Usuario;
import como.loja.sistemavendas.model.Venda;
import como.loja.sistemavendas.service.VendaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vendas") // Garante o mapeamento base da rota de vendas (/vendas)
public class VendaController {

    private final VendaService vendaService;

    // Injeção de dependência via construtor (Boa prática!)
    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    // ROTA PARA EXIBIR O HISTÓRICO
    @GetMapping("/historico")
    public String exibirHistorico(Model model, HttpSession session) {
        // Impedir usuários deslogados de verem o histórico
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        List<Venda> vendas = vendaService.listarTodas();
        model.addAttribute("vendas", vendas);
        return "historico"; // renderiza o historico.html
    }

    // 🕊️ ROTA ADICIONADA: FINALIZAR A VENDA DO CARRINHO
    @PostMapping("/finalizar")
    public String finalizarVenda(HttpSession session) {
        // 1. Pega o usuário logado na sessão (pode ser emersonadmin ou santinha)
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        try {
            // 2. Chama o método do Service que já criamos e processa as baixas e o salvamento
            vendaService.finalizarVenda(usuarioLogado);

            // 3. Deu tudo certo? Redireciona para o histórico de vendas atualizado
            return "redirect:/vendas/historico";
        } catch (IllegalStateException e) {
            // Caso o carrinho esteja vazio ou falte estoque, volta para o carrinho com a mensagem de erro
            return "redirect:/carrinho?erro=" + e.getMessage();
        }
    }

    // 🔒 ROTA DE EXCLUSÃO PROTEGIDA CONTRA ACESSOS INDEVIDOS
    @PostMapping("/deletar/{id}")
    public String deletarVenda(@PathVariable Long id, HttpSession session) {
        // 1. Puxa o usuário que está ativamente logado na sessão do navegador
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        // 2. Barreira de Segurança: Se não houver usuário ou se o perfil NÃO for ADMIN, bloqueia
        if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPerfil())) {
            // Aborta a operação e retorna um parâmetro de erro para a URL
            return "redirect:/vendas/historico?erro=acesso-negado";
        }

        // 3. Se passou pela barreira (é o emersonadmin), executa a exclusão com sucesso
        vendaService.deletarItensDaVenda(id);
        vendaService.deletarVenda(id);

        // Redireciona e atualiza a tela limpa
        return "redirect:/vendas/historico";
    }
}