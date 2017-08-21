package com.example.batch.repository;

import com.example.batch.domain.Bonus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BonusMapper {
    public void insert(Bonus bonus);
}
