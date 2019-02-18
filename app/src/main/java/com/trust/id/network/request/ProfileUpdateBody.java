package com.trust.id.network.request;

import com.google.gson.annotations.SerializedName;

public class ProfileUpdateBody {
    private final String type = "person";
    private Attributes attributes;

    public ProfileUpdateBody(Attributes attributes) {
        this.attributes = attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public static class Attributes {
        private String names;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("sur_name")
        private String surName;
        private String gender;
        private final String status = "alive";
        private final boolean verified = false;
        private String birthday;
        private String nationality;

        public Attributes(String names, String lastName, String surName, String gender, String birthday, String nationality) {
            this.names = names;
            this.lastName = lastName;
            this.surName = surName;
            this.gender = gender;
            this.birthday = birthday;
            this.nationality = nationality.toLowerCase();
        }

        public void setNames(String names) {
            this.names = names;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}
