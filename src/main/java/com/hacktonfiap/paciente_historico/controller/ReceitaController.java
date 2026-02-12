package com.hacktonfiap.paciente_historico.controller;

import com.hacktonfiap.paciente_historico.entity.Receita;
import com.hacktonfiap.paciente_historico.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    @PostMapping("/{pacienteId}")
    public ResponseEntity<Receita> upload(
            @PathVariable Long pacienteId,
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        return ResponseEntity.ok(
                receitaService.salvar(pacienteId, file)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {

        Receita receita = receitaService.buscarPorId(id);

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=\"" + receita.getNomeOriginal() + "\"")
                .header("Content-Type", receita.getTipoArquivo())
                .body(receita.getArquivo());
    }

}
