package com.trust.id2.model;



import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

/**
 * {
 * "address": {
 * "country": "CL"
 * },
 * "sub": "fgY18e5JI49hV5YcpE9c9e_XhOU3b5WMME9138j2nBk",
 * "name": "JAVIER ANDRES",
 * "inum": "2438b6e2-0441-4526-a65f-afb6577aa0a3",
 * "user_name": "2438b6e2-0441-4526-a65f-afb6577aa0a3",
 * "birthdate": 596246400000,
 * "gender": "male",
 * "given_name": "JAVIER ANDRES",
 * "middle_name": "ALVARADO",
 * "family_name": "LETELIER",
 * "phone_number_verified": false,
 * "email_verified": false,
 * "email": "jlettelier@autentia.cl"
 * }
 */
public class Profile {

    public static final String dummy = "{\"address\":{\"country\":\"CL\"},\"sub\":\"s9vH7pZppj1niIdHK8Xdl9Viy0IdnVvsnAplN2KJHVM\",\"birthdate\":\"19890712000000.000Z\",\"gender\":\"male\",\"profile\":\"{ \\\"credentials\\\": [{ \\\"sub\\\": \\\"17162369-3\\\", \\\"iss\\\": \\\"CL_REGISTROCIVIL\\\"}]}\",\"name\":\"FELIPE ANDRES\",\"given_name\":\"FELIPE ANDRES\",\"middle_name\":\"TORRES\",\"family_name\":\"NAVARRO\"}";
    @SerializedName("birthdate")
    private String birthdate;
    @SerializedName("gender")
    private String gender;
    @SerializedName("given_name")
    private String givenName;
    @SerializedName("middle_name")
    private String middleName;
    @SerializedName("family_name")
    private String familyName;
    @SerializedName("email_verified")
    private boolean emailVerified;
    @SerializedName("email")
    private String email;
    @SerializedName("profile")
    private String profileRaw;
    private Address address;

    public String getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getProfileRaw() {
        RutProfile rutProfile = new Gson().fromJson(profileRaw, RutProfile.class);
        return (rutProfile != null) ? rutProfile.getRut() : "--";
    }

    public String getProfileId() {
        RutProfile rutProfile = new Gson().fromJson(profileRaw, RutProfile.class);
        return (rutProfile != null) ? rutProfile.getId() : null;
    }

    public class RutProfile {
        @SerializedName("credentials")
        List<Credential> credentialList;

        public String getRut() {
            return (credentialList != null && !credentialList.isEmpty()) ? credentialList.get(0).getSub() : "Desconocido";
        }

        public String getId() {
            return (credentialList != null && !credentialList.isEmpty()) ? credentialList.get(0).getProfileId() : null;
        }
    }

    public class Address {
        private String country;

        public String getCountry() {
            return country;
        }
    }

    public class Credential {
        private String sub;
        @SerializedName("profile_id")
        private String profileId;

        public String getSub() {
            return sub;
        }

        public String getProfileId() {
            return profileId;
        }
    }

    public String getFullName() {
        return String.format(Locale.getDefault(), "%s %s %s", getGivenName(), getFamilyName(), getMiddleName());
    }

    public boolean isMale() {
        return getGender().equals("male");
    }

    public boolean missingData() {
        return getGivenName() == null || getGivenName().equals("S/N")
                || getFamilyName() == null || getFamilyName().equals("S/A")
                || getMiddleName() == null || getMiddleName().equals("S/A")
                || getGender() == null
                || getAddress() == null || getAddress().getCountry() == null || getAddress().getCountry().isEmpty();
    }

}
