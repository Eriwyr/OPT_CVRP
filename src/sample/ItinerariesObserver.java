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
    private static int scale = 4;
    private static int xOffset = 320;
    private static int yOffset = 100;
    private static float clientScale = 7.0f;
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
        clearItineraries();
        Client tempClient = null;
        Client logisticCenter = itineraries.getLogisticCenter();

        gc.setFill(Color.RED);
        gc.fillOval(logisticCenter.getX() * scale +xOffset, logisticCenter.getY() * scale +yOffset, logisticCenterScale, logisticCenterScale);

        for (Itinerary itinerary : itineraries.getItineraries()) {
            tempClient = logisticCenter;
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            for (Client client : itinerary.getItinerary()) {
                gc.fillOval(client.getX() * scale+xOffset, client.getY() * scale+yOffset, clientScale, clientScale);
                gc.strokeLine(client.getX() * scale+xOffset, client.getY() * scale+yOffset, tempClient.getX() * scale+xOffset, tempClient.getY() * scale+yOffset);
                gc.fillText(String.valueOf(client.getId()),client.getX() * scale+xOffset, client.getY() * scale+yOffset);
                tempClient = client;

            }
            gc.strokeLine(tempClient.getX() * scale+xOffset, tempClient.getY() * scale+yOffset, logisticCenter.getX() * scale+xOffset, logisticCenter.getY() * scale+yOffset);
        }
    }

    public void clearItineraries(){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
    }
}
