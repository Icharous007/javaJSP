package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;


import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOCadastroUsuario implements Serializable  {
	
	private static final long serialVersionUID = -5710697298694030975L;

	private Connection conexao;
	
	private DAOConsultaUsuario consultaUmUsuario=  new DAOConsultaUsuario();
	
	public DAOCadastroUsuario() {
		conexao = ConexaoUnica.getConexao();
	}
	
	public ModelLogin cadastrarUsuario(ModelLogin modelLogin, Long usuarioPai) throws Exception {
		String sql = "INSERT INTO public.usuarios(login_usuario, senha_usuario, "
				+ "email, nome, usurario_pai, useradmin, perfil_usuario, sexo_usuario, cidade, cep, endereco, data_de_nascimento,renda) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?, ?,?);";
		PreparedStatement preparedStatement = null;

		try {
			
			preparedStatement = conexao.prepareStatement(sql);			
			preparedStatement.setString(1,modelLogin.getLogin());
			preparedStatement.setString(2,modelLogin.getSenha());
			preparedStatement.setString(3,modelLogin.getEmail());
			preparedStatement.setString(4,modelLogin.getNome());
			preparedStatement.setLong(5,usuarioPai);
			preparedStatement.setBoolean(6, modelLogin.isUserAdmin());
			preparedStatement.setString(7, modelLogin.getPerfil_usuario());
			preparedStatement.setString(8, modelLogin.getSexo());
			preparedStatement.setString(9, modelLogin.getCidade());
			preparedStatement.setString(10, modelLogin.getCEP());
			preparedStatement.setString(11, modelLogin.getEndereco());
			preparedStatement.setDate(12, new java.sql.Date(modelLogin.getDataDeNascimento().getTime()));
			preparedStatement.setDouble(13, modelLogin.getRenda());
			
			
			preparedStatement.execute();
			conexao.commit();
			
			return this.consultaUmUsuario.consultaUsuarioPorLogin(modelLogin.getLogin());
		}
		finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}			
		}
	}

	public ModelLogin cadastrarUsuarioComFoto(ModelLogin modelLogin, Long usuarioPai) throws Exception{
		String sql = "INSERT INTO public.usuarios(login_usuario, senha_usuario, email, nome, usurario_pai, "
				+ "useradmin, perfil_usuario, sexo_usuario, foto_user, sufixo_foto_user, cep, endereco, cidade, data_de_nascimento, renda ) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement preparedStatement = null;

		try {
			
			preparedStatement = conexao.prepareStatement(sql);			
			preparedStatement.setString(1,modelLogin.getLogin());
			preparedStatement.setString(2,modelLogin.getSenha());
			preparedStatement.setString(3,modelLogin.getEmail());
			preparedStatement.setString(4,modelLogin.getNome());
			preparedStatement.setLong(5,usuarioPai);
			preparedStatement.setBoolean(6, modelLogin.isUserAdmin());
			preparedStatement.setString(7, modelLogin.getPerfil_usuario());
			preparedStatement.setString(8, modelLogin.getSexo());
			preparedStatement.setString(9, modelLogin.getFoto_do_usuario());
			preparedStatement.setString(10, modelLogin.getSufixo_da_foto_do_usuario());
			preparedStatement.setString(11, modelLogin.getCEP());
			preparedStatement.setString(12, modelLogin.getEndereco());
			preparedStatement.setString(13, modelLogin.getCidade());
			preparedStatement.setDate(14, new java.sql.Date(modelLogin.getDataDeNascimento().getTime()));
			preparedStatement.setDouble(15, modelLogin.getRenda());
			preparedStatement.execute();
			conexao.commit();
			
			return this.consultaUmUsuario.consultaUsuarioPorLogin(modelLogin.getLogin());
		}
		finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}			
		}
	}
}
