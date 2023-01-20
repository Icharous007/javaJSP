package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import dao.DAOConsultaUsuario;
import dao.DAOTelefoneRepository;

@WebServlet("/ServeletTelefoneController")
public class ServeletTelefoneController extends ServLetGenericUtilsEntendendoServlet {
	private static final long serialVersionUID = 1L;
	String id= null;
	String idTelefone= null;
	String sNumero;
	Long idDoUsuario=null;
	Long idDoTelefone=null;
	DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();
	DAOTelefoneRepository telefoneRepository = new DAOTelefoneRepository();
	ModelLogin modelLogin = null;
	ModelTelefone modelTelefone = null;
	final String paginaDeCastro = "principal/cadastro.jsp";
	final String atributoModelLogin = "modelLogin";
	String msg = "";
	Double totalDeRgistros=null;
	List<ModelLogin> listaDeUsuariosCadastrados  = null;
	Integer totalDePaginas = 0;
	List<ModelTelefone> listaDeTelefonesDoUsuario = null;
	
       
    public ServeletTelefoneController() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		id = request.getParameter("idDoUsuario");
		if(id == null || id == "null"|| id.isEmpty()) {
			retornarParaPaginaDeCadastro(request, response, modelLogin);
			return;
		}
		try {
			String acao = request.getParameter("acao");
			
			if ("deletarTelefone".equalsIgnoreCase(acao)){
				
				this.idTelefone = request.getParameter("idDoTelefone");
				this.idDoTelefone = Long.parseLong(idTelefone);
				telefoneRepository.deletarTelefone(idDoTelefone);
				
			}else {
					
				
			}
			
			idDoUsuario = Long.parseLong(id);
			modelLogin = consultaUsuario.consultaUsuarioPorId(idDoUsuario);			
			this.listaDeTelefonesDoUsuario = telefoneRepository.telefonesPorUsuarioId(idDoUsuario);	
			
		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage();
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			request.setAttribute("msg", erro);
			redirecionador.forward(request, response);
		}
		request.setAttribute("modelTelefones", this.listaDeTelefonesDoUsuario);
		request.setAttribute("modelLogin", modelLogin);
		msg="telefones do usuario.";
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/telefone.jsp");
		request.setAttribute("msg", msg);
		redirecionador.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			id = request.getParameter("idDoUsuarioTelefone");
			sNumero = request.getParameter("numeroTelefoneUsuario");
			modelTelefone = new ModelTelefone();
			idDoUsuario = Long.parseLong(id);
			
			//modelTelefone.setId(idDoUsuario);
			modelTelefone.setNumero(sNumero);
			ModelLogin modelLogin = consultaUsuario.consultaUsuarioPorId(idDoUsuario);
			modelTelefone.setUsuario_id(modelLogin);
			modelTelefone.setUsuario_pai(consultaUsuario.consultaUsuarioPorId(super.getUsuarioLogado(request)));
			
			telefoneRepository.gravarTelefone(modelTelefone);
			this.listaDeTelefonesDoUsuario = telefoneRepository.telefonesPorUsuarioId(idDoUsuario);
			
			request.setAttribute("modelTelefones", this.listaDeTelefonesDoUsuario);
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefone", modelTelefone);
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/telefone.jsp");
			redirecionador.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			String erro = e.getMessage();
			request.setAttribute("msg", erro);
			RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
			redirecionador.forward(request, response);
		}
		
		
	}
	
	private void retornarParaPaginaDeCadastro(HttpServletRequest request, HttpServletResponse response, ModelLogin modelLogin)throws ServletException, IOException {
		try {
			
			this.modelLogin = modelLogin;
			
			totalDeRgistros = consultaUsuario.consultaTotalDeRegistros(super.getUsuarioLogado(request));
			if(totalDeRgistros>0) {
				totalDePaginas =super.totalDePaginas(totalDeRgistros);
			}
		} catch (Exception e) {
			retornaErro(request, response, e);
			return;
		}
		msg="Falha ao abrir telefones";
		
		request.setAttribute("totalDePaginas", totalDePaginas);
		request.setAttribute("listaDeUsuariosCadastrados", listaDeUsuariosCadastrados);
		request.setAttribute(atributoModelLogin, modelLogin);
		request.setAttribute("msg", msg);
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/cadastro.jsp");
		redirecionador.forward(request, response);
	}

	private void retornaErro(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException{
		e.printStackTrace();
		String erro = e.getMessage();
		RequestDispatcher redirecionador = request.getRequestDispatcher("principal/erro.jsp");
		request.setAttribute("msg", erro);
		redirecionador.forward(request, response);
	}
}
