package algorithms;

import dataStructure.Itineraries;
import dataStructure.neighborhood.InversionWithinItinerary;
import dataStructure.neighborhood.NeighborhoodStrategie;

import java.util.Random;

public class SimulatedAnnealing {
    NeighborhoodStrategie neighborhoodStrategie;
    private Itineraries itineraries;
    private Random random;

    public SimulatedAnnealing(Itineraries itineraries) {
        random = new Random();
        this.itineraries = itineraries;
        this.neighborhoodStrategie= new InversionWithinItinerary();

    }


    public void getRandomNeighbor() {
        neighborhoodStrategie.computeNeighbor(this.itineraries);

    }

    public void setStrategie(NeighborhoodStrategie neighborhoodStrategie) {
        this.neighborhoodStrategie = neighborhoodStrategie;

    }

    public Itineraries getItineraries() {
        return itineraries;
    }



}
