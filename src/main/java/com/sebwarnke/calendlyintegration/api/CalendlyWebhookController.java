package com.sebwarnke.calendlyintegration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebwarnke.calendlyintegration.model.CalendlyEvent;
import com.sebwarnke.calendlyintegration.services.OrchestratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/calendly")
public class CalendlyWebhookController {

    Logger log = LoggerFactory.getLogger(CalendlyWebhookController.class);

    private OrchestratorService orchestratorService;

    @Autowired
    public CalendlyWebhookController(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PostMapping("/deliver")
    public void onEventReceived(@RequestBody CalendlyEvent calendlyEvent, @RequestParam String channel) throws IOException {
        log.info("New even created: " + new ObjectMapper().writeValueAsString(calendlyEvent));
        orchestratorService.processCalendlyEvent(calendlyEvent, channel);
    }
}
