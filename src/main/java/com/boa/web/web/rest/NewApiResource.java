package com.boa.web.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.boa.web.request.DesactivateUserRequest;
import com.boa.web.response.DesactivateUserResponse;
import com.boa.web.service.NewApiService;
import com.boa.web.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api(description = "CmsServices", tags = "BOA Card Management System.")
public class NewApiResource {

    private final Logger log = LoggerFactory.getLogger(NewApiResource.class);

    private final NewApiService newApiService;

    public NewApiResource(NewApiService newApiService) {
        this.newApiService = newApiService;
    }

    @PostMapping("/deactivateUser")
    public ResponseEntity<DesactivateUserResponse> desactivateUser(@RequestBody DesactivateUserRequest blockRequest, HttpServletRequest request)
            throws URISyntaxException {
        log.debug("REST request to deactivateUser : {}", blockRequest);
        DesactivateUserResponse blockResponse = new DesactivateUserResponse();
        if (controleParam(blockRequest.getDigitalId()) /*|| controleParam(blockRequest.getInstitutionId())*/) {
            blockResponse.setCode(ICodeDescResponse.PARAM_ABSENT_CODE);
            blockResponse.setDateResponse(Instant.now());
            blockResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
            return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization")).body(blockResponse);
        }
        blockResponse = newApiService.desactivateUser(blockRequest, request);
        return ResponseEntity.ok().header("Authorization", request.getHeader("Authorization"))
        .body(blockResponse);
    }

    public Boolean controleParam(Object param) {
        Boolean flag = false;
        if (StringUtils.isEmpty(param))
            flag = true;
        return flag;
    }
    
}
