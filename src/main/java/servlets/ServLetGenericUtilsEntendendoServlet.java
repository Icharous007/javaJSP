package servlets;

import dao.DAOConsultaUsuario;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;

public class ServLetGenericUtilsEntendendoServlet  extends HttpServlet{

	private static final long serialVersionUID = -5390865472494719997L;
	
	private DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	
	public Long getUsuarioLogado(HttpServletRequest request) throws Exception {
		
		HttpServletRequest requisicao = (HttpServletRequest) request;
		HttpSession sessao = requisicao.getSession();
		ModelLogin usuarioLogado = (ModelLogin) sessao.getAttribute("usuarioLogado");
		usuarioLogado = consultaUsuario.consultaUsuarioPorLogin(usuarioLogado.getLogin());
		sessao.setAttribute("isUserAdmin", usuarioLogado.isUserAdmin());
		return usuarioLogado.getId();
	}
	
	public Integer totalDePaginas(Double totalDeRgistros) {
		Integer registrosPorPagina = 3;
		Integer totalDePaginas =(int) (totalDeRgistros/registrosPorPagina);
		if(totalDeRgistros%registrosPorPagina > 0) {
			totalDePaginas++;
		}
		return totalDePaginas;
	}
}