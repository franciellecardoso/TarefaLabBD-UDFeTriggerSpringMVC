package br.edu.fateczl.SpringMVCCRUD.persitence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.fateczl.SpringMVCCRUD.model.Produto;

@Repository
public class ProdutoDao implements IProdutoDao {

	@Autowired
	GenericDao gDao;

	@Override
	public String insereProduto(Produto p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_produto (?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "I");
		cs.setInt(2, p.getCodigo());
		cs.setString(3, p.getNome());
		cs.setDouble(4, p.getValorUnitario());
		cs.setInt(5, p.getQtdEstoque());
		cs.registerOutParameter(6, Types.VARCHAR);

		cs.execute();
		String saida = cs.getString(6);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public String atualizaProduto(Produto p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_produto (?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "U");
		cs.setInt(2, p.getCodigo());
		cs.setString(3, p.getNome());
		cs.setDouble(4, p.getValorUnitario());
		cs.setInt(5, p.getQtdEstoque());
		cs.registerOutParameter(6, Types.VARCHAR);

		cs.execute();
		String saida = cs.getString(6);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public String excluiProduto(Produto p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_produto (?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setInt(2, p.getCodigo());
		cs.setString(3, p.getNome());
		cs.setDouble(4, p.getValorUnitario());
		cs.setInt(5, p.getQtdEstoque());
		cs.registerOutParameter(6, Types.VARCHAR);

		cs.execute();
		String saida = cs.getString(6);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public Produto consultaProduto(Produto p) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT nome, valor_unitario, qtd_estoque FROM produto WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, p.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			p.setNome(rs.getString("nome"));
			p.setValorUnitario(rs.getDouble("valor_unitario"));
			p.setQtdEstoque(rs.getInt("qtd_estoque"));
		}
		rs.close();
		ps.close();
		c.close();
		return p;
	}

	@Override
	public List<Produto> consultaProdutos() throws SQLException, ClassNotFoundException {
		List<Produto> produtos = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, valor_unitario, qtd_estoque FROM produto";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Produto p = new Produto();
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			p.setValorUnitario(rs.getDouble("valor_unitario"));
			p.setQtdEstoque(rs.getInt("qtd_estoque"));

			produtos.add(p);
		}
		rs.close();
		ps.close();
		c.close();
		return produtos;
	}

	@Override
	public int consultaQtdForaEstoque(int qtdMinima) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT dbo.fn_qtdProdutosForaEstoque(?) AS qtdForaEstoque";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, qtdMinima);
		ResultSet rs = ps.executeQuery();
		int total = 0;
		if (rs.next()) {
			total = rs.getInt("qtdForaEstoque");
		}
		rs.close();
		ps.close();
		c.close();
		return total;
	}

	@Override
	public List<Produto> consultaListaProdutosForaEstoque(int qtdMinima) throws SQLException, ClassNotFoundException {
		List<Produto> produtos = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, qtd_estoque FROM fn_produtosForaEstoque(?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, qtdMinima);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Produto p = new Produto();
			p.setCodigo(rs.getInt("codigo"));
			p.setNome(rs.getString("nome"));
			p.setQtdEstoque(rs.getInt("qtd_estoque"));

			produtos.add(p);
		}
		rs.close();
		ps.close();
		c.close();
		return produtos;
	}
}
