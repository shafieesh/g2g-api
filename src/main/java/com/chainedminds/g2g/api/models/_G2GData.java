package com.chainedminds.g2g.api.models;

import com.chainedminds.models._FileData;
import com.chainedminds.utilities.DynamicConfig;

import java.util.List;

public class _G2GData {

    public  AccountData account = new AccountData();
    public  ClientData client = new ClientData();

    public int request;
    public Integer subRequest;
    public int response;

    public Long chatID;

    public String message;
    public List<String> messages;

    public _FileData file;
    public List<_FileData> files;

    public Order order;
    public List<Order> orders;

    public Offer offer;
    public List<Offer> offers;

    public Customer customer;
    public List<Customer> customers;
    public List<String> customerIDs;


    public static class AccountData {

        public static int id = Integer.parseInt(DynamicConfig.getMap("API-G2G-ID"));
        public static String credential = DynamicConfig.getMap("API-G2G-Credential");
    }

    public static class ClientData {

        public static String appName = "API";
        public final String platform = "API";
        public final String version = "1.0.0";
        public static String language = "en";
    }

    public static class Order {

        public String store;
        public String type;
        public String link;
        public String game;
        public String server;
        public String sellID;
        public String buyID;
        public String title;
        public String customerID;
        public String customer;
        public String amount;
        public String price;
        public String tax;
        public String state;
        public String logs;
        public long creationTime;
        public long lastCheck;
        public long lastUpdate;
    }

    public static class Offer {

        public String id;
        public String link;
        public String price;
        public String store;
    }

    public static class Customer {

        public String id;
        public String name;
        public Integer level;
        public transient Boolean isActive = true;
        public transient Boolean notFound = false;
        public Long registrationTime;
        public Long lastCheck;
        public Long lastUpdate;
    }
}
