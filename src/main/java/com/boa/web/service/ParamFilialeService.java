package com.boa.web.service;

//import springfox.documentation.swagger2.mappers.ModelMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;

import com.boa.web.domain.ParamFiliale;
import com.boa.web.domain.Tracking;
import com.boa.web.domain.User;
import com.boa.web.repository.ParamFilialeRepository;
import com.boa.web.request.CardHistoryRequest;
import com.boa.web.request.CardlessRequest;
import com.boa.web.request.CardsDetailRequest;
import com.boa.web.request.CardsRequest;
import com.boa.web.request.ChangeCardAuthRestrictionRequest;
import com.boa.web.request.ChangeCardRequest;
import com.boa.web.request.ChargementCardRequest;
import com.boa.web.request.ChargementCarteWso2Request;
import com.boa.web.request.CheckBankActivateCardRequest;
import com.boa.web.request.GetCardAuthRestrictionsRequest;
import com.boa.web.request.PrepareCardToCardTransferRequest;
import com.boa.web.request.PrepareChangeCardOptionRequest;
import com.boa.web.response.CardLessError;
import com.boa.web.response.CardlessResponse;
import com.boa.web.response.ChangeCardAuthRestrictionResponse;
import com.boa.web.response.ChargeCardResponse;
import com.boa.web.response.CheckBankActivateCardResponse;
import com.boa.web.response.Client;
import com.boa.web.response.ExecuteCardToCardTransferResponse;
import com.boa.web.response.GenericResponse;
import com.boa.web.response.GetCardsDetailResponse;
import com.boa.web.response.GetCardsResponse;
import com.boa.web.response.PrepareCardToCardTransferResponse;
import com.boa.web.response.PrepareChangeCardOptionResponse;
import com.boa.web.response.Reply;
import com.boa.web.response.cardhistory.Address;
import com.boa.web.response.cardhistory.Amount;
import com.boa.web.response.cardhistory.Country;
import com.boa.web.response.cardhistory.GetCardHistoryResponse;
import com.boa.web.response.cardhistory.Operation;
import com.boa.web.response.cardhistory.ResultingBalance;
import com.boa.web.response.cardhistory.State;
import com.boa.web.response.cardlimit.CardLimitResponse;
import com.boa.web.response.cardlimit.CardLimit_;
import com.boa.web.response.cardsrequest.Card;
import com.boa.web.response.cardsrequest.CardDetails;
import com.boa.web.response.cardsrequest.OutputStringField;
import com.boa.web.response.cardsrequest.Status;
import com.boa.web.response.cardsrequest.Type;
import com.boa.web.response.changecardlimit.ChangeCardLimitResponse;
import com.boa.web.response.getCardAuthRestrictions.GetCardAuthRestrictionsResponse;
import com.boa.web.response.getCardAuthRestrictions.OperationType;
import com.boa.web.response.getCardAuthRestrictions.Region;
import com.boa.web.response.getCardAuthRestrictions.Restriction;
import com.boa.web.response.prepareChangeCardOption.HiddenInput;
import com.boa.web.response.prepareChangeCardOption.Information;
import com.boa.web.response.prepareChangeCardOption.PrepareChangeCardOption;
import com.boa.web.service.util.ICodeDescResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParamFiliale}.
 */
@Service
@Transactional
public class ParamFilialeService {

    private final Logger log = LoggerFactory.getLogger(ParamFilialeService.class);

    private final ParamFilialeRepository paramFilialeRepository;
    private final TrackingService trackingService;
    private final UserService userService;

    public ParamFilialeService(ParamFilialeRepository paramFilialeRepository, TrackingService trackingService,
            UserService userService) {
        this.paramFilialeRepository = paramFilialeRepository;
        this.trackingService = trackingService;
        this.userService = userService;
    }

    /**
     * Save a paramFiliale.
     *
     * @param paramFiliale the entity to save.
     * @return the persisted entity.
     */
    public ParamFiliale save(ParamFiliale paramFiliale) {
        log.debug("Request to save ParamFiliale : {}", paramFiliale);
        return paramFilialeRepository.save(paramFiliale);
    }

    /**
     * Get all the paramFiliales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParamFiliale> findAll(Pageable pageable) {
        log.debug("Request to get all ParamFiliales");
        return paramFilialeRepository.findAll(pageable);
    }

    /**
     * Get one paramFiliale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParamFiliale> findOne(Long id) {
        log.debug("Request to get ParamFiliale : {}", id);
        return paramFilialeRepository.findById(id);
    }

    /**
     * Delete the paramFiliale by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParamFiliale : {}", id);
        paramFilialeRepository.deleteById(id);
    }

    /**
     * @param cardsRequest
     * @param request
     * @return GetCardsResponse
     */
    public GetCardsResponse getCards(CardsRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getCards");
        Tracking tracking = new Tracking();
        GetCardsResponse genericResponse = new GetCardsResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (GetCardsResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                        ICodeDescResponse.CLIENT_ABSENT_CODE, ICodeDescResponse.CLIENT_ABSENT_DESC,
                        request.getRequestURI(), tab[1]);
                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        if (filiale == null) {
            genericResponse = (GetCardsResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);

            return genericResponse;
        }
        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("catCarte", cardsRequest.getCatCarte()).toString();
            tracking.setRequestTr(jsonString);
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
                log.info("result == [{}]", result);
                // System.out.println("result==" + result);
                obj = new JSONObject(result);
                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("client-card-identifier")) {
                    genericResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");

                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);

                    tracking.setResponseTr(result);
                    // System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    return genericResponse;
                }
                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                if (obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("get-cards-response")
                        .get("card") instanceof JSONArray)
                    jsonArray = obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("get-cards-response")
                            .getJSONArray("card");
                else
                    jsonObject = obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("get-cards-response")
                            .getJSONObject("card");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Card card = new Card();
                        JSONObject myObj = jsonArray.getJSONObject(i);
                        card.setClientCardIdentifier(myObj.getString("client-card-identifier"));
                        card.setEmbossedName(myObj.getString("embossed-name"));
                        card.setNumber(myObj.getString("number"));
                        card.setCurrency(myObj.getString("currency"));
                        card.setAvailableBalance(myObj.getInt("available-balance"));
                        Type type = new Type();
                        type.setDefaultIdentifier(myObj.getJSONObject("type").getString("default-identifier"));
                        type.setDescription(myObj.getJSONObject("type").getString("description"));
                        card.setType(type);
                        card.setCategory(myObj.getString("category"));
                        card.setBrand(myObj.getString("brand"));
                        Status status = new Status();
                        JSONObject sObject = myObj.getJSONObject("status");
                        if(sObject.toString().contains("default-identifier"))
                            status.setDefaultIdentifier(myObj.getJSONObject("status").getString("default-identifier"));
                        if(sObject.toString().contains("identifier"))
                            status.setIdentifier(myObj.getJSONObject("status").getInt("identifier"));
                        if(sObject.toString().contains("description"))
                            status.setDescription(myObj.getJSONObject("status").getString("description"));
                        card.setStatus(status);
                        card.setActive(myObj.getBoolean("active"));
                        card.setPinNotSet(myObj.getBoolean("pinNotSet"));
                        card.setExpiryDate(myObj.getString("expiry-date"));
                        card.setReissuable(myObj.getBoolean("reissuable"));
                        card.setClientCardAccountOwner(myObj.getBoolean("client-card-account-owner"));
                        card.setSupplementaryCard(myObj.getBoolean("supplementary-card"));

                        // System.out.println("linked-accounts===+++"
                        // + myObj.getJSONObject("linked-accounts").getString("account-identifier"));
                        String str = myObj.getJSONObject("linked-accounts").getString("account-identifier");

                        card.setLinkedAccounts(str);
                        genericResponse.getCard().add(card);

                    }
                } else if (jsonObject != null) {
                    Card card = new Card();
                    JSONObject myObj = jsonObject;// jsonArray.getJSONObject(i);
                    card.setClientCardIdentifier(myObj.getString("client-card-identifier"));
                    card.setEmbossedName(myObj.getString("embossed-name"));
                    card.setNumber(myObj.getString("number"));
                    card.setCurrency(myObj.getString("currency"));
                    card.setAvailableBalance(myObj.getInt("available-balance"));
                    Type type = new Type();
                    type.setDefaultIdentifier(myObj.getJSONObject("type").getString("default-identifier"));
                    type.setDescription(myObj.getJSONObject("type").getString("description"));
                    card.setType(type);
                    card.setCategory(myObj.getString("category"));
                    card.setBrand(myObj.getString("brand"));
                    Status status = new Status();
                    JSONObject sObject = myObj.getJSONObject("status");
                    if(sObject.toString().contains("default-identifier"))
                        status.setDefaultIdentifier(myObj.getJSONObject("status").getString("default-identifier"));
                    if(sObject.toString().contains("identifier"))
                        status.setIdentifier(myObj.getJSONObject("status").getInt("identifier"));
                    if(sObject.toString().contains("description"))
                        status.setDescription(myObj.getJSONObject("status").getString("description"));
                    card.setStatus(status);
                    card.setActive(myObj.getBoolean("active"));
                    card.setPinNotSet(myObj.getBoolean("pinNotSet"));
                    card.setExpiryDate(myObj.getString("expiry-date"));
                    card.setReissuable(myObj.getBoolean("reissuable"));
                    card.setClientCardAccountOwner(myObj.getBoolean("client-card-account-owner"));
                    card.setSupplementaryCard(myObj.getBoolean("supplementary-card"));

                    // System.out.println("linked-accounts===+++"
                    // + myObj.getJSONObject("linked-accounts").getString("account-identifier"));
                    String str = myObj.getJSONObject("linked-accounts").getString("account-identifier");

                    card.setLinkedAccounts(str);
                    genericResponse.getCard().add(card);
                }

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

            }
            os.close();
        } catch (Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.dateResponse(Instant.now()).endPointTr(request.getRequestURI());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            log.error("errorrr== [{}]", e);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * @param cardsRequest
     * @param request
     * @return PrepareChangeCardOptionResponse
     */
    public PrepareChangeCardOptionResponse prepareChangeCardOptionProxy(PrepareChangeCardOptionRequest cardsRequest,
            HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("prepareChangeCardOption");
        Tracking tracking = new Tracking();
        PrepareChangeCardOptionResponse genericResponse = new PrepareChangeCardOptionResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (PrepareChangeCardOptionResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);

                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        if (filiale == null) {
            genericResponse = (PrepareChangeCardOptionResponse) clientAbsent(genericResponse, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);

            return genericResponse;
        }

        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).put("action", cardsRequest.getAction())
                    .put("entryKey", cardsRequest.getEntryKey()).put("entryValue", cardsRequest.getEntryValue())
                    .toString();
            tracking.setRequestTr(jsonString);
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
                // System.out.println("result==" + result);
                log.info("result == [{}]", result);
                obj = new JSONObject(result);
                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("prepare-change-card-option-response")) {
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
                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                PrepareChangeCardOption cardOption = new PrepareChangeCardOption();
                JSONArray jsonArray = obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("prepare-change-card-option-response").getJSONArray("string-input");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject myObj = jsonArray.getJSONObject(i);
                    Information information = new Information();
                    information.setDescription(myObj.getString("description"));
                    information.setIdentifier(myObj.getString("identifier"));
                    information.setLabel(myObj.getString("label"));
                    information.setPlaceholder(myObj.getString("placeholder"));
                    information.setStereotype(myObj.getString("stereotype"));
                    cardOption.getInfos().add(information);
                }
                HiddenInput hiddenInput = new HiddenInput();
                hiddenInput.setIdentifier(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("prepare-change-card-option-response").getJSONObject("hidden-input")
                        .getString("identifier"));
                hiddenInput.setValue(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("prepare-change-card-option-response").getJSONObject("hidden-input")
                        .getString("value"));
                cardOption.setHiddenInput(hiddenInput);
                genericResponse.setPrepareChangeCardOption(cardOption);
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

            }
            os.close();
        } catch (Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now()).endPointTr(request.getRequestURI());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * @param cardsRequest
     * @param request
     * @return GetCardsDetailResponse
     */
    public GetCardsDetailResponse getCardDetails(CardsDetailRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getCardDetailsProxy");
        Tracking tracking = new Tracking();
        GetCardsDetailResponse genericResponse = new GetCardsDetailResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (GetCardsDetailResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);

                return genericResponse;
            }
        } catch (IOException e1) {
            genericResponse = (GetCardsDetailResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.CLIENT_ABSENT_CODE, ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(),
                    tab[1]);

            return genericResponse;
        }
        if (filiale == null) {
            genericResponse = (GetCardsDetailResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }

        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).toString();
            tracking.setRequestTr(jsonString);
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
                // System.out.println("result===========>" + result);
                log.info("result == [{}]", result);
                obj = new JSONObject(result);
                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("get-card-details-response")) {
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
                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                CardDetails cardDetails = new CardDetails();

                JSONObject myObj = obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("get-card-details-response").getJSONObject("card");

                cardDetails.setClientCardIdentifier(myObj.getString("client-card-identifier"));
                cardDetails.setEmbossedName(myObj.getString("embossed-name"));
                cardDetails.setNumber(myObj.getString("number"));
                cardDetails.setNumberCard(myObj.getString("number"));
                // log.info("card clear ========================================= > [{}] et
                // [{}]",
                // cardDetails.getNumberCard(), cardDetails.getClientCardIdentifier());
                cardDetails.setCurrency(myObj.getString("currency"));
                cardDetails.setAvailableBalance(myObj.getInt("available-balance"));
                Type type = new Type();
                // BeanUtils.copyProperties(myObj.getJSONObject("type"), type);
                // System.out.println("type==="+myObj.getString("type"));
                type.setDefaultIdentifier(myObj.getJSONObject("type").getString("default-identifier"));
                type.setDescription(myObj.getJSONObject("type").getString("description"));
                cardDetails.setType(type);
                cardDetails.setCategory(myObj.getString("category"));
                cardDetails.setBrand(myObj.getString("brand"));
                Status status = new Status();
                status.setDefaultIdentifier(myObj.getJSONObject("status").getString("default-identifier"));
                status.setIdentifier(myObj.getJSONObject("status").getInt("identifier"));
                status.setDescription(myObj.getJSONObject("status").getString("description"));
                cardDetails.setStatus(status);
                cardDetails.setActive(myObj.getBoolean("active"));
                cardDetails.setPinNotSet(myObj.getBoolean("pinNotSet"));
                cardDetails.setExpiryDate(myObj.getString("expiry-date"));
                cardDetails.setReissuable(myObj.getBoolean("reissuable"));
                cardDetails.setClientCardAccountOwner(myObj.getBoolean("client-card-account-owner"));
                cardDetails.setSupplementaryCard(myObj.getBoolean("supplementary-card"));
                OutputStringField field = new OutputStringField();
                field.setIdentifier(myObj.getJSONObject("output-string-field").getString("identifier"));
                field.setHidden(myObj.getJSONObject("output-string-field").getString("hidden"));
                field.setLabel(myObj.getJSONObject("output-string-field").getString("label"));
                field.setStereotype(myObj.getJSONObject("output-string-field").getString("stereotype"));
                field.setValue(myObj.getJSONObject("output-string-field").getString("value"));
                cardDetails.setOutputStringField(field);
                genericResponse.setCard(cardDetails);

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

            }
            os.close();
        } catch (Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now()).endPointTr(request.getRequestURI());
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);

        return genericResponse;
    }

    /**
     * @param cardsRequest
     * @param request
     * @return CardLimitResponse
     */
    public CardLimitResponse getCardLimitProxy(CardsDetailRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getCardLimitProxy");
        Tracking tracking = new Tracking();
        CardLimitResponse genericResponse = new CardLimitResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (CardLimitResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                        ICodeDescResponse.CLIENT_ABSENT_CODE, ICodeDescResponse.CLIENT_ABSENT_DESC,
                        request.getRequestURI(), tab[1]);

                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        if (filiale == null) {
            genericResponse = (CardLimitResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).toString();
            tracking.setRequestTr(jsonString);
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
                // System.out.println("result==" + result);
                log.info("result getCardLimit ***== [{}]", result);
                obj = new JSONObject(result);
                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString()
                        .contains("get-card-limits-response")) {
                    genericResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");

                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);

                    tracking.setResponseTr(result);
                    // System.out.println("tab 1=" + tab[1]);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    return genericResponse;
                }

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                if (obj.getJSONObject("Envelope").getJSONObject("Body")
                .getJSONObject("get-card-limits-response").getJSONArray("card-limit") instanceof JSONArray)
                    jsonArray = obj.getJSONObject("Envelope").getJSONObject("Body")
                    .getJSONObject("get-card-limits-response").getJSONArray("card-limit");
                else
                    jsonObject = obj.getJSONObject("Envelope").getJSONObject("Body")
                    .getJSONObject("get-card-limits-response").getJSONObject("card-limit");

                /*JSONArray jsonArray = obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("get-card-limits-response").getJSONArray("card-limit");*/
                if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    CardLimit_ cardLimit = new CardLimit_();
                    JSONObject myObj = jsonArray.getJSONObject(i);
                    cardLimit.setType(myObj.getString("@type"));
                    cardLimit.setIdentifier(myObj.getInt("identifier"));
                    cardLimit.setName(myObj.getString("name"));
                    cardLimit.setDescription(myObj.getString("currency"));
                    cardLimit.setIsActive(myObj.getBoolean("is-active"));
                    cardLimit.setIsChangeable(myObj.getBoolean("is-changeable"));
                    cardLimit.setIsPermanent(myObj.getBoolean("is-permanent"));
                    cardLimit.setCurrency(myObj.getString("currency"));
                    cardLimit.setValue(myObj.getInt("value"));
                    cardLimit.setUsedValue(myObj.getInt("used-value"));
                    cardLimit.setIsPerTransaction(myObj.getBoolean("is-per-transaction"));
                    genericResponse.getCardLimit().add(cardLimit);

                }
            }else if (jsonObject != null) {
                JSONObject myObj = jsonObject;
                CardLimit_ cardLimit = new CardLimit_();
                cardLimit.setType(myObj.getString("@type"));
                cardLimit.setIdentifier(myObj.getInt("identifier"));
                cardLimit.setName(myObj.getString("name"));
                cardLimit.setDescription(myObj.getString("currency"));
                cardLimit.setIsActive(myObj.getBoolean("is-active"));
                cardLimit.setIsChangeable(myObj.getBoolean("is-changeable"));
                cardLimit.setIsPermanent(myObj.getBoolean("is-permanent"));
                cardLimit.setCurrency(myObj.getString("currency"));
                cardLimit.setValue(myObj.getInt("value"));
                cardLimit.setUsedValue(myObj.getInt("used-value"));
                cardLimit.setIsPerTransaction(myObj.getBoolean("is-per-transaction"));
                genericResponse.getCardLimit().add(cardLimit);
            }

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
            }
            os.close();
        } catch (Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * @param cardsRequest
     * @param request
     * @return GetCardHistoryResponse
     */
    public GetCardHistoryResponse getCardHistoryProxy(CardHistoryRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getCardHistoryProxy");
        Tracking tracking = new Tracking();
        GetCardHistoryResponse genericResponse = new GetCardHistoryResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (GetCardHistoryResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);

                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        if (filiale == null) {
            genericResponse = (GetCardHistoryResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).put("startNum", cardsRequest.getStartNum())
                    .put("maxCount", cardsRequest.getMaxCount()).put("dateFrom", cardsRequest.getDateFrom())
                    .put("dateTo", cardsRequest.getDateTo()).put("hold", cardsRequest.getHold()).toString();
            tracking.setRequestTr(jsonString);
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
                log.info("getCardHistoryProxy result ok == [{}]", result);
                obj = new JSONObject(result);
                if (!obj.getJSONObject("Envelope").getJSONObject("Body").toString().contains("operation")) {
                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.HSTORIQUE_VIDE);
                    tracking.setCodeResponse(ICodeDescResponse.HSTORIQUE_VIDE + "");

                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    tracking.setLoginActeur(login);

                    tracking.setResponseTr(result);
                    tracking.setTokenTr(tab[1]);
                    trackingService.save(tracking);
                    return genericResponse;
                }
                log.info("bef jsarray");
                /*
                 * JSONObject objResult = obj.getJSONObject("Envelope").getJSONObject("Body")
                 * .getJSONObject("get-card-history-response").getJSONObject("operation");
                 * JSONArray jsonArray = null;5 log.info("after jsarray");
                 */

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;
                if (obj.getJSONObject("Envelope").getJSONObject("Body").getJSONObject("get-card-history-response")
                        .get("operation") instanceof JSONArray)
                    jsonArray = obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("get-card-history-response").getJSONArray("operation");
                else
                    jsonObject = obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("get-card-history-response").getJSONObject("operation");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Operation operation = new Operation();
                        JSONObject myObj = jsonArray.getJSONObject(i);
                        operation.setDatetime(myObj.getString("datetime"));
                        operation.setIdentifier(myObj.getInt("identifier"));
                        Type type = new Type();
                        type.setDefaultIdentifier(myObj.getJSONObject("type").getString("identifier"));
                        type.setDescription(myObj.getJSONObject("type").getString("description"));
                        operation.setType(type);
                        Amount amount = new Amount();
                        amount.setAmount(myObj.getJSONObject("amount").getDouble("amount"));
                        amount.setCurrency(myObj.getJSONObject("amount").getString("currency"));
                        operation.setAmount(amount);
                        operation.setDescription(myObj.getString("description"));
                        operation.setIsReversal(myObj.getBoolean("is-reversal"));
                        State state = new State();
                        state.setIdentifier(myObj.getJSONObject("state").getInt("identifier"));
                        state.setDescription(myObj.getJSONObject("state").getString("description"));
                        operation.setState(state);
                        Address address = new Address();
                        Country country = new Country();
                        country.setName(myObj.getJSONObject("address").getJSONObject("country").getString("name"));
                        country.setAlpha2Code(
                                myObj.getJSONObject("address").getJSONObject("country").getString("alpha-2-code"));
                        country.setAlpha3Code(
                                myObj.getJSONObject("address").getJSONObject("country").getString("alpha-3-code"));
                        country.setNumber3Code(
                                myObj.getJSONObject("address").getJSONObject("country").getInt("number-3-code"));
                        address.setCountry(country);
                        address.setCity(myObj.getJSONObject("address").getString("city"));
                        address.setAddress(myObj.getJSONObject("address").getString("address"));
                        operation.setAddress(address);
                        operation.setIsHold(myObj.getBoolean("is-hold"));
                        ResultingBalance resultingBalance = new ResultingBalance();
                        resultingBalance.setAmount(myObj.getJSONObject("resulting-balance").getInt("amount"));
                        resultingBalance.setCurrency(myObj.getJSONObject("resulting-balance").getString("currency"));
                        operation.setResultingBalance(resultingBalance);
                        operation.setDirection(myObj.getString("direction"));
                        operation.setMcc(myObj.getInt("mcc"));
                        genericResponse.getOperation().add(operation);

                    }
                } else if (jsonObject != null) {
                    Operation operation = new Operation();
                    JSONObject myObj = jsonObject;
                    operation.setDatetime(myObj.getString("datetime"));
                    operation.setIdentifier(myObj.getInt("identifier"));
                    Type type = new Type();
                    type.setDefaultIdentifier(myObj.getJSONObject("type").getString("identifier"));
                    type.setDescription(myObj.getJSONObject("type").getString("description"));
                    operation.setType(type);
                    Amount amount = new Amount();
                    amount.setAmount(myObj.getJSONObject("amount").getDouble("amount"));
                    amount.setCurrency(myObj.getJSONObject("amount").getString("currency"));
                    operation.setAmount(amount);
                    operation.setDescription(myObj.getString("description"));
                    operation.setIsReversal(myObj.getBoolean("is-reversal"));
                    State state = new State();
                    state.setIdentifier(myObj.getJSONObject("state").getInt("identifier"));
                    state.setDescription(myObj.getJSONObject("state").getString("description"));
                    operation.setState(state);
                    Address address = new Address();
                    Country country = new Country();
                    country.setName(myObj.getJSONObject("address").getJSONObject("country").getString("name"));
                    country.setAlpha2Code(
                            myObj.getJSONObject("address").getJSONObject("country").getString("alpha-2-code"));
                    country.setAlpha3Code(
                            myObj.getJSONObject("address").getJSONObject("country").getString("alpha-3-code"));
                    country.setNumber3Code(
                            myObj.getJSONObject("address").getJSONObject("country").getInt("number-3-code"));
                    address.setCountry(country);
                    address.setCity(myObj.getJSONObject("address").getString("city"));
                    address.setAddress(myObj.getJSONObject("address").getString("address"));
                    operation.setAddress(address);
                    operation.setIsHold(myObj.getBoolean("is-hold"));
                    ResultingBalance resultingBalance = new ResultingBalance();
                    resultingBalance.setAmount(myObj.getJSONObject("resulting-balance").getInt("amount"));
                    resultingBalance.setCurrency(myObj.getJSONObject("resulting-balance").getString("currency"));
                    operation.setResultingBalance(resultingBalance);
                    operation.setDirection(myObj.getString("direction"));
                    operation.setMcc(myObj.getInt("mcc"));
                    genericResponse.getOperation().add(operation);
                }
                // }

                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = br.readLine();
                log.info("getCardHistoryProxy result error == [{}]", result);
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
            os.close();
        } catch (Exception e) {
            //pb interpretation resp
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            log.error("errorrr==[{}]", e.getMessage());

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * @param cardsRequest
     * @param request
     * @return ChangeCardLimitResponse
     */
    public ChangeCardLimitResponse changeCardLimitProxy(ChangeCardRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("changeCardLimitProxy");
        Tracking tracking = new Tracking();
        ChangeCardLimitResponse genericResponse = new ChangeCardLimitResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (ChangeCardLimitResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);

                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }
        if (filiale == null) {
            genericResponse = (ChangeCardLimitResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }

        try {
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).put("limitIdent", cardsRequest.getLimitIdent())
                    .put("amount", cardsRequest.getAmount()).put("currency", cardsRequest.getCurrency())
                    .put("countClimit", cardsRequest.getCountClimit()).put("isActive", cardsRequest.getIsActive())
                    .put("duration", cardsRequest.getDuration()).toString();
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
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultstring"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
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
                // System.out.println("result===========>" + result);
                obj = new JSONObject(result);

                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                tracking.setResponseTr(result);
                // System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);

                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
            os.close();
        } catch (

        Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC);
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);

        return genericResponse;
    }

    public ChargeCardResponse chargeCardResponse(ChargementCardRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("chargementCarteProxy");
        Tracking tracking = new Tracking();
        ChargeCardResponse genericResponse = new ChargeCardResponse();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (ChargeCardResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            CardsDetailRequest cardsDetailRequest = new CardsDetailRequest();
            cardsDetailRequest.setCartIdentif(cardsRequest.getCartIdentifTarget());
            cardsDetailRequest.setCompte(cardsRequest.getCompteCardTarget());
            cardsDetailRequest.setInstitutionId(cardsRequest.getInstitutionId());
            cardsDetailRequest.setLangue(cardsRequest.getLangue());
            cardsDetailRequest.setPays(cardsRequest.getPays());
            cardsDetailRequest.setVariant(cardsRequest.getVariant());
            GetCardsDetailResponse cardsDetailResponse = getCardDetails(cardsDetailRequest, request);
            if (cardsDetailResponse.getCode() == 200) {
                URL url = new URL(filiale.getEndPoint());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/xml");
                conn.setRequestProperty("Accept", "application/xml");

                ChargementCarteWso2Request carteWso2Request = new ChargementCarteWso2Request();
                carteWso2Request.setCodopsc("GAB");
                // carteWso2Request.setComptec(cardsRequest.getCompteSource());
                carteWso2Request.setCompted(cardsRequest.getCompteSource());
                carteWso2Request.setComptec("");
                carteWso2Request.setCountry(cardsRequest.getPays());
                carteWso2Request.setDateT(cardsRequest.getDateTrans());
                String[] data = cardsDetailResponse.getCard().getExpiryDate().split("-");
                String date = data[0].substring(2) + data[2].substring(0, 2);
                log.info("data === [{}]", date);
                carteWso2Request.setDatexpir(date);
                carteWso2Request.setDevise(cardsDetailResponse.getCard().getCurrency());
                carteWso2Request.setDispo("DISPONIBLE");
                carteWso2Request.setLibelle("DEMANDE AUTO");
                carteWso2Request.setVal("V");
                carteWso2Request.setMntdev(cardsRequest.getMontant());
                carteWso2Request.setMntfrais(cardsRequest.getMontantFrais());
                carteWso2Request.setNumcarte(cardsDetailResponse.getCard().getNumberCard());
                carteWso2Request.setRefrel(cardsRequest.getRefRel());
                carteWso2Request.setReftrans("");

                StringBuilder builder = new StringBuilder();
                builder.append("<body><chargement>");
                builder.append("<codopsc>" + carteWso2Request.getCodopsc() + "</codopsc>");
                builder.append("<comptec>" + carteWso2Request.getComptec() + "</comptec>");
                builder.append("<compted>" + carteWso2Request.getCompted() + "</compted>");
                builder.append("<country>" + carteWso2Request.getCountry() + "</country>");
                builder.append("<dateT>" + carteWso2Request.getDateT() + "</dateT>");
                builder.append("<datexpir>" + carteWso2Request.getDatexpir() + "</datexpir>");
                builder.append("<devise>" + carteWso2Request.getDevise() + "</devise>");
                builder.append("<dispo>" + carteWso2Request.getDispo() + "</dispo>");
                builder.append("<libelle>" + carteWso2Request.getLibelle() + "</libelle>");
                builder.append("<mntdev>" + carteWso2Request.getMntdev() + "</mntdev>");
                builder.append("<mntfrais>" + carteWso2Request.getMntfrais() + "</mntfrais>");
                builder.append("<numcarte>" + carteWso2Request.getNumcarte() + "</numcarte>");
                builder.append("<refrel>" + carteWso2Request.getRefrel() + "</refrel>");
                builder.append("<reftrans>" + carteWso2Request.getReftrans() + "</reftrans>");
                builder.append("<val>" + carteWso2Request.getVal() + "</val>");
                builder.append("</chargement></body>");

                tracking.setRequestTr(builder.toString());
                log.info("carteWso2Request [{}]", builder.toString());
                OutputStream os = conn.getOutputStream();
                byte[] postDataBytes = builder.toString().getBytes();
                String result = "";

                os.write(postDataBytes);
                os.flush();

                BufferedReader br = null;
                JSONObject obj = new JSONObject();
                if (conn != null && conn.getResponseCode() > 0) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    result = br.readLine();
                    log.info("result chargement == [{}]", result);
                    obj = new JSONObject(result);
                    if (obj.getJSONObject("chargementCarte").toString().contains("RCOD")) {
                        genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                        genericResponse.setDateResponse(Instant.now());
                        genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                        genericResponse.setReference("");
                        genericResponse.setResultat(
                                obj.getJSONObject("chargementCarte").getJSONObject("response").getString("RCOD"));
                        genericResponse.setTexte(
                                obj.getJSONObject("chargementCarte").getJSONObject("response").getString("RMSG"));
                        tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                        tracking.setDateResponse(Instant.now());
                        tracking.setEndPointTr(filiale.getEndPoint());
                        tracking.setLoginActeur(login);
                        tracking.setDateRequest(Instant.now());
                        tracking.setResponseTr(result);
                        tracking.setTokenTr(tab[1]);
                    }
                }
                os.close();
            } else {
                genericResponse = (ChargeCardResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                        cardsDetailResponse.getCode(), cardsDetailResponse.getDescription(), request.getRequestURI(),
                        tab[1]);
                return genericResponse;
            }
        } catch (Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            log.error("errorrr except==[{}]", e);
        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public PrepareCardToCardTransferResponse prepareCardToCardTransfer(PrepareCardToCardTransferRequest cardsRequest,
            HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("prepareCardToCardTransferProxy");
        Tracking tracking = new Tracking();
        PrepareCardToCardTransferResponse genericResponse = new PrepareCardToCardTransferResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (PrepareCardToCardTransferResponse) clientAbsent(genericResponse, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (PrepareCardToCardTransferResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
                        ICodeDescResponse.CLIENT_ABSENT_DESC, request.getRequestURI(), tab[1]);

                return genericResponse;
            }
        } catch (IOException e1) {
            log.info("error = [{}]", e1.getMessage());
        }

        try {
            /*
             * CardsDetailRequest cardsDetailRequest = new CardsDetailRequest();
             * cardsDetailRequest.setCartIdentif(cardsRequest.getCardclidentifier());
             * cardsDetailRequest.setCompte(cardsRequest.getCompte());
             * cardsDetailRequest.setInstitutionId(cardsRequest.getInstitutionId());
             * cardsDetailRequest.setLangue(cardsRequest.getCountry());
             * cardsDetailRequest.setPays(cardsRequest.getCountry());
             * cardsDetailRequest.setVariant(cardsRequest.getVariant());
             * GetCardsDetailResponse cardsDetailResponse =
             * getCardDetails(cardsDetailRequest, request); if
             * (cardsDetailResponse.getCode() != 200) {
             * tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");
             * tracking.responseTr(ICodeDescResponse.CLIENT_ABSENT_DESC);
             * tracking.dateResponse(Instant.now());
             * tracking.tokenTr(tab[1]).dateRequest(Instant.now()) .loginActeur(login);
             * tracking.setEndPointTr(request.getRequestURI());
             * tracking.setRequestTr(cardsDetailRequest.toString());
             * genericResponse.setCode(ICodeDescResponse.CLIENT_ABSENT_CODE);
             * genericResponse.setDateResponse(Instant.now());
             * genericResponse.setDescription(ICodeDescResponse.CLIENT_ABSENT_DESC);
             * trackingService.save(tracking); log.error("errorrr== [{}]",
             * cardsDetailResponse.getCode()); return genericResponse;
             * 
             * }
             */
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String jsonString = "";
            jsonString = new JSONObject().put("opidentifier", cardsRequest.getOpidentifier())
                    .put("clidentifier", client.getIdClient()).put("langage", cardsRequest.getLangage())
                    .put("cardclidentifier", cardsRequest.getCardclidentifier())
                    .put("country", cardsRequest.getCountry()).put("variant", cardsRequest.getVariant())
                    .put("tcardnumb", cardsRequest.getTcardnumb()).put("rname", cardsRequest.getRname())
                    .put("amount", cardsRequest.getAmount()).put("currency", cardsRequest.getCurrency())
                    // .put("cardidentif", cardsRequest.getCardidentif())

                    .put("entkey", cardsRequest.getEntkey()).put("entval", cardsRequest.getEntval()).toString();
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
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                            .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
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
                // System.out.println("result===========>" + result);
                obj = new JSONObject(result);

                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                genericResponse.setAmount(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("prepare-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getJSONObject("fee").getDouble("amount"));
                genericResponse.setCurrency(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("prepare-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getJSONObject("fee").getString("currency"));
                tracking.setResponseTr(result);
                // System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);

                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
            os.close();
        } catch (

        Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC);
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);

        return genericResponse;
    }

    public ExecuteCardToCardTransferResponse executeCardToCardTransfer(PrepareCardToCardTransferRequest cardsRequest,
            HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("executeCardToCardTransferProxy");
        Tracking tracking = new Tracking();
        ExecuteCardToCardTransferResponse genericResponse = new ExecuteCardToCardTransferResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (ExecuteCardToCardTransferResponse) clientAbsent(genericResponse, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (ExecuteCardToCardTransferResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
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
            jsonString = new JSONObject().put("opidentifier", cardsRequest.getOpidentifier())
                    .put("clidentifier", client.getIdClient()).put("langue", cardsRequest.getLangage())
                    .put("country", cardsRequest.getCountry()).put("variant", cardsRequest.getVariant())
                    .put("cardclidentifier", cardsRequest.getCardclidentifier())
                    .put("limitIdent", cardsRequest.getCardnumber()).put("cardidentif", cardsRequest.getCardidentif())
                    .put("tcardnumb", cardsRequest.getTcardnumb()).put("rname", cardsRequest.getRname())
                    .put("amount", cardsRequest.getAmount()).put("currency", cardsRequest.getCurrency())
                    .put("entkey", cardsRequest.getEntkey()).put("entval", cardsRequest.getEntval()).toString();
            log.info("execute card to card req == [{}]", jsonString);
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
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("business-fault")
                            .getJSONObject("rejected-operation-exception").getString("message-for-client"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
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
                // System.out.println("result===========>" + result);
                obj = new JSONObject(result);

                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                // com.boa.web.response.Amount amount = new com.boa.web.response.Amount();
                Amount amount = new Amount();
                amount.setAmount(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getJSONObject("amount").getDouble("amount"));
                amount.setCurrency(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getJSONObject("amount").getString("currency"));
                genericResponse.setAmount(amount);
                Type type = new Type();
                type.setDefaultIdentifier(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getJSONObject("type").getString("identifier"));
                type.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getJSONObject("type").getString("description"));
                genericResponse.setType(type);
                genericResponse.setDateTime(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getString("datetime"));
                genericResponse.setIdentifier(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getString("identifier"));
                genericResponse.setIsHold(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getBoolean("is-hold"));
                genericResponse.setMcc(obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-card-to-card-transfer-response").getJSONObject("operation-info")
                        .getString("mcc"));
                tracking.setResponseTr(result);
                // System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);

                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
            os.close();
        } catch (

        Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC);
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);

        return genericResponse;
    }

    public CheckBankActivateCardResponse checkBankActivateCardProxy(CheckBankActivateCardRequest cardsRequest,
            HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("checkBankActivateCardProxy");
        Tracking tracking = new Tracking();
        CheckBankActivateCardResponse genericResponse = new CheckBankActivateCardResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (CheckBankActivateCardResponse) clientAbsent(genericResponse, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (CheckBankActivateCardResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
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
            jsonString = new JSONObject().put("operationIdentifier", cardsRequest.getIdoperation())
                    .put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).put("entKey1", cardsRequest.getEntKey1())
                    .put("entValue1", cardsRequest.getEntValue1()).put("entKey2", cardsRequest.getEntKey2())
                    .put("entValue2", cardsRequest.getEntValue2()).put("entKey3", cardsRequest.getEntKey3())
                    .put("entValue3", cardsRequest.getEntValue3()).toString();
            log.info("execute card to card req == [{}]", jsonString);
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
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("system-fault")
                            .getJSONObject("system-exception").getString("description"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
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
                // System.out.println("result===========>" + result);
                obj = new JSONObject(result);

                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                obj = obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("execute-change-card-option-response").getJSONObject("output-string-field");
                genericResponse.setHidden(obj.getBoolean("hidden"));
                genericResponse.setIdentifier(obj.getString("identifier"));
                genericResponse.setImportant(obj.getBoolean("important"));
                genericResponse.setLabel(obj.getString("label"));
                genericResponse.setStereotype(obj.getString("stereotype"));
                genericResponse.setValue(obj.getInt("value"));

                tracking.setResponseTr(result);
                // System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);

                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
            os.close();
        } catch (

        Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC);
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public GetCardAuthRestrictionsResponse getCardAuthRestrictionsProxy(GetCardAuthRestrictionsRequest cardsRequest,
            HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("getCardAuthRestrictionsProxy");
        Tracking tracking = new Tracking();
        GetCardAuthRestrictionsResponse genericResponse = new GetCardAuthRestrictionsResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (GetCardAuthRestrictionsResponse) clientAbsent(genericResponse, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (GetCardAuthRestrictionsResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
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
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).toString();
            log.info("execute card to card req == [{}]", jsonString);
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
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getJSONObject("detail").getJSONObject("system-fault")
                            .getJSONObject("system-exception").getString("description"));
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");

                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    log.info("####################### end point = [{}]", filiale.getEndPoint());
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
                // System.out.println("result===========>" + result);
                obj = new JSONObject(result);

                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                obj = obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("get-card-auth-restrictions-response");
                OperationType operationType = new OperationType();
                operationType.setIdentifier(obj.getJSONObject("operation-type").getInt("identifier"));
                operationType.setName(obj.getJSONObject("operation-type").getString("name"));
                genericResponse.setOperationType(operationType);
                JSONArray jsonArray = obj.getJSONArray("region");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Region region = new Region();
                    JSONObject myObj = jsonArray.getJSONObject(i);
                    region.setIdentifier(myObj.getInt("identifier"));
                    region.setName(myObj.getString("name"));
                    genericResponse.getRegion().add(region);
                }
                if (obj.toString().contains("restriction")) {
                    JSONArray restArray = null;
                    JSONObject restObject = null;
                    if (obj.get("restriction") instanceof JSONArray)
                    restArray = obj.getJSONArray("restriction");
                    else
                    restObject = obj.getJSONObject("restriction");
                    if (restArray != null) {
                        for (int i = 0; i < restArray.length(); i++) {
                            Restriction restriction = new Restriction();
                            JSONObject myObj = restArray.getJSONObject(i);
                            restriction
                            .operationTypeIdentifier(myObj.getString("operation-type-identifier"))
                            .regionIdentifier(myObj.getString("region-identifier"))
                            .isActive(myObj.getBoolean("is-active"))
                            ;
                            genericResponse.getRestrictions().add(restriction);
                        }
                    }else if(restObject != null) {
                            Restriction restriction = new Restriction();
                            restriction
                            .operationTypeIdentifier(restObject.getString("operation-type-identifier"))
                            .regionIdentifier(restObject.getString("region-identifier"))
                            .isActive(restObject.getBoolean("is-active"))
                            ;
                            genericResponse.getRestrictions().add(restriction);
                    }
                }

                tracking.setResponseTr(result);
                // System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);

                tracking.setCodeResponse(ICodeDescResponse.ECHEC_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
            os.close();
        } catch (

        Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC);
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public ChangeCardAuthRestrictionResponse changeCardAuthRestrictionProxy(
            ChangeCardAuthRestrictionRequest cardsRequest, HttpServletRequest request) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("changeCardAuthRestrictionProxy");
        Tracking tracking = new Tracking();
        ChangeCardAuthRestrictionResponse genericResponse = new ChangeCardAuthRestrictionResponse();
        Client client = new Client();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (ChangeCardAuthRestrictionResponse) clientAbsent(genericResponse, tracking,
                    request.getRequestURI(), ICodeDescResponse.FILIALE_ABSENT_CODE,
                    ICodeDescResponse.SERVICE_ABSENT_DESC, request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        try {
            client = this.callApiIdClient(cardsRequest.getCompte(), cardsRequest.getInstitutionId());
            if (client == null) {
                genericResponse = (ChangeCardAuthRestrictionResponse) clientAbsent(genericResponse, tracking,
                        request.getRequestURI(), ICodeDescResponse.CLIENT_ABSENT_CODE,
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
            jsonString = new JSONObject().put("idClient", client.getIdClient()).put("langue", cardsRequest.getLangue())
                    .put("pays", cardsRequest.getPays()).put("variant", cardsRequest.getVariant())
                    .put("cartIdentif", cardsRequest.getCartIdentif()).put("operationId", cardsRequest.getOperationId())
                    .put("regionId", cardsRequest.getRegionId()).put("isactive", cardsRequest.getIsactive())
                    .put("startDate", cardsRequest.getStartDate()).put("endDate", cardsRequest.getEndDate()).toString();
            log.info("execute card to card req == [{}]", jsonString);
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
                    genericResponse.setFaultCode(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultcode"));
                    genericResponse.setFaultString(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultstring"));
                    genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("Fault").getString("faultstring"));
                    tracking.setCodeResponse(ICodeDescResponse.CLIENT_ABSENT_CODE + "");

                    tracking.setDateResponse(Instant.now());
                    tracking.setEndPointTr(filiale.getEndPoint());
                    log.info("####################### end point = [{}]", filiale.getEndPoint());
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
                // System.out.println("result===========>" + result);
                obj = new JSONObject(result);

                // System.out.println("obj resp==" +
                // obj.getJSONObject("Envelope").getJSONObject("Body").toString());
                genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                obj = obj.getJSONObject("Envelope").getJSONObject("Body")
                        .getJSONObject("change-card-auth-restriction-response");
                genericResponse.setIsActive(obj.getBoolean("is-active"));

                tracking.setResponseTr(result);
                // System.out.println("tab 1=" + tab[1]);
                tracking.setTokenTr(tab[1]);

                tracking.setCodeResponse(ICodeDescResponse.SUCCES_CODE + "");

                tracking.setDateResponse(Instant.now());
                tracking.setEndPointTr(filiale.getEndPoint());
                tracking.setLoginActeur(login);
                tracking.setDateRequest(Instant.now());
                tracking.setResponseTr(result);
                tracking.setTokenTr(tab[1]);

            }
            os.close();
        } catch (

        Exception e) {
            tracking.setCodeResponse(ICodeDescResponse.FILIALE_ABSENT_CODE + "");
            tracking.tokenTr(tab[1]).dateRequest(Instant.now()).loginActeur(login);
            tracking.responseTr(ICodeDescResponse.FILIALE_ABSENT_DESC);
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(ICodeDescResponse.FILIALE_ABSENT_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC);
            log.error("errorrr==[{}]", e);

        }
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * @param numAccount
     * @param institutionId
     * @return Client
     * @throws IOException
     */
    public Client callApiIdClient(String numAccount, String institutionId) throws IOException {
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("apiIdClient");
        if (filiale == null) {
            return null;
        }
        Client client = new Client();
        URL url = new URL(filiale.getEndPoint());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        String jsonString = "";
        try {
            jsonString = new JSONObject().put("compte", numAccount).put("institutionId", institutionId).toString();
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            if (conn != null && conn.getResponseCode() == ICodeDescResponse.SUCCES_CODE) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result call api client == [{}]", result);
                // System.out.println("result==####µµµµµ" + result);
                obj = new JSONObject(result);
                if (obj.isNull("infoClient"))
                    // if (!obj.getJSONObject("infoClient").toString().contains("idClient"))
                    return null;
                client.setIdClient(obj.getJSONObject("infoClient").getJSONObject("infoClient").getString("idClient"));
            }
            os.close();

        } catch (JSONException e) {
            return null;
        }

        return client;
    }

    /**
     * Methode pour logger si le client est absent
     * 
     * @param genericResponse
     * @param tracking
     * @param filiale
     * @param code
     * @param description
     * @param request
     * @param token
     * @return GenericResponse
     */
    public GenericResponse clientAbsent(GenericResponse genericResponse, Tracking tracking, String filiale,
            Integer code, String description, String request, String token) {
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        genericResponse.setCode(code);
        genericResponse.setDateResponse(Instant.now());
        genericResponse.setDescription(description);
        tracking.setCodeResponse(code + "");
        tracking.setDateResponse(Instant.now());
        tracking.setEndPointTr(filiale);
        tracking.setLoginActeur(login);
        tracking.setDateRequest(Instant.now());
        tracking.setResponseTr(description);
        tracking.setTokenTr(token);
        tracking.setRequestTr(request);
        trackingService.save(tracking);
        return genericResponse;
    }

    public CardlessResponse cardlessChargement(CardlessRequest cardsRequest, HttpServletRequest request) {
        CardlessResponse genericResponse = new CardlessResponse();
        Optional<User> user = userService.getUserWithAuthorities();
        String login = user.isPresent() ? user.get().getLogin() : "";
        ParamFiliale filiale = paramFilialeRepository.findByCodeFiliale("cardless");
        Tracking tracking = new Tracking();
        String autho = request.getHeader("Authorization");
        String[] tab = autho.split("Bearer");
        if (filiale == null) {
            genericResponse = (CardlessResponse) clientAbsent(genericResponse, tracking, request.getRequestURI(),
                    ICodeDescResponse.FILIALE_ABSENT_CODE, ICodeDescResponse.SERVICE_ABSENT_DESC,
                    request.getRequestURI(), tab[1]);
            return genericResponse;
        }
        OutputStream os = null;
        try {
            log.info("end point wso2== [{}]", filiale.getEndPoint());
            URL url = new URL(filiale.getEndPoint());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            String jsonString = new JSONObject().put("card_exp_date", cardsRequest.getWithdrawalDueDate())
                    .put("amount", cardsRequest.getAmount()).put("currency", cardsRequest.getCurrency())
                    .put("send_card_acc_num", cardsRequest.getSenderAccountNumber())
                    .put("dest_phone_number", cardsRequest.getDestCellPhone())
                    .put("institut_id", cardsRequest.getInstitutionId()).toString();
            log.info("req== [{}]", jsonString);
            tracking.setRequestTr(jsonString);
            os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();

            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            log.info("resp code [{}]", conn.getResponseCode());
            // log.info("resp code [{}]", conn.);
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ligne = br.readLine();
                while (ligne != null) {
                    result += ligne;
                    ligne = br.readLine();
                }
                log.info("resp ===== [{}]", result);
                obj = new JSONObject(result);
                log.info("resp ===== [{}]", obj.toString());
                if (!obj.isNull("Envelope")) {
                    obj = obj.getJSONObject("Envelope").getJSONObject("Body")
                            .getJSONObject("cardlessRemittanceResponse").getJSONObject("reply");
                    if (obj.toString().contains("errorCode")) {
                        Reply reply = new Reply();
                        reply.setErrorCode(obj.getString("errorCode"));
                        reply.setErrorDescription(obj.getString("errorDescription"));
                        reply.setStatus(obj.getString("status"));
                        reply.setCurrencyCode(cardsRequest.getCurrency());
                        reply.setAmount(cardsRequest.getAmount());
                        reply.setAccount(cardsRequest.getSenderAccountNumber());
                        genericResponse.setReply(reply);
                        genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                        genericResponse.setDateResponse(Instant.now());
                        genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                        tracking = createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(), result, tab[1]);
                    } else if (obj.toString().contains("amount")) {
                        log.info("with amount [{}]", obj.toString());
                        Reply reply = new Reply();
                        reply.setAccount(obj.getString("account"));
                        reply.setAmount(obj.getJSONObject("amount").getDouble("amount"));
                        reply.setCurrencyCode(obj.getJSONObject("amount").getString("currencyCode"));
                        reply.setOtp(obj.getString("otp"));
                        reply.setRemittanceReferenceNumber(obj.getString("remittanceReferenceNumber"));
                        reply.setTxnNum(obj.getString("txnNum"));
                        reply.setStatus(obj.getString("status"));
                        genericResponse.setReply(reply);
                        genericResponse.setCode(ICodeDescResponse.SUCCES_CODE);
                        genericResponse.setDateResponse(Instant.now());
                        genericResponse.setDescription(ICodeDescResponse.SUCCES_DESCRIPTION);
                        tracking = createTracking(ICodeDescResponse.SUCCES_CODE, filiale.getEndPoint(), result, tab[1]);
                    }
                } else {
                    // echec car result null
                    genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                    tracking = createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(), "Aucune reponse",
                            tab[1]);
                }
            } else {
                genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription(ICodeDescResponse.ECHEC_DESCRIPTION);
                tracking = createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(),
                        "Le proxy a donne une reponse innatendu", tab[1]);
            }
            os.close();
        } catch (Exception e) {
            log.error("exception in cardless [{}]", e);
            genericResponse.setCode(ICodeDescResponse.ECHEC_CODE);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription(ICodeDescResponse.FILIALE_ABSENT_DESC+" Message="+e.getMessage());
            tracking = createTracking(ICodeDescResponse.ECHEC_CODE, filiale.getEndPoint(), e.getMessage(), tab[1]);
        }
        trackingService.save(tracking);
        return genericResponse;
    }

    public Tracking createTracking(Integer code, String endPoint, String result, String token) {
        Tracking tracking = new Tracking();
        tracking.setCodeResponse(code.toString());
        tracking.setDateResponse(Instant.now());
        tracking.setEndPointTr(endPoint);
        tracking.setLoginActeur(userService.getUserWithAuthorities().get().getLogin());
        tracking.setResponseTr(result);
        tracking.setTokenTr(token);
        tracking.setDateRequest(Instant.now());
        return tracking;
    }

}
