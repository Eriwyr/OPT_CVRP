package algorithms;

import dataStructure.Itineraries;
import dataStructure.Itinerary;
import static java.lang.Math.exp;
import dataStructure.neighborhood.*;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    NeighborhoodStrategie neighborhoodStrategie;
    private Itineraries itineraries;
    private Random random;

    public SimulatedAnnealing(Itineraries itineraries) {

        ArrayList<NeighborhoodStrategie> neighborhoods = new ArrayList<>();
        neighborhoods.add(new InversionWithinItinerary());
        neighborhoods.add(new InvertionBeteweenItineraries());
        neighborhoods.add(new InvertionBeteweenItineraries());
        neighborhoods.add(new MoveClient());
        // neighborhoods.add(new TwoOpt());

        int indexNeighborhood;
        float t0; // TODO Calc T0
        float t;
        int n1 =100; //TODO determine n1
        int n2 =100; //TODO determine n2
        Itineraries xmin = itineraries;
        double distanceMin = xmin.calcDistance();



        Itineraries xi = xmin ;
        Itineraries xi1;
        Itineraries y;

        double distanceNeighbor;
        double distanceX;
        double deltaDistance;

        float p;


        double fmin = itineraries.calcDistance();
        for(int k = 0 ; k<n1; k++){
            for(int l = 0 ; l<n2; l++){
                indexNeighborhood = random.nextInt(neighborhoods.size());
                NeighborhoodStrategie neighborhood = neighborhoods.get(indexNeighborhood);
                y = neighborhood.computeNeighbor(xi);

                distanceNeighbor = y.calcDistance();
                distanceX = xi.calcDistance();
                deltaDistance = distanceNeighbor - distanceX;,

                if (deltaDistance <= 0){
                    xi = new Itineraries(y);
                    distanceX = xi.calcDistance();
                    if (distanceX < distanceMin ) {
                        distanceMin =  distanceX;
                        xmin = new Itineraries(xi);
                    }
                } else {
                    p = random.nextFloat();
                    if (p<exp(-deltaDistance/t)) {
                        xi = new Itineraries(y);

                    }

                }


            }
            t = t; //TODO change t
        }

        /*random = new Random();
        this.itineraries = itineraries;
        this.neighborhoodStrategie= new InversionWithinItinerary();
*/

    }


    public void getRandomNeighbor() {
        neighborhoodStrategie.computeNeighbor(this.itineraries);

    }

    public void setStrategie(NeighborhoodStrategie neighborhoodStrategie) {
        this.neighborhoodStrategie = neighborhoodStrategie;

    }

    public Itineraries getItineraries() {
        return itineraries;
    }



}
