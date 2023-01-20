package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.util.List;

import dao.DAOConsultaUsuario;

@MultipartConfig
@WebServlet("/ServeLetPaginacao")
public class ServeLetPaginacao extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	private ServLetGenericUtils genericUtils = new ServLetGenericUtils();
	public ServeLetPaginacao() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pagina = request.getParameter("pagina");
		Integer numeroDaPagina = pagina != null ?Integer.parseInt(pagina):0;
		List<ModelLogin> listaDeUsuariosPaginada = null;
		Integer totalDePaginas =0;
		String msg= null;
		try {
			Long usuarioPai = genericUtils.getUsuarioLogado(request);
			Double totalDeRgistros = consultaUsuario.consultaTotalDeRegistros(usuarioPai);
			if(totalDeRgistros>0) {
				totalDePaginas = genericUtils.totalDePaginas(totalDeRgistros);				
			}
			listaDeUsuariosPaginada = consultaUsuario.consultaUsariosPaginada(usuarioPai, numeroDaPagina);
			msg="Registros da pagina: "+pagina;
		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage();
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			request.setAttribute("msg", erro);
			redirecionador.forward(request, response);
		}
		request.setAttribute("totalDePaginas", totalDePaginas);
		request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosPaginada);
		request.setAttribute("msg", msg);;
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
		redirecionador.forward(request, response);
		
	}

}
