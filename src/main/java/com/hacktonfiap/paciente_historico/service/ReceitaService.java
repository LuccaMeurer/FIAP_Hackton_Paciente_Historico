package com.hacktonfiap.paciente_historico.service;

import com.hacktonfiap.paciente_historico.entity.Paciente;
import com.hacktonfiap.paciente_historico.entity.Receita;
import com.hacktonfiap.paciente_historico.repository.PacienteRepository;
import com.hacktonfiap.paciente_historico.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final PacienteRepository pacienteRepository;

    public Receita salvar(Long pacienteId, MultipartFile file) throws IOException {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        String filename = file.getOriginalFilename();

        if (filename == null) {
            throw new RuntimeException("Nome do arquivo inválido.");
        }

        filename = filename.toLowerCase();

        boolean extensaoValida =
                filename.endsWith(".pdf") ||
                        filename.endsWith(".jpg") ||
                        filename.endsWith(".jpeg") ||
                        filename.endsWith(".png");

        if (!extensaoValida) {
            throw new RuntimeException("Arquivo inválido.");
        }

        Receita receita = Receita.builder()
                .nomeOriginal(file.getOriginalFilename())
                .tipoArquivo(file.getContentType())
                .arquivo(file.getBytes())
                .tamanho(file.getSize())
                .paciente(paciente)
                .build();

        return receitaRepository.save(receita);
    }


    public Receita buscarPorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

}