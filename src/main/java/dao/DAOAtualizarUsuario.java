package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import conexao.ConexaoUnica;
import model.ModelLogin;

public class DAOAtualizarUsuario implements Serializable {

	private static final long serialVersionUID = -8283751754938326282L;
	private Connection conexao;
	private DAOConsultaUsuario consultaUmUsuario = new DAOConsultaUsuario();

	public DAOAtualizarUsuario() {
		conexao = ConexaoUnica.getConexao();
	}

	public ModelLogin atuliazaUsuarioPorId(ModelLogin modelLogin) throws Exception {
		PreparedStatement preparedStatement = null;
		int update;
		final String sql = "UPDATE public.usuarios SET login_usuario=?,senha_usuario=?,email=?,nome=?, useradmin = ?, "
				+ "sexo_usuario = ?, perfil_usuario=?, foto_user=?,  sufixo_foto_user=?, cep=?, cidade= ?, endereco=?, data_de_nascimento= ?, renda=?  "
				+ "WHERE id =?";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getEmail());
			preparedStatement.setString(4, modelLogin.getNome());
			preparedStatement.setBoolean(5, modelLogin.isUserAdmin());
			preparedStatement.setString(6, modelLogin.getSexo());
			preparedStatement.setString(7, modelLogin.getPerfil_usuario());
			preparedStatement.setString(8, modelLogin.getFoto_do_usuario());
			preparedStatement.setString(9, modelLogin.getSufixo_da_foto_do_usuario());
			preparedStatement.setString(10, modelLogin.getCEP());
			preparedStatement.setString(11, modelLogin.getCidade());
			preparedStatement.setString(12,modelLogin.getEndereco());
			preparedStatement.setDate(13,new java.sql.Date(modelLogin.getDataDeNascimento().getTime()) );
			preparedStatement.setDouble(14, modelLogin.getRenda());
			
			preparedStatement.setLong(15, modelLogin.getId());
			
			update = preparedStatement.executeUpdate();
			if (update > 0) {
				conexao.commit();
				return consultaUmUsuario.consultaUsuarioPorId(modelLogin.getId());
			}
			return null;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	public ModelLogin atuliazaUsuarioPorIdSemFoto(ModelLogin modelLogin) throws Exception {
		PreparedStatement preparedStatement = null;
		int update;
		final String sql = "UPDATE public.usuarios SET login_usuario=?,senha_usuario=?,email=?,nome=?, useradmin = ?, "
				+ "sexo_usuario = ?, perfil_usuario=?, cep=?, cidade= ?, endereco=?, data_de_nascimento=? , renda=? "
				+ "WHERE id =?";
		try {
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getEmail());
			preparedStatement.setString(4, modelLogin.getNome());
			preparedStatement.setBoolean(5, modelLogin.isUserAdmin());
			preparedStatement.setString(6, modelLogin.getSexo());
			preparedStatement.setString(7, modelLogin.getPerfil_usuario());
			preparedStatement.setString(8, modelLogin.getCEP());
			preparedStatement.setString(9, modelLogin.getCidade());
			preparedStatement.setString(10,modelLogin.getEndereco());
			preparedStatement.setDate(11,new java.sql.Date(modelLogin.getDataDeNascimento().getTime()) );
			preparedStatement.setDouble(12, modelLogin.getRenda());
			
			preparedStatement.setLong(13, modelLogin.getId());
			
			update = preparedStatement.executeUpdate();
			if (update > 0) {
				conexao.commit();
				return consultaUmUsuario.consultaUsuarioPorId(modelLogin.getId());
			}
			return null;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}
}
