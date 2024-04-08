package com.jmhresults.models;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class BenchmarkDTO {
	private String jmhVersion;
    private String benchmark;
    private String mode;
    private int threads;
    private int forks;
    private String jvm;
    private List<String> jvmArgs;
    private String jdkVersion;
    private String vmName;
    private String vmVersion;
    private int warmupIterations;
    private String warmupTime;
    private int warmupBatchSize;
    private int measurementIterations;
    private String measurementTime;
    private int measurementBatchSize;
    private Map<String, String> params;
    private PrimaryMetricDTO primaryMetric;
    private Map<String, Object> secondaryMetrics;
}



