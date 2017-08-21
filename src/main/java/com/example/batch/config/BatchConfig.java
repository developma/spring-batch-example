package com.example.batch.config;

import com.example.batch.chunk.BonusCalcProcessor;
import com.example.batch.domain.Bonus;
import com.example.batch.domain.Emp;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory,
                       SqlSessionFactory sqlSessionFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Bean
    public MyBatisCursorItemReader empReader() {
        final MyBatisCursorItemReader<Emp> reader = new MyBatisCursorItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.example.batch.repository.EmpMapper.findAll");
        return reader;
    }

    @Bean
    public MyBatisCursorItemReader bonusReader() {
        final MyBatisCursorItemReader<Bonus> reader = new MyBatisCursorItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.example.batch.repository.BonusMapper.findAll");
        return reader;
    }

    @Bean
    public BonusCalcProcessor processor() {
        return new BonusCalcProcessor();
    }

    @Bean
    public MyBatisBatchItemWriter<Bonus> writer() {
        final MyBatisBatchItemWriter<Bonus> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("insert");
        return writer;
    }

    @Bean
    public FlatFileItemWriter<Bonus> fileWriter() {
        final FlatFileItemWriter<Bonus> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("out/bonus.txt"));
        writer.setHeaderCallback(headerWriter -> headerWriter.append("EMP_ID")
                .append(",")
                .append("PAYMENTS"));
        writer.setLineAggregator(item -> new StringBuilder()
                .append(item.getEmpId())
                .append(",")
                .append(item.getPayments())
                .toString());
        return writer;
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                System.out.println("before job");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                System.out.println("after job");
            }
        };
    }

    @Bean
    public Step readEmpAndWriteBonus() {
        return stepBuilderFactory
                .get("readEmpAndWriteBonus")
                .<Emp, Bonus>chunk(10)
                .reader(empReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step readBonusAndWriteFile() {
        return stepBuilderFactory
                .get("readBonusAndWriteFile")
                .<Bonus, Bonus>chunk(10)
                .reader(bonusReader())
                .writer(fileWriter())
                .build();
    }

    @Bean
    public Job singleJob() {
        return jobBuilderFactory
                .get("singleJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(readEmpAndWriteBonus())
                .end()
                .build();
    }

    @Bean
    public Job multiJob() {
        return jobBuilderFactory
                .get("multiJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(readEmpAndWriteBonus())
                .next(readBonusAndWriteFile())
                .end()
                .build();
    }
}
