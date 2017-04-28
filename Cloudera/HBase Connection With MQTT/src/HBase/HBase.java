package HBase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class HBase implements MqttCallback{
	static Producer<String, String> producer;

	public void testMQTT() {

		String topic = "aqua";
		

		//String content = "{FirstName:Heba , LastName:badr}";
		int qos = 2;
		String broker = "tcp://m12.cloudmqtt.com:18232";
		String clientId = "JavaSample";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId,
					persistence);

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName("yikfssan");
			connOpts.setPassword("6JdVzKhtJwxS".toCharArray());
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);

			sampleClient.setCallback(this);
			sampleClient.subscribe(topic);

			System.out.println("Connected");
			//System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(/* content.getBytes() */);
			//message.setPayload(content.getBytes());

			message.setQos(qos);
			//sampleClient.publish(topic, message);
			//System.out.println("Message published");

			// sampleClient.disconnect();
			// System.out.println("Disconnected");
			// System.exit(0);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	@Override
	//ana 3amlt set l callback w subscribe w ana fadya ay msg tgyly yndh deh 
	public void messageArrived(String topic, MqttMessage message)
			throws Exception {
		System.out.println(message);
	    Configuration config = HBaseConfiguration.create();
	    org.apache.hadoop.hbase.client.HTable htable =  new HTable(config, "emp");
	    Put p = new Put(Bytes.toBytes("row1")); 
	    p.add(Bytes.toBytes("personal"), Bytes.toBytes("city"), Bytes.toBytes(message.toString()));
	    htable.put(p);
	    System.out.println("data inserted");
		//KeyedMessage<String, String> kafkaMessage = new KeyedMessage<String, String>("aqua", message.toString());
	//	producer.send(kafkaMessage);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}
   public static void main(String[] args) throws IOException {
	   HBase mqtt = new HBase();
		mqtt.testMQTT();
		Properties props = new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("serializer.class","kafka.serializer.StringEncoder");
		ProducerConfig config = new ProducerConfig(props); 
        producer = new Producer<String, String>(config); 
        

  }
}
