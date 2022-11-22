package com.example.mqttclient.config;

public abstract class MqttBrokerConfig {

    protected final String broker = "homeassistant.local";
    protected final int qos = 1;
    protected Boolean hasSSL = false; /*By default SSL is disabled */
    protected Integer port = 8123; /* Default port */
    protected final String userName = "uservps";
    protected final String password = "";
    protected final String TCP = "tcp://";
    protected final String SSL = "ssl://";

    public abstract void publishMessage(String topic, String message);

    public abstract void disconnect();

    /**
     * Custom Configuration
     *
     * @param broker
     * @param port
     * @param ssl
     * @param withUserNamePass
     */
    protected abstract void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass);

    /**
     * Default Configuration
     */
    protected abstract void config();
}
