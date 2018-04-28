package sample;

import algorithms.GeneticAlgorithm;
import algorithms.SimulatedAnnealing;
import dataStructure.Client;
import dataStructure.Itineraries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    private Thread t;

    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    Canvas canvas;

    @FXML
    LineChart<String,Number> chart;

    public Controller() {

        obs = null;
        t=null;
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
    public void startSim(ActionEvent event){
        // === Simulated Annealing ===
        if(obs != null){
            obs.clearItineraries();
            itineraries.deleteObserver(obs);
        }
        ArrayList<Integer> tab = new ArrayList<>();
        tab.add(10);
        tab.add(20);
        XYChart.Series<String,Number>  series = new XYChart.Series<String,Number>();
        series.getData().add(new XYChart.Data<String, Number>("1",100));
        series.getData().add(new XYChart.Data<String, Number>("1",200));
        chart.getData().add(series);
        if(t!=null){
            t.interrupt();
        }

        if(check1.isSelected()) {
            this.itineraries = new Itineraries();
            Canvas canvas = (Canvas) ((Button) event.getSource()).getScene().lookup("#canvas");
            System.out.println(canvas);
            obs = new ItinerariesObserver(canvas, itineraries,1,1000);
            itineraries.addObserver(obs);
            loadItinirariesFromFile();
            SimulatedAnnealing sim = new SimulatedAnnealing(itineraries);
            t = new Thread(sim);
            t.start();
        }
        else if(check2.isSelected()){
            this.itineraries = new Itineraries();
            Canvas canvas = (Canvas) ((Button)event.getSource()).getScene().lookup("#canvas");
            System.out.println(canvas);
            //itineraries = new Itineraries();
            obs = new ItinerariesObserver(canvas,itineraries,100,7000);
            itineraries.addObserver(obs);
            loadClientsFromFile();
            itineraries.generateRandomItineraries(clientsFromFile);

            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(clientsFromFile, 7000 ,50,80,  itineraries, 100);

            t = new Thread(geneticAlgorithm);
            t.start();
            System.out.println("OKOKOKOKOKOK");
        }
    }

    @FXML
    public void stop(ActionEvent event){
        if(obs != null){
            obs.clearItineraries();
        }
        itineraries.deleteObserver(obs);
        t.interrupt();
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

    public void reset(){

    }

}
