package sample;

import algorithms.GeneticAlgorithm;
import algorithms.SimulatedAnnealing;
import dataStructure.Client;
import dataStructure.Itineraries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import manageFiles.ParseFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {
    private Itineraries itineraries;
    private ItinerariesObserver obs;
    private LinkedList<Client> clientsFromFile;

    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    Canvas canvas;

    public Controller() {
        this.itineraries = new Itineraries();
        obs = null;
        //algoChoice.getItems().add("Simulated Annealing");
        //algoChoice.getItems().add("Genetic Algorithm");



    }

    public void loadItinirariesFromFile(){
        ParseFiles parser = new ParseFiles("src/data/data01.txt");
        try {
            clientsFromFile =  parser.createClientsFromFile();
            itineraries.generateFirstItineraries(clientsFromFile);
            //itineraries.generateRandomItineraries(clients);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("file doesn't exist");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadClientsFromFile(){
        ParseFiles parser = new ParseFiles("src/data/data01.txt");
        try {
            clientsFromFile =  parser.createClientsFromFile();
            //itineraries.generateFirstItineraries(clientsFromFile);
            //itineraries.generateRandomItineraries(clientsFromFile);
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
        /*Canvas canvas = (Canvas) ((Button)event.getSource()).getScene().lookup("#canvas");
        System.out.println(canvas);
        obs = new ItinerariesObserver(canvas,itineraries);
        itineraries.addObserver(obs);
        loadItinirariesFromFile();*/
        //SimulatedAnnealing sim = new SimulatedAnnealing(itineraries);


    }

    @FXML
    public void startSim(ActionEvent event){
        // === Simulated Annealing ===
        Canvas canvas = (Canvas) ((Button)event.getSource()).getScene().lookup("#canvas");
        System.out.println(canvas);
        obs = new ItinerariesObserver(canvas,itineraries);
        itineraries.addObserver(obs);
        loadItinirariesFromFile();
        SimulatedAnnealing sim = new SimulatedAnnealing(itineraries);
        Thread t = new Thread(sim);
        t.start();

        /*Canvas canvas = (Canvas) ((Button)event.getSource()).getScene().lookup("#canvas");
        System.out.println(canvas);
        //itineraries = new Itineraries();
        obs = new ItinerariesObserver(canvas,itineraries);
        itineraries.addObserver(obs);
        loadClientsFromFile();
        itineraries.generateRandomItineraries(clientsFromFile);

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(clientsFromFile, 7000 ,50,80,  itineraries, 100);

        Thread t = new Thread(geneticAlgorithm);
        t.start();*/
    }

    @FXML
    public void stop(ActionEvent event){
        if(obs != null){
            obs.clearItineraries();
        }
    }

    @FXML
    public void chooseAlgorithm1(ActionEvent event){
        check1 = (CheckBox) ((CheckBox)event.getSource()).getScene().lookup("#check1");
        check2.setSelected(false);
        System.out.println("Simulated Annealing choosed");
    }
    @FXML
    public void chooseAlgorithm2(ActionEvent event){
        check2 = (CheckBox) ((CheckBox)event.getSource()).getScene().lookup("#check2");
        check1.setSelected(false);
        System.out.println("Genetic Algorithm choosed");
    }

}
