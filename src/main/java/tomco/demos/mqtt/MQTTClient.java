package tomco.demos.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by tomco on 20/05/2017.
 */
public class MQTTClient {
    private MqttClient client;

    public MQTTClient(MqttClient client) {
        this.client = client;
    }

    public static MQTTClient connect(String broker, String clientId) {
        MqttClient mqtt = null;
        try {
            mqtt = new MqttClient(broker, clientId, new MemoryPersistence());
            mqtt.connect();
            return new MQTTClient(mqtt);
        } catch (MqttException e) {
            throw new RuntimeException("Could not connect to broker.", e);
        }
    }

    public void sendMessage(String topic, String content) {
        this.sendMessage(topic, content.getBytes());
    }

    public void sendMessage(String topic, byte[] content) {
        MqttMessage message = new MqttMessage(content);
        message.setQos(0);
        try {
            if(client.isConnected()) {
                client.publish(topic, message);
            } else {
                System.out.println("Not connected yet!");
            }
        } catch (MqttException e) {
            throw new RuntimeException("Unable to publish message.", e);
        }
    }

    public void subscribe(String topic, IMqttMessageListener messageListener) {
        try {
            client.subscribe(topic, messageListener);
        } catch (MqttException e) {
            throw new RuntimeException("Unable to subscribe");
        }
    }
}
