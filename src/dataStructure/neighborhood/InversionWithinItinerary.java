package dataStructure.neighborhood;

import dataStructure.Itineraries;

public class InversionWithinItinerary implements NeighborhoodStrategie{


    @Override
    public void computeNeighbor(Itineraries itineraries) {

        int indexItinerary = random.nextInt(itineraries.getNumberOfItineraries());

        int index1 = random.nextInt(itineraries.getItineraries().get(indexItinerary).getItinerary().size());
        int index2 = index1;

        while (index2 == index1)
            index2= random.nextInt(itineraries.getItineraries().get(indexItinerary).getItinerary().size());

        System.out.println("Modify itinerary "+indexItinerary);
        System.out.println("Index1 is "+index1);
        System.out.println("Index2 is "+index2);
        itineraries.invertionWithinItinerary(indexItinerary, index1, index2);
    }
}
