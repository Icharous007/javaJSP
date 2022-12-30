package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOLoginRepository implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private Connection conexao;
	
	public DAOLoginRepository() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public boolean autenticacao(ModelLogin modelLogin) throws Exception {
		String sql = "SELECT * FROM public.usuarios WHERE upper(login_usuario)=  upper(?) and upper(senha_usuario) = upper(?)";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet  = null;
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			
			resultSet = preparedStatement.executeQuery();
			
			return resultSet.next();
			
		} finally {
			if (preparedStatement!=null) {
				preparedStatement.close();
			}
			if(resultSet!=null) {
				resultSet.close();				
			}
		}
	}
}
