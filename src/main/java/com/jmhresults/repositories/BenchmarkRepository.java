package com.jmhresults.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jmhresults.entities.Benchmark;

public interface BenchmarkRepository extends JpaRepository<Benchmark, Long> {
    // You can define custom query methods here if needed
}