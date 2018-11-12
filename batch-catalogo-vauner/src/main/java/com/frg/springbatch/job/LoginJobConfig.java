package com.frg.springbatch.job;

import com.frg.springbatch.common.LoggingLoginProcessor;
import com.frg.springbatch.common.LoggingLoginWriter;
import com.frg.springbatch.model.LoginDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * @author Fran Rivera
 */
@Configuration
public class LoginJobConfig {

    private static final String PROPERTY_REST_API_URL = "rest.api.login.url";

    @Bean
    ItemReader<LoginDTO> restStudentReader(Environment environment, RestTemplate restTemplate) {
        return new LoginReader(environment.getRequiredProperty(PROPERTY_REST_API_URL), restTemplate);
    }

    @Bean
    ItemProcessor<LoginDTO, LoginDTO> restStudentProcessor() {
        return new LoggingLoginProcessor();
    }

    @Bean
    ItemWriter<LoginDTO> restStudentWriter() {
        return new LoggingLoginWriter();
    }

    @Bean
    Step restStudentStep(ItemReader<LoginDTO> restStudentReader,
                         ItemProcessor<LoginDTO, LoginDTO> restStudentProcessor,
                         ItemWriter<LoginDTO> restStudentWriter,
                         StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("restStudentStep")
                .<LoginDTO, LoginDTO>chunk(1)
                .reader(restStudentReader)
                .processor(restStudentProcessor)
                .writer(restStudentWriter)
                .build();
    }

    @Bean
    Job restStudentJob(JobBuilderFactory jobBuilderFactory,
                       @Qualifier("restStudentStep") Step restStudentStep) {
        return jobBuilderFactory.get("restStudentJob")
                .incrementer(new RunIdIncrementer())
                .flow(restStudentStep)
                .end()
                .build();
    }
}
