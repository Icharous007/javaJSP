package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOConsultaUsuario;
import dao.DAODeletarUsuario;
@WebServlet("/ServLetDeletarUsuarioPorGet")
public class ServLetDeletarUsuarioPorGet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAODeletarUsuario deletarUsuario = new DAODeletarUsuario();
	private DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	private ServLetGenericUtils genericUtils = new ServLetGenericUtils();

	public ServLetDeletarUsuarioPorGet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String atributoModelLogin = "modelLogin";
		String id = request.getParameter("idDoUsuario");
		String nome = request.getParameter("nomeDoUsuario");
		String login = request.getParameter("loginDoUsuario");
		String email = request.getParameter("emailDoUsuario");
		String acao = request.getParameter("acao");
		String msg;

		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setLogin(login);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		try {
			Long usuarioPai = genericUtils.getUsuarioLogado(request);
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("AJAX")) {
				if (!modelLogin.IsNew() && deletarUsuario.deletarUsuarioPorId(modelLogin.getId())) {
					msg = "Exclusão pelo AJAX realizada com sucesso!";
					response.getWriter().write(msg);
				} else {
					msg = "Não foi possível realizar a exclusão pelo AJAX. Verifique os dados.";
					response.getWriter().write(msg);
				}
			} else {

				if (!modelLogin.IsNew() && deletarUsuario.deletarUsuarioPorId(modelLogin.getId())) {
					msg = "Usuario Deletado com Sucesso.";
				} else {
					msg = "Não foi possível Deletar o usuario.";
					request.setAttribute(atributoModelLogin, modelLogin);
				}
				List<ModelLogin> listaDeUsuariosCadastrados= new ArrayList<>();
				listaDeUsuariosCadastrados= consultaUsuario.consultaUsariosTodos(usuarioPai);
				request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosCadastrados);
				request.setAttribute("msg", msg);
				RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
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
