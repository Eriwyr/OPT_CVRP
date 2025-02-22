package dataStructure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import static java.lang.Math.*;
import static java.util.stream.Collectors.joining;

public class Itinerary {
    private LinkedList<Client> itinerary;
    private int totalQuantity;
    private double totalDistance;
    private Client logisticCenter;


    public Itinerary(Itinerary newItinerary) {
        this.itinerary = new LinkedList<>();
        for (Client c : newItinerary.getItinerary()) {

            itinerary.add(c);
        }
        this.logisticCenter = newItinerary.getLogisticCenter();
        if (itinerary.size() > 0) {
            calcTotalQuantity();
            calcTotaDistance();
        }
    }

    public Itinerary(LinkedList<Client> clients, Client logisticCenter) {
        this.itinerary = new LinkedList<>();

        for (Client c : clients) {

            itinerary.add(c);
        }

        this.logisticCenter = logisticCenter;

        if (itinerary.size() > 0) {
            calcTotalQuantity();
            calcTotaDistance();
        }
        //totalQuantity=0;
    }

    public Itinerary(Client logisticCenter) {
        this.itinerary = new LinkedList<>();
        this.totalQuantity = 0;
        this.totalDistance = 0;
        this.logisticCenter = logisticCenter;
    }

    public Client getLogisticCenter() {
        return logisticCenter;
    }


    public double calcTotaDistance() {
        ListIterator<Client> iterator = itinerary.listIterator();
        Client client = itinerary.getFirst();
        Client nextClient = null;
        double X = 0;
        double Y = 0;
        totalDistance = 0;
        while (iterator.hasNext()) {
            nextClient = iterator.next();

            X = pow(abs(((double) client.getX() - (double) nextClient.getX())), 2);
            Y = pow(abs(((double) client.getY() - (double) nextClient.getY())), 2);

            totalDistance += (sqrt(X + Y));

            client = nextClient;
        }
        return totalDistance;
    }

    public double distanceToLogisticCenter(Client client) {

        double X = pow(abs(((double) client.getX() - (double) logisticCenter.getX())), 2);
        double Y = pow(abs(((double) client.getY() - (double) logisticCenter.getY())), 2);

        return sqrt(X + Y);
    }


    public double calcTotaDistance(Client logisticCenter) {

        double X = pow(abs(((double) itinerary.getFirst().getX() - (double) logisticCenter.getX())), 2);
        double Y = pow(abs(((double) itinerary.getFirst().getY() - (double) logisticCenter.getY())), 2);

        double distance = sqrt(X + Y);

        X = pow(abs(((double) itinerary.getLast().getX() - (double) logisticCenter.getX())), 2);
        Y = pow(abs(((double) itinerary.getLast().getY() - (double) logisticCenter.getY())), 2);

        distance += sqrt(X + Y);

        return calcTotaDistance() + distance;
    }

    public void inversion(int index1, int index2) {
        try {

            Collections.swap(itinerary, index1, index2);

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }


    public int calcTotalQuantity() {
        totalQuantity = 0;
        for (Client client : itinerary) {
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

    public void add(int i, Client client) {
        itinerary.add(i, client);
    }

    public Client remove(int i) {

        try {
            return itinerary.remove(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(Client client) {

        try {
            itinerary.remove(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
