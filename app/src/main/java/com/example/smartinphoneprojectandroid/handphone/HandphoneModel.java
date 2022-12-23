package com.example.smartinphoneprojectandroid.handphone;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class HandphoneModel {
    @SerializedName("id")
    private Integer id;

    @SerializedName("phone_photo")
    private String phone_photo;

    @SerializedName("name")
    private String name;

    @SerializedName("network")
    private String network;

    @SerializedName("launch")
    private Date launch;

    @SerializedName("body")
    private String body;

    @SerializedName("display")
    private String display;

    @SerializedName("platform")
    private String platform;

    @SerializedName("memory")
    private String memory;

    @SerializedName("maincam")
    private String maincam;

    @SerializedName("selfcam")
    private String selfcam;

    @SerializedName("sound")
    private String sound;

    @SerializedName("comms")
    private String comms;

    @SerializedName("features")
    private String features;

    @SerializedName("battery")
    private String battery;

    @SerializedName("tests")
    private String tests;

    public HandphoneModel(Integer id, String phone_photo, String name, String network, Date launch, String body, String display, String platform, String memory, String maincam, String selfcam, String sound, String comms, String features, String battery, String tests) {
        this.id = id;
        this.phone_photo = phone_photo;
        this.name = name;
        this.network = network;
        this.launch = launch;
        this.body = body;
        this.display = display;
        this.platform = platform;
        this.memory = memory;
        this.maincam = maincam;
        this.selfcam = selfcam;
        this.sound = sound;
        this.comms = comms;
        this.features = features;
        this.battery = battery;
        this.tests = tests;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone_photo() {
        return phone_photo;
    }

    public void setPhone_photo(String phone_photo) {
        this.phone_photo = phone_photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Date getLaunch() {
        return launch;
    }

    public void setLaunch(Date launch) {
        this.launch = launch;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMaincam() {
        return maincam;
    }

    public void setMaincam(String maincam) {
        this.maincam = maincam;
    }

    public String getSelfcam() {
        return selfcam;
    }

    public void setSelfcam(String selfcam) {
        this.selfcam = selfcam;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getComms() {
        return comms;
    }

    public void setComms(String comms) {
        this.comms = comms;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }
}
