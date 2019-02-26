package com.trust.id2.network.req;

public class FirebaseBody {
    private Data data;

    public FirebaseBody(String token, String id) {
        data = new Data(token, id);
    }

    private class Data {
        private String id;
        private String type = "person";
        private Attrib attributes;

        public Data(String token, String id) {
            this.attributes = new Attrib(token);
            this.id = id;
        }
    }

    private class Attrib {
        private Profile profile;

        public Attrib(String token) {
            this.profile = new Profile(token);
        }
    }

    private class Profile {
        private Channels channels;

        public Profile(String token) {
            this.channels = new Channels(token);
        }
    }

    private class Channels {
        private String android;

        public Channels(String android) {
            this.android = android;
        }
    }
}
