package dataStructure.neighborhood;

import dataStructure.Itineraries;
import dataStructure.Itinerary;

import java.util.LinkedList;

public class TwoOpt implements NeighborhoodStrategie {
    private int count = 0;
    private int count1 = 1;
    private int indexItinerary1;
    private int indexItinerary2;
    private int index1;
    private int index2;
    Itineraries itinerariesToBeReturned;

    public Itineraries computeNeighbor(Itineraries itinerariesSource) {
        Itineraries itinerariesForIndex = new Itineraries(itinerariesSource);

        int quantityItinerary1 = maxCapacity + 1;
        int quantityItinerary2 = maxCapacity + 1;

        count = 0;

        while (quantityItinerary2 > maxCapacity || quantityItinerary1 > maxCapacity) {

            count++;
            indexItinerary1 = random.nextInt(itinerariesForIndex.getNumberOfItineraries());
            indexItinerary2 = random.nextInt(itinerariesForIndex.getNumberOfItineraries()); // itinerary1 and itinerary1 can be the same

            while (itinerariesForIndex.get(indexItinerary1).size() == 2 || itinerariesForIndex.get(indexItinerary2).size() == 2) {
                indexItinerary1 = random.nextInt(itinerariesForIndex.getNumberOfItineraries());
                indexItinerary2 = random.nextInt(itinerariesForIndex.getNumberOfItineraries());
            }

            if (indexItinerary1 == indexItinerary2) {
                if (itinerariesSource.get(indexItinerary1).size() > 2) {

                    Itineraries newSingleItineraries = new Itineraries(itinerariesSource);

                    // if itinerary1 and itinerary1 are the same, index1 needs to be different and not neighbour to index2
                    int indexItinerary = indexItinerary1;

                    index1 = random.nextInt(newSingleItineraries.get(indexItinerary).size());
                    index2 = index1;

                    while (index2 == index1 || index2 == index1 - 1 || index2 == index1 + 1) {

                        if (newSingleItineraries.get(indexItinerary).size() == 3 && index1 == 1) {
                            index1 = random.nextInt(newSingleItineraries.get(indexItinerary).size());
                        }
                        index2 = random.nextInt(newSingleItineraries.get(indexItinerary).size());
                    }
                    if (index2 < index1) {
                        int tmp = index2;
                        index2 = index1;
                        index1 = tmp;
                    }

                    Itinerary newItinerary = new Itinerary(new LinkedList<>(), newSingleItineraries.get(indexItinerary).getLogisticCenter());

                    Itinerary workingItinerary = newSingleItineraries.get(indexItinerary);

                    int i;

                    for (i = 0; i <= index1; i++) {
                        newItinerary.add(workingItinerary.get(i));
                    }

                    for (i = index2; i > index1; i--) {
                        newItinerary.add(workingItinerary.get(i));
                    }
                    for (i = index2 + 1; i < workingItinerary.size(); i++) {
                        newItinerary.add(workingItinerary.get(i));
                    }

                    newSingleItineraries.set(indexItinerary, newItinerary);

                    itinerariesToBeReturned = new Itineraries(newSingleItineraries);
                } else {
                    itinerariesToBeReturned = new Itineraries(itinerariesSource);
                }


                // 2-opt 2 itinéraires
            } else {
                Itineraries newTwoItineraries = new Itineraries(itinerariesSource);


                Itinerary itinerary1 = new Itinerary(newTwoItineraries.get(indexItinerary1));
                Itinerary itinerary1old = new Itinerary(newTwoItineraries.get(indexItinerary1));
                Itinerary itinerary2old = new Itinerary(newTwoItineraries.get(indexItinerary2));

                if (itinerary1.size() > 2 && itinerary2old.size() > 2) {
                    int index1 = random.nextInt(itinerary1.size());
                    int index2 = random.nextInt(itinerary2old.size());

                    while (index1 == itinerary1.size() - 1 && index2 == itinerary2old.size() - 1) {
                        //On en change qu'un, pas la peine de changer les deux

                        index2 = random.nextInt(itinerary2old.size() - 1); // On empèche d'avoir le même problème
                    }

                    int i;

                    int size = itinerary1.size();

                    for (i = index1 + 1; i < size; i++) {
                        itinerary1.remove(itinerary1.size() - 1);
                    }

                    for (i = index2; i >= 0; i--) {
                        itinerary1.add(itinerary2old.get(i));
                    }

                    for (i = index2; i >= 0; i--) {
                        itinerary2old.remove(0);
                    }

                    Itinerary itinerary2new = new Itinerary(itinerary2old);

                    for (i = itinerary1old.size() - 1; i > index1; i--) {
                        itinerary2new.add(0, itinerary1old.get(i));
                    }

                    newTwoItineraries.set(indexItinerary1, itinerary1);
                    newTwoItineraries.set(indexItinerary2, itinerary2new);
                    itinerariesToBeReturned = new Itineraries(newTwoItineraries);

                } else {
                    itinerariesToBeReturned = new Itineraries(itinerariesSource);
                }
            }

            quantityItinerary1 = itinerariesToBeReturned.get(indexItinerary1).calcTotalQuantity();
            quantityItinerary2 = itinerariesToBeReturned.get(indexItinerary2).calcTotalQuantity();
        }
        return itinerariesToBeReturned;
    }
}
