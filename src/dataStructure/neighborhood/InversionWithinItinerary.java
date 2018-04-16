package dataStructure.neighborhood;

import dataStructure.Itineraries;

public class InversionWithinItinerary implements NeighborhoodStrategie{


    @Override
    public Itineraries computeNeighbor(Itineraries itinerariesSource) {
        Itineraries newItineraries = new Itineraries(itinerariesSource);

        int quantityItinerary = maxCapacity +1;

        while (quantityItinerary > maxCapacity) {


            int indexItinerary = random.nextInt(newItineraries.getNumberOfItineraries());

            int index1 = random.nextInt(newItineraries.getItineraries().get(indexItinerary).getItinerary().size());
            int index2 = index1;

            while (index2 == index1)
                index2 = random.nextInt(newItineraries.getItineraries().get(indexItinerary).getItinerary().size());
            newItineraries.invertionWithinItinerary(indexItinerary, index1, index2);

            quantityItinerary = newItineraries.get(indexItinerary).CalcTotalQuantity();
        }

        return newItineraries;
    }
}
