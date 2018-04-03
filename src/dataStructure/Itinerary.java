package dataStructure;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.joining;

public class Itinerary {
    private LinkedList<Client> itinerary;
    private int totalQuantity;
    private double totalDistance;
    private Client logisticCenter;



    public Itinerary(LinkedList<Client> itinerary, Client logisticCenter) {
        this.itinerary = itinerary;
        this.logisticCenter = logisticCenter;
        calcTotalQuantity();
        calcTotaDistance();
    }

    public int CalcTotalQuantity(){

        for (Client client:itinerary) {
            totalQuantity += client.getQuantity();
        }
        return totalQuantity;

    }

    public double calcTotaDistance(){
        ListIterator<Client> iterator = itinerary.listIterator();
        Client client = itinerary.getFirst();
        Client nextClient = null;
        double X = 0;
        double Y = 0;

        while(iterator.hasNext()){
            nextClient = iterator.next();

            X = pow(abs(((double)client.getX() - (double)nextClient.getX())),2);
            Y = pow(abs(((double)client.getY() - (double)nextClient.getY())),2);

            totalDistance += (sqrt(X+Y));

            client = nextClient;
        }
        return totalDistance;
    }

    public void inversion(int index1, int index2) {
        try {

            Collections.swap(itinerary, index1, index2);

        } catch (IndexOutOfBoundsException e ) {
            System.out.println(e.getMessage());
        }
    }

    public int calcTotalQuantity() {
        totalQuantity = 0;
        for (Client client :itinerary ) {
            totalQuantity += client.getQuantity();

        }
        return totalQuantity;
    }

    public LinkedList<Client> getItinerary() {
        return itinerary;
    }

    public Client getFirstClient() {
        return itinerary.getFirst();
    }

    public Client getLastClient() {
        return itinerary.getLast();
    }


    /*@Override
    public String toString() {



        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("clients  :  \n");

        itinerary.forEach(client -> {

            toStringBuilder.append(client.getId())
                    .append(", ");

        });

        return toStringBuilder.toString();

    }*/


    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("Clients : ");


        final String arcListToString = itinerary
                .stream()
                .map(Client::toString)
                .collect(joining(", "));
        toStringBuilder.append(arcListToString)
                .append(" total quantity : ")
                .append(calcTotalQuantity());

        return toStringBuilder.toString();
    }

    public int size() {
        return itinerary.size();
    }


}
