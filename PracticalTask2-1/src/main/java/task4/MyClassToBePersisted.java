package task4;

import java.io.Serializable;

public class MyClassToBePersisted implements Serializable {
    private String profile;
    private String group;

    /**
     * Constructor, initializes fields with empty strings
     */
    public MyClassToBePersisted() {
        this.profile = "";
        this.group = "";
    }

    /**
     * Constructor, initializes profile and group with provided values.
     * @param profile new profile.
     * @param group new group.
     */
    public MyClassToBePersisted(String profile, String group) {
        this.profile = profile;
        this.group = group;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "profile: " + this.profile + "\n"
                + "group: " + this.group;
    }
}
