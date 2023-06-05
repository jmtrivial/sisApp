  package com.cathythome.sis;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

import androidx.appcompat.view.menu.MenuView;

  public class Server implements Runnable{
    private BufferedReader is;
    private BufferedWriter os;
    private BlockingQueue<String> queue;
    public MainActivity activity;
    public String message, config;
    private char groupe;

      public Server(MainActivity activity, BlockingQueue<String> queue){
        this.queue = queue;
        this.activity = activity;
        this.message="";
        this.config = "";
    }

    @Override
    public void run() {
        // final String serverHost = "192.168.0.10"; // CRDV
        final String serverHost = "10.0.2.2"; // machine virtuelle dans android studio
        Socket socketOfClient = null;
        this.os = null;
        this.is = null;

        while(true){
            try { //connexion avec python
                socketOfClient = new Socket(serverHost, 14000);

                this.os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                this.is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + serverHost);
            } catch (IOException e) {
                System.err.println(e);
            }
            if(socketOfClient != null){
                Log.d("connected", "connected");
                activity.setWaitingFragment();
                break;
            }
        }
        this.server();
    }

    public void server(){
        //quand le serveur reçoit un message de python contenant les infos de construction, il le récupère et le transfère à l'activité principale
        try {
            this.config = this.is.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //this.config = "<communication_tablette><groupe numbers=\"0\" /><individuels numbers=\"12345678\" /><sonAmbiant titre=\"Son_de_voiture\" /><sonAmbiant titre=\"Son_de_coq\" /><sonPonctuel titre=\"Son_de_pluie\" /><sonPonctuel titre=\"Son_de_tram\" /></communication_tablette>";

        this.groupe = this.config.charAt(41);

        this.activity.removeWaitingFragment();

        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                activity.setData(config);
            }
        });

        while (true) { //envoie les infos de la tablette vers python
            try {
                Integer ok = Character.compare(this.groupe, (char) 0);
                if(queue.isEmpty()) {
                    if (is.ready()){
                        this.message = this.is.readLine();
                        Log.d("stop", this.message);
                        this.activity.creation();
                        this.server();
                    }
                    if (ok > 48) {
                        queue.offer("<communication_appli><demande info=\"1\" /></communication_appli>");
                    }
                }
                if(queue.isEmpty() == false) {
                    String data = queue.take();
                    this.os.write(data);
                    try{
                        this.os.flush();
                    }
                    catch(SocketException e){
                        Log.d("broken", "cassé");
                        this.config = "";
                        this.activity.creation();
                        this.run();
                    }
                    if(data.contains("demande")){
                        this.message = this.is.readLine();

                        this.activity.runOnUiThread(new Runnable() {
                            public void run() {
                                activity.setCompteur(message);
                            }
                        });
                    }
                }
                sleep(200);
            } catch (InterruptedException | IOException e) {
                Log.d("system", String.valueOf(e));
                e.printStackTrace();
            }
        }
    }
}
