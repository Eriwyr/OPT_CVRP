package dataStructure.neighborhood;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import dataStructure.Client;
import dataStructure.Itineraries;
import dataStructure.Itinerary;

import java.util.Collections;
import java.util.LinkedList;

public class TwoOpt implements NeighborhoodStrategie {
    private int count = 0;
    private int count1 = 1;
    private int indexItinerary1;
    private int indexItinerary2;
    private int index1;
    private int index2;
    Itineraries itinerariesToBeReturned;

    public Itineraries computeNeighbor(Itineraries itinerariesSource)  {
        Itineraries itinerariesForIndex = new Itineraries(itinerariesSource);

        //System.out.println("Itinéraire initial : ");
        //System.out.println(itinerariesForIndex);

        int quantityItinerary1 = maxCapacity+1;
        int quantityItinerary2 = maxCapacity+1;

        count=0;

        while (quantityItinerary2 >maxCapacity || quantityItinerary1 >maxCapacity) {
            //System.out.println("While 1");
            count++;
            indexItinerary1 = random.nextInt(itinerariesForIndex.getNumberOfItineraries());
            indexItinerary2 = random.nextInt(itinerariesForIndex.getNumberOfItineraries()); // itinerary1 and itinerary1 can be the same

            while (itinerariesForIndex.get(indexItinerary1).size() == 2 ||itinerariesForIndex.get(indexItinerary2).size()==2) {
                //System.out.println("While 2");
                indexItinerary1 = random.nextInt(itinerariesForIndex.getNumberOfItineraries());
                indexItinerary2 = random.nextInt(itinerariesForIndex.getNumberOfItineraries());
            }



            // ==== 2-opt sur un seul itinéraire ====
            if (indexItinerary1 == indexItinerary2) {
                if( itinerariesSource.get(indexItinerary1).size()>2) {


                    Itineraries newSingleItineraries = new Itineraries(itinerariesSource);

                    // if itinerary1 and itinerary1 are the same, index1 needs to be different and not neighbour to index2
                    int indexItinerary = indexItinerary1;

                    //System.out.println("Même indices itinéraire : "+indexItinerary);
                    //System.out.println("L'ititéraire est : ");
                    //System.out.println(newSingleItineraries.get(indexItinerary));

                    index1 = random.nextInt(newSingleItineraries.get(indexItinerary).size());
                    index2 = index1;

                    //System.out.println("première indice : "+index1+ " pour une taille de "+newSingleItineraries.get(indexItinerary).size());
                    while (index2 == index1 || index2 == index1 - 1 || index2 == index1 + 1) {
                        /*System.out.println(newSingleItineraries.get(indexItinerary));
                        System.out.print("While 3 : taille = " + newSingleItineraries.get(indexItinerary).size() + " ");
                        System.out.println("index 1 =" + index1 + " index 2 =" + index2);*/
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
                    //System.out.println("Index choisis : "+ index1+" "+index2);

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
                    //System.out.println("Itinéraire crée : ");
                    //System.out.println(newItinerary);
                    //System.out.println("\n\nRESULAT FINAL  : ");
                    //System.out.println(newSingleItineraries+"\n\n");

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

                if( itinerary1.size()>2 && itinerary2old.size()>2 ) {

                    //System.out.println("Index itinairaires : "+indexItinerary1+" "+indexItinerary2);
                    //System.out.println("Les itinéraire sont : ");
                    //System.out.println(newTwoItineraries.get(indexItinerary1)+"\n");
                    //System.out.println(newTwoItineraries.get(indexItinerary2)+"\n");

                    //System.out.println("itinerary1 " + itinerary1);
                    //System.out.println("itinerary2old " + itinerary2old);

                    int index1 = random.nextInt(itinerary1.size());
                    int index2 = random.nextInt(itinerary2old.size());

                    while (index1 == itinerary1.size() - 1 && index2 == itinerary2old.size() - 1) {
                        //On en change q'un, pas la peine de changer les deux
                        /*System.out.println("While 4");
                        System.out.println("itinerary2old size "+itinerary2old.size());
                        System.out.println("itinerary2old "+itinerary2old);
                        System.out.println("index1 "+index1);
                        System.out.println("index2 "+index2);*/

                        index2 = random.nextInt(itinerary2old.size() - 1); // On empèche d'avoir le même problème
                    }
                    //System.out.println("Index choisis : "+index1+" "+index2);

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
                    //System.out.println("nouveau itinéraire crées : ");
                    //System.out.println("== 1 == ");
                    //System.out.println(itinerary1);

                    //System.out.println("== 2 == ");
                    //System.out.println(itinerary2new);

               /* Itineraries finalItineraries = new Itineraries();
                finalItineraries.setLogisticCenter(newItineraries.getLogisticCenter());

                for (int j = 0; j < newItineraries.size(); j++) {
                    if (j == indexItinerary1) {
                        finalItineraries.add(itinerary1);
                    } else if (j == indexItinerary2) {
                        finalItineraries.add(itinerary2new);
                    } else {
                        finalItineraries.add(newItineraries.get(j));
                    }


                }*/
                    newTwoItineraries.set(indexItinerary1, itinerary1);
                    newTwoItineraries.set(indexItinerary2, itinerary2new);

                    //newItineraries = new Itineraries(newItineraries);
                    //System.out.println("\n\nRESULAT FINAL  : ");
                    //System.out.println(newTwoItineraries+"\n\n");

                    itinerariesToBeReturned = new Itineraries(newTwoItineraries);
                } else {
                    itinerariesToBeReturned = new Itineraries(itinerariesSource);
                }
            }

            //System.out.println("newItineraries.get(indexItinerary1) = "+newItineraries.get(indexItinerary1));
            //System.out.println("newItineraries.get(indexItinerary2) = "+newItineraries.get(indexItinerary2));

            quantityItinerary1 = itinerariesToBeReturned.get(indexItinerary1).calcTotalQuantity();
            quantityItinerary2 = itinerariesToBeReturned.get(indexItinerary2).calcTotalQuantity();

            //System.out.println("quantityItinerary1 "+quantityItinerary1 +" quantityItinerary2  "+quantityItinerary2);
        }
        boolean b = quantityItinerary2 >maxCapacity || quantityItinerary1 >maxCapacity;

        //System.out.println("\n----- ACCEPTED ( || = "+b+"-----\n");
        //System.out.println("idex itinerary : "+indexItinerary1+" "+indexItinerary2);
        //System.out.println("idex : "+index1+" "+index2);
        //System.out.println("Returning : ");
        //System.out.println(itinerariesToBeReturned);

        return itinerariesToBeReturned;


        }
}
