package br.edu.fateczl.SpringMVCCRUD.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.fateczl.SpringMVCCRUD.model.Produto;
import br.edu.fateczl.SpringMVCCRUD.persitence.ProdutoDao;

@Controller
public class ProdutoController {
	@Autowired
	ProdutoDao pDao;

	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		return new ModelAndView("produto");
	}

	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.POST)
	public ModelAndView findAlunos(@RequestParam Map<String, String> params, ModelMap model) {
		String cmd = params.get("button");
		List<Produto> listaProdutos = new ArrayList<Produto>();
		Produto p = validaCampos(params, cmd);
		String saida = "";
		String erro = "";

		try {
			if (cmd.contains("Cadastrar")) {
				if (p != null) {
					saida = pDao.insereProduto(p);
					p = new Produto();
				}
			}
			if (cmd.contains("Atualizar")) {
				if (p != null) {
					saida = pDao.atualizaProduto(p);
					p = new Produto();
				}
			}
			if (cmd.contains("Excluir")) {
				if (p != null) {
					saida = pDao.excluiProduto(p);
					p = new Produto();
				}
			}
			if (cmd.contains("Consultar")) {
				if (p != null) {
					p = pDao.consultaProduto(p);
				}
			}
			if (cmd.contains("Listar")) {
				listaProdutos = pDao.consultaProdutos();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("produto", p);
			model.addAttribute("listaProdutos", listaProdutos);
		}
		return new ModelAndView("produto");
	}

	private Produto validaCampos(Map<String, String> params, String cmd) {
		Produto p = new Produto();
		if (cmd.contains("Cadastrar") || cmd.contains("Atualizar")) {
			if (!params.get("codigo").trim().isEmpty() && !params.get("nome").trim().isEmpty()
					&& !params.get("valor").trim().isEmpty() && !params.get("estoque").trim().isEmpty()) {
				p.setCodigo(Integer.parseInt(params.get("codigo").trim()));
				p.setNome(params.get("nome").trim());
				p.setValorUnitario(Float.parseFloat(params.get("valor").trim()));
				p.setQtdEstoque(Integer.parseInt(params.get("estoque").trim()));
			}
		}
		if (cmd.contains("Excluir") || cmd.contains("Consultar")) {
			if (!params.get("codigo").trim().isEmpty()) {
				p.setCodigo(Integer.parseInt(params.get("codigo").trim()));
			}
		}
		return p;
	}
}
