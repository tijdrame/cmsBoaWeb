package com.boa.web.web.rest;

import com.boa.web.domain.ParamGeneral;
import com.boa.web.service.ParamGeneralService;
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
 * REST controller for managing {@link com.boa.web.domain.ParamGeneral}.
 */
@RestController
@RequestMapping("/api")
public class ParamGeneralResource {

    private final Logger log = LoggerFactory.getLogger(ParamGeneralResource.class);

    private static final String ENTITY_NAME = "paramGeneral";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamGeneralService paramGeneralService;

    public ParamGeneralResource(ParamGeneralService paramGeneralService) {
        this.paramGeneralService = paramGeneralService;
    }

    /**
     * {@code POST  /param-generals} : Create a new paramGeneral.
     *
     * @param paramGeneral the paramGeneral to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramGeneral, or with status {@code 400 (Bad Request)} if the paramGeneral has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-generals")
    public ResponseEntity<ParamGeneral> createParamGeneral(@RequestBody ParamGeneral paramGeneral) throws URISyntaxException {
        log.debug("REST request to save ParamGeneral : {}", paramGeneral);
        if (paramGeneral.getId() != null) {
            throw new BadRequestAlertException("A new paramGeneral cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamGeneral result = paramGeneralService.save(paramGeneral);
        return ResponseEntity.created(new URI("/api/param-generals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /param-generals} : Updates an existing paramGeneral.
     *
     * @param paramGeneral the paramGeneral to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramGeneral,
     * or with status {@code 400 (Bad Request)} if the paramGeneral is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramGeneral couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-generals")
    public ResponseEntity<ParamGeneral> updateParamGeneral(@RequestBody ParamGeneral paramGeneral) throws URISyntaxException {
        log.debug("REST request to update ParamGeneral : {}", paramGeneral);
        if (paramGeneral.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamGeneral result = paramGeneralService.save(paramGeneral);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paramGeneral.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /param-generals} : get all the paramGenerals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramGenerals in body.
     */
    @GetMapping("/param-generals")
    public List<ParamGeneral> getAllParamGenerals() {
        log.debug("REST request to get all ParamGenerals");
        return paramGeneralService.findAll();
    }

    /**
     * {@code GET  /param-generals/:id} : get the "id" paramGeneral.
     *
     * @param id the id of the paramGeneral to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramGeneral, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-generals/{id}")
    public ResponseEntity<ParamGeneral> getParamGeneral(@PathVariable Long id) {
        log.debug("REST request to get ParamGeneral : {}", id);
        Optional<ParamGeneral> paramGeneral = paramGeneralService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramGeneral);
    }

    /**
     * {@code DELETE  /param-generals/:id} : delete the "id" paramGeneral.
     *
     * @param id the id of the paramGeneral to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/param-generals/{id}")
    public ResponseEntity<Void> deleteParamGeneral(@PathVariable Long id) {
        log.debug("REST request to delete ParamGeneral : {}", id);
        paramGeneralService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
