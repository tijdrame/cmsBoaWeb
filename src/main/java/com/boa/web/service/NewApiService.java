package com.boa.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boa.web.domain.ParamFiliale;
import com.boa.web.domain.Tracking;
import com.boa.web.repository.ParamFilialeRepository;
import com.boa.web.request.DesactivateUserRequest;
import com.boa.web.response.DesactivateUserResponse;
import com.boa.web.service.util.ICodeDescResponse;
import com.boa.web.service.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewApiService {

    private final Logger log = LoggerFactory.getLogger(ApiService.class);

    private final ParamFilialeRepository paramFilialeRepository;
    private final TrackingService trackingService;
    private final UserService userService;
    private final ParamFilialeService paramFilialeService;
    private final Utils utils;
    // private final ParamGeneralService paramGeneralService;

    public NewApiService(ParamFilialeRepository paramFilialeRepository, ParamFilialeService paramFilialeService,
            UserService userService, TrackingService trackingService,
            Utils utils/* , ParamGeneralService paramGeneralService */) {
        this.paramFilialeRepository = paramFilialeRepository;
        this.trackingService = trackingService;
        this.userService = userService;
        this.paramFilialeService = paramFilialeService;
        this.utils = utils;
        // this.paramGeneralService = paramGeneralService;
    }

    public DesactivateUserResponse desactivateUser(DesactivateUserRequest blockRequest, HttpServletRequest request) {
        log.info("Enter in deactivateUser=== [{}]", blockRequest);

        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        DesactivateUserResponse genericResp = new DesactivateUserResponse();
        Tracking tracking = new Tracking();
        tracking.setDateRequest(Instant.now());

        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("deactivateUser");
        if (filiale == null) {
            genericResp = (DesactivateUserResponse) paramFilialeService.clientAbsent(genericResp, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResp;
        }
        try {
            String jsonStr = new JSONObject().put("digital_id", blockRequest.getDigitalId())
                    .put("pays", blockRequest.getPays()).put("param1", blockRequest.getParam1())
                    .put("param2", blockRequest.getParam2()).put("param3", blockRequest.getParam3()).toString();
            log.info("Request for deactivateUser [{}]", jsonStr);
            HttpURLConnection conn = utils.doConnexion(filiale.getEndPoint(), jsonStr, "application/json", null);
            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            String result = "";
            log.info("resp code desactivateUser envoi [{}]", conn.getResponseCode());
            if (conn != null && conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("desactivateUser result ===== [{}]", result);
                obj = new JSONObject(result);
                obj = obj.getJSONObject("cardDesactivation").getJSONObject("response");
                log.info("ob to str =[{}]", obj.toString());
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.readValue(obj.toString(), Map.class);
                // genericResp.setDataOauth(map);
                if (obj.toString() != null && !obj.isNull("response") && obj.getInt("response") == 1) {
                    genericResp.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResp.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                    genericResp.setDateResponse(Instant.now());
                    genericResp.setData(obj.getInt("response"));
                    tracking = paramFilialeService.createTracking(ICodeDescResponse.SUCCES_CODE, filiale.getEndPoint(),
                            result, tab[1]);
                } else if (obj.toString() != null && !obj.isNull("response")
                        && (obj.getInt("response") == 0 || obj.getInt("response")==2 )) {
                    genericResp.setData(obj.getInt("response"));
                    genericResp.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResp.setDateResponse(Instant.now());
                    genericResp.setDescription(
                            obj.getInt("response")==0 ? ICodeDescResponse.USER_DEJA_DESACTIVE
                                    : ICodeDescResponse.COMPTE_INEXISTANT);

                    tracking = paramFilialeService.createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(),
                            result, tab[1]);
                }
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp envoi error ===== [{}]", result);
                obj = new JSONObject(result);
                /*
                 * ObjectMapper mapper = new ObjectMapper(); Map<String, Object> map =
                 * mapper.readValue(result, Map.class);
                 */
                obj = new JSONObject(result);
                // genericResp.setData(map);
                genericResp.setCode(ICodeDescResponse.ECHEC_CODE);
                genericResp.setDateResponse(Instant.now());
                genericResp.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                tracking = paramFilialeService.createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(),
                        result, tab[1]);
            }
        } catch (Exception e) {
            log.error("Exception in oAuth [{}]", e);
            genericResp.setCode(ICodeDescResponse.ECHEC_CODE);
            genericResp.setDateResponse(Instant.now());
            // genericResp.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION + " " +
            // e.getMessage());
            genericResp.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION + e.getMessage());
            tracking = paramFilialeService.createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(),
                    e.getMessage(), tab[1]);
        }
        trackingService.save(tracking);
        return genericResp;
    }

}
