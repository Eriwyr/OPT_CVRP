package dataStructure;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import static java.lang.Math.*;
import static java.util.stream.Collectors.joining;

public class Itineraries extends Observable {
    private LinkedList<Itinerary> itineraries;
    private Client logisticCenter;
    private double distanceTotale;

    public void setItineraries(LinkedList<Itinerary> itineraries) {
        this.itineraries = itineraries;
        this.distanceTotale = calcDistance();
        setChanged();
        notifyObservers();

    }

    public Itineraries(LinkedList<Itinerary> itineraries, Client logisticCenter) {

        this.itineraries = itineraries;
        this.logisticCenter = logisticCenter;
        this.distanceTotale = calcDistance();
    }

    public void setLogisticCenter(Client logisticCenter) {
        this.logisticCenter = logisticCenter;
    }

    public Itineraries(Itineraries itinerariesSource) {

        itineraries = new LinkedList<>();
        for (Itinerary it : itinerariesSource.getItineraries()) {
            itineraries.add(new Itinerary(it.getItinerary(), it.getLogisticCenter()));
        }
        logisticCenter = new Client(itinerariesSource.getLogisticCenter());

        this.distanceTotale = calcDistance();
    }

    public Itineraries() {
        itineraries = new LinkedList<>();

    }

    public void add(Itinerary itinerary) {

        itineraries.add(itinerary);
        this.distanceTotale = calcDistance();

    }

    public Client getLogisticCenter() {
        return logisticCenter;
    }


    public void invertionWithinItinerary(int indexItinerary, int indexClient1, int indexClient2) {

        itineraries.get(indexItinerary).inversion(indexClient1, indexClient2);
        this.distanceTotale = calcDistance();
    }

    public void invertionBeteweenItineraries(int indexItinerary1, int indexItinerary2, int indexClient1, int indexClient2) {


        Itinerary itinerary1 = itineraries.get(indexItinerary1);
        Itinerary itinerary2 = itineraries.get(indexItinerary2);

        Client client1 = itinerary1.getItinerary().remove(indexClient1);
        Client client2 = itinerary2.getItinerary().remove(indexClient2);


        itinerary1.getItinerary().add(indexClient1, client2);
        itinerary2.getItinerary().add(indexClient2, client1);
        this.distanceTotale = calcDistance();
    }

    public int getNumberOfItineraries() {
        return itineraries.size();
    }

    public void set(int i, Itinerary itinerary) {
        itineraries.set(i, itinerary);

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

        toStringBuilder.append("\nDistance totale : ")
                .append(calcDistance())
                .append("\n");
        return toStringBuilder.toString();

    }

    public Itinerary get(int index) {
        try {
            return itineraries.get(index);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void generateRandomItineraries(LinkedList<Client> clients) {

        int randomIndex = 0;
        int quantity = 0;
        LinkedList<Client> clientsCopy = new LinkedList<>();
        for (Client client : clients) {
            clientsCopy.add(client);
        }

        if (clientsCopy.get(0).getId() == 0) {
            logisticCenter = clientsCopy.get(0);
            clientsCopy.remove(0);
        }

        Client tempClient = null;

        Random random = new Random();

        LinkedList<Client> itinerary = new LinkedList<>();

        while (!(clientsCopy.size() == 1)) {
            randomIndex = random.nextInt(clientsCopy.size() - 1);
            tempClient = clientsCopy.get(randomIndex);
            clientsCopy.remove(randomIndex);
            quantity += tempClient.getQuantity();

            if (quantity > 100) {
                itineraries.add(new Itinerary(itinerary, logisticCenter));
                quantity = tempClient.getQuantity();
                itinerary = new LinkedList<>();

            }

            itinerary.add(tempClient);
        }
        itineraries.add(new Itinerary(itinerary, logisticCenter));
        this.distanceTotale = calcDistance();

        setChanged();
        notifyObservers();
    }

    public void generateFirstItineraries(LinkedList<Client> clients) {
        int quantity = 0;
        Client tempClient = null;
        logisticCenter = clients.get(0);
        LinkedList<Client> itinerary = new LinkedList<>();


        for (int i = 1; i < clients.size(); i++) {
            tempClient = clients.get(i);
            quantity += tempClient.getQuantity();

            if (quantity > 100) {
                itineraries.add(new Itinerary(itinerary, logisticCenter));
                quantity = tempClient.getQuantity();
                itinerary = new LinkedList<>();

            }

            itinerary.add(tempClient);

        }
        itineraries.add(new Itinerary(itinerary, logisticCenter));
        this.distanceTotale = calcDistance();

        setChanged();
        notifyObservers();
    }

    public double distanceToLogisticCenter(Client client) {

        double X = pow(abs(((double) client.getX() - (double) logisticCenter.getX())), 2);
        double Y = pow(abs(((double) client.getY() - (double) logisticCenter.getY())), 2);

        return sqrt(X + Y);
    }

    public double calcDistance() {
        Client firstClient;
        Client lastClient;

        double totalDistance = 0;
        for (Itinerary itinerary : itineraries) {

            firstClient = itinerary.getFirstClient();
            lastClient = itinerary.getLastClient();

            totalDistance += abs(itinerary.calcTotaDistance());
            totalDistance += abs(distanceToLogisticCenter(firstClient));
            totalDistance += abs(distanceToLogisticCenter(lastClient));

        }
        return totalDistance;
    }

    public LinkedList<Client> getClientsFromItineraries() {
        LinkedList<Client> clients = new LinkedList<>();

        for (Itinerary itinerary : itineraries)
            clients.addAll(itinerary.getItinerary());

        return clients;
    }

    public int size() {
        return itineraries.size();
    }

    public boolean validateQuantities(int maxQuantity) {
        for (Itinerary itinerary : itineraries) {
            if (itinerary.calcTotalQuantity() > maxQuantity) return false;
        }
        return true;

    }

    public double getDistanceTotale() {
        return distanceTotale;
    }
}
