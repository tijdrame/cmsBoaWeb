package com.boa.web.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.boa.web.domain.ParamFiliale;
import com.boa.web.domain.ParamGeneral;
import com.boa.web.domain.Tracking;
import com.boa.web.domain.User;
import com.boa.web.repository.ParamFilialeRepository;
import com.boa.web.request.CardsDetailRequest;
import com.boa.web.request.ExecuteBankActivateCardRequest;
import com.boa.web.request.ExecuteCardToOwnCardTransferRequest;
import com.boa.web.request.GetCardBankActivationParametersRequest;
import com.boa.web.request.GetPrepaidDechargementRequest;
import com.boa.web.request.IdClientRequest;
import com.boa.web.request.PrepareCardToOwnCardTransferRequest;
import com.boa.web.response.Annulation;
import com.boa.web.response.Client;
import com.boa.web.response.ExecuteBankActivateCardResponse;
import com.boa.web.response.ExecuteCardToOwnCardTransferResponse;
import com.boa.web.response.GetCardBankActivationParametersResponse;
import com.boa.web.response.GetCardsDetailResponse;
import com.boa.web.response.GetPrepaidDechargementResponse;
import com.boa.web.response.IdClientResponse;
import com.boa.web.response.PrepareCardToOwnCardTransferResponse;
import com.boa.web.response.prepareChangeCardOption.HiddenInput;
import com.boa.web.response.prepareChangeCardOption.Information;
import com.boa.web.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ApiService
 */
@Service
@Transactional
public class ApiService {

    private final Logger log = LoggerFactory.getLogger(ApiService.class);

    private final ParamFilialeRepository paramFilialeRepository;
    private final TrackingService trackingService;
    private final UserService userService;
    private final ParamFilialeService paramFilialeService;
    private final ParamGeneralService paramGeneralService;

    public ApiService(ParamFilialeRepository paramFilialeRepository, ParamFilialeService paramFilialeService,
            UserService userService, TrackingService trackingService, ParamGeneralService paramGeneralService) {
        this.paramFilialeRepository = paramFilialeRepository;
        this.trackingService = trackingService;
        this.userService = userService;
        this.paramFilialeService = paramFilialeService;
        this.paramGeneralService = paramGeneralService;
    }

    /*
     * ################ MEL21022020 Fin:ExecuteBankActivateCard#####################
     */

    /*---------MEL21022020 :GetCardBankActivationParametersProxy--------------*/
    public GetCardBankActivationParametersResponse GetCardBankActivationParameters(
            GetCardBankActivationParametersRequest getCardBankActivationParametersRequest, HttpServletRequest request) {

        log.info("trace0 :05022020");
        Optional<User> user = userService.getUserWithAuthorities();
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        GetCardBankActivationParametersResponse genericResponse = new GetCardBankActivationParametersResponse();
        Client client = new Client();
        log.info("trace1:05022020");
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getCardBankActivationParametersProxy");
        if (filiale == null) {
            genericResponse = (GetCardBankActivationParametersResponse) paramFilialeService.clientAbsent(
                    genericResponse, tracking, request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = this.paramFilialeService.callApiIdClientByIdCard(
                    getCardBankActivationParametersRequest.getCartIdentif(),
                    getCardBankActivationParametersRequest.getInstitutionId());

            if (client == null) {
                genericResponse = (GetCardBankActivationParametersResponse) paramFilialeService.clientAbsent(
                        genericResponse, tracking, request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);
                return genericResponse;
            }

            log.info("ELM1");

        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }

        log.info("ELM2");

        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";

            jsonString = new JSONObject().put("idClient", client.getIdClient())
                    .put("langue", getCardBankActivationParametersRequest.getLangue())
                    .put("pays", getCardBankActivationParametersRequest.getPays())
                    .put("variant", getCardBankActivationParametersRequest.getVariant())
                    .put("cartIdentif", getCardBankActivationParametersRequest.getCartIdentif())
                    .put("action", getCardBankActivationParametersRequest.getAction())
                    .put("entKey", getCardBankActivationParametersRequest.getEntKey())
                    .put("entValue", getCardBankActivationParametersRequest.getEntValue()).toString();

            log.info("jsonString 11022020 : {}", jsonString);

            tracking.setRequestTr(jsonString);
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";
            os.write(postDataBytes);
            os.flush();
            BufferedReader br = null;
            JSONObject obj = new JSONObject();

            if (conn.getResponseCode() == 500) {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = br.readLine();
                // System.out.println("err=" + result);
                log.info("err == [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("Envelope").getJSONObject("Body").toString().contains("Fault")) {

                    // genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                    // .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setBusinessfault(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultstring"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    genericResponse.setDateResponse(Instant.now());
                    // genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                    // .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                    // .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    // genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
                    // .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                    // .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    // TODO 11022020
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(user.isPresent() ? user.get().getLogin() : "");
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    trackingService.save(tracking);
                    return genericResponse;
                }
            }

            if (conn != null && conn.getResponseCode() > 0) {
                log.info("ELM4");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result == [{}]", result);
                obj = new JSONObject(result);

                log.info("MEL18022020 [{}]", result);

                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("prepare-change-card-option-response")) {

                    // TODO 11022020

                    log.info("Here ELM14022020");
                    genericResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(user.isPresent() ? user.get().getLogin() : "");
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    return genericResponse;

                }

                // JSONObject myObj1 = obj.getJSONObject("Envelope").getJSONObject("Body")
                // .getJSONObject("Fault");
                // Todo

                // genericResponse.setFaultcode(myObj.getString("faultcode"));
                // genericResponse.setFaultstring(myObj.getString("faultstring"));

                if (getCardBankActivationParametersRequest.getEntValue().contains("VALID")) {
                    JSONObject myObjA = obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("prepare-change-card-option-response").getJSONObject("string-input");

                    Information information = new Information();
                    information.setIdentifier(myObjA.getString("identifier"));
                    information.setStereotype(myObjA.getString("stereotype"));
                    information.setLabel(myObjA.getString("label"));
                    information.setDescription(myObjA.getString("description"));
                    information.setPlaceholder(myObjA.getString("placeholder"));

                    if (obj.toString().contains("hidden-input")) {
                        JSONObject myObjB = obj.getJSONObject("Envelope").getJSONObject("Body")
                                .getJSONObject("prepare-change-card-option-response").getJSONObject("hidden-input");

                        HiddenInput hiddenInput = new HiddenInput();
                        hiddenInput.setIdentifier(myObjB.getString("identifier"));
                        hiddenInput.setValue(myObjB.getString("value"));
                        genericResponse.setHiddenInput(hiddenInput);
                    }

                    genericResponse.getStringInput().add(information);

                }

                if (getCardBankActivationParametersRequest.getEntValue().contains("TEMPORARY_BLOCKED_BY_USER")) {

                    JSONArray jsonArray = obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("prepare-change-card-option-response").getJSONArray("string-input");
                    log.info("1MEL18022020");

                    if (obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                            .contains("prepare-change-card-option-response")) {
                        log.info(" Log1 test 18022020");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject myObj1 = jsonArray.getJSONObject(i);
                            Information information = new Information();
                            information.setIdentifier(myObj1.getString("identifier"));
                            information.setStereotype(myObj1.getString("stereotype"));
                            information.setLabel(myObj1.getString("label"));
                            information.setDescription(myObj1.getString("description"));
                            information.setPlaceholder(myObj1.getString("placeholder"));

                            log.info("Identifier == [{}]", myObj1.getString("identifier"));
                            genericResponse.getStringInput().add(information);
                        }
                        if (obj.toString().contains("hidden-input")) {
                            JSONObject myObjB = obj.getJSONObject("Envelope").getJSONObject("Body")
                                    .getJSONObject("prepare-change-card-option-response").getJSONObject("hidden-input");

                            HiddenInput hiddenInput = new HiddenInput();
                            hiddenInput.setIdentifier(myObjB.getString("identifier"));
                            hiddenInput.setValue(myObjB.getString("value"));
                            genericResponse.setHiddenInput(hiddenInput);
                        }

                    }

                }
                log.info(" Log2 test 18022020");
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");
                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(user.isPresent() ? user.get().getLogin() : "");
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
        }

        catch (IOException | JSONException e1) {
            log.error(" error = [{}]", e1.getMessage());
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now())
                    .loginActeur(user.isPresent() ? user.get().getLogin() : "");
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e1.getMessage());
        }

        trackingService.save(tracking);

        return genericResponse;
    }

    /*
     * ################################ Fin GetCardBankActivationParametersProxy
     * ################################
     */

    /*
     * ################################ Fin GetCardBankActivationParametersProxy
     * ################################
     */

    /*---------MEL04022020:GetPrepaidDechargementResponse--------------*/
    public GetPrepaidDechargementResponse GetPrepaidDechargement(GetPrepaidDechargementRequest GetPrepaidDechargement,
            HttpServletRequest request) {
        Tracking tracking = new Tracking();
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        log.info("trace0 :05022020");
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        GetPrepaidDechargementResponse getPrepaidDechargementResponse = new GetPrepaidDechargementResponse();
        log.info("trace1:05022020");
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("dechargementCarteProxy_V2");
        if (filiale == null) {
            getPrepaidDechargementResponse = (GetPrepaidDechargementResponse) paramFilialeService.clientAbsent(
                    getPrepaidDechargementResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return getPrepaidDechargementResponse;
        }

        Optional<ParamGeneral> optionalPM = paramGeneralService.findByCodeAndPays(ICodeDescResponse.COMPTE_DAP, GetPrepaidDechargement.getPays());
        if(!optionalPM.isPresent()){
            getPrepaidDechargementResponse = (GetPrepaidDechargementResponse) paramFilialeService.clientAbsent(getPrepaidDechargementResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.COMPTE_DAP_ABSENT,
                    request.getRequestURI(), tab[1]);
            return getPrepaidDechargementResponse;
        }

        log.info("trace2:05022020");

        try {

            CardsDetailRequest cardsDetailRequest = new CardsDetailRequest();

            /*--------MEL : Remplissage Requete Detail des la carte------------*/
            cardsDetailRequest.setCompte(GetPrepaidDechargement.getCompteCardSource());
            cardsDetailRequest.setCartIdentif(GetPrepaidDechargement.getCartIdentifSource());
            // cardsDetailRequest.setCompte(GetPrepaidDechargement.getCompte());
            cardsDetailRequest.setInstitutionId(GetPrepaidDechargement.getInstitutionId());
            cardsDetailRequest.setLangue(GetPrepaidDechargement.getLangue());
            cardsDetailRequest.setPays(GetPrepaidDechargement.getPays());
            cardsDetailRequest.setVariant(GetPrepaidDechargement.getVariant());

            log.info("trace3:05022020");

            GetCardsDetailResponse cardsDetailResponse = paramFilialeService.getCardDetails(cardsDetailRequest,
                    request);

            if (cardsDetailResponse == null) {
                getPrepaidDechargementResponse = (GetPrepaidDechargementResponse) paramFilialeService.clientAbsent(
                        getPrepaidDechargementResponse, tracking, request.getRequestURI(),
                        ICodeDescResponse.CLIENT_ABSENT_CODE, ICodeDescResponse.CLIENT_ABSENT_DESC,
                        request.getRequestURI(), tab[1]);

                getPrepaidDechargementResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
                getPrepaidDechargementResponse.setDateResponse(Instant.now());
                getPrepaidDechargementResponse.setDescription(ICodeDescResponse.PARAM_ABSENT_DESC);
                tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(request.getRequestURI());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                // tracking.setResponseTr(result);
                tracking.setRequestTr(request.getRequestURI());
                System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);
                trackingService.save(tracking);
                log.info("MEL3");
                return getPrepaidDechargementResponse;
            }

            log.info("trace4:05022020");

            /*---- Transformation JSON to XML----*/
            // epayChargementRequestXml=
            try {
                URL url = new URL(filiale.getEndPoint());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setRequestProperty("Accept", "application/xml");

                String result = "";

                log.info("trace6:05022020");

                log.info("trace7:05022020");

                /*
                 * JSONObject jsonString1 = new JSONObject().put("codopsc", "GAB")
                 * .put("comptec", GetPrepaidDechargement.getCompteTarget()).put("compted",
                 * null) .put("country", GetPrepaidDechargement.getPays()) .put("dateT",
                 * GetPrepaidDechargement.getDateTrans()) .put("datexpir",
                 * cardsDetailResponse.getCard().getExpiryDate()) .put("devise",
                 * cardsDetailResponse.getCard().getCurrency()).put("dispo", "DISPONIBLE")
                 * .put("libelle", GetPrepaidDechargement.getLibelle()) .put("mntdev",
                 * GetPrepaidDechargement.getMontant()) .put("mntfrais",
                 * GetPrepaidDechargement.getMontantFrais()) .put("numcarte",
                 * cardsDetailResponse.getCard().getNumber()) .put("refrel",
                 * GetPrepaidDechargement.getRefRel()).put("val", "V");
                 * 
                 * log.info("jsonString  Value{}", jsonString1.toString());
                 */

                StringBuilder builder = new StringBuilder();
                builder.append("<body><dechargement>");
                builder.append("<codopsc>" + "GAB" + "</codopsc>");
                builder.append("<comptec>" + GetPrepaidDechargement.getCompteTarget() + "</comptec>");
                builder.append("<compted>" + GetPrepaidDechargement.getCompteCardSource() + "</compted>");
                builder.append("<country>" + GetPrepaidDechargement.getPays() + "</country>");
                builder.append("<dateT>" + GetPrepaidDechargement.getDateTrans() + "</dateT>");
                // builder.append("<datexpir>" + cardsDetailResponse.getCard().getExpiryDate() +
                // "</datexpir>");
                String[] data = cardsDetailResponse.getCard().getExpiryDate().split("-");
                String date = data[0].substring(2) + data[1].substring(0, 2);
                log.info("data === [{}]", date);
                // carteWso2Request.setDatexpir(date);
                builder.append("<datexpir>" + date + "</datexpir>");
                // builder.append("<devise>" + cardsDetailResponse.getCard().getCurrency() +
                // "</devise>");
                builder.append("<devise>" + 952 + "</devise>");

                builder.append("<dispo>" + "DISPONIBLE" + "</dispo>");
                builder.append("<libelle>" + GetPrepaidDechargement.getLibelle() + "</libelle>");
                builder.append("<mntdev>" + GetPrepaidDechargement.getMontant() + "</mntdev>");
                builder.append("<mntfrais>" + GetPrepaidDechargement.getMontantFrais() + "</mntfrais>");
                builder.append("<numcarte>" + cardsDetailResponse.getCard().getNumberCard() + "</numcarte>");
                builder.append("<refrel>" + GetPrepaidDechargement.getRefRel() + "</refrel>");
                builder.append("<reftrans>" + "nil" + "</reftrans>");
                builder.append("<val>" + "V" + "</val>");
                builder.append("<compteDap>" + optionalPM.get().getVarString1() + "</compteDap>");
                builder.append("</dechargement></body>");

                tracking.setRequestTr(builder.toString());
                log.info("carteWso2Request [{}]", builder.toString());
                OutputStream os = conn.getOutputStream();
                byte[] postDataBytes = builder.toString().getBytes();
                result = "";

                os.write(postDataBytes);
                os.flush();

                BufferedReader br = null;

                /*----------------------------*/

                log.info("trace9:05022020");
                // BufferedReader br1 = null;
                log.info("response code [{}]", conn.getResponseCode());
                JSONObject obj = new JSONObject();
                if (conn.getResponseCode() == 500) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    result = br.readLine();
                    obj = new JSONObject(result);
                    log.info("res 500= [{}]", result);
                    getPrepaidDechargementResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    getPrepaidDechargementResponse.setDateResponse(Instant.now());
                    getPrepaidDechargementResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    getPrepaidDechargementResponse.setResultat(
                            obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("RCOD"));
                    getPrepaidDechargementResponse.setTexte(
                            obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("RMSG"));
                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");

                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    log.info("err == [{}]", result);
                    return getPrepaidDechargementResponse;

                }
                if (conn != null && conn.getResponseCode() > 0) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    result = br.readLine();
                    log.info("result dechargement =========================== [{}]", result);
                    obj = new JSONObject(result);
                    if (obj.getJSONObject("dechargementCarte").getJSONObject("response").toString()
                            .contains("annulation"))
                        log.info("in annulation ", obj.getJSONObject("dechargementCarte").getJSONObject("response")
                                .getJSONObject("annulation").toString());

                    if (obj.getJSONObject("dechargementCarte").getJSONObject("response").toString()
                            .contains("annulation")) {
                        log.info("in annulation ", obj.getJSONObject("dechargementCarte").getJSONObject("response")
                                .getJSONObject("annulation").toString());
                        getPrepaidDechargementResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                        getPrepaidDechargementResponse.setDateResponse(Instant.now());
                        getPrepaidDechargementResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                        // if
                        // (!obj.getJSONObject("dechargementCarte").getJSONObject("response").getJSONObject("annulation")
                        // .toString().contains(null)) {
                        Annulation annulation = new Annulation();
                        annulation.setRcod(obj.getJSONObject("dechargementCarte").getJSONObject("response")
                                .getJSONObject("annulation").getString("RCOD"));
                        annulation.setRmsg(obj.getJSONObject("dechargementCarte").getJSONObject("response")
                                .getJSONObject("annulation").getString("RMSG"));
                        getPrepaidDechargementResponse.setAnnulation(annulation);
                        // }
                        getPrepaidDechargementResponse.setReference(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("NOOPER"));
                        getPrepaidDechargementResponse.setResultat(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("CCOD"));
                        getPrepaidDechargementResponse.setTexte(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("CMSG"));
                        tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");

                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(filiale.getEndPoint());
                        tracking.setLoginActeur(login);
                        tracking.setDateRequest(Instant.now());
                        tracking.setResponseTr(result);
                        tracking.setTokenTr(tab[1]);
                        trackingService.save(tracking);
                        return getPrepaidDechargementResponse;
                    }

                    if (obj.getJSONObject("dechargementCarte").getJSONObject("response").toString().contains("00")) {
                        log.info("res sucess 00= [{}]", result);

                        getPrepaidDechargementResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                        getPrepaidDechargementResponse.setDateResponse(Instant.now());
                        getPrepaidDechargementResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                        getPrepaidDechargementResponse.setReference("");
                        getPrepaidDechargementResponse.setResultat(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("RCOD"));
                        getPrepaidDechargementResponse.setTexte(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("RMSG"));
                        tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(filiale.getEndPoint());
                        tracking.setLoginActeur(login);
                        tracking.setDateRequest(Instant.now());
                        tracking.setResponseTr(result);
                        tracking.setTokenTr(tab[1]);
                    } else {
                        log.info("res echec diff 00= [{}]", result);

                        getPrepaidDechargementResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                        getPrepaidDechargementResponse.setDateResponse(Instant.now());
                        getPrepaidDechargementResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                        getPrepaidDechargementResponse.setReference("");
                        getPrepaidDechargementResponse.setResultat(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("RCOD"));
                        getPrepaidDechargementResponse.setTexte(
                                obj.getJSONObject("dechargementCarte").getJSONObject("response").getString("RMSG"));
                        tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");

                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(filiale.getEndPoint());
                        tracking.setLoginActeur(login);
                        tracking.setDateRequest(Instant.now());
                        tracking.setResponseTr(result);
                        tracking.setTokenTr(tab[1]);
                    }
                    os.close();

                }
            } catch (Exception e) {
                tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
                tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
                tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
                tracking.dateResponse(Instant.now());
                getPrepaidDechargementResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
                getPrepaidDechargementResponse.setDateResponse(Instant.now());
                getPrepaidDechargementResponse
                        .setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e.getStackTrace());
                log.error("errorrr== [{}]", e);

            }

        } catch (Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            getPrepaidDechargementResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            getPrepaidDechargementResponse.setDateResponse(Instant.now());
            getPrepaidDechargementResponse
                    .setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e.getMessage());
            log.error("errorrr==", e.getMessage());

        }
        trackingService.save(tracking);
        return getPrepaidDechargementResponse;

    }

    /*---------MEL10022020 Debut :prepareCardToOwnCardTransfer--------------*/
    public PrepareCardToOwnCardTransferResponse PrepareCardToOwnCardTransfer(
            PrepareCardToOwnCardTransferRequest prepareCardToOwnCardTransferRequest, HttpServletRequest request) {
        Tracking tracking = new Tracking();
        PrepareCardToOwnCardTransferResponse genericResponse = new PrepareCardToOwnCardTransferResponse();
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("prepareCardToOwnCardTransfer");
        if (filiale == null) {
            genericResponse = (PrepareCardToOwnCardTransferResponse) paramFilialeService.clientAbsent(genericResponse,
                    tracking, request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }

        Client client = new Client();

        log.info("Compte  == [{}]", prepareCardToOwnCardTransferRequest.getCompte());
        log.info("InstitutionID  == [{}]", prepareCardToOwnCardTransferRequest.getInstitutionId());

        try {
            client = paramFilialeService.callApiIdClientByIdCard(prepareCardToOwnCardTransferRequest.getSourceCardId(),
                    prepareCardToOwnCardTransferRequest.getInstitutionId());
            log.info("client  == [{}]", client);
            if (client == null) {
                genericResponse = (PrepareCardToOwnCardTransferResponse) paramFilialeService.clientAbsent(
                        genericResponse, tracking, request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);
                return genericResponse;
            }

        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        prepareCardToOwnCardTransferRequest.setClidentifier(client.getIdClient());

        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            try {
                jsonString = new JSONObject().put("opidentifier", prepareCardToOwnCardTransferRequest.getidoperation())
                        .put("clidentifier", prepareCardToOwnCardTransferRequest.getClidentifier())
                        .put("langage", prepareCardToOwnCardTransferRequest.getlangue())
                        .put("country", prepareCardToOwnCardTransferRequest.getPays())
                        .put("variant", prepareCardToOwnCardTransferRequest.getVariant())
                        .put("srcaccnumb", prepareCardToOwnCardTransferRequest.getSourceCardId())
                        .put("tcardclident", prepareCardToOwnCardTransferRequest.getreceiverCardId())
                        .put("amount", prepareCardToOwnCardTransferRequest.getMontant())
                        .put("currency", prepareCardToOwnCardTransferRequest.getCurrency())
                        .put("entkey", prepareCardToOwnCardTransferRequest.getEntkey())
                        .put("etval", prepareCardToOwnCardTransferRequest.getEntval()).toString();

            } catch (JSONException e2) {
                // TODO Auto-generated catch block
                log.info("error = [{}]", e2.getMessage());
            }

            tracking.setRequestTr(jsonString);
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();

            if (conn.getResponseCode() == 500) {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = br.readLine();
                log.info("err == [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("Fault").getJSONObject("detail")
                        .getJSONObject("business-fault").toString().contains("rejected-operation-exception")) {

                    // genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                    // .getJSONObject("Fault").getString("faultcode"));
                    // genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                    // .getJSONObject("Fault").getString("faultstring"));
                    // genericResponse.setCode(conn.getResponseCode());
                    // genericResponse.setDateResponse(Instant.now());
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                            .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    genericResponse.setFaultstring(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                            .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    genericResponse.setFaultcode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    // TODO 11022020
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    trackingService.save(tracking);
                    return genericResponse;
                }
            }

            if (conn != null && conn.getResponseCode() > 0) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                // System.out.println("result==" + result);
                log.info("result == [{}]", result);
                try {
                    obj = new JSONObject(result);

                    if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                            .contains("prepare-card-to-own-card-transfer-response")) {

                        log.info("Mouhcine Log Here");

                        // TODO 11022020
                        genericResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                        genericResponse.setDateResponse(Instant.now());
                        genericResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                        tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");

                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(filiale.getEndPoint());
                        tracking.setLoginActeur(login);

                        tracking.setResponseTr(result);
                        tracking.setTokenTr(tab[1]);
                        trackingService.save(tracking);
                        return genericResponse;

                    }

                    // CardDetails cardDetails = new CardDetails();

                    log.info("Mouhcine1 Log Here");

                    JSONObject myObj;

                    // Test des elements 28/08/2020
                    if (!obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("prepare-card-to-own-card-transfer-response").isNull("operation-info")) {// test
                                                                                                                    // operation-info

                        if (obj.getJSONObject("Envelope").getJSONObject("Body")
                                .getJSONObject("prepare-card-to-own-card-transfer-response")
                                .getJSONObject("operation-info").getJSONObject("fee") != null) {// test sur l'objet fees

                            myObj = obj.getJSONObject("Envelope").getJSONObject("Body")
                                    .getJSONObject("prepare-card-to-own-card-transfer-response")
                                    .getJSONObject("operation-info").getJSONObject("fee");

                            genericResponse.getFee().setAmount(Double.parseDouble(myObj.getString("amount")));
                            genericResponse.getFee().setCurrency(myObj.getString("currency"));

                        } else {// fin test objet fee
                            genericResponse.getFee().setAmount(0.0);
                            genericResponse.getFee().setCurrency("");
                        }

                    } else {// fin test operation-info
                        genericResponse.getFee().setAmount(0.0);
                        genericResponse.getFee().setCurrency("");
                    }

                    // genericResponse.setAmount(myObj.getString("amount"));
                    // genericResponse.setCurrency(myObj.getString("currency"));
                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    // System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);

                } catch (JSONException e) {
                    log.error(" error = [{}]", e.getMessage());
                    tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
                    tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
                    tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
                    tracking.dateResponse(Instant.now());
                    genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse
                            .setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e.getMessage());
                }

            }
        }

        catch (IOException | JSONException e1) {
            log.error(" error = [{}]", e1.getMessage());
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e1.getMessage());
        }

        trackingService.save(tracking);
        return genericResponse;
    }

    /*
     * ######################################### -MEL10022020 Fin
     * :prepareCardToOwnCardTransfer################################################
     * #####
     */

    /*
     * ######################################## -MEL10022020 Debut
     * :executeCardToOwnCardTransfer################################################
     * #####
     */
    public ExecuteCardToOwnCardTransferResponse ExecuteCardToOwnCardTransfer(
            ExecuteCardToOwnCardTransferRequest executeCardToOwnCardTransferRequest, HttpServletRequest request) {
        Tracking tracking = new Tracking();
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ExecuteCardToOwnCardTransferResponse genericResponse = new ExecuteCardToOwnCardTransferResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("executeCardToOwnCardTransfert");
        if (filiale == null) {
            genericResponse = (ExecuteCardToOwnCardTransferResponse) paramFilialeService.clientAbsent(genericResponse,
                    tracking, request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        Client client = new Client();
        try {
            client = paramFilialeService.callApiIdClientByIdCard(executeCardToOwnCardTransferRequest.getSourceCardId(),
                    executeCardToOwnCardTransferRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (ExecuteCardToOwnCardTransferResponse) paramFilialeService.clientAbsent(
                        genericResponse, tracking, request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);
                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String jsonString = "";
            jsonString = new JSONObject().put("opidentifier", executeCardToOwnCardTransferRequest.getidoperation())
                    .put("clidentifier", client.getIdClient())
                    .put("langage", executeCardToOwnCardTransferRequest.getlangue())
                    .put("srcaccnumb", executeCardToOwnCardTransferRequest.getsourceCardId())
                    .put("tcardclident", executeCardToOwnCardTransferRequest.getreceiverCardId())
                    .put("amount", executeCardToOwnCardTransferRequest.getMontant())
                    .put("currency", executeCardToOwnCardTransferRequest.getCurrency()).toString();

            log.info("jsonString 11022020 : {}", jsonString);
            tracking.setRequestTr(jsonString);
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";
            os.write(postDataBytes);
            os.flush();
            BufferedReader br = null;
            JSONObject obj = new JSONObject();

            if (conn.getResponseCode() == 500) {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = br.readLine();
                log.info("err == [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("Fault").getJSONObject("detail")
                        .getJSONObject("business-fault").toString().contains("rejected-operation-exception")) {
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                            .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                            .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    trackingService.save(tracking);
                    return genericResponse;
                }
            }

            if (conn != null && conn.getResponseCode() > 0) {
                log.info("ELM4");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result == [{}]", result);
                obj = new JSONObject(result);
                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("execute-card-to-own-card-transfer-response")) {
                    genericResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    return genericResponse;

                }
                if (obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("execute-card-to-own-card-transfer-response")) {
                    log.info("Here ELM");
                    if (!obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("execute-card-to-own-card-transfer-response").isNull("operation-info")) {// test
                                                                                                                    // operation-info
                        JSONObject myObj = obj.getJSONObject("Envelope").getJSONObject("Body")
                                .getJSONObject("execute-card-to-own-card-transfer-response")
                                .getJSONObject("operation-info");
                        if (myObj.getJSONObject("type") != null) {
                            genericResponse.getType()
                                    .setDescription(myObj.getJSONObject("type").getString("description"));
                            genericResponse.getType()
                                    .setIdentifier(myObj.getJSONObject("type").getString("identifier"));
                        } else {
                            genericResponse.getType().setDescription("");
                            genericResponse.getType().setIdentifier("");
                        }
                        if (myObj.getString("identifier") != null) {
                            genericResponse.setIdentifier(myObj.getString("identifier"));
                        } else {
                            genericResponse.setIdentifier("");
                        }
                        if (myObj.getJSONObject("amount") != null) {
                            genericResponse.getAmount()
                                    .setAmount(Double.parseDouble(myObj.getJSONObject("amount").getString("amount")));
                            genericResponse.getAmount()
                                    .setCurrency(myObj.getJSONObject("amount").getString("currency"));
                        } else {
                            genericResponse.getAmount().setAmount(0.0);
                            genericResponse.getAmount().setCurrency("");
                        }
                        genericResponse.setIshold(myObj.getString("is-hold"));
                    }
                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);

                    paramFilialeService.invalidateCache(client.getIdClient());

                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);

                } else {
                    genericResponse.getType().setDescription("");
                    genericResponse.getType().setIdentifier("");
                    genericResponse.setIdentifier("");
                    genericResponse.getAmount().setAmount(0.0);
                    genericResponse.getAmount().setCurrency("");
                    genericResponse.setIshold("");
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                }

            }
        }

        catch (IOException | JSONException e1) {
            log.error(" error = [{}]", e1.getMessage());
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e1.getMessage());
        }

        trackingService.save(tracking);

        return genericResponse;
    }

    /*---------MEL10022020 Fin :executeCardToOwnCardTransfer --------------*/

    /*---------------------------*/

    public IdClientResponse IdClient(IdClientRequest IdClient, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        log.info("trace0");
        IdClientResponse idClientResponse = new IdClientResponse();
        log.info("trace1");
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("apiIdClient");
        Tracking tracking = new Tracking();
        log.info("trace2");
        // construire json

        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        Client client = new Client();

        // test sur enregistrement API Proxy
        if (filiale == null) {
            // return null;

            /*- TODO--*/

            idClientResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            idClientResponse.setDateResponse(Instant.now());
            idClientResponse.setDescription(ICodeDescResponse.PARAM_ABSENT_DESC);
            tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
            tracking.setDateResponse(Instant.now());
            tracking.setEndPointTr(request.getRequestURI());
            tracking.setLoginActeur(login);
            tracking.setDateRequest(Instant.now());
            // tracking.setResponseTr(result);
            tracking.setRequestTr(request.getRequestURI());
            System.out.println("tab 1=" + tab[1]);
            tracking.setTokenTr(tab[1]);
            trackingService.save(tracking);
            log.info("MEL3");
            return idClientResponse;

        }

        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";

            log.info("MEL1");
            jsonString = new JSONObject().put("compte", IdClient.getCompte())
                    .put("institutionId", IdClient.getInstitutionId()).toString();

            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            if (conn != null && conn.getResponseCode() > 0) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                System.out.println("result===========================" + result);
                obj = new JSONObject(result);

                // log.info("MEL21 {}", obj.getJSONObject("infoClient").toString()
                // .contains("idClient"));

                if (obj.isNull("infoClient")) {
                    idClientResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    idClientResponse.setDateResponse(Instant.now());
                    idClientResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(request.getRequestURI());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setRequestTr(request.getRequestURI());
                    System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    log.info("MEL3");
                    return idClientResponse;
                }

                // Si un champ est absent
                // obj.getJSONObject("infoClient").getJSONObject("infoClient").getJSONObject("idClient")

                if (!obj.getJSONObject("infoClient").getJSONObject("infoClient").toString().contains("idClient"))

                {
                    idClientResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    idClientResponse.setDateResponse(Instant.now());
                    idClientResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(request.getRequestURI());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setRequestTr(request.getRequestURI());
                    System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    log.info("MEL3");
                    return idClientResponse;
                }

                if (!obj.getJSONObject("infoClient").getJSONObject("infoClient").toString().contains("compte"))

                {
                    idClientResponse.setCode(ICodeDescResponse.COMPTE_ABSENT_CODE);
                    idClientResponse.setDateResponse(Instant.now());
                    idClientResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
                    tracking.setCodeResponse(ICodeDescResponse.COMPTE_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(request.getRequestURI());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setRequestTr(request.getRequestURI());
                    System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    log.info("MEL3");
                    return idClientResponse;
                }

                if (!obj.getJSONObject("infoClient").getJSONObject("infoClient").toString().contains("institutionId"))

                {
                    idClientResponse.setCode(ICodeDescResponse.INSTITUTION_ABSENT_CODE);
                    idClientResponse.setDateResponse(Instant.now());
                    idClientResponse.setDescription(ICodeDescResponse.PARAM_DESCRIPTION);
                    tracking.setCodeResponse(ICodeDescResponse.INSTITUTION_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(request.getRequestURI());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setRequestTr(request.getRequestURI());
                    System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    log.info("MEL3");
                    return idClientResponse;
                }

                /*-------------------------------------------*/
                if (obj.isNull("infoClient"))

                {
                    idClientResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    idClientResponse.setDateResponse(Instant.now());
                    idClientResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(request.getRequestURI());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setRequestTr(request.getRequestURI());
                    System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    log.info("MEL3");
                    return idClientResponse;
                }
                JSONObject jsonArray = obj.getJSONObject("infoClient").getJSONObject("infoClient");
                idClientResponse.setIdClient((jsonArray.get("idClient").toString()));
                idClientResponse.setCompte((jsonArray.get("compte").toString()));
                idClientResponse.setInstitutionId((jsonArray.get("institutionId").toString()));
                idClientResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                idClientResponse.setDateResponse(Instant.now());
                idClientResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");
                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);
                tracking.setRequestTr(request.getRequestURI());
            }
        } catch (Exception e) {
            log.error("errorrr==[{}]", e.getMessage());
        }
        trackingService.save(tracking);
        idClientResponse.setCode(ICodeDescResponse.SUCCES_CODE);
        idClientResponse.setDateResponse(Instant.now());
        idClientResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);

        return idClientResponse;

    }

    /*---------  Debut :  MEL21022020:ExecuteBankActivateCard--------------*/
    /**
     * @param executeBankActivateCardRequest
     * @param request
     * @return
     */
    public ExecuteBankActivateCardResponse ExecuteBankActivateCard(
            ExecuteBankActivateCardRequest executeBankActivateCardRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        log.info("trace0 :MEL21022020");
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        ExecuteBankActivateCardResponse genericResponse = new ExecuteBankActivateCardResponse();
        Client client = new Client();
        log.info("trace1:MEL21022020");
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("executeBankActivateCard");
        if (filiale == null) {
            genericResponse = (ExecuteBankActivateCardResponse) paramFilialeService.clientAbsent(genericResponse,
                    tracking, request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = paramFilialeService.callApiIdClientByIdCard(executeBankActivateCardRequest.getCartIdentif(),
                    executeBankActivateCardRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (ExecuteBankActivateCardResponse) paramFilialeService.clientAbsent(genericResponse,
                        tracking, request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);
                return genericResponse;
            }
            log.info("trace4 :MEL21022020");
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        log.info("trace5 :MEL21022020");
        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient())
                    .put("langue", executeBankActivateCardRequest.getLangue())
                    .put("cartIdentif", executeBankActivateCardRequest.getCartIdentif())
                    .put("operationIdentif", executeBankActivateCardRequest.getIdoperation()).toString();
            log.info("jsonString MEL21022020 : {}", jsonString);
            tracking.setRequestTr(jsonString);
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";
            os.write(postDataBytes);
            os.flush();
            BufferedReader br = null;
            JSONObject obj = new JSONObject();

            if (conn.getResponseCode() == 500) {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = br.readLine();
                log.info("err == [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("Envelope").getJSONObject("Body").toString().contains("Fault")) {
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setFaultstring(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    tracking.setDateRequest(Instant.now());
                    trackingService.save(tracking);
                    return genericResponse;
                }
            }

            if (conn != null && conn.getResponseCode() > 0) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result == [{}]", result);
                obj = new JSONObject(result);
                if (obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("complete-change-card-option-response")) {
                    genericResponse.setAction(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("complete-change-card-option-response").getString("action"));
                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);

                    paramFilialeService.invalidateCache(client.getIdClient());

                    tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setDateRequest(Instant.now());
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);

                } else {
                    log.info("Here MEL21022020");
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");
                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);
                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    return genericResponse;
                }

            }
        }

        catch (IOException | JSONException e1) {
            log.error(" error = [{}]", e1.getMessage());
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC + " Message=" + e1.getMessage());
        }

        trackingService.save(tracking);

        return genericResponse;
    }

    /*
     * ################ MEL21022020 Fin:ExecuteBankActivateCard#####################
     */

}