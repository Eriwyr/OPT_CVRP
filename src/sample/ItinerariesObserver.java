package sample;

import dataStructure.Client;
import dataStructure.Itineraries;
import dataStructure.Itinerary;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

public class ItinerariesObserver implements Observer{
    Canvas canvas;
    GraphicsContext gc;
    Itineraries itineraries;

    private int displayNb =9;

    private static int scale = 4;
    private static float clientScale = 5.0f;
    private static float logisticCenterScale = 8.0f;

    public ItinerariesObserver(Canvas canvas, Itineraries itineraries) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.itineraries = itineraries;
    }

    @Override
    public void update(Observable o, Object arg) {
        drawItineraries();
    }

    public void drawItineraries(){
        displayNb++;
        if (displayNb%10==0) {
            clearItineraries();
            Client tempClient = null;
            Client logisticCenter = itineraries.getLogisticCenter();

            gc.setFill(Color.RED);
            gc.fillOval(logisticCenter.getX() * scale, logisticCenter.getY() * scale, logisticCenterScale, logisticCenterScale);

            for (Itinerary itinerary : itineraries.getItineraries()) {
                tempClient = logisticCenter;
                gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                for (Client client : itinerary.getItinerary()) {
                    gc.fillOval(client.getX() * scale, client.getY() * scale, clientScale, clientScale);
                    gc.strokeLine(client.getX() * scale, client.getY() * scale, tempClient.getX() * scale, tempClient.getY() * scale);
                    tempClient = client;

                }
                gc.strokeLine(tempClient.getX() * scale, tempClient.getY() * scale, logisticCenter.getX() * scale, logisticCenter.getY() * scale);

            }
        }

    }

    public void clearItineraries(){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
    }
}
