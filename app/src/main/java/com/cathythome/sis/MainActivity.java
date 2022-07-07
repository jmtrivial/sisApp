package com.cathythome.sis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cathythome.sis.Ambiants.Ambiants;
import com.cathythome.sis.Ponctuels.IndividualAdapter;
import com.cathythome.sis.Ponctuels.Ponctuels;
import com.cathythome.sis.Sons.Sons;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MainActivity extends AppCompatActivity {

    public int[] alpha;
    private String[] types;
    private boolean active;
    private ArrayList[] donnees;
    private ArrayList infos, sonsAmbiants, sonsPonctuels;
    private Sons sons;
    private Ambiants ambiants;
    private Ponctuels ponctuels;
    private Server p;
    private String data;
    private BlockingQueue<String> queue;
    private ArrayList<String> soundsList;
    public WaitingFragment waitingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = new LinkedBlockingQueue<String>();
        p = new Server(this, queue);
        this.creation();
        final FrameLayout layout = (FrameLayout) findViewById(R.id.frame_layout_main);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                } else {
                    layout.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);
                }
                new Thread(p).start();

            }
        });
    }

    public void creation(){
        this.data = "";
        this.active=false;
        this.alpha = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75, 75, 75, 75, 75, 75, 75, 75, 75};

        this.configureAndShowFragment();
    }

    public void setData(String data){
        this.data = data;

        xmlRead(this.data);

        Log.d("data", String.valueOf(this.donnees[0]));
        sons = new Sons(this, this.donnees);
        ambiants = new Ambiants(this);
        ponctuels = new Ponctuels(this, this.donnees);

        soundsList = new ArrayList<String>();
        soundsList.add("Glisser un son ici");

    }

    public void sending(String data){ //envoie au serveur, pour envoyer à python
        queue.offer(data);
    }

    public void send(String type, int enceinte, String id, String data){ //fabrique le XML pour envoyer au serveur

        String send = "<communication_appli>";
        send+= "<type style=\"" + type + "\" />";
        send+= "<enceinte numero=\"" + String.valueOf(enceinte) + "\" />";

        switch (id){
            case "volume":
                send+= "<volume value=\"" + data + "\" />";
                break;
            case "state":
                send+= "<state value=\"" + data + "\" />";
                break;
            case "son":
                send+= "<son titre=\"" + data + "\" />";
                break;
            case "trajectoire":
                send+= "<trajectoire value=\"" + data + "\" />";
                break;
            case "automatique":
                send+= "<automatique value=\"" + data + "\" />";
                break;
            case "stop":
                send+= "<stop value=\"" + data + "\" />";
                break;
        }
        send += "</communication_appli>";
        sending(send);
        Log.d("envoi", send);
    }

    public void xmlRead(String data){ // décompose le XML pour construire l'appli
        this.data = data;

        this.infos = new ArrayList<String>();
        this.sonsAmbiants = new ArrayList<String>();
        this.sonsPonctuels = new ArrayList<String>();

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // parse XML file
            InputStream is = new ByteArrayInputStream(this.data.getBytes(UTF_8));
            //InputStream is = getAssets().open("file.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            // optional, but recommended
            doc.getDocumentElement().normalize();

            //les infos d'enceintes
            this.types = new String[]{"groupe", "individuels"};
            for (int i=0; i<this.types.length; i++){
                NodeList list = doc.getElementsByTagName(this.types[i]);
                Node node = list.item(0);
                Element element = (Element) node;
                this.infos.add(element.getAttribute("numbers"));
            }

            //les infos de son
            NodeList list = doc.getElementsByTagName("sonAmbiant");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                Element element = (Element) node;
                this.sonsAmbiants.add(element.getAttribute("titre"));
            }

            NodeList list2 = doc.getElementsByTagName("sonPonctuel");
            for (int i = 0; i < list2.getLength(); i++) {
                Node node = list2.item(i);
                Element element = (Element) node;
                this.sonsPonctuels.add(element.getAttribute("titre"));
            }

            //tableau contenant toutes les données de construction de l'appli
            this.donnees = new ArrayList[]{this.infos, this.sonsAmbiants, this.sonsPonctuels};

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void configureAndShowFragment(){
        this.waitingFragment = (WaitingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (this.waitingFragment == null) {
            // B - Create new main fragment
            this.waitingFragment = new WaitingFragment(this.active);
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, waitingFragment)
                    .commit();
        }
    }

    public void removeWaitingFragment(){
        getSupportFragmentManager().beginTransaction()
                .remove(waitingFragment)
                .commit();
    }

    public void setCompteur(String compteursString){
        if(String.valueOf(this.infos.get(0)).equals("0") == false) {
            compteursString = compteursString.substring(1, compteursString.length() - 1);
            String[] compteursStr = compteursString.split(", ");
            int[] compteurs = new int[compteursStr.length];
            for(int i=0; i<compteursStr.length; i++) {
                compteurs[i] = Integer.parseInt(compteursStr[i]);
            }
            this.ponctuels.setCompteurs(compteurs);
        }
    }

    public void setAlpha(int[] i) {
        this.alpha = i;
    }

    public void setWaitingFragment() {
        waitingFragment.setAffichage();
    }
}