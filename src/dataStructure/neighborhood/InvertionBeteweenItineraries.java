package dataStructure.neighborhood;

import dataStructure.Itineraries;

import java.util.Random;

public class InvertionBeteweenItineraries {//} implements NeighborhoodStrategie{

    //@Override
    public Itineraries computeNeighbor(Itineraries itinerariesSource) {
        Itineraries newItineraries = new Itineraries(itinerariesSource);

        int maxCapacity = 100;
        Random random = new Random();
        int quantityItinerary1 = maxCapacity +1;
        int quantityItinerary2 = maxCapacity +1;


        while (quantityItinerary2 > maxCapacity || quantityItinerary1 > maxCapacity) {


            int indexItinerary1 = random.nextInt(newItineraries.getNumberOfItineraries());
            int indexItinerary2 = indexItinerary1;

            while (indexItinerary2 == indexItinerary1)
                indexItinerary2 = random.nextInt(newItineraries.getNumberOfItineraries());

            int index1 = random.nextInt(newItineraries.getItineraries().get(indexItinerary1).getItinerary().size());
            int index2 = index1;

            while (index1 == index2)
                index2 = random.nextInt(newItineraries.getItineraries().get(indexItinerary2).getItinerary().size());

            newItineraries.invertionBeteweenItineraries(indexItinerary1, indexItinerary2, index1, index2);
            quantityItinerary1 = newItineraries.get(indexItinerary1).CalcTotalQuantity();
            quantityItinerary2 = newItineraries.get(indexItinerary2).CalcTotalQuantity();

        }

       return newItineraries;
    }
}
