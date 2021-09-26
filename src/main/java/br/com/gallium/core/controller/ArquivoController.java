package br.com.gallium.core.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.gallium.core.exception.StorageFileNotFoundException;
import br.com.gallium.core.service.StorageService;

@Controller
@RequestMapping("arquivo")
public class ArquivoController {

    private final StorageService storageService;

    @Autowired
    public ArquivoController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files",
                            storageService
                                    .loadAll()
                                    .map( path ->
                                    MvcUriComponentsBuilder
                                            .fromMethodName(ArquivoController.class, "serveFile", path.getFileName().toString())
                                            .build()
                                            .toUri()
                                            .toString())
                                            .collect(Collectors.toList())
                            );

        return "arquivo/index";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("messageError","Não existe nenhum arquivo para ser salvo!");
        }
        else{
            try {
                storageService.store(file);
                redirectAttributes.addFlashAttribute("message","Arquivo " + file.getOriginalFilename() + " salvo com sucesso!");
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("messageError","Arquivo já existente ou não foi possivel fazer o upload o arquivo!");
            }
        }

        return "redirect:/arquivo";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
