package filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexaoUnica;

//@WebFilter("/FiltroDeAutenticacao")
//intercepta todas as requisições para a aplicação
@WebFilter(urlPatterns = { "/principal/*","/ServLetUsuarioController","/ServLetPerquisaUsuario" })
public class FiltroDeAutenticacao extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private static Connection conexao;

	public FiltroDeAutenticacao() {
		super();
	}

	/*
	 * Encerra o processos quando o servidor é desligado por exemplo finaliza a
	 * conexão com o banco de dados.
	 */
	@Override
	public void destroy() {
		try {
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Interceptador das requiscões e as repostas da Aplicação Por exemplo:
	 * Validação de autenticação; O commit e o rollback das transações do banco de
	 * dados Validar e redirecinar as páninas.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			HttpServletRequest requisicao = (HttpServletRequest) request;
			String urlParaSerAutenticada = requisicao.getServletPath();
			//System.out.println(urlParaSerAutenticada);
			HttpSession sessao = requisicao.getSession();
			ModelLogin usuarioLogado = (ModelLogin) sessao.getAttribute("usuarioLogado");

			if ((usuarioLogado == null || ( usuarioLogado.getLogin().isEmpty()) || ( usuarioLogado.getLogin().isBlank())) && !urlParaSerAutenticada.equalsIgnoreCase("/principal/ServletLogin")) {
				RequestDispatcher redirecionador = request.getRequestDispatcher("/index.jsp?url=" + urlParaSerAutenticada);
				String msg = "Sem Usuario Logado. Por favor realize o Login.";
				request.setAttribute("msg", msg);
				redirecionador.forward(request, response);
				return;
			} else {
				chain.doFilter(request, response);
			}
			
			conexao.commit();

		} catch (Exception e) {
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			String erro = e.getMessage();
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			request.setAttribute("msg", erro);
			redirecionador.forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig) É o processo que inicializa os processo quando
	 *      o servidor é iniciado. Por exemplo a conexão do banco de dados.
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		conexao= ConexaoUnica.getConexao();
	}

}
