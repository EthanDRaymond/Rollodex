package com.psu.hack.rollodex.card;

import org.json.JSONException;
import org.json.JSONObject;

public class Card {

    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_PHONE_NUMBER = "phone_number";
    private static final String JSON_KEY_EMAIL_ADDRESS = "email_address";

    private String name;
    private String phoneNumber;
    private String emailAddress;

    /**
     * Generate a card
     * @param name          the name of the card owner
     * @param phoneNumber   the owner's phone number
     * @param emailAddress  the owner's email address
     */
    public Card(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Generate a card
     * @param jsonObject    the json code
     * @throws JSONException    thrown when there is a problem parsing the json.
     */
    public Card(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString(JSON_KEY_NAME);
        this.phoneNumber = jsonObject.getString(JSON_KEY_PHONE_NUMBER);
        this.emailAddress = jsonObject.getString(JSON_KEY_EMAIL_ADDRESS);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Converts this instance to json code
     * @return      a json object containing this instance
     * @throws JSONException    thrown if there is a problem creating the json code
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_KEY_NAME, this.name);
        jsonObject.put(JSON_KEY_PHONE_NUMBER, this.phoneNumber);
        jsonObject.put(JSON_KEY_EMAIL_ADDRESS, this.emailAddress);
        return jsonObject;
    }

}
