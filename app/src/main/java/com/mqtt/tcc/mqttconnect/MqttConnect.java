package com.mqtt.tcc.mqttconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;

public  class MqttConnect extends AppCompatActivity {




    public MqttAndroidClient mqttAndroidClient;
    public int qos= 1;
    final String topico= "thorgtbsMQTT/umidade";


    final String serverUri = "tcp://m14.cloudmqtt.com:18909";

    final String clientId = "MQTT_THORQUATO_TESTE_Android";
    final String subscriptionTopic = "";

    final String username = "txhtcwjv";
    final String password = "Fz8MLQifU88t";
    public TextView dataReceived;
    private RelativeLayout mqttConnect;
    public LineChart chart;
    public LineData data;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt_connect);


        dataReceived = findViewById(R.id.textoId);

        // mqttoptions
        MqttConnectOptions conectarSenha = new MqttConnectOptions();
        conectarSenha.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        conectarSenha.setUserName(username);
        conectarSenha.setPassword(password.toCharArray());

        mqttAndroidClient= new MqttAndroidClient(this.getApplicationContext(),serverUri,clientId);

        try{
            IMqttToken token = mqttAndroidClient.connect(conectarSenha);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MqttConnect.this,"conectado",Toast.LENGTH_SHORT).show();
                    assinatura();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
        mqttAndroidClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                dataReceived.setText(message.toString()+"%");


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void assinatura(){
        try{
             mqttAndroidClient.subscribe(topico,0);
            Toast.makeText(MqttConnect.this,"Tópico assinado",Toast.LENGTH_SHORT).show();


        }catch (MqttException e) {
            e.printStackTrace();
        }
    }
}