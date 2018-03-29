import algorithms.SimulatedAnnealing;
import dataStructure.Itineraries;
import dataStructure.neighborhood.MoveClient;

public class Main {

    public static void main(String[] args) {

        Itineraries itineraries = new Itineraries();
        System.out.println("Before :");
        System.out.println(itineraries);

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(itineraries);
        //simulatedAnnealing.setStrategie( new InversionWithinItinerary());
        //simulatedAnnealing.setStrategie( new InvertionBeteweenItineraries());
        simulatedAnnealing.setStrategie( new MoveClient());


        simulatedAnnealing.getRandomNeighbor();

        System.out.println("After ");
        System.out.println(simulatedAnnealing.getItineraries());

    }
}
