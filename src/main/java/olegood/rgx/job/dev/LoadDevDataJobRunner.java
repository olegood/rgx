package olegood.rgx.job.dev;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.OrganizationRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecutionException;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev-ignore")
@RequiredArgsConstructor
@Component
public class LoadDevDataJobRunner {

  private final OrganizationRepository organizationRepository;

  private final JobOperator jobOperator;
  private final Job loadDevDataJob;

  @PostConstruct
  void postConstruct() throws JobExecutionException {
    if (organizationRepository.count() > 0) {
      return;
    }
    var params = new JobParametersBuilder().addString("run.id", "dev-init").toJobParameters();
    jobOperator.start(loadDevDataJob, params);
  }
}
