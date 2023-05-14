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
public class ProdutoEstoqueController {
	@Autowired
	ProdutoDao pDao;

	@RequestMapping(name = "produtoEstoque", value = "/produtoEstoque", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		return new ModelAndView("produtoestoque");
	}

	@RequestMapping(name = "produtoEstoque", value = "/produtoEstoque", method = RequestMethod.POST)
	public ModelAndView findAlunos(@RequestParam Map<String, String> params, ModelMap model) {
		String cmd = params.get("button");
		List<Produto> listaProdutos = new ArrayList<Produto>();
		int qtdMinima = validaCampos(params);
		int min = 0;
		String erro = "";

		try {
			if (cmd.contains("Qtd")) {
				if (qtdMinima > 0) {
					min = pDao.consultaQtdForaEstoque(qtdMinima);
					qtdMinima = 0;
				}
			}
			if (cmd.contains("Lista")) {
				listaProdutos = pDao.consultaListaProdutosForaEstoque(qtdMinima);
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("qtdMinima", qtdMinima);
			if (min > 0) {
				model.addAttribute("totalMinimo", min);
			}
			model.addAttribute("erro", erro);
			model.addAttribute("listaProdutos", listaProdutos);
		}
		return new ModelAndView("produtoestoque");
	}

	private int validaCampos(Map<String, String> params) {
		if (!params.get("qtdMinima").trim().isEmpty()) {
			int qtdMinima = Integer.parseInt(params.get("qtdMinima").trim());
			return qtdMinima;
		} else {
			return 0;
		}
	}
}
