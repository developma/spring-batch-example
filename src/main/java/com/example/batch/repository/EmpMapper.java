package com.example.batch.repository;

import com.example.batch.domain.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {

    public List<Emp> findAll();
}
