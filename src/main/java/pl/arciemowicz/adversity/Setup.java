package pl.arciemowicz.adversity;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.arciemowicz.adversity.domain.AnalyticsData;

@EnableBatchProcessing
@Configuration
public class Setup {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MongoTemplate mongoTemplate;

    public Setup(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public Job readCSVFile() {
        return jobBuilderFactory.get("readCSVFile").incrementer(new RunIdIncrementer()).start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<AnalyticsData, AnalyticsData>chunk(10).reader(reader())
                .writer(writer()).build();
    }

    @Bean
    public FlatFileItemReader<AnalyticsData> reader() {
        FlatFileItemReader<AnalyticsData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<AnalyticsData>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"datasource", "campaign", "date", "clicks", "impressions"});

            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<AnalyticsData>() {{
                setTargetType(AnalyticsData.class);
            }});
        }});
        return reader;
    }

    @Bean
    public MongoItemWriter<AnalyticsData> writer() {
        MongoItemWriter<AnalyticsData> writer = new MongoItemWriter<AnalyticsData>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("analyticsData");
        return writer;
    }
}
