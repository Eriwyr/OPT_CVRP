package algorithms;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import dataStructure.Itineraries;
import static java.lang.Math.exp;
import dataStructure.neighborhood.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing implements Runnable{
    NeighborhoodStrategie neighborhoodStrategie;
    private Itineraries itineraries;
    private Random random;
    private Itineraries xmin;
    ArrayList<NeighborhoodStrategie>neighborhoods;
    int iterationNumber;
    int bearingNumber;
    public SimulatedAnnealing(Itineraries itineraries, int iterationNumber, int bearingNumber) {

        System.out.println("Start");
        neighborhoods = new ArrayList<>();
        neighborhoods.add(new InversionWithinItinerary());
        neighborhoods.add(new InvertionBeteweenItineraries());
        neighborhoods.add(new InvertionBeteweenItineraries());
        neighborhoods.add(new MoveClient());
        neighborhoods.add(new TwoOpt());
        xmin = itineraries;
        this.itineraries= itineraries;
        this.bearingNumber=bearingNumber;
        this.iterationNumber=iterationNumber;

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


    @Override
    public void run() {
        float t0 = 10.0f;
        float t = t0;
        //int n1 =1000; //TODO determine n1
        //int n2 =10; //TODO determine n2

        double distanceMin = xmin.calcDistance();
        System.out.println("Début : ");
        System.out.println("Start distance : "+distanceMin);
        float lambda =0.99f;


        Itineraries xi = xmin ;
        Itineraries xi1;
        Itineraries y = xmin;

        double distanceNeighbor;
        double distanceX =0;
        double deltaDistance;

        float p;

        random = new Random();
        List<NeighborhoodStrategie> listNeighborhoodStrategie= new ArrayList<>();
        listNeighborhoodStrategie.add(new TwoOpt());
        listNeighborhoodStrategie.add(new InvertionBeteweenItineraries());
        listNeighborhoodStrategie.add(new InversionWithinItinerary());
        listNeighborhoodStrategie.add(new MoveClient());
        NeighborhoodStrategie neighborhood = null;




        //MoveClient neighborhood = new MoveClient();

        double fmin = itineraries.calcDistance();
        for(int k = 0 ; k<iterationNumber; k++){
            for(int l = 0 ; l<bearingNumber; l++){

                int n = random.nextInt(listNeighborhoodStrategie.size());
                System.out.println(n);
                neighborhood = listNeighborhoodStrategie.get(2);


                y = neighborhood.computeNeighbor(xi);
                distanceNeighbor = y.calcDistance();

                distanceX = xi.calcDistance();
                deltaDistance = distanceNeighbor - distanceX;
                if (deltaDistance <= 0){

                    xi.setItineraries(y.getItineraries());
                    distanceX = xi.calcDistance();


                } else {
                    p = random.nextFloat();
                    if (p<exp(-deltaDistance/t)) {
                        xi.setItineraries(y.getItineraries());

                        //xi.setItineraries(LocalOpt.optimize(y).getItineraries());
                        distanceX = xi.calcDistance();
                    } else {
                        xi.setItineraries(xi.getItineraries());
                    }


                }


            }

            t = t*lambda;
        }

        //xi.setItineraries(xi.getItineraries());
        System.out.println("Final distance : "+distanceX);
        /*System.out.println("xi : ");*/
        System.out.println(xi);
        /*random = new Random();
        this.itineraries = itineraries;
        this.neighborhoodStrategie= new InversionWithinItinerary();
*/
    }
}
