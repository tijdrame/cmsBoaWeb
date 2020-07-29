package com.boa.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import com.boa.web.domain.ParamFiliale;
import com.boa.web.domain.Tracking;
import com.boa.web.repository.ParamFilialeRepository;
import com.boa.web.request.GetInfoClientRequest;
import com.boa.web.response.GetInfoClientResponse;
import com.boa.web.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * InfoClientService
 */

@Service
@Transactional
public class InfoClientService {

    private final Logger log = LoggerFactory.getLogger(InfoClientService.class);

    private final ParamFilialeRepository paramFilialeRepository;
    private final ParamFilialeService paramFilialeService;

    public InfoClientService(ParamFilialeRepository paramFilialeRepository, ParamFilialeService paramFilialeService) {
        this.paramFilialeRepository = paramFilialeRepository;
        this.paramFilialeService = paramFilialeService;
    }

    public GetInfoClientResponse getClientInfo(GetInfoClientRequest clientRequest, HttpServletRequest request) {

        GetInfoClientResponse response = new GetInfoClientResponse();
        /*
         * response.setCode(200); response.setCompte("0512478965852");
         * response.setDateResponse(Instant.now());
         * response.setDescription("Operation Effectuee avec succes");
         * response.setIdClient("2501487"); response.setInstitutionId("10313");
         */

        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("apiIdClient");
        if (filiale == null) {
            response = (GetInfoClientResponse) paramFilialeService.clientAbsent(response, new Tracking(),
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), "");
            return response;
        }
        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";

            jsonString = new JSONObject().put("compte", clientRequest.getCompte())
                    .put("institutionId", clientRequest.getInstitutionId()).toString();

            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            if (conn != null && conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result=========================={}", result);
                obj = new JSONObject(result);

                if (obj.isNull("infoClient")) {
                    response.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    response.setDateResponse(Instant.now());
                    response.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    return response;
                } else {

                    /**
                     * La requête vient sans idClient
                     */
                    if (!obj.getJSONObject("infoClient").getJSONObject("infoClient").toString().contains("idClient")
                            || !obj.getJSONObject("infoClient").getJSONObject("infoClient").toString()
                                    .contains("compte")
                            || !obj.getJSONObject("infoClient").getJSONObject("infoClient").toString()
                                    .contains("institutionId")) {
                        response.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                        response.setDateResponse(Instant.now());
                        response.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                        return response;
                    } else {
                        JSONObject jsonArray = obj.getJSONObject("infoClient").getJSONObject("infoClient");
                        response.setCode(ICodeDescResponse.SUCCES_CODE);
                        response.setDateResponse(Instant.now());
                        response.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                        response.setIdClient((jsonArray.get("idClient").toString()));
                        response.setCompte((jsonArray.get("compte").toString()));
                        response.setInstitutionId((jsonArray.get("institutionId").toString()));
                        return response;
                    }

                }

            }else{
                response.setIdClient("");
                response.setCompte("");
                response.setInstitutionId("");
                response.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                response.setDateResponse(Instant.now());
                response.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                return response;
            }
        } catch (Exception e) {
            log.error("errorrr==[{}]", e.getMessage());
        }
        return response;
    }
}