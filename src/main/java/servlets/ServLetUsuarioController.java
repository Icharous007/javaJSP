package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import dao.DAOAtualizarUsuario;
import dao.DAOCadastroUsuario;
import dao.DAOValidadorDeLogin;

@WebServlet("/ServLetUsuarioController")
public class ServLetUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOCadastroUsuario cadastroUsuario = new DAOCadastroUsuario();
	private DAOValidadorDeLogin daoValidadorDeLogin = new DAOValidadorDeLogin();
	private DAOAtualizarUsuario atualizarUsuario = new DAOAtualizarUsuario();

	public ServLetUsuarioController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("teste");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("idDoUsuario");
		String nome = request.getParameter("nomeDoUsuario");
		String login = request.getParameter("loginDoUsuario");
		String email = request.getParameter("emailDoUsuario");
		String senha = request.getParameter("senhaDoUsuario");
		String msg;
		final String atributoModelLogin = "modelLogin";
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setLogin(login);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setSenha(senha);
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);

		try {
			if (daoValidadorDeLogin.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {

				msg = "Login já existe na base de dados escolha outro por favor.";

			} else {
				if (modelLogin.IsNew()) {
					msg = "Cadastro Realizado com sucesso.";
					modelLogin = cadastroUsuario.cadastrarUsuario(modelLogin);

				} else {
					msg = "Para Alterar os Dados do Usuario utilize o Botão ATUALIZAR!.";
					
				}
			}
			request.setAttribute(atributoModelLogin, modelLogin);
			request.setAttribute("msg", msg);
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
			redirecionador.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage();
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			request.setAttribute("msg", erro);
			redirecionador.forward(request, response);
		}
	}
}
