package br.edu.fateczl.SpringMVCCRUD.persitence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.SpringMVCCRUD.model.Produto;

public interface IProdutoDao {
	public String insereProduto(Produto p) throws SQLException, ClassNotFoundException;

	public String atualizaProduto(Produto p) throws SQLException, ClassNotFoundException;

	public String excluiProduto(Produto p) throws SQLException, ClassNotFoundException;

	public Produto consultaProduto(Produto p) throws SQLException, ClassNotFoundException;

	public List<Produto> consultaProdutos() throws SQLException, ClassNotFoundException;

	public int consultaQtdForaEstoque(int qtdMinima) throws SQLException, ClassNotFoundException;

	public List<Produto> consultaListaProdutosForaEstoque(int qtdMinima) throws SQLException, ClassNotFoundException;
}
