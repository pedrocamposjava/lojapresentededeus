package como.loja.sistemavendas.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        boolean logado = (session != null && session.getAttribute("usuarioLogado") != null);

        String uri = request.getRequestURI();

        if (uri.equals("/") || uri.equals("/login") || uri.contains("css")) {
            return true;
        }

        if (!logado) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}