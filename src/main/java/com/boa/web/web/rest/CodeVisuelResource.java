package com.boa.web.web.rest;

import com.boa.web.domain.CodeVisuel;
import com.boa.web.service.CodeVisuelService;
import com.boa.web.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boa.web.domain.CodeVisuel}.
 */
@RestController
@RequestMapping("/api")
public class CodeVisuelResource {

    private final Logger log = LoggerFactory.getLogger(CodeVisuelResource.class);

    private static final String ENTITY_NAME = "codeVisuel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodeVisuelService codeVisuelService;

    public CodeVisuelResource(CodeVisuelService codeVisuelService) {
        this.codeVisuelService = codeVisuelService;
    }

    /**
     * {@code POST  /code-visuels} : Create a new codeVisuel.
     *
     * @param codeVisuel the codeVisuel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codeVisuel, or with status {@code 400 (Bad Request)} if the codeVisuel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/code-visuels")
    public ResponseEntity<CodeVisuel> createCodeVisuel(@RequestBody CodeVisuel codeVisuel) throws URISyntaxException {
        log.debug("REST request to save CodeVisuel : {}", codeVisuel);
        if (codeVisuel.getId() != null) {
            throw new BadRequestAlertException("A new codeVisuel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodeVisuel result = codeVisuelService.save(codeVisuel);
        return ResponseEntity.created(new URI("/api/code-visuels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /code-visuels} : Updates an existing codeVisuel.
     *
     * @param codeVisuel the codeVisuel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codeVisuel,
     * or with status {@code 400 (Bad Request)} if the codeVisuel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codeVisuel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/code-visuels")
    public ResponseEntity<CodeVisuel> updateCodeVisuel(@RequestBody CodeVisuel codeVisuel) throws URISyntaxException {
        log.debug("REST request to update CodeVisuel : {}", codeVisuel);
        if (codeVisuel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodeVisuel result = codeVisuelService.save(codeVisuel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codeVisuel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /code-visuels} : get all the codeVisuels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codeVisuels in body.
     */
    @GetMapping("/code-visuels")
    public List<CodeVisuel> getAllCodeVisuels() {
        log.debug("REST request to get all CodeVisuels");
        return codeVisuelService.findAll();
    }

    /**
     * {@code GET  /code-visuels/:id} : get the "id" codeVisuel.
     *
     * @param id the id of the codeVisuel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codeVisuel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/code-visuels/{id}")
    public ResponseEntity<CodeVisuel> getCodeVisuel(@PathVariable Long id) {
        log.debug("REST request to get CodeVisuel : {}", id);
        Optional<CodeVisuel> codeVisuel = codeVisuelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codeVisuel);
    }

    /**
     * {@code DELETE  /code-visuels/:id} : delete the "id" codeVisuel.
     *
     * @param id the id of the codeVisuel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/code-visuels/{id}")
    public ResponseEntity<Void> deleteCodeVisuel(@PathVariable Long id) {
        log.debug("REST request to delete CodeVisuel : {}", id);
        codeVisuelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
