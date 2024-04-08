package com.jmhresults.entities;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

 
@Setter
@Getter

@Entity
@Table(name="benchmark_results")
public class Benchmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jmhVersion;
    private String benchmark;
    private String mode;
    private int threads;
    private int forks;
    private String jdkVersion;
    private String vmName;
    private String vmVersion;
    private int warmupIterations;
    private String warmupTime;
    private int warmupBatchSize;
    private int measurementIterations;
    private String measurementTime;
    private int measurementBatchSize;
    private double primaryMetricScore;
    private String primaryMetricScoreUnit;
    
    @ElementCollection
    private List<List<Double>> primaryMetricRawData;

}

