package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	private Long id;
	private String nome;
	private String email;
	private boolean userAdmin;
	private Long usuario_id;
	private String perfil_usuario;
	private String sexo;
	private String foto_do_usuario;
	private String sufixo_da_foto_do_usuario;
	private String CEP;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String numero;
		
	public boolean IsNew() {
		return this.id == null;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isUserAdmin() {
		return userAdmin;
	}
	public void setUserAdmin(boolean userAdmin) {
		this.userAdmin = userAdmin;
	}
	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}
	public String getPerfil_usuario() {
		return perfil_usuario;
	}
	public void setPerfil_usuario(String perfil_usuario) {
		this.perfil_usuario = perfil_usuario;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getFoto_do_usuario() {
		return foto_do_usuario;
	}
	public void setFoto_do_usuario(String foto_do_usuario) {
		this.foto_do_usuario = foto_do_usuario;
	}
	public String getSufixo_da_foto_do_usuario() {
		return sufixo_da_foto_do_usuario;
	}
	public void setSufixo_da_foto_do_usuario(String sufixo_da_foto_do_usuario) {
		this.sufixo_da_foto_do_usuario = sufixo_da_foto_do_usuario;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cEP) {
		CEP = cEP;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}	
}
