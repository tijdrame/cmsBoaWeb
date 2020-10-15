package com.boa.web.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.boa.web.domain.Tracking;
import com.boa.web.domain.User;
import com.boa.web.request.ExecuteBankActivateCardRequest;
import com.boa.web.request.ExecuteCardToOwnCardTransferRequest;
import com.boa.web.request.GetCardBankActivationParametersRequest;
import com.boa.web.request.GetPrepaidDechargementRequest;
import com.boa.web.request.IdClientRequest;
import com.boa.web.request.PrepareCardToOwnCardTransferRequest;
import com.boa.web.response.ExecuteBankActivateCardResponse;
import com.boa.web.response.ExecuteCardToOwnCardTransferResponse;
import com.boa.web.response.GetCardBankActivationParametersResponse;
import com.boa.web.response.GetPrepaidDechargementResponse;
import com.boa.web.response.IdClientResponse;
import com.boa.web.response.PrepareCardToOwnCardTransferResponse;
import com.boa.web.service.ApiService;
import com.boa.web.service.TrackingService;
import com.boa.web.service.UserService;
import com.boa.web.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * ApiResource
 */
@RestController
@RequestMapping("/api")
@Api(description = "CmsServices", tags = "BOA Card Management System.")
public class ApiResource {

        private final Logger log = LoggerFactory.getLogger(ApiResource.class);

        @Value("${jhipster.clientApp.name}")
        private String applicationName;

        private final ApiService apiService;
        private final TrackingService trackingService;
        private final UserService userService;

        public ApiResource(ApiService apiService, TrackingService trackingService, UserService userService) {
                this.apiService = apiService;
                this.trackingService = trackingService;
                this.userService = userService;
        }

        /*
         * ############################ Debut MEL 13022020 :
         * getCardBankActivationParameters#############################3
         */

        /*@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE }, path = "/CardBankActivationParameters")*/
        @PostMapping("/CardBankActivationParameters")
        public ResponseEntity<GetCardBankActivationParametersResponse> GetCardBankActivationParameters(
                        @RequestBody GetCardBankActivationParametersRequest getCardBankActivationParametersRequest,
                        HttpServletRequest request) throws URISyntaxException {
                log.debug("REST request to getCardBankActivationParameters : {}",
                                getCardBankActivationParametersRequest);

                GetCardBankActivationParametersResponse getCardBankActivationParametersResponse = apiService
                                .GetCardBankActivationParameters(getCardBankActivationParametersRequest, request);
                log.info("Appel fin ");

                return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                                .body(getCardBankActivationParametersResponse);

        }

        /*
         * ############################ Fin MEL 13022020 :
         * getCardBankActivationParameters#############################3
         */

        /*-----MEL04022020 :   getPrepaidDechargement --------*/

        //@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
          //              MediaType.APPLICATION_JSON_VALUE }, path = "/dechargement")
        @PostMapping("/dechargement")
        public ResponseEntity<GetPrepaidDechargementResponse> GetPrepaidDechargement(
                        @RequestBody GetPrepaidDechargementRequest getPrepaidDechargement, HttpServletRequest request)
                        throws URISyntaxException {
                log.info("REST request to getPrepaidDechargement : {}", getPrepaidDechargement);

                GetPrepaidDechargementResponse getPrepaidDechargementtResponse = apiService
                                .GetPrepaidDechargement(getPrepaidDechargement, request);
                log.info("Appel fin ");

                return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                                .body(getPrepaidDechargementtResponse);

        }

        /*---------------*/
        /*-----MEL10022020 :   prepareCardToOwnCardTransfer --------*/

        /*@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE }, path = "/prepareCardToOwnCardTransfer")*/
        @PostMapping("/prepareCardToOwnCardTransfer")
        public ResponseEntity<PrepareCardToOwnCardTransferResponse> PrepareCardToOwnCardTransfer(
                        @RequestBody PrepareCardToOwnCardTransferRequest prepareCardToOwnCardTransfer,
                        HttpServletRequest request) throws URISyntaxException {
                log.debug("REST request to PrepareCardToOwnCardTransferRequest : {}", prepareCardToOwnCardTransfer);

                PrepareCardToOwnCardTransferResponse prepareCardToOwnCardTransferResponse = apiService
                                .PrepareCardToOwnCardTransfer(prepareCardToOwnCardTransfer, request);
                log.info("Appel fin ");

                return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                                .body(prepareCardToOwnCardTransferResponse);

        }

        /*---------------*/

        /*-----MEL10022020 :   executeCardToOwnCardTransfer --------*/

        //@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
          //              MediaType.APPLICATION_JSON_VALUE }, path = "/executeCardToOwnCardTransfer")
        @PostMapping("/executeCardToOwnCardTransfer")
        public ResponseEntity<ExecuteCardToOwnCardTransferResponse> ExecuteCardToOwnCardTransfer(
                        @RequestBody ExecuteCardToOwnCardTransferRequest executeCardToOwnCardTransfer,
                        HttpServletRequest request) throws URISyntaxException {
                log.debug("REST request to ExecuteCardToOwnCardTransfer : {}", executeCardToOwnCardTransfer);

                ExecuteCardToOwnCardTransferResponse executeCardToOwnCardTransferResponse = apiService
                                .ExecuteCardToOwnCardTransfer(executeCardToOwnCardTransfer, request);
                log.info("Appel fin ");

                return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                                .body(executeCardToOwnCardTransferResponse);

        }

        /*---------------*/
        /*---------------*/

        /*@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE }, path = "/IdClient")*/
        @PostMapping("/IdClient")
        public ResponseEntity<IdClientResponse> IdClient(@RequestBody IdClientRequest idClient,
                        HttpServletRequest request) throws URISyntaxException {
                log.debug("REST request to IdClient : {}", idClient);
                Optional<User> user = userService.getUserWithAuthorities();
                String login = user.isPresent()?user.get().getLogin():"";
                log.info("Appel Debut ");
                String autho = request.getHeader("Authorization");
                String[] tab = autho.split("Bearer");
                IdClientResponse idClientResponse = new IdClientResponse();
                Tracking tracking = new Tracking();

                if (idClient.getCompte() == null || (idClient.getCompte().isEmpty())
                                || (idClient.getCompte().equals(""))) {
                        log.info("MEL150 ");

                        /*--- Response json  --- */

                        idClientResponse.setCode(ICodeDescResponse.COMPTE_ABSENT_CODE);
                        idClientResponse.setDateResponse(Instant.now());
                        idClientResponse.setDescription(ICodeDescResponse.COMPTE_ABSENT_DESC);
                        tracking.setCodeResponse(ICodeDescResponse.COMPTE_ABSENT_CODE + "");
                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(request.getRequestURI());
                        tracking.setLoginActeur(login);
                        tracking.setDateRequest(Instant.now());
                        // tracking.setResponseTr(result);
                        tracking.setRequestTr(request.getRequestURI());
                        System.out.println("tab 1=" + tab[1]);
                        tracking.setTokenTr(tab[1]);

                        log.info("MEL3");

                        /*-----------------------------*/

                        return ResponseEntity.ok().body(idClientResponse);

                }

                if (idClient.getInstitutionId()==null || (idClient.getInstitutionId().isEmpty())
                                || (idClient.getInstitutionId().equals(""))) {
                        log.info("MEL1600000 ");

                        /*--- Response json  --- */

                        idClientResponse.setCode(ICodeDescResponse.INSTITUTION_ABSENT_CODE);
                        idClientResponse.setDateResponse(Instant.now());
                        idClientResponse.setDescription(ICodeDescResponse.INSTITUTION_ABSENT_DESC);
                        tracking.setCodeResponse(ICodeDescResponse.INSTITUTION_ABSENT_CODE + "");
                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(request.getRequestURI());
                        tracking.setLoginActeur(user.isPresent()?user.get().getLogin():"");
                        tracking.setDateRequest(Instant.now());
                        // tracking.setResponseTr(result);
                        tracking.setRequestTr(request.getRequestURI());
                        System.out.println("tab 1=" + tab[1]);
                        tracking.setTokenTr(tab[1]);
                        // trackingService.save(tracking);
                        log.info("MEL3");

                        /*-----------------------------*/

                        idClientResponse.setCode(301);

                        return ResponseEntity.ok().body(idClientResponse);
                }

                idClientResponse = apiService.IdClient(idClient, request);

                log.info("AppelidclietRes {} ", idClientResponse);

                if (!idClientResponse.toString().isEmpty() || idClientResponse != null) {
                        log.info("idClientResponse is Not null ");
                        idClientResponse = apiService.IdClient(idClient, request);
                        return ResponseEntity.ok().body(idClientResponse);
                }

                log.info("Appel OK ");
                trackingService.save(tracking);

                return ResponseEntity.unprocessableEntity().body(idClientResponse);

        }

        /* ############################ Debut MEL17022020 : ExecuteBankActivateCard############################# */
        

        
        

        /*@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_JSON_VALUE }, path = "/ExecuteBankActivateCard")*/
        @PostMapping("/ExecuteBankActivateCard")
public ResponseEntity<ExecuteBankActivateCardResponse> ExecuteBankActivateCard(
                @RequestBody ExecuteBankActivateCardRequest executeBankActivateCardRequest,
                HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to executeBankActivateCard : {}",
                        executeBankActivateCardRequest);

        ExecuteBankActivateCardResponse executeBankActivateCardResponse = apiService
                        .ExecuteBankActivateCard(executeBankActivateCardRequest, request);
        log.info("Appel fin ");

        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
                        .body(executeBankActivateCardResponse);

}

/* ############################ Fin MEL MEL21022020 : ExecuteBankActivateCard############################# */

}