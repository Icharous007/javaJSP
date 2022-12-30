package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;


import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOCadastroUsuario implements Serializable  {
	
	private static final long serialVersionUID = -5710697298694030975L;

	private Connection conexao;
	
	private DAOConsultaUmUsuario consultaUmUsuario=  new DAOConsultaUmUsuario();
	
	public DAOCadastroUsuario() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public ModelLogin cadastrarUsuario(ModelLogin modelLogin) throws Exception {
		String sql = "INSERT INTO public.usuarios(login_usuario, senha_usuario, email, nome) VALUES (?, ?, ?, ?);";
		PreparedStatement preparedStatement = null;

		try {
			
			preparedStatement = conexao.prepareStatement(sql);			
			preparedStatement.setString(1,modelLogin.getLogin());
			preparedStatement.setString(2,modelLogin.getSenha());
			preparedStatement.setString(3,modelLogin.getEmail());
			preparedStatement.setString(4,modelLogin.getNome());
			 preparedStatement.execute();
			conexao.commit();
			
			return this.consultaUmUsuario.cosultaUsuarioPorLogin(modelLogin.getLogin());
		}
		finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}			
		}
	}
}
