package algorithms;

import dataStructure.Client;
import dataStructure.Itineraries;
import dataStructure.Itinerary;

import java.util.LinkedList;

import static java.lang.Math.*;

public final class LocalOpt {
    static int count = 0;

    public static Itineraries optimize(Itineraries itinerariesToOptimize) {
        count++;

        LinkedList<Itinerary> newItinerariesList = new LinkedList<>();

        for (Itinerary itinerary : itinerariesToOptimize.getItineraries()) {
            if (itinerary.size() < 5) {
                newItinerariesList.add(itinerary);
            } else {

                newItinerariesList.add(greedyOptimisation(itinerary));
            }
        }

        return new Itineraries(newItinerariesList, itinerariesToOptimize.getLogisticCenter());

    }

    public static Itinerary greedyOptimisation(Itinerary itineraryToOptimize) {
        LinkedList<Client> newItinerary = new LinkedList<>();
        LinkedList<Client> oldItinerary = new LinkedList<>();
        oldItinerary.addAll(itineraryToOptimize.getItinerary());
        Client closestClient;
        Client currentClient;
        newItinerary.add(oldItinerary.get(0));
        oldItinerary.remove(oldItinerary.get(0));
        while (oldItinerary.size() != 0) {

            currentClient = newItinerary.getLast();
            closestClient = oldItinerary.get(0);

            double minDistance = distanceBetweenTwoClients(currentClient, closestClient);
            for (int i = 1; i < oldItinerary.size(); i++) {
                double distance = distanceBetweenTwoClients(currentClient, oldItinerary.get(i));
                if (distance < minDistance) {
                    minDistance = distance;
                    closestClient = oldItinerary.get(i);
                }
            }
            oldItinerary.remove(closestClient);
            newItinerary.add(closestClient);
        }
        return new Itinerary(newItinerary, itineraryToOptimize.getLogisticCenter());
    }

    public static double distanceBetweenTwoClients(Client client1, Client client2) {
        double X = 0;
        double Y = 0;

        X = pow(abs(((double) client1.getX() - (double) client2.getX())), 2);
        Y = pow(abs(((double) client1.getY() - (double) client2.getY())), 2);

        return (sqrt(X + Y));
    }
}
