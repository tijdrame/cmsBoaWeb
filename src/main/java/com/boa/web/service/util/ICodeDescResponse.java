package com.boa.web.service.util;

/**
 * ICodeDescResponse
 */
public interface ICodeDescResponse {

    public static Integer SUCCES_CODE = 200;
    public static Integer ECHEC_CODE = 301;
    public static String ECHEC_DESCRIPTION = "Opération échouée.";
    public static Integer CLIENT_ABSENT_CODE = 405;
    public static Integer FILIALE_ABSENT_CODE = 404;
    public static Integer PARAM_ABSENT_CODE = 304;
    public static String PARAM_DESCRIPTION = "Parametre(s) Compte et/ou Institution absent(s)";
    public static String SUCCES_DESCRIPTION = "Operation effectuee avec succes";
    public static String FILIALE_ABSENT_DESC = "Proxy injoignable";
    public static String CLIENT_ABSENT_DESC = "Numero Client absent dans nos livres!";
    public static String PARAM_ABSENT_DESC = "Parametre non trouve!";
    public static String SERVICE_ABSENT_DESC = "Service non parametre!";
    public static String HSTORIQUE_VIDE = "Historique de carte vide!";

    /*-------------*/
    public static Integer COMPTE_ABSENT_CODE = 406;
    public static String COMPTE_ABSENT_DESC = "Parametre compte absent";
    
    
    public static Integer INSTITUTION_ABSENT_CODE = 407;
    public static String INSTITUTION_ABSENT_DESC = "Parametre client Absent";
    /*------*/

}