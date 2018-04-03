package dataStructure.neighborhood;

import dataStructure.Itineraries;

public class InvertionBeteweenItineraries implements NeighborhoodStrategie{

    @Override
    public Itineraries computeNeighbor(Itineraries itineraries) {
        int quantityItinerary1 = maxCapacity+1;
        int quantityItinerary2 = maxCapacity+1;


        while (quantityItinerary2 >maxCapacity || quantityItinerary1 >maxCapacity) {


            int indexItinerary1 = random.nextInt(itineraries.getNumberOfItineraries());
            int indexItinerary2 = indexItinerary1;

            while (indexItinerary2 == indexItinerary1)
                indexItinerary2 = random.nextInt(itineraries.getNumberOfItineraries());

            int index1 = random.nextInt(itineraries.getItineraries().get(indexItinerary1).getItinerary().size());
            int index2 = index1;

            while (index1 == index2)
                index2 = random.nextInt(itineraries.getItineraries().get(indexItinerary2).getItinerary().size());

            System.out.println("Itinerary1 is " + indexItinerary1+", index1 is "+index1+ "(size of Itinerary1 is "+itineraries.getItineraries().get(indexItinerary1).getItinerary().size()+")");
            System.out.println("Itinerary2 is " + indexItinerary2+", index2 is "+index2+ "(size of Itinerary2 is "+itineraries.getItineraries().get(indexItinerary2).getItinerary().size()+")");

            itineraries.invertionBeteweenItineraries(indexItinerary1, indexItinerary2, index1, index2);
            quantityItinerary1 = itineraries.get(indexItinerary1).CalcTotalQuantity();
            quantityItinerary2 = itineraries.get(indexItinerary2).CalcTotalQuantity();
        }

       return null;
    }
}
