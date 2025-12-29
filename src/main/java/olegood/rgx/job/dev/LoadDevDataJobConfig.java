package olegood.rgx.job.dev;

import lombok.extern.slf4j.Slf4j;
import olegood.rgx.domain.Organization;
import olegood.rgx.domain.OrganizationRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class LoadDevDataJobConfig {

  @Bean
  public Job loadDevDataJob(JobRepository jobRepository, Step loadOrganizations) {
    return new JobBuilder(jobRepository).start(loadOrganizations).build();
  }

  @Bean
  public Step loadOrganizations(
      JobRepository jobRepository,
      PlatformTransactionManager txManager,
      FlatFileItemReader<Organization> organizationDataItemReader,
      ItemWriter<Organization> organizationDataItemWriter) {
    return new StepBuilder(jobRepository)
        .<Organization, Organization>chunk(20)
        .transactionManager(txManager)
        .reader(organizationDataItemReader)
        .writer(organizationDataItemWriter)
        .build();
  }

  @Bean
  public FlatFileItemReader<Organization> organizationDataItemReader() {
    return new FlatFileItemReaderBuilder<Organization>()
        .name("organizationDataItemReader")
        .resource(new ClassPathResource("data/organizations-100.csv"))
        .delimited()
        .names(
            "Id",
            "Code",
            "Name",
            "Website",
            "Country",
            "Description",
            "Founded",
            "Industry",
            "Number of employees",
            "Status")
        .linesToSkip(1)
        .targetType(Organization.class)
        .build();
  }

  @Bean
  public ItemWriter<Organization> organizationDataItemWriter(OrganizationRepository repository) {
    return repository::saveAll;
  }
}
