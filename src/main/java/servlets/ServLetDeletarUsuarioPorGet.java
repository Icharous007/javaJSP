package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import dao.DAODeletarUsuario;

public class ServLetDeletarUsuarioPorGet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAODeletarUsuario deletarUsuario = new DAODeletarUsuario();
	public ServLetDeletarUsuarioPorGet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String atributoModelLogin = "modelLogin";
		String id          	= request.getParameter("idDoUsuario");
		String nome	= request.getParameter("nomeDoUsuario");
		String login  	= request.getParameter("loginDoUsuario");
		String email 	= request.getParameter("emailDoUsuario");
		String msg;
		
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setLogin(login);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		try {
			
			if(!modelLogin.IsNew() &&  deletarUsuario.deletarUsuarioPorId(modelLogin.getId())) {
				msg = "Usuario Deletado com Sucesso.";
			}else {
				msg = "Não foi possível Deletar o usuario.";
				request.setAttribute(atributoModelLogin, modelLogin);
			}
			
			request.setAttribute("msg", msg);
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
			redirecionador.forward(request, response);
			
		} catch (Exception e) {
		}
	}
}
