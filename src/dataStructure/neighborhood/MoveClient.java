package dataStructure.neighborhood;

import dataStructure.Client;
import dataStructure.Itineraries;

public class MoveClient implements NeighborhoodStrategie {
    @Override
    public Itineraries computeNeighbor(Itineraries itinerariesSource) {
        Itineraries newItineraries = new Itineraries(itinerariesSource);

        int indexItiniraryFrom = random.nextInt(newItineraries.getNumberOfItineraries());
        int indexItiniraryTo = random.nextInt(newItineraries.getNumberOfItineraries());

        int indexClientToMove = random.nextInt(newItineraries.get(indexItiniraryFrom).size());

        int indexClientNewPosition = indexClientToMove;
        while (indexClientNewPosition == indexClientToMove)
            indexClientToMove = random.nextInt(newItineraries.get(indexItiniraryTo).size());

        int quantityItineraryTo = maxCapacity +1;

        while (quantityItineraryTo > maxCapacity) {

            Client clientMoving = newItineraries.get(indexItiniraryFrom).getItinerary().remove(indexClientToMove);
            newItineraries.get(indexItiniraryFrom).getItinerary().add(indexClientNewPosition,clientMoving );

            quantityItineraryTo = newItineraries.get(indexItiniraryFrom).CalcTotalQuantity();

        }
        return  newItineraries;
    }
}
