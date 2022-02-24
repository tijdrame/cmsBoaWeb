package com.boa.web.web.rest;

import com.boa.web.domain.ParamFiliale;
import com.boa.web.request.CardHistoryRequest;
import com.boa.web.request.CardlessRequest;
import com.boa.web.request.CardsDetailRequest;
import com.boa.web.request.ChangeCardAuthRestrictionRequest;
import com.boa.web.request.ChangeCardRequest;
import com.boa.web.request.ChargementCardRequest;
import com.boa.web.request.CheckBankActivateCardRequest;
import com.boa.web.request.ConsultationSoldeRequest;
import com.boa.web.request.GetCardAuthRestrictionsRequest;
import com.boa.web.request.GetCardsBisRequest;
import com.boa.web.request.GetCommissionRequest;
import com.boa.web.request.GetListCompteRequest;
import com.boa.web.request.PrepareCardToCardTransferRequest;
import com.boa.web.request.PrepareChangeCardOptionRequest;
import com.boa.web.request.PrepareToCardFirstRequest;
import com.boa.web.request.VerifSeuilRequest;
import com.boa.web.response.CardlessResponse;
import com.boa.web.response.ChangeCardAuthRestrictionResponse;
import com.boa.web.response.ChargeCardResponse;
import com.boa.web.response.CheckBankActivateCardResponse;
import com.boa.web.response.ConsultationSoldeResponse;
import com.boa.web.response.ExecuteCardToCardTransferResponse;
import com.boa.web.response.GetCardsDetailResponse;
import com.boa.web.response.GetCardsResponse;
import com.boa.web.response.GetCommissionResponse;
import com.boa.web.response.GetListCompteResponse;
import com.boa.web.response.PrepareCardToCardTransferResponse;
import com.boa.web.response.PrepareChangeCardOptionResponse;
import com.boa.web.response.VerifSeuilResponse;
import com.boa.web.response.cardhistory.GetCardHistoryResponse;
import com.boa.web.response.cardlimit.CardLimitResponse;
import com.boa.web.response.changecardlimit.ChangeCardLimitResponse;
import com.boa.web.response.getCardAuthRestrictions.GetCardAuthRestrictionsResponse;
import com.boa.web.service.ParamFilialeService;
import com.boa.web.service.util.ICodeDescResponse;
import com.boa.web.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing {@link com.boa.web.domain.ParamFiliale}.
 */
@RestController
@RequestMapping("/api")
@Api(description = "CmsServices", tags="BOA Card Management System.")
public class ParamFilialeResource {

    private final Logger log = LoggerFactory.getLogger(ParamFilialeResource.class);

    private static final String ENTITY_NAME = "paramFiliale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamFilialeService paramFilialeService;

    public ParamFilialeResource(ParamFilialeService paramFilialeService) {
        this.paramFilialeService = paramFilialeService;
    }

    /**
     * {@code POST  /param-filiales} : Create a new paramFiliale.
     *
     * @param paramFiliale the paramFiliale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new paramFiliale, or with status {@code 400 (Bad Request)}
     *         if the paramFiliale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/param-filiales")
    @ApiIgnore
    public ResponseEntity<ParamFiliale> createParamFiliale(@RequestBody ParamFiliale paramFiliale)
            throws URISyntaxException {
        log.debug("REST request to save ParamFiliale : {}", paramFiliale);
        if (paramFiliale.getId() != null) {
            throw new BadRequestAlertException("A new paramFiliale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamFiliale result = paramFilialeService.save(paramFiliale);
        return ResponseEntity
                .created(new URI("/api/param-filiales/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /param-filiales} : Updates an existing paramFiliale.
     *
     * @param paramFiliale the paramFiliale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated paramFiliale, or with status {@code 400 (Bad Request)} if
     *         the paramFiliale is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the paramFiliale couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/param-filiales")
    @ApiIgnore
    public ResponseEntity<ParamFiliale> updateParamFiliale(@RequestBody ParamFiliale paramFiliale)
            throws URISyntaxException {
        log.debug("REST request to update ParamFiliale : {}", paramFiliale);
        if (paramFiliale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamFiliale result = paramFilialeService.save(paramFiliale);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
                paramFiliale.getId().toString())).body(result);
    }

    /**
     * {@code GET  /param-filiales} : get all the paramFiliales.
     *
     * 
     * @param pageable the pagination information.
     * 
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of paramFiliales in body.
     */
    @GetMapping("/param-filiales")
    @ApiIgnore
    public ResponseEntity<List<ParamFiliale>> getAllParamFiliales(Pageable pageable) {
        log.debug("REST request to get a page of ParamFiliales");
        Page<ParamFiliale> page = paramFilialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /param-filiales/:id} : get the "id" paramFiliale.
     *
     * @param id the id of the paramFiliale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the paramFiliale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/param-filiales/{id}")
    @ApiIgnore
    public ResponseEntity<ParamFiliale> getParamFiliale(@PathVariable Long id) {
        log.debug("REST request to get ParamFiliale : {}", id);
        Optional<ParamFiliale> paramFiliale = paramFilialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramFiliale);
    }

    /**
     * {@code DELETE  /param-filiales/:id} : delete the "id" paramFiliale.
     *
     * @param id the id of the paramFiliale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    /*@DeleteMapping("/param-filiales/{id}")
    @ApiIgnore
    public ResponseEntity<Void> deleteParamFiliale(@PathVariable Long id) {
        log.debug("REST request to delete ParamFiliale : {}", id);
        paramFilialeService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }*/

    @PostMapping("/getCards")
    public ResponseEntity<GetCardsResponse> getCards(@RequestBody GetCardsBisRequest cardsRequest, HttpServletRequest request)
            throws URISyntaxException {
        log.debug("REST request to getCards : {}", cardsRequest);
        GetCardsResponse cardsResponse = new GetCardsResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            cardsResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            cardsResponse.setDateResponse(Instant.now());
            cardsResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(cardsResponse);
        }
        cardsResponse = paramFilialeService.getCardsBis(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
        //.cacheControl(doCache())
        .body(cardsResponse);
    }

    @PostMapping("/getCardsByDigitalId") 
    public ResponseEntity<GetCardsResponse> getCardsByDigitalId(@RequestBody com.boa.web.request.GetCardsByDigitalIdRequest cardsRequest, HttpServletRequest request)
            throws URISyntaxException {
        log.debug("REST request to getCards : {}", cardsRequest);
        GetCardsResponse cardsResponse = new GetCardsResponse();
        if (controleParam(cardsRequest.getDigitalId()) || controleParam(cardsRequest.getLangue())) {
            cardsResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            cardsResponse.setDateResponse(Instant.now());
            cardsResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(cardsResponse);
        }
        cardsResponse = paramFilialeService.getCardsByDigitalId(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(cardsResponse);
    }

    @PostMapping("/prepareChangeCardOption")
    public ResponseEntity<PrepareChangeCardOptionResponse> prepareChangeCardOptionProxy(
            @RequestBody PrepareChangeCardOptionRequest cardsRequest, HttpServletRequest request)
            throws URISyntaxException {
        log.debug("REST request to prepareChangeCardOption : {}", cardsRequest);
        PrepareChangeCardOptionResponse changeCardOptionResponse = new PrepareChangeCardOptionResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            changeCardOptionResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            changeCardOptionResponse.setDateResponse(Instant.now());
            changeCardOptionResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(changeCardOptionResponse);
        }
        changeCardOptionResponse = paramFilialeService.prepareChangeCardOptionProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(changeCardOptionResponse);
    }

    @PostMapping("/getCardDetails")
    public ResponseEntity<GetCardsDetailResponse> getCardDetails(@RequestBody CardsDetailRequest cardsRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getCardDetails : {}", cardsRequest);
        GetCardsDetailResponse cardsDetailResponse = new GetCardsDetailResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            cardsDetailResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            cardsDetailResponse.setDateResponse(Instant.now());
            cardsDetailResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(cardsDetailResponse);
        }
        cardsDetailResponse = paramFilialeService.getCardDetails(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(cardsDetailResponse);
    }

    @PostMapping("/getCardLimit")
    public ResponseEntity<CardLimitResponse> getCardLimitProxy(@RequestBody CardsDetailRequest cardsRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to  getCardLimitProxy : {}", cardsRequest);
        CardLimitResponse cardsLimitResponse = new CardLimitResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            cardsLimitResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            cardsLimitResponse.setDateResponse(Instant.now());
            cardsLimitResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(cardsLimitResponse);
        }
        cardsLimitResponse = paramFilialeService.getCardLimitProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(cardsLimitResponse);
    }

    @PostMapping("/getCardHistory")
    public ResponseEntity<GetCardHistoryResponse> getCardHistoryProxy(@RequestBody CardHistoryRequest cardsRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to save getCardHistory : {}", cardsRequest);
        GetCardHistoryResponse cardHistoryResponse = new GetCardHistoryResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            cardHistoryResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            cardHistoryResponse.setDateResponse(Instant.now());
            cardHistoryResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(cardHistoryResponse);
        }
        cardHistoryResponse = paramFilialeService.getCardHistoryProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(cardHistoryResponse);
    }

    @PostMapping("/changeCardLimit")
    public ResponseEntity<ChangeCardLimitResponse> changeCardLimitProxy(@RequestBody ChangeCardRequest cardsRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to changeCardLimit : {}", cardsRequest);
        ChangeCardLimitResponse changeCardLimitResponse = new ChangeCardLimitResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            changeCardLimitResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            changeCardLimitResponse.setDateResponse(Instant.now());
            changeCardLimitResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(changeCardLimitResponse);
        }
        changeCardLimitResponse = paramFilialeService.changeCardLimitProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(changeCardLimitResponse);
    }

    @PostMapping("/chargementCarte")
    public ResponseEntity<ChargeCardResponse> chargementCarteProxy(@RequestBody ChargementCardRequest cardsRequest,
            HttpServletRequest request) throws URISyntaxException {
        log.debug("pays :======= {}", cardsRequest.getPays());
        log.debug("REST request to chargementCarte :======= {}", cardsRequest);
        ChargeCardResponse chargeCardResponse = new ChargeCardResponse();
        if (controleParam(cardsRequest.getCompteSource()) || controleParam(cardsRequest.getInstitutionId())
                || controleParam(cardsRequest.getMontant())
                || controleParam(cardsRequest.getCartIdentifTarget())) {
            log.info("param ko======");
            chargeCardResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            chargeCardResponse.setDateResponse(Instant.now());
            chargeCardResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(chargeCardResponse);
        }
        chargeCardResponse = paramFilialeService.chargeCardResponse(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(chargeCardResponse);
    }
    /*public CacheControl doCache() {
        return CacheControl.maxAge(10, TimeUnit.MINUTES); 
    }*/

    @PostMapping("/prepareCardToCardTransfer")
    public ResponseEntity<PrepareCardToCardTransferResponse> prepareCardToCardTransfer(
            @RequestBody PrepareToCardFirstRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to prepareCardToCardTransfer :======= {}", cardsRequest);
        PrepareCardToCardTransferResponse prepareCardToCardTransferResponse = new PrepareCardToCardTransferResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            log.info("param ko======");
            prepareCardToCardTransferResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            prepareCardToCardTransferResponse.setDateResponse(Instant.now());
            prepareCardToCardTransferResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(prepareCardToCardTransferResponse);
        }
        PrepareCardToCardTransferRequest prepareCardToCardTransferRequest = new PrepareCardToCardTransferRequest();
        prepareCardToCardTransferRequest.opidentifier(cardsRequest.getIdoperation()).compte(cardsRequest.getCompte())
                .institutionId(cardsRequest.getInstitutionId()).langage(cardsRequest.getLangue())
                .country(cardsRequest.getPays()).variant(cardsRequest.getVariant())
                .cardclidentifier(cardsRequest.getSourceCardidClient()).tcardnumb(cardsRequest.getReceiverCardNumber())
                .rname(cardsRequest.getReceiverName()).amount(cardsRequest.getMontant())
                .currency(cardsRequest.getCurrency()).entkey(cardsRequest.getEntkey()).entval(cardsRequest.getEntval());
        prepareCardToCardTransferResponse = paramFilialeService
                .prepareCardToCardTransfer(prepareCardToCardTransferRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(prepareCardToCardTransferResponse);
    }

    @PostMapping("/executeCardToCardTransfer")
    public ResponseEntity<ExecuteCardToCardTransferResponse> executeCardToCardTransfer(
            @RequestBody PrepareToCardFirstRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to prepareCardToCardTransfer :======= {}", cardsRequest);
        ExecuteCardToCardTransferResponse prepareCardToCardTransferResponse = new ExecuteCardToCardTransferResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            log.info("param ko======");
            prepareCardToCardTransferResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            prepareCardToCardTransferResponse.setDateResponse(Instant.now());
            prepareCardToCardTransferResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(prepareCardToCardTransferResponse);
        }
        PrepareCardToCardTransferRequest prepareCardToCardTransferRequest = new PrepareCardToCardTransferRequest();
        prepareCardToCardTransferRequest.opidentifier(cardsRequest.getIdoperation()).compte(cardsRequest.getCompte())
                .institutionId(cardsRequest.getInstitutionId()).langage(cardsRequest.getLangue())
                .country(cardsRequest.getPays()).variant(cardsRequest.getVariant())
                .cardclidentifier(cardsRequest.getCardIdClient()).tcardnumb(cardsRequest.getReceiverCardNumber())
                .rname(cardsRequest.getReceiverName()).amount(cardsRequest.getMontant())
                .currency(cardsRequest.getCurrency()).entkey(cardsRequest.getEntkey()).entval(cardsRequest.getEntval());
        prepareCardToCardTransferResponse = paramFilialeService.executeCardToCardTransfer(prepareCardToCardTransferRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(prepareCardToCardTransferResponse);
    }

    @PostMapping("/checkBankActivateCard")
    public ResponseEntity<CheckBankActivateCardResponse> checkBankActivateCard(
            @RequestBody CheckBankActivateCardRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to checkBankActivateCard :======= [{}]", cardsRequest);
        CheckBankActivateCardResponse checkBankActivateCardResponse = new CheckBankActivateCardResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            log.info("param ko======");
            checkBankActivateCardResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            checkBankActivateCardResponse.setDateResponse(Instant.now());
            checkBankActivateCardResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(checkBankActivateCardResponse);
        }
        
        checkBankActivateCardResponse = paramFilialeService.checkBankActivateCardProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(checkBankActivateCardResponse);
    }

    @PostMapping("/getCardAuthRestrictions")
    public ResponseEntity<GetCardAuthRestrictionsResponse> getCardAuthRestrictionsProxy(
            @RequestBody GetCardAuthRestrictionsRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to getCardAuthRestrictionsProxy :======= [{}]", cardsRequest);
        GetCardAuthRestrictionsResponse cardAuthRestrictionsResponse = new GetCardAuthRestrictionsResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            log.info("param ko======");
            cardAuthRestrictionsResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            cardAuthRestrictionsResponse.setDateResponse(Instant.now());
            cardAuthRestrictionsResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(cardAuthRestrictionsResponse);
        }
        
        cardAuthRestrictionsResponse = paramFilialeService.getCardAuthRestrictionsProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(cardAuthRestrictionsResponse);
    }

    @PostMapping("/changeCardAuthRestriction")
    public ResponseEntity<ChangeCardAuthRestrictionResponse> changeCardAuthRestrictionProxy(
            @RequestBody ChangeCardAuthRestrictionRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to changeCardAuthRestrictionProxy :======= [{}]", cardsRequest);
        ChangeCardAuthRestrictionResponse changeCardAuthRestrictionResponse = new ChangeCardAuthRestrictionResponse();
        if (controleParam(cardsRequest.getCompte()) || controleParam(cardsRequest.getInstitutionId())) {
            log.info("param ko======");
            changeCardAuthRestrictionResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            changeCardAuthRestrictionResponse.setDateResponse(Instant.now());
            changeCardAuthRestrictionResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(changeCardAuthRestrictionResponse);
        }
        
        changeCardAuthRestrictionResponse = paramFilialeService.changeCardAuthRestrictionProxy(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(changeCardAuthRestrictionResponse);
    }

    @PostMapping("/cardlessRemittance")
    public ResponseEntity<CardlessResponse> cardlessChargement(
            @RequestBody CardlessRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to changeCardAuthRestrictionProxy :======= [{}]", cardsRequest);
        CardlessResponse response = new CardlessResponse();
        if (controleParam(cardsRequest.getCurrency()) || controleParam(cardsRequest.getDestCellPhone())||
        controleParam(cardsRequest.getInstitutionId()) || controleParam(cardsRequest.getSenderAccountNumber())||
        (cardsRequest.getWithdrawalDueDate().equals(null)) || cardsRequest.getAmount().equals(null)) {
            log.info("param ko======");
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(response);
        }
        
        response = paramFilialeService.cardlessChargement(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(response);
    }

    /*@PostMapping("/cardlessRemittanceByCardNumber")
    public ResponseEntity<CardlessRemittanceByCardNumberResponse> cardlessRemittanceByCardNumber(
            @RequestBody CardlessRemittanceByCardNumberRequest cardsRequest, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to cardlessRemittanceByCardNumber :======= [{}]", cardsRequest);
        CardlessRemittanceByCardNumberResponse response = new CardlessRemittanceByCardNumberResponse();
        if (controleParam(cardsRequest.getCurrency()) || controleParam(cardsRequest.getDestCellPhone())||
        controleParam(cardsRequest.getInstitutionId()) || controleParam(cardsRequest.getSenderCardNumber())||
        (cardsRequest.getWithdrawalDueDate().equals(null)) || cardsRequest.getAmount().equals(null)) {
            log.info("param ko======");
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(response);
        }
        
        response = paramFilialeService.cardlessRemittanceByCardNumber(cardsRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(response);
    }*/

    @PostMapping("/consultationSolde")
    public ResponseEntity<ConsultationSoldeResponse> consultationSolde(
            @RequestBody ConsultationSoldeRequest soldeRequest, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to consultationSolde :======= [{}]", soldeRequest);
        ConsultationSoldeResponse response = new ConsultationSoldeResponse();
        if (controleParam(soldeRequest.getCartIdentif()) || controleParam(soldeRequest.getCompte())||
        controleParam(soldeRequest.getLangue()) || controleParam(soldeRequest.getPays())
        ) {
            log.info("param ko======");
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(response);
        }
        
        response = paramFilialeService.consultationSolde(soldeRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(response);
    }

    @PostMapping("/verifSeuil")
    public ResponseEntity<VerifSeuilResponse> verifSeuil(
            @RequestBody VerifSeuilRequest vRequest, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to verifSeuil :======= [{}]", vRequest);
        VerifSeuilResponse response = new VerifSeuilResponse();
        if (controleParam(vRequest.getCodeOperation()) || controleParam(vRequest.getCompte())||
        controleParam(vRequest.getLangue()) || controleParam(vRequest.getMontant())|| controleParam(vRequest.getCountry())
        ) {
            log.info("param ko======");
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(response);
        }
        
        response = paramFilialeService.verifSeuil(vRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(response);
    }

    @PostMapping("/getCommission")
    public ResponseEntity<GetCommissionResponse> getCommission(
            @RequestBody GetCommissionRequest cRequest, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to getCommission :======= [{}]", cRequest);
        GetCommissionResponse response = new GetCommissionResponse();
        if (controleParam(cRequest.getCodeOperation()) || 
        controleParam(cRequest.getLangue()) || controleParam(cRequest.getMontant())
        || controleParam(cRequest.getCompte())|| controleParam(cRequest.getCountry())
        ) {
            log.info("param ko======");
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(response);
        }
        
        response = paramFilialeService.getCommission(cRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(response);
    }

    @PostMapping("/getListComptes")
    public ResponseEntity<GetListCompteResponse> getListComptes(
            @RequestBody GetListCompteRequest cRequest, HttpServletRequest request) throws URISyntaxException {
        log.info("REST request to getCommission :======= [{}]", cRequest);
        GetListCompteResponse response = new GetListCompteResponse();
        if (controleParam(cRequest.getComptes()) || 
        controleParam(cRequest.getCountry())
        ) {
            log.info("param ko======");
            response.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            response.setDateResponse(Instant.now());
            response.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                    .body(response);
        }
        
        response = paramFilialeService.getListComptes(cRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                .body(response);
    }
    
    public Boolean controleParam(Object param) {
        Boolean flag = false;
        if (StringUtils.isEmpty(param))
            flag = true;
        return flag;
    }
}
