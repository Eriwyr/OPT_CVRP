package dataStructure.neighborhood;

import dataStructure.Itineraries;

public class InversionWithinItinerary implements NeighborhoodStrategie{


    @Override
    public Itineraries computeNeighbor(Itineraries itinerariesSource) {
        Itineraries newItineraries = null;

        int quantityItinerary = maxCapacity +1;

        while (quantityItinerary > maxCapacity) {
            newItineraries = new Itineraries(itinerariesSource);

            int indexItinerary = random.nextInt(newItineraries.size());

            if (itinerariesSource.get(indexItinerary).size()>1) {


                int index1 = random.nextInt(newItineraries.get(indexItinerary).size());
                int index2 = index1;

                while (index2 == index1)
                    index2 = random.nextInt(newItineraries.get(indexItinerary).size());

                newItineraries.invertionWithinItinerary(indexItinerary, index1, index2);

                System.out.println("après ");
                System.out.println(newItineraries);
                quantityItinerary = newItineraries.get(indexItinerary).calcTotalQuantity();
            }
        }

        System.out.println("Last is choosen");
        return newItineraries;
    }
}
