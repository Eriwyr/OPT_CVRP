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
import javafx.scene.control.TextField;
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
    private ArrayList<Double> distancies;

    private int numberOfIteration;
    private int temperatureNumber;
    private int coolingRate;


    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    private TextField nbIterations;

    @FXML
    private TextField temperature;


    @FXML
    Canvas canvas;

    @FXML
    LineChart<String,Number> chart;

    public Controller() {

        obs = null;
        t=null;
        distancies = new ArrayList<>();
        this.numberOfIteration = 0;
        this.temperatureNumber =0;
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
        reset();

        if(check1.isSelected()) {

            getParametersAnnealing();

            this.itineraries = new Itineraries();
            Canvas canvas = (Canvas) ((Button) event.getSource()).getScene().lookup("#canvas");
            System.out.println(canvas);
            obs = new ItinerariesObserver(canvas, itineraries,10,numberOfIteration,distancies);
            itineraries.addObserver(obs);
            loadItinirariesFromFile();
            SimulatedAnnealing sim = new SimulatedAnnealing(itineraries, 1000, 10, 10, 0.99f);
            t = new Thread(sim);
            t.start();
        }
        else if(check2.isSelected()){

            getParametersGenetic();
            this.itineraries = new Itineraries();
            Canvas canvas = (Canvas) ((Button)event.getSource()).getScene().lookup("#canvas");
            System.out.println(canvas);
            //itineraries = new Itineraries();
            obs = new ItinerariesObserver(canvas,itineraries,100,numberOfIteration,distancies);
            itineraries.addObserver(obs);
            loadClientsFromFile();
            itineraries.generateRandomItineraries(clientsFromFile);

            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(clientsFromFile, numberOfIteration ,50,80,  itineraries, 100);

            t = new Thread(geneticAlgorithm);
            t.start();
        }
    }

    @FXML
    public void stop(ActionEvent event){
        reset();
    }

    public void reset(){
        if(obs != null){
            obs.clearItineraries();
            itineraries.deleteObserver(obs);
        }
        if(distancies != null){
            distancies.clear();
        }
        if(t!=null){
            t.interrupt();
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

    @FXML
    public void displayChart(ActionEvent event){
        chart.getData().clear();
        XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
        for(int i = 0 ; i<distancies.size();i++){
            if(i%10==0) {
                series.getData().add(new XYChart.Data<String, Number>(String.valueOf(i), distancies.get(i)));
            }
        }
        series.setName("Evolution de la distance totale");
        chart.setAnimated(false);
        chart.getData().add(series);
    }

    public int getNbIterations(){
        int iterations = 0;

        String tmp = nbIterations.getText();

        try {
            if(tmp.matches("[0-9]*")){
                iterations = Integer.parseInt(tmp);

            }
        }
        catch(NumberFormatException e){
            return 0;
        }
        return iterations;


    }

    public int getTemperature(){
        int temperatureNumber = 0;

        String tmp = nbIterations.getText();

        try {
            if(tmp.matches("[0-9]*")){
                temperatureNumber = Integer.parseInt(tmp);

            }
        }
        catch(NumberFormatException e){
            return 0;
        }
        return temperatureNumber;

    }

    public void getParametersAnnealing(){
       this.numberOfIteration = getNbIterations();
        this.temperatureNumber = getTemperature();
    }

    public void getParametersGenetic(){

    }


}
