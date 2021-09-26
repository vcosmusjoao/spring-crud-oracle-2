package br.com.gallium.core.controller;

import br.com.gallium.core.exception.ResourceNotFoundException;
import br.com.gallium.core.model.Funcionario;
import br.com.gallium.core.model.LojaDeRoupa;
import br.com.gallium.core.repository.LojaDeRoupaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping({"loja", "/"}) //locahost:8080/loja
public class LojaDeRoupaController {

    private LojaDeRoupaRepository repository;

    public LojaDeRoupaController(LojaDeRoupaRepository repository){
        this.repository = repository;
    }

    //Listando na index
    @GetMapping
    public String getAllLojaDeRoupa(Model model){
        model.addAttribute("lojasDeRoupa", repository.findAll());
        return "loja/index";
    }

    //Get Form
    @GetMapping("cadastrar")
    public String cadastrar(LojaDeRoupa lojaDeRoupa){
        return "loja/form";
    }

    //Cadastrando
    @PostMapping("cadastrar")
    public String cadastrar(LojaDeRoupa lojaDeRoupa, RedirectAttributes redirectAttributes){

        String msg = "Loja Cadastrada com sucesso!";
        if(lojaDeRoupa.getCodLoja() != 0) msg = "Loja Atualizada com sucesso!";

        repository.save(lojaDeRoupa);
        redirectAttributes.addFlashAttribute("msg", msg);

        return "redirect:/";
    }

    //Get from para editar
    @GetMapping("editar/{id}")
    public String updateLojaDeRoupa(@PathVariable("id") int id, Model model){

        model.addAttribute("lojaDeRoupa", repository.findById(id));

        return "loja/form";
    }

    // locahost:8080/loja/{id} -> resStatus 200 OK || 404 NOT FOUND
    @PostMapping("excluir/{id}")
    public String deleteLojaDeRoupa(@PathVariable("id") int id, RedirectAttributes redirectAttributes){

        repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        repository.deleteById(id);

        redirectAttributes.addFlashAttribute("msg", "Loja Removida, jutamente com o seus funcinarios!");

        return "redirect:/";
    }

    //Listar funcionarios por loja
    @GetMapping("{id}/funcionarios")
    public  String buscarFuncionariosPorLoja(@PathVariable("id") int id,Model model){

        LojaDeRoupa lojaDeRoupa =  repository.findById(id).get();

        model.addAttribute("lojaDeRoupa", lojaDeRoupa);
        model.addAttribute("funcionarios", lojaDeRoupa.getFuncionarios());

        return "loja/func-loja";
    }

    // locahost:8080/loja/buscar?nome=atributo -> resStatus 200 OK
    @GetMapping("buscar")
    public List<LojaDeRoupa> buscarLojaPeloNome(@RequestParam String nome){
        return repository.findByNomeLojaContainsIgnoreCase(nome);
    }



}