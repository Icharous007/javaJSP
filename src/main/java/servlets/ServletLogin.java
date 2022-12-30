package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import dao.DAOLoginRepository;

@WebServlet(urlPatterns = {"/ServletLogin", "/principal/ServletLogin"}) //esse mapeamento está no arquivo web.xml
public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
       
    public ServletLogin() {
       
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if(acao!=null && !acao.isEmpty()&&acao.equalsIgnoreCase("Logout")) {
			request.getSession().invalidate();
			RequestDispatcher redirecionador = request.getRequestDispatcher("index.jsp");
			redirecionador.forward(request, response);
		}
		else {
			doPost(request, response);			
		}		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String url = request.getParameter("url");
			String login = request.getParameter("Login");
			String senha = request.getParameter("Senha");
			
			String msg;
			
			if(login!=null &&  !login.isEmpty() && !login.equalsIgnoreCase("null") && senha!=null && !senha.isEmpty() && !senha.equalsIgnoreCase("null")) {
				
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
				
				if(daoLoginRepository.autenticacao(modelLogin)) {
					
					msg = "Login e senha validos.";
					request.getSession().setAttribute("usuarioLogado", modelLogin);
					request.getSession().setAttribute("loginDoUsuarioLogado", modelLogin.getLogin());
					request.setAttribute("msg", msg);
					
					if(url==null || url.equalsIgnoreCase("null")) {
						url = "/principal/pagina_principal.jsp";
					}
					
					RequestDispatcher redirecionador = request.getRequestDispatcher(url);
					redirecionador.forward(request, response);
				}
			}else {
				msg = "Login ou Senha inválidos... Repita os processo de Login.";
				RequestDispatcher redirecionador = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", msg);
				redirecionador.forward(request, response);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage();
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			request.setAttribute("msg", erro);
			redirecionador.forward(request, response);
			
		}
		
	}
}
