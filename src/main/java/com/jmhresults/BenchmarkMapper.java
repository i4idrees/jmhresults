package com.jmhresults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.jmhresults.entities.Benchmark;
import com.jmhresults.models.BenchmarkDTO;

@Mapper
public interface BenchmarkMapper {
    BenchmarkMapper INSTANCE = Mappers.getMapper(BenchmarkMapper.class);

    
    Benchmark toEntity(BenchmarkDTO benchmarkDTO);

    
}
