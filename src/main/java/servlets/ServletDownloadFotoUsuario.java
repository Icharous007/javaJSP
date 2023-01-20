package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;

import dao.DAOFotoUsuario;

@MultipartConfig
@WebServlet("/ServletDownloadFotoUsuario")
public class ServletDownloadFotoUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOFotoUsuario fotoUsuario = new DAOFotoUsuario();
       
    public ServletDownloadFotoUsuario() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		Long identificador = id != null && !id.isEmpty() ? Long.parseLong(id) : null;
		try {
			ModelLogin modelLogin = fotoUsuario.getFotoDoUsuario(identificador);
			if(modelLogin.getFoto_do_usuario()!=null && !modelLogin.getFoto_do_usuario().isEmpty()) {
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getSufixo_da_foto_do_usuario());
				response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFoto_do_usuario().split("\\,")[1]));
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

}
