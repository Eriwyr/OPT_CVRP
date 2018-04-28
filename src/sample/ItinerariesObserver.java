package sample;

import dataStructure.Client;
import dataStructure.Itineraries;
import dataStructure.Itinerary;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Observable;
import java.util.Observer;

public class ItinerariesObserver implements Observer{
    Canvas canvas;
    GraphicsContext gc;
    Itineraries itineraries;
    private boolean isFirst;
    private boolean isLastIteration;
    Color[] color;
    int refreshRate;
    private int callNumber;
    private int numberOfIteration;
    private static int scale = 4;
    private static int xOffset = 320;
    private static int yOffset = 100;
    private static float clientScale = 7.0f;
    private static float logisticCenterScale = 10.0f;

    public ItinerariesObserver(Canvas canvas, Itineraries itineraries,int refreshRate, int numberOfIteration) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.itineraries = itineraries;
        isFirst = true;
        this.refreshRate = refreshRate;
        isLastIteration = false;
        this.numberOfIteration = numberOfIteration;
        callNumber = 0;

    }

    @Override
    public void update(Observable o, Object arg) {
        if(callNumber % refreshRate ==0) {
            drawItineraries();
        }
        callNumber++;
        if(callNumber == numberOfIteration){
            drawItineraries();
        }
    }


    public void drawItineraries() {
        if (isFirst) {
            color = new Color[itineraries.size()];
            for (int i = 0; i < itineraries.size(); i++) {
                color[i] = Color.color(Math.random(), Math.random(), Math.random());
            }
            System.out.println("ok");
            isFirst = false;
        }

        clearItineraries();
        Client tempClient = null;
        Client logisticCenter = itineraries.getLogisticCenter();

        gc.setFont(new Font("Arial", 16));
        gc.setFill(Color.RED);
        gc.fillOval(logisticCenter.getX() * scale + xOffset, logisticCenter.getY() * scale + yOffset, logisticCenterScale, logisticCenterScale);
        gc.fillText("Logistic Center", logisticCenter.getX() * scale + xOffset + 10, logisticCenter.getY() * scale + yOffset);
        gc.setLineWidth(3);

        gc.setFill(Color.BLACK);
        gc.fillText("Iteration number : " + String.valueOf(callNumber), 24, 44);

        int j = 0;
        for (Itinerary itinerary : itineraries.getItineraries()) {
            tempClient = logisticCenter;
            gc.setFill(color[j]);
            gc.fillText("Itinerary " + j + " : " + String.valueOf(itinerary.calcTotaDistance()), 24, 94 + 30 * j);
            for (Client client : itinerary.getItinerary()) {
                gc.fillOval(client.getX() * scale + xOffset, client.getY() * scale + yOffset, clientScale, clientScale);
                gc.strokeLine(client.getX() * scale + xOffset, client.getY() * scale + yOffset, tempClient.getX() * scale + xOffset, tempClient.getY() * scale + yOffset);
                gc.setStroke(color[j]);
                gc.fillText(String.valueOf(client.getId()), client.getX() * scale + xOffset, client.getY() * scale + yOffset);
                tempClient = client;

            }
            gc.strokeLine(tempClient.getX() * scale + xOffset, tempClient.getY() * scale + yOffset, logisticCenter.getX() * scale + xOffset, logisticCenter.getY() * scale + yOffset);
            j++;
        }
        gc.setFill(Color.BLACK);

        gc.fillText("Total distance : " + String.valueOf(itineraries.calcDistance()), 24, 94 + 30 * j);
    }



    public void clearItineraries(){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
    }
}
