package sample;

import algorithms.GeneticAlgorithm;
import dataStructure.Client;
import dataStructure.Itineraries;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {
    private static Stage primaryStage;

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage); // **Set the Stage**
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();

    }

    public static void main(String[] args) {
       launch(args);

       /* Client A = new Client(0, 0, 0, 0);
        Client client1 = new Client(1, 1, 1, 0);
        Client client2 = new Client(2, 1, 1, 0);
        Client client3 = new Client(3, 1, 1, 0);
        Client client4 = new Client(4, 1, 1, 0);
        Client client5 = new Client(5, 1, 1, 0);
        Client client6 = new Client(6, 1, 1, 0);
        Client client7 = new Client(7, 1, 1, 0);
        Client client8 = new Client(8, 1, 1, 0);
        Client client9 = new Client(9, 1, 1, 0);
        Client client10 = new Client(10, 1, 1, 0);
        Client client11 = new Client(11, 1, 1, 0);
        Client client12 = new Client(12, 1, 1, 0);
        Client client13 = new Client(13, 1, 1, 0);
        Client client14 = new Client(14, 1, 1, 0);
        Client client15 = new Client(15, 1, 1, 0);
        Client client16 = new Client(16, 1, 1, 0);
        Client client17 = new Client(17, 1, 1, 0);
        Client client18 = new Client(18, 1, 1, 0);
        Client client19 = new Client(19, 1, 1, 0);
        Client client20 = new Client(20, 1, 1, 0);

        LinkedList list =new LinkedList();
        list.add(client1);
        list.add(client2);
        list.add(client3);
        list.add(client4);
        list.add(client5);
        list.add(client6);
        list.add(client7);
        list.add(client8);
        list.add(client9);
        list.add(client10);
        list.add(client11);
        list.add(client12);
        list.add(client13);
        list.add(client14);
        list.add(client15);
        list.add(client16);
        list.add(client17);
        list.add(client18);
        list.add(client19);
        list.add(client20);

        Itineraries itineraries = new Itineraries(list, A);
        itineraries.generateRandomItineraries(list);


        GeneticAlgorithm g = new GeneticAlgorithm();
        g.generateInitialPopulation();
        System.out.println("finish");*/
    }
}
