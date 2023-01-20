<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Usuarios Cadastrados Modal-->
<div class="modal fade" id="modalDeUsuariosCadastrados" tabindex="-1"
	role="dialog" aria-labelledby="modalDeUsuariosCadastrados"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="tituloDoModalDeUsuariosCadastrados">Usuarios
					Cadastrados:</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="input-group ">
						<input type="text" name="nomeParaPesquisar"
							class="form-control form-control-user" id="nomeParaPesquisar"
							placeholder="digite o nome" required="required">
						<button type="button"
							class="btn bg-gradient-success text-gray-100 "
							onclick="pesquisarUsuarioPorNome(0);">Pesquisar</button>
					</div>
					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">Resultado da
								Pesquisa:</h6>
						</div>
						<div class="card-body">
							<div class="table-responsive"
								style="overflow: scroll; height: 300px;">
								<table class="table table-bordered"
									id="tabelaDeUsuariosCadastradosModal" cellspacing="0">
									<thead>
										<tr>
											<th>ID</th>
											<th>Nome</th>
											<th>E-mail</th>
											<th>Selecionar</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>ID</th>
											<th>Nome</th>
											<th>E-mail</th>
											<th>Selecionar</th>
										</tr>
									</tfoot>
									<tbody>
									</tbody>
								</table>
							</div>
							<span id="totalDeRegistros"></span>
							<div>
								<nav>
									<ul class="pagination" id="ULpaginacaoAjax">										
																			
									</ul>
								</nav>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Fechar</button>
			</div>
		</div>
	</div>
</div>