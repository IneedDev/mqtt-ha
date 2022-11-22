package com.example.mqttclient.publisher;

import com.example.mqttclient.config.MqttBrokerConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class MqttPublisherImpl extends MqttBrokerConfig implements MqttCallback {


    private static final String fota_fetch_record = "fota_fetch_record";
    private String brokerUrl = null;
    final private String colon = ":";
    final private String clientId = UUID.randomUUID().toString();

    public MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;
    private static final Logger logger = LoggerFactory.getLogger(MqttPublisherImpl.class);

    private MqttPublisherImpl() {
        this.config();
    }
    private MqttPublisherImpl(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
        this.config(broker, port, ssl, withUserNamePass);
    }

    public static MqttPublisherImpl getInstance() {
        return new MqttPublisherImpl();
    }


    public static MqttPublisherImpl getInstance(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
        return new MqttPublisherImpl(broker, port, ssl, withUserNamePass);
    }

    @PostConstruct
    public void run() {

        String topic = "outTemperature";
        String tempValue = "23";
        String lightTopic = "inLight/cmnd/Power";
        String lightvalue = "0";
        String lightTopicHa = "inLight";
        String lightvalueHa = "0";

        String lightTopicII = "inLightII";
        String lightvalueII = "0";

//        while (true) {
//            Integer value = getRandomNumber(10, 20);
//            publishMessage(topic, value.toString());
//            logger.info("Message published on topic {} with message {}", topic, value);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        publishMessage(topic, tempValue);
        publishMessage(lightTopic, lightvalue);
        publishMessage(lightTopicHa, lightvalueHa);

        publishMessage(lightTopicII, lightvalueII);

        logger.info("Message published on topic {} with message {}", lightTopic, lightvalue);

    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void publishMessage(String topic, String message) {
        try {
            MqttMessage mqttmessage = new MqttMessage(message.getBytes());
            mqttmessage.setQos(this.qos);
            mqttmessage.setRetained(false);
            this.mqttClient.publish(topic, mqttmessage);
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    @Override
    public void disconnect() {
        try {
            this.mqttClient.disconnect();
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }

    @Override
    public void connectionLost(Throwable arg0) {
        logger.info("Connection Lost");

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        logger.info("delivery completed");

    }


    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        // Leave it blank for Publisher

    }
    @Override
    protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
        // Like we did in MqttSubscribe
    }

    @Override
    protected void config() {
        logger.info("Inside Config with parameter");
        String ha = "homeassistant.local:1883";
        String vps = "";
        this.brokerUrl = this.TCP + vps;
        logger.info("broker url {}", this.brokerUrl);

        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            this.connectionOptions.setPassword(this.password.toCharArray());
            this.connectionOptions.setUserName(this.userName);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            logger.error("Broker not connected");
        }    }
}
