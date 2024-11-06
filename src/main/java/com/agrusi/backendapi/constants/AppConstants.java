package com.agrusi.backendapi.constants;

public interface AppConstants {

    /*
     * 1. "JWT_KEY" --> A constant that is unique to the domain of our
     * application, and it's something that's supposed to be a SECRET
     * in a real production application, SO KEEP IT SECURE IN
     * PRODUCTION LIKE AS AN ENVIRONMENT VARIABLE!
     *
     * 2. "JWT_HEADER" --> The header, in this case named "Authorization"
     * will be the name of the header that the JWT will be inserted into,
     * and we'll use that token to make sure that the user is
     * authorized to view some page
     */

    String AGRUSI_URL = "https://agrusi.com";

//    public static final String JWT_KEY = "mY_AmAzInG_AnD_SuPeR_SeCrEt_jWt_kEy";
//    public static final String JWT_HEADER = "Authorization";
}
