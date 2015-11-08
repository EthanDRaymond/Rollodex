package com.psu.hack.rollodex.card;

/**
 * Created by matt on 11/7/2015.
 */
public class UserCard extends Card {

    public static String name, phoneNumber, emailAddress;

    public UserCard(String name, String phoneNumber, String emailAddress) {
        super( name,  phoneNumber,  emailAddress);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
}
