package com.boa.web.service.util;

/**
 * ICodeDescResponse
 */
public interface ICodeDescResponse {

    public static Integer SUCCES_CODE = 200;
    public static Integer ECHEC_CODE = 301;
    public static String ECHEC_DESCRIPTION = "Operation echouee.";
    public static Integer CLIENT_ABSENT_CODE = 405;
    public static Integer FILIALE_ABSENT_CODE = 404;
    public static Integer PARAM_ABSENT_CODE = 304;
    public static String PARAM_DESCRIPTION = "Parametre(s) Compte et/ou Institution absent(s)";
    public static String SUCCES_DESCRIPTION = "Operation effectuee avec succes";
    public static String FILIALE_ABSENT_DESC = "Une erreur est survenue";
    public static String INSTITUTION_NON_PARAMETRE = "Institution non parametre.";
    public static String CLIENT_ABSENT_DESC = "Numero Client absent dans nos livres";
    public static String PARAM_ABSENT_DESC = "Parametre non trouve.";
    public static String SERVICE_ABSENT_DESC = "Service non parametre.";
    public static String COMPTE_DAP_ABSENT = "Compte DAP non parametre pour ce pays.";
    public static String COMPTE_DAP = "COMPTEDAP";
    public static String HSTORIQUE_VIDE = "Historique de carte vide!";

    /*-------------*/
    public static Integer COMPTE_ABSENT_CODE = 406;
    public static String COMPTE_ABSENT_DESC = "Parametre compte absent";
    
    
    public static Integer INSTITUTION_ABSENT_CODE = 407;
    public static String INSTITUTION_ABSENT_DESC = "Parametre client Absent";
    public static String SEUIL_LIMITE = "Exception lors de la recuperation du Seuil.";
    public static String FRAIS_NON_REMONTEE = "Frais non remontee.";
    /*------*/

}