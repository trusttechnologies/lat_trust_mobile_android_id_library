package com.trust.id2.network.req;

public class FirebaseUpdateBody {
    private final String type = "person";
    private Attributes attributes;

    public FirebaseUpdateBody(Profile profile) {
        this.attributes = new Attributes(profile);
    }


    public static class Attributes {
        private Profile profile;
        private final String status = "alive";
        private final boolean verified = false;

        public Attributes(Profile profile) {
            this.profile = profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }

    public static class Profile {
        private Channel channels;

        public Profile(String token) {
            this.channels = new Channel(token);
        }
    }

    public static class Channel {
        private String android;

        public Channel(String android) {
            this.android = android;
        }
    }
}
