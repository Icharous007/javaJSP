package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoUnica;
import model.ModelTelefone;

public class DAOTelefoneRepository implements Serializable {

	private static final long serialVersionUID = 4714120627238352996L;

	private Connection conexao = null;

	private String sql = null;
	private PreparedStatement statement = null;
	private ResultSet resultado = null;
	private DAOConsultaUsuario consultaUsuario = new DAOConsultaUsuario();

	public DAOTelefoneRepository() {
		conexao = ConexaoUnica.getConexao();
	}

	public ModelTelefone gravarTelefone(ModelTelefone telefone) throws Exception {
		ModelTelefone modelTelefone = null;
		if (!jaExisteTelefone(telefone)) {

			this.sql = "INSERT INTO public.telefone (numero, usuario_id, usuario_pai) VALUES (?,?,?)";
			this.statement = null;
			try {
				statement = conexao.prepareStatement(sql);
				statement.setString(1, telefone.getNumero());
				statement.setLong(2, telefone.getUsuario_id().getId());
				statement.setLong(3, telefone.getUsuario_pai().getId());

				statement.execute();
				conexao.commit();
			} finally {
				if (statement != null) {
					statement.close();
				}
			}
		}
		return modelTelefone;
	}

	public void deletarTelefone(Long id) throws Exception {
		this.sql = "DELETE FROM public.telefone WHERE id=?";
		this.statement = null;

		try {
			statement = conexao.prepareStatement(sql);
			statement.setLong(1, id);

			statement.executeUpdate();
			conexao.commit();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	public boolean jaExisteTelefone(ModelTelefone telefone) throws Exception {
		String sqlProcura = "SELECT COUNT(1) as totalDeRegistros FROM public.telefone WHERE numero= ? and usuario_id=?;";

		this.statement = null;
		ResultSet resultSet = null;
		try {
			statement = conexao.prepareStatement(sqlProcura);
			statement.setString(1, telefone.getNumero());
			statement.setLong(2, telefone.getUsuario_id().getId());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int existe = resultSet.getInt(1);
				if (existe > 0) {
					return true;
				} else {
					return false;
				}
			}

		} finally {
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return false;
	}

	public List<ModelTelefone> telefonesPorUsuarioId(Long usuario_id) throws Exception {
		List<ModelTelefone> telefones = new ArrayList<>();
		this.sql = "SELECT * FROM public.telefone WHERE usuario_id = ?";
		try {

			this.statement = conexao.prepareStatement(sql);
			this.statement.setLong(1, usuario_id);
			this.resultado = this.statement.executeQuery();
			while (this.resultado.next()) {
				ModelTelefone telefone = new ModelTelefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));
				telefone.setUsuario_id(this.consultaUsuario.consultaUsuarioPorId(resultado.getLong("usuario_id")));
				telefone.setUsuario_pai(this.consultaUsuario.consultaUsuarioPorId(resultado.getLong("usuario_pai")));

				telefones.add(telefone);
			}
			return telefones;
		} finally {
			if (this.statement != null) {
				this.statement.close();
			}
			if (this.resultado != null) {
				this.resultado.close();
			}
		}
	}

	public static List<ModelTelefone> telefonesPorUsuarioId(Long usuario_id, Connection conexao) throws Exception {
		List<ModelTelefone> telefones = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultado = null;
		String sql = "SELECT * FROM public.telefone WHERE usuario_id = ?";
		try {

			statement = conexao.prepareStatement(sql);
			statement.setLong(1, usuario_id);
			resultado = statement.executeQuery();
			while (resultado.next()) {
				ModelTelefone telefone = new ModelTelefone();
				telefone.setId(resultado.getLong("id"));
				telefone.setNumero(resultado.getString("numero"));
				// telefone.setUsuario_id(this.consultaUsuario.consultaUsuarioPorId(
				// resultado.getLong("usuario_id")));
				// telefone.setUsuario_pai(this.consultaUsuario.consultaUsuarioPorId(
				// resultado.getLong("usuario_pai")));

				telefones.add(telefone);
			}
			return telefones;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (resultado != null) {
				resultado.close();
			}
		}
	}
}
