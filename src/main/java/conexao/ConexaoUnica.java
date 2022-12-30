package conexao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoUnica  implements Serializable {
		
	private static final long serialVersionUID = -7688228357152437236L;
	private static final String URL_BANCO = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "0415154";
	
	private static Connection conexao = null;
	
	public ConexaoUnica() {
		conectar();
	}
	
	static {
		conectar();
	}
	
	public static Connection getConexao() {
		return conexao;
	}
	
	private static void conectar() {
		try {
			if (conexao == null) {
				Class.forName("org.postgresql.Driver"); //carrega o driver do Postgres.
				conexao = DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
				conexao.setAutoCommit(false);//sem auto commit no banco de dados.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
