package com.jmhresults.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

			Long lastRecordId = null; // Initialize variable to store last record ID

			// Save each BenchmarkDTO to the database
			for (BenchmarkDTO benchmarkDTO : benchmarkDTOArray) {
				Benchmark entity = BenchmarkMapper.INSTANCE.toEntity(benchmarkDTO);
				entity.setPrimaryMetricScore(benchmarkDTO.getPrimaryMetric().getScore());
				entity.setPrimaryMetricScoreUnit(benchmarkDTO.getPrimaryMetric().getScoreUnit());
				// entity.setParams(benchmarkDTO.getParams());
				// entity.setPrimaryMetricRawData(benchmarkDTO.getPrimaryMetric().getRawData());

				// Save params as a string in db
				if (benchmarkDTO.getParams() != null)
					entity.setParam(mapToString(benchmarkDTO.getParams()));

				Benchmark savedEntity = benchmarkRepository.save(entity);
				lastRecordId = savedEntity.getId();

				// Save Benchmark raw data values in text file
				String filePath = writeDataToFile("RawData-" + lastRecordId,
						benchmarkDTO.getPrimaryMetric().getRawData().toString());

				entity.setRawDataPath(filePath);
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

	private static String writeDataToFile(String fileName, String data) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(data);

			File file = new File(fileName);
			String absoluteFilePath = file.getAbsolutePath();

			return absoluteFilePath;
		} catch (IOException e) {
			System.err.println("Error writing data to file: " + e.getMessage());
			return "File not created";
		}
	}

	public static String mapToString(Map<String, String> map) {
		StringBuilder stringBuilder = new StringBuilder();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
		}

		// Remove the trailing comma and space
		if (stringBuilder.length() > 0) {
			stringBuilder.setLength(stringBuilder.length() - 2);
		}

		return stringBuilder.toString();
	}
}
