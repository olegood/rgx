package olegood.rgx.probe.dev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DevStartup {

  @EventListener(ApplicationStartedEvent.class)
  void onStarted() {
    log.info("Started.");
  }

  @EventListener(ApplicationReadyEvent.class)
  void onReady() {
    log.info("Ready to serve.");
  }
}
