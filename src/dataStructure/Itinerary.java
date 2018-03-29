package dataStructure;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.joining;

public class Itinerary {
    private LinkedList<Client> itinerary;
    private int totalQuantity;
    private int totalDistance;


    public Itinerary(LinkedList<Client> itinerary) {
        this.itinerary = itinerary;
        calcTotalQuantity();
        calcTotaDistance();



    }

    public void CalcTotalQuantity(){

        for (Client client:itinerary) {
            totalQuantity += client.getQuantity();
        }

    }

    public void calcTotaDistance(){
        ListIterator<Client> iterator = itinerary.listIterator();
        Client client = itinerary.getFirst();
        Client nextClient = null;
        double X = 0;
        double Y = 0;

        while(iterator.hasNext()){
            nextClient = iterator.next();

            X = pow(((double)client.getX() - (double)nextClient.getX()),2);
            Y = pow(((double)client.getY() - (double)nextClient.getY()),2);

            totalDistance += (sqrt(X+Y));

            client = nextClient;
        }
    }

    public void inversion(int index1, int index2) {
        try {

            Collections.swap(itinerary, index1, index2);

        } catch (IndexOutOfBoundsException e ) {
            System.out.println(e.getMessage());
        }
    }

    public void calcTotalQuantity() {
    }

    public LinkedList<Client> getItinerary() {
        return itinerary;
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
        toStringBuilder.append(arcListToString);
        return toStringBuilder.toString();
    }
}
