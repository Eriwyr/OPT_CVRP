package dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import static java.lang.Math.*;
import static java.util.stream.Collectors.joining;

public class Itineraries extends Observable {
    private LinkedList<Itinerary> itineraries;
    private Client logisticCenter;


    public void setItineraries(LinkedList<Itinerary> itineraries) {
        this.itineraries = itineraries;
        setChanged();
        notifyObservers();

    }

    public Itineraries(Itineraries itinerariesSource) {
       // itineraries = new LinkedList<>(itinerariesSource.getItineraries());
        itineraries = new LinkedList<>();
        for (Itinerary it :itinerariesSource.getItineraries()) {
            itineraries.add(new Itinerary(it.getItinerary(), it.getLogisticCenter()));

        }
        logisticCenter = new Client(itinerariesSource.getLogisticCenter());

    }
    public Itineraries() {
        itineraries = new LinkedList<>();

    }



    public Client getLogisticCenter() {
        return logisticCenter;
    }


    public void invertionWithinItinerary(int indexItinerary, int indexClient1, int indexClient2) {

        itineraries.get(indexItinerary).inversion(indexClient1,indexClient2 );
    }

    public void invertionBeteweenItineraries(int indexItinerary1, int indexItinerary2, int indexClient1, int indexClient2) {


        Itinerary itinerary1 = itineraries.get(indexItinerary1);
        Itinerary itinerary2 = itineraries.get(indexItinerary2);

        Client client1 = itinerary1.getItinerary().remove(indexClient1);
        Client client2 = itinerary2.getItinerary().remove(indexClient2);


        itinerary1.getItinerary().add(indexClient1, client2);
        itinerary2.getItinerary().add(indexClient2, client1);
    }

    public int getNumberOfItineraries() {
        return itineraries.size();
    }

    public void set(){

    }
    public LinkedList<Itinerary> getItineraries() {
        return itineraries;
    }

    @Override
    public String toString() {

        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("[")
                .append(getClass().getName())
                .append("@")
                .append(Integer.toHexString(hashCode()))
                .append("]\n");

       final String itinerariesListToString = itineraries
                .stream()
                .map(Itinerary::toString)
                .collect(joining("\n"));

        toStringBuilder.append(itinerariesListToString);

      /*  toStringBuilder.append("\nDistance totale : ")
                .append(calcDistance())
                .append("\n");*/
        return toStringBuilder.toString();

    }

    /*@Override
    public String toString() {
        return "LinkedList : "+itineraries.toString();
    }*/



      public Itinerary get(int index) {
        try {
            return itineraries.get(index);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void generateFirstItineraries(ArrayList<Client> clients){
        int j = 0;
        int quantity=0;
        Client tempClient  = null;
        logisticCenter = clients.get(0);
        LinkedList<Client> itinerary = new LinkedList<>();


        for(int i = 1;i<clients.size();i++){
            tempClient = clients.get(i);
            quantity += tempClient.getQuantity();

            if(quantity>100){
                itineraries.add( new Itinerary(itinerary,logisticCenter));
                quantity = tempClient.getQuantity();
                itinerary = new LinkedList<>();

            }

            itinerary.add(tempClient);

        }
        itineraries.add(new Itinerary(itinerary,logisticCenter));

        setChanged();
        notifyObservers();
/*
        LinkedList<Client> clientList1 = new LinkedList<Client>();
        Client client11 = new Client(1, 2, 2, 0);
        Client client12 = new Client(2, 3, 3, 0);
        Client client13 = new Client(3, 4, 4, 0);
        Client client14 = new Client(4, 5, 6, 0);

        clientList1.add(client11);
        clientList1.add(client12);
        clientList1.add(client13);
        clientList1.add(client14);

        Itinerary itinerary1 = new Itinerary(clientList1, logisticCenter);


        LinkedList<Client> clientList2 = new LinkedList<Client>();
        Client client21 = new Client(5, 4, 6, 0);
        Client client22 = new Client(6, 6, 7, 0);
        Client client23 = new Client(7, 7, 8, 0);

        clientList2.add(client21);
        clientList2.add(client22);
        clientList2.add(client23);

        Itinerary itinerary2 = new Itinerary(clientList2, logisticCenter);


        LinkedList<Client> clientList3 = new LinkedList<Client>();
        Client client31 = new Client(8, 10, 1, 0);
        Client client32 = new Client(9, 9, 2, 0);
        Client client33 = new Client(10, 8, 3, 0);
        Client client34 = new Client(11, 7, 4, 0);
        Client client35 = new Client(12, 7, 5, 0);

        clientList3.add(client31);
        clientList3.add(client32);
        clientList3.add(client33);
        clientList3.add(client34);
        clientList3.add(client35);

        Itinerary itinerary3 = new Itinerary(clientList3, logisticCenter);

        itineraries.add(itinerary1);
        itineraries.add(itinerary2);
        itineraries.add(itinerary3);

        setChanged();
        notifyObservers();
    */
    }

    public int getnumberItinirary() {
        return itineraries.size();
    }

    public double distanceToLogisticCenter(Client client) {

        double X = pow(abs(((double)client.getX() - (double)logisticCenter.getX())),2);
        double Y = pow(abs(((double)client.getY() - (double)logisticCenter.getY())),2);

        return sqrt(X+Y);
    }

    public double calcDistance() {
        Client firstClient;
        Client lastClient;

        double totalDistance = 0;
        for (Itinerary itinerary : itineraries ) {

            firstClient = itinerary.getFirstClient();
            lastClient = itinerary.getLastClient();


            totalDistance += abs(itinerary.calcTotaDistance());
            totalDistance += abs(distanceToLogisticCenter(firstClient));
            totalDistance += abs(distanceToLogisticCenter(lastClient));


        }
        return totalDistance;
    }
}
