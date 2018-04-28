package dataStructure.neighborhood;

import dataStructure.Client;
import dataStructure.Itineraries;

public class MoveClient implements NeighborhoodStrategie {
    @Override
    public Itineraries computeNeighbor(Itineraries itinerariesSource) {
        Itineraries newItineraries =null;

        int quantityItineraryTo = maxCapacity +1;
        int indexItiniraryFrom=0;
        int indexItiniraryTo ;
        int indexClientToMove;
        int indexClientNewPosition;


        Client clientMoved;
        while (quantityItineraryTo > maxCapacity) {


            indexItiniraryFrom = random.nextInt(itinerariesSource.size());
            indexClientToMove = random.nextInt(itinerariesSource.get(indexItiniraryFrom).size());

            indexItiniraryTo = random.nextInt(itinerariesSource.size());


            if (itinerariesSource.get(indexItiniraryFrom).size() != 1) {
                if (indexItiniraryFrom == indexItiniraryTo) {


                    indexClientNewPosition = indexClientToMove;

                    while (indexClientNewPosition == indexClientToMove)
                        indexClientNewPosition = random.nextInt(itinerariesSource.get(indexItiniraryTo).size());



                } else {
                    indexClientNewPosition = random.nextInt(itinerariesSource.get(indexItiniraryTo).size());
                }

                    newItineraries = new Itineraries(itinerariesSource);

                    clientMoved = newItineraries.get(indexItiniraryFrom).remove(indexClientToMove);
                    newItineraries.get(indexItiniraryTo).add(indexClientNewPosition, clientMoved);
                    quantityItineraryTo = newItineraries.get(indexItiniraryTo).calcTotalQuantity();




            }

        }

        return  newItineraries;
    }
}
