package dataStructure.neighborhood;

import dataStructure.Itineraries;

public class InversionWithinItinerary implements NeighborhoodStrategie{


    @Override
    public Itineraries computeNeighbor(Itineraries itineraries) {

        Itineraries newItinerary = new Itineraries(itineraries);

        int quantityItinerary = maxCapacity +1;

        while (quantityItinerary > maxCapacity) {


            int indexItinerary = random.nextInt(newItinerary.getNumberOfItineraries());

            int index1 = random.nextInt(newItinerary.getItineraries().get(indexItinerary).getItinerary().size());
            int index2 = index1;

            while (index2 == index1)
                index2 = random.nextInt(newItinerary.getItineraries().get(indexItinerary).getItinerary().size());

            System.out.println("Modify itinerary " + indexItinerary);
            System.out.println("Index1 is " + index1);
            System.out.println("Index2 is " + index2);
            newItinerary.invertionWithinItinerary(indexItinerary, index1, index2);

            quantityItinerary = newItinerary.get(indexItinerary).CalcTotalQuantity();
        }

        return newItinerary;
    }
}
