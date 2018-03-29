package dataStructure.neighborhood;

import dataStructure.Itineraries;

public class InvertionBeteweenItineraries implements NeighborhoodStrategie{

    @Override
    public void computeNeighbor(Itineraries itineraries) {



        int indexItinerary1 = random.nextInt(itineraries.getNumberOfItineraries());
        int indexItinerary2 = indexItinerary1;

        while (indexItinerary2 == indexItinerary1 )
            indexItinerary2= random.nextInt(itineraries.getNumberOfItineraries());

        int index1 = random.nextInt(itineraries.getItineraries().get(indexItinerary1).getItinerary().size());
        int index2 = index1;

        while (index1 == index2)
            index2 = random.nextInt(itineraries.getItineraries().get(indexItinerary2).getItinerary().size());

        System.out.println("Itinerary1 is "+indexItinerary1);
        System.out.println("Itinerary2 is "+indexItinerary2);
        System.out.println("Index1 is "+index1);
        System.out.println("Index2 is "+index2);
        itineraries.invertionBeteweenItineraries(indexItinerary1 ,indexItinerary2, index1, index2);
    }
}
