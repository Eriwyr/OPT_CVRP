package dataStructure.neighborhood;

import dataStructure.Itineraries;

import java.util.Random;

public interface NeighborhoodStrategie {
    Random random =  new Random();
    void computeNeighbor(Itineraries itineraries);

}
