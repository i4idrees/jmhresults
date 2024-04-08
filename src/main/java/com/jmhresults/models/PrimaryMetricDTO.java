package com.jmhresults.models;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class PrimaryMetricDTO {
    private double score;
    private String scoreError;
    private Map<String, Double> scorePercentiles;
    private String scoreUnit;
    private List<List<Double>> rawData;
    private List<String> scoreConfidence;
    private Map<String, String> secondaryMetrics;
}