package br.com.gallium.core.controller;

import br.com.gallium.core.exception.ResourceNotFoundException;
import br.com.gallium.core.model.Cargo;
import br.com.gallium.core.model.Funcionario;
import br.com.gallium.core.model.LojaDeRoupa;
import br.com.gallium.core.repository.FuncionarioRepository;
import br.com.gallium.core.repository.LojaDeRoupaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

// locahost:8080/funcionario
@Controller
@RequestMapping("funcionario")
public class FuncionarioController {

    private FuncionarioRepository repository;
    private LojaDeRoupaRepository lojaDeRoupaRepository;

    public FuncionarioController(FuncionarioRepository repository, LojaDeRoupaRepository lojaDeRoupaRepository){
        this.repository = repository;
        this.lojaDeRoupaRepository = lojaDeRoupaRepository;
    }

    //Listando na index
    @GetMapping
    public String getAllLojaDeRoupa(Model model){
        model.addAttribute("funcionarios", repository.findAll());
        return "funcionario/index";
    }

    //Get Form
    @GetMapping("cadastrar")
    public String cadastrar(Funcionario funcionario, Model model){
        model.addAttribute("lojasDeRoupa", lojaDeRoupaRepository.findAll());

        return "funcionario/form";
    }


    //Get from para editar
    @GetMapping("editar/{id}")
    public String update(@PathVariable("id") int id, Model model){
        model.addAttribute("lojasDeRoupa", lojaDeRoupaRepository.findAll());
        model.addAttribute("funcionario", repository.findById(id));
        return "funcionario/form";
    }

    //Cadastrando
    @PostMapping("cadastrar")
    public String cadastrar(Funcionario funcionario, @RequestParam("codLoja") int codLoja, RedirectAttributes redirectAttributes) throws ParseException {

        LojaDeRoupa lojaFuncionario = lojaDeRoupaRepository.findById(codLoja).get();
        lojaFuncionario.addFuncionario(funcionario);

        String msg = "Funcionario cadastrado com sucesso!";

        if(funcionario.getCodFuncionario() != 0) msg = "Funcionario atualizado com sucesso!";

        repository.save(funcionario);

        redirectAttributes.addFlashAttribute("msg", msg);

        return "redirect:/funcionario";

    }



    @PostMapping("excluir/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes redirectAttributes){

        repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        repository.deleteById(id);

        redirectAttributes.addFlashAttribute("msg", "Funcionario removido!");

        return "redirect:/funcionario";

    }
}