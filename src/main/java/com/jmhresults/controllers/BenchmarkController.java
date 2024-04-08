package com.jmhresults.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmhresults.BenchmarkMapper;
import com.jmhresults.entities.Benchmark;
import com.jmhresults.models.BenchmarkDTO;
import com.jmhresults.repositories.BenchmarkRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/JMHResults")
@RequiredArgsConstructor
public class BenchmarkController {

    private final BenchmarkRepository benchmarkRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadBenchmark(@RequestParam("file") MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BenchmarkDTO[] benchmarkDTOArray = objectMapper.readValue(file.getInputStream(), BenchmarkDTO[].class);

            // Save each BenchmarkDTO to the database
            for (BenchmarkDTO benchmarkDTO : benchmarkDTOArray) {
                Benchmark entity = BenchmarkMapper.INSTANCE.toEntity(benchmarkDTO);
                entity.setPrimaryMetricScore(benchmarkDTO.getPrimaryMetric().getScore());
                entity.setPrimaryMetricScoreUnit(benchmarkDTO.getPrimaryMetric().getScoreUnit());
                entity.setPrimaryMetricRawData(benchmarkDTO.getPrimaryMetric().getRawData());

                benchmarkRepository.save(entity);
            }
            return ResponseEntity.ok("Record inserted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Benchmark>> getBenchmarks() {
        try {
            return ResponseEntity.ok(benchmarkRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
