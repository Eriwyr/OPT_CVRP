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


    public Itinerary() {
        this.itinerary = new LinkedList<>();
    }

    public Itinerary(LinkedList<Client> clients, Client logisticCenter) {
        this.itinerary = new LinkedList<>();
        for (Client c : clients) {

            itinerary.add(c);
        }
        this.logisticCenter = logisticCenter;
        if (itinerary.size()>0) {
            calcTotalQuantity();
            calcTotaDistance();
        }
        //totalQuantity=0;
    }

    public Client getLogisticCenter() {
        return logisticCenter;
    }

    public int CalcTotalQuantity(){
        totalQuantity=0;
        for (Client client:itinerary) {
            totalQuantity += client.getQuantity();
        }
        return totalQuantity;

    }

    public double distanceDeuxClients(Client client1, Client client2) {
        double X = 0;
        double Y = 0;

        X = pow(abs(((double)client1.getX() - (double)client2.getX())),2);
        Y = pow(abs(((double)client1.getY() - (double)client2.getY())),2);

        return (sqrt(X+Y));
    }

    public double calcTotaDistance(){
        ListIterator<Client> iterator = itinerary.listIterator();
        Client client = itinerary.getFirst();
        Client nextClient = null;
        double X = 0;
        double Y = 0;
        totalDistance=0;
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


    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append("[linked list : ")
                .append(itinerary.getClass().getName())
                .append("@")
                .append(Integer.toHexString(itinerary.hashCode()))
                .append("]\n");

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


    public Client get(int i) {
        return itinerary.get(i);
    }

    public void add(Client client) {
        itinerary.add(client);
    }
}
