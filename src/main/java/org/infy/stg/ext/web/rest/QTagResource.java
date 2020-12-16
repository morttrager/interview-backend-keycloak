package org.infy.stg.ext.web.rest;

import org.infy.stg.domain.QTag;
import org.infy.stg.ext.service.QTagService;
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
 * REST controller for managing {@link org.infy.stg.domain.QTag}.
 */
@RestController("extQTagResource")
@RequestMapping("/api/ext")
public class QTagResource extends org.infy.stg.web.rest.QTagResource{

    private final Logger log = LoggerFactory.getLogger(QTagResource.class);

    private static final String ENTITY_NAME = "interviewDbQTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QTagService qTagService;

    public QTagResource(QTagService qTagService) {
        super(qTagService);
        this.qTagService = qTagService;
    }

}
