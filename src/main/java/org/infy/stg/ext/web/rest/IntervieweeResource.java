package org.infy.stg.ext.web.rest;

import org.infy.stg.domain.Interviewee;
import org.infy.stg.ext.service.IntervieweeService;
import org.infy.stg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.infy.stg.domain.Interviewee}.
 */
@RestController("extIntervieweeResource")
@RequestMapping("/api/ext")

public class IntervieweeResource extends org.infy.stg.web.rest.IntervieweeResource {

    private final Logger log = LoggerFactory.getLogger(IntervieweeResource.class);

    private static final String ENTITY_NAME = "interviewDbInterviewee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntervieweeService intervieweeService;

    public IntervieweeResource(IntervieweeService intervieweeService) {
        super(intervieweeService);
        this.intervieweeService = intervieweeService;
    }


}
