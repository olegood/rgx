package olegood.rgx.job.dev;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoadDevDataJobRunner implements CommandLineRunner {

  private final JobOperator jobOperator;
  private final Job loadDevDataJob;

  @Override
  public void run(String... args) throws Exception {
    var params = new JobParametersBuilder().addString("run.id", "dev-init").toJobParameters();
    jobOperator.start(loadDevDataJob, params);
  }
}
