package dataStructure.neighborhood;

import dataStructure.Client;
import dataStructure.Itineraries;

public class MoveClient implements NeighborhoodStrategie {
    @Override
    public void computeNeighbor(Itineraries itineraries) {
        int indexItiniraryFrom = random.nextInt(itineraries.getNumberOfItineraries());
        int indexItiniraryTo = random.nextInt(itineraries.getNumberOfItineraries());

        int indexClientToMove = random.nextInt(itineraries.get(indexItiniraryFrom).size());

        int indexClientNewPosition = indexClientToMove;
        while (indexClientNewPosition == indexClientToMove)
            indexClientToMove = random.nextInt(itineraries.get(indexItiniraryTo).size());

        int quantityItineraryTo = maxCapacity +1;

        while (quantityItineraryTo > maxCapacity) {

            Client clientMoving = itineraries.get(indexItiniraryFrom).getItinerary().remove(indexClientToMove);
            itineraries.get(indexItiniraryFrom).getItinerary().add(indexClientNewPosition,clientMoving );

            quantityItineraryTo = itineraries.get(indexItiniraryFrom).CalcTotalQuantity();

        }
    }
}
