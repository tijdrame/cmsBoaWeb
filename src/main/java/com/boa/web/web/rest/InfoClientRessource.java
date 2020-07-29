
package com.boa.web.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.boa.web.request.GetInfoClientRequest;
import com.boa.web.response.GetInfoClientResponse;
import com.boa.web.service.InfoClientService;
import com.boa.web.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * InfoClientRessource
 */

@RestController
@RequestMapping("/api")
@Api(description = "CmsServices", tags="BOA Card Management System.")
public class InfoClientRessource {

    private final Logger log = LoggerFactory.getLogger(InfoClientRessource.class);

    private final InfoClientService infoClientService;
    private final ParamFilialeResource paramfilialeResource;

    public InfoClientRessource(InfoClientService infoClientService,
            ParamFilialeResource paramfilialeResource) {
        this.infoClientService = infoClientService;
        this.paramfilialeResource = paramfilialeResource;
    }


    @PostMapping("/getInfoClient") 
    public ResponseEntity<GetInfoClientResponse> getInfoClient(@RequestBody GetInfoClientRequest clientRequest, HttpServletRequest request)
            throws URISyntaxException {
        log.debug("REST request to getInfoClient : {}", clientRequest);
        GetInfoClientResponse clientResponse = new GetInfoClientResponse();
        if (paramfilialeResource.controleParam(clientRequest.getCompte()) || paramfilialeResource.controleParam(clientRequest.getInstitutionId())) {
            clientResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            clientResponse.setDateResponse(Instant.now());
            clientResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(clientResponse);
        }
        clientResponse = infoClientService.getClientInfo(clientRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(clientResponse);
    }


}