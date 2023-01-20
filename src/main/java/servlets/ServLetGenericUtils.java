package servlets;

import java.io.Serializable;

import dao.DAOConsultaUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;

public class ServLetGenericUtils implements Serializable{

	private static final long serialVersionUID = -2896703716310740262L;
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
