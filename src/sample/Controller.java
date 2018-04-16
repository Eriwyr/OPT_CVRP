package sample;

import algorithms.SimulatedAnnealing;
import dataStructure.Client;
import dataStructure.Itineraries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import manageFiles.ParseFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    Itineraries itineraries;
    ItinerariesObserver obs;

    Canvas canvas;

    public Controller() {
        this.itineraries = new Itineraries();
        obs = null;
    }

    public void loadClientFromFile(){
        ParseFiles parser = new ParseFiles("src/data/data01.txt");
        try {
            ArrayList<Client> clients =  parser.createClientsFromFile();
            itineraries.generateFirstItineraries(clients);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("file doesn't exist");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void play(ActionEvent event){
        Canvas canvas = (Canvas) ((Button)event.getSource()).getScene().lookup("#canvas");
        System.out.println(canvas);
        obs = new ItinerariesObserver(canvas,itineraries);
        itineraries.addObserver(obs);
        loadClientFromFile();
        //SimulatedAnnealing sim = new SimulatedAnnealing(itineraries);


    }

    @FXML
    public void startSim(ActionEvent event){

        SimulatedAnnealing sim = new SimulatedAnnealing(itineraries);
        Thread t = new Thread(sim);
        t.start();
    }

    @FXML
    public void stop(ActionEvent event){
        if(obs != null){
            obs.clearItineraries();
        }
    }

}
