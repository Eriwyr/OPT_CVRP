package dataStructure.neighborhood;

import dataStructure.Itineraries;

import java.util.Random;

public interface NeighborhoodStrategie {
    Random random = new Random();
    int maxCapacity = 100;

    Itineraries computeNeighbor(Itineraries itineraries);

}
