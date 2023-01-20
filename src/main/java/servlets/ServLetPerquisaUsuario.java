package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOConsultaUsuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet("/ServLetPerquisaUsuario")
public class ServLetPerquisaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	private ServLetGenericUtils genericUtils = new ServLetGenericUtils();
	public ServLetPerquisaUsuario() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if (acao != null && !"null".equals(acao) && !acao.isEmpty() && !acao.isBlank()) {
			switch (acao) {
			case "AJAX":
				consultaUsuariosComAJAX(request, response);
				break;
			case "selecionadoDoModal":
				consultaUsuarioSelecionadoPorId(request, response);
				break;
			default:
				redicionaParaCadastro(request, response);
				throw new IllegalArgumentException("Unexpected value: " + acao);
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private void redicionaParaCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = "Não há redirecionamento diponível";
		List<ModelLogin> listaDeUsuariosCadastrados = new ArrayList<>();
		try {
			Long usuarioPai = genericUtils.getUsuarioLogado(request);
			listaDeUsuariosCadastrados = consultaUsuario.consultaUsariosTodos(usuarioPai);
		} catch (Exception e) {	
			redirecionaParaErro(e.getMessage(), request, response);
			return;
		}
		request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosCadastrados);
		request.setAttribute("msg", msg);
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
		redirecionador.forward(request, response);
	}
	
	private void redirecionaParaErro(String erro, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
		request.setAttribute("msg", erro);
		redirecionador.forward(request, response);
	}
	
	private void consultaUsuariosComAJAX( HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		List<ModelLogin> listaDeUsuarios = null;
		String nome = request.getParameter("nomeDoUsuario");
		String pagina = request.getParameter("pagina");
		
		Double quantidadeDeRegistros = -1.0;
		Integer totalDePaginas = 0;
		if (nome != null && !nome.isEmpty() && !nome.isBlank()) {

			try {
				Integer offset = Integer.parseInt(pagina);
				Long usuarioPai = genericUtils.getUsuarioLogado(request);
				quantidadeDeRegistros= consultaUsuario.consultaTotalDeRegistrosPorNomeAJAX(nome, usuarioPai);
				if(quantidadeDeRegistros>0) {
					totalDePaginas = genericUtils.totalDePaginas(quantidadeDeRegistros);	
				}
				
				listaDeUsuarios = consultaUsuario.consultaUsuariosPorNomePaginada_ajax(nome, usuarioPai, offset);				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(listaDeUsuarios);
				
				response.addHeader("totalDePaginas",""+totalDePaginas);
				response.getWriter().write(json);
			} catch (Exception e) {
				redirecionaParaErro(e.getMessage(), request, response);
			}
		}		
	}
	
	private void consultaUsuarioSelecionadoPorId( HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ModelLogin login = null;
		String id = request.getParameter("id");
		String msg;
		Long idDoUsuario = -1L;
		Integer totalDePaginas =0;
		String pagina = request.getParameter("pagina");
		Integer numeroDaPagina = pagina != null ?Integer.parseInt(pagina):0;
		List<ModelLogin> listaDeUsuariosCadastrados = new ArrayList<>();
		try {
			idDoUsuario = Long.parseLong(id);
		} catch (Exception e) {
			redirecionaParaErro(e.getMessage(), request, response);
			return;
		}
		if (idDoUsuario != null) {

			try {
				Long usuarioPai = genericUtils.getUsuarioLogado(request);

				listaDeUsuariosCadastrados=consultaUsuario.consultaUsariosPaginada(usuarioPai, numeroDaPagina);
				login = consultaUsuario.consultaUsuarioPorId(idDoUsuario);
				Double totalDeRgistros = consultaUsuario.consultaTotalDeRegistros(usuarioPai);
				if(totalDeRgistros>0) {
					totalDePaginas = genericUtils.totalDePaginas(totalDeRgistros);				
				}
				
				msg = "Usuario Selecionado: ";
				request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosCadastrados);
				request.setAttribute("modelLogin", login);
				request.setAttribute("msg", msg);
				request.setAttribute("totalDePaginas", totalDePaginas);
				RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
				redirecionador.forward(request, response);

			} catch (Exception e) {
				redirecionaParaErro(e.getMessage(), request, response);
			}
		}
	}
}
