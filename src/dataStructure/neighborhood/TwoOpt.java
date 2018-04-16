package dataStructure.neighborhood;

import dataStructure.Itineraries;
import dataStructure.Itinerary;

public class TwoOpt implements NeighborhoodStrategie {
    public Itineraries computeNeighbor(Itineraries itineraries)  {

        int quantityItinerary1 = maxCapacity+1;
        int quantityItinerary2 = maxCapacity+1;


        while (quantityItinerary2 >maxCapacity || quantityItinerary1 >maxCapacity) {

            int indexItinerary1 = random.nextInt(itineraries.getNumberOfItineraries());
            int indexItinerary2 = random.nextInt(itineraries.getNumberOfItineraries()); // itinerary1 and itinerary1 can be the same


            int index1 = random.nextInt(itineraries.getItineraries().get(indexItinerary1).getItinerary().size());
            int index2;
            //test :
            indexItinerary2=indexItinerary1;

            System.out.println("old :"+itineraries.get(indexItinerary2));

            if (indexItinerary1==indexItinerary2) { // if itinerary1 and itinerary1 are the same, index1 needs to be different and not neighbour to index2
                int indexItinerary = indexItinerary1;
                index2 = index1;
                while (index2 == index1 || index2 == index1-1 || index2 == index1+1 || index2 < index1) {
                    index2 = random.nextInt(itineraries.getItineraries().get(indexItinerary1).getItinerary().size());

                    index1 = random.nextInt(itineraries.getItineraries().get(indexItinerary1).getItinerary().size());
                }
                System.out.println("Index1 = "+index1+" index2 = "+index2);
                Itinerary newItinerary = new Itinerary();

                Itinerary workingItinerary = itineraries.get(indexItinerary);
                int i;
                for ( i = 0; i <= index1; i++ ) {
                    newItinerary.add(workingItinerary.get(i));
                }
                System.out.println("étape 1 : "+newItinerary);

                for ( i = index2; i >index1; i-- ) {
                    newItinerary.add(workingItinerary.get(i));
                }
                System.out.println("étape 2 : "+newItinerary);
                for (i = index2+1 ; i<workingItinerary.size(); i++) {
                    newItinerary.add(workingItinerary.get(i));
                }
                System.out.println("étape 3 : "+newItinerary);

                System.out.println();

                itineraries.set(indexItinerary, newItinerary);

                System.out.println("new : "+newItinerary);


            } else {
                index2 = random.nextInt(itineraries.getItineraries().get(indexItinerary1).getItinerary().size());
            }





            quantityItinerary1 = itineraries.get(indexItinerary1).CalcTotalQuantity();
            quantityItinerary2 = itineraries.get(indexItinerary2).CalcTotalQuantity();
        }
        return null;

    }
}
