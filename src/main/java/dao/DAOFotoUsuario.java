package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOFotoUsuario implements Serializable {

	private static final long serialVersionUID = -5375941549318296556L;
	private Connection conexao = null;

	public DAOFotoUsuario() {
		conexao= ConexaoUnica.getConexao();
	}

	public ModelLogin getFotoDoUsuario(Long identificador) throws Exception {
		String sql = "SELECT foto_user, sufixo_foto_user FROM public.usuarios WHERE id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ModelLogin modelLogin = new ModelLogin();
		
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setLong(1,identificador);
			
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				modelLogin.setFoto_do_usuario(resultSet.getString(1));
				modelLogin.setSufixo_da_foto_do_usuario(resultSet.getString(2));				
			}
			return modelLogin;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		
	}
	
	
}
