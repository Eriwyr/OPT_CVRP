package algorithms;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import dataStructure.Itineraries;
import static java.lang.Math.exp;
import dataStructure.neighborhood.*;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing implements Runnable{
    NeighborhoodStrategie neighborhoodStrategie;
    private Itineraries itineraries;
    private Random random;
    private Itineraries xmin;
    ArrayList<NeighborhoodStrategie>neighborhoods;
    public SimulatedAnnealing(Itineraries itineraries) {

        System.out.println("Start");
        neighborhoods = new ArrayList<>();
        neighborhoods.add(new InversionWithinItinerary());
        neighborhoods.add(new InvertionBeteweenItineraries());
        neighborhoods.add(new InvertionBeteweenItineraries());
        neighborhoods.add(new MoveClient());
        neighborhoods.add(new TwoOpt());
        xmin = itineraries;
        this.itineraries= itineraries;


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
        int n1 =1000; //TODO determine n1
        int n2 =10; //TODO determine n2

        double distanceMin = xmin.calcDistance();
        System.out.println("Début : ");
        //System.out.println("Start distance : "+distanceMin);
        float lambda =0.99f;


        Itineraries xi = xmin ;
        Itineraries xi1;
        Itineraries y = xmin;

        double distanceNeighbor;
        double distanceX =0;
        double deltaDistance;

        float p;

        random = new Random();
        TwoOpt neighborhood = new TwoOpt();
        //InvertionBeteweenItineraries neighborhood = new InvertionBeteweenItineraries();
        //InversionWithinItinerary neighborhood = new InversionWithinItinerary();
        //MoveClient neighborhood= new MoveClient(); //TODO : correct

        double fmin = itineraries.calcDistance();
        for(int k = 0 ; k<n1; k++){
            for(int l = 0 ; l<n2; l++){
                /*System.out.println("\n");
                System.out.println("================================= START =======================================");
                System.out.println("current x :");
                System.out.println(xi);
                System.out.println();
                System.out.println("\n---------------------------- DEBUT ALGO -----------------------------------\n");*/
                y = neighborhood.computeNeighbor(xi);
                /*System.out.println("\n---------------------------- FIN ALGO -----------------------------------\n");
                System.out.println("Computed y : ");
                System.out.println(y);*/
                distanceNeighbor = y.calcDistance();
                //System.out.println("================================= FINISH =======================================");

                distanceX = xi.calcDistance();
                deltaDistance = distanceNeighbor - distanceX;
                if (deltaDistance <= 0){

                    xi.setItineraries(y.getItineraries());
                    distanceX = xi.calcDistance();

                    //System.out.println("<0, nouveau distcancex = "+distanceX);
                    /*if (distanceX < distanceMin ) {
                        distanceMin =  distanceX;
                        xmin = new Itineraries(xi);
                    }*/
                } else {
                    p = random.nextFloat();
                    if (p<exp(-deltaDistance/t)) {
                        //xi.setItineraries(y.getItineraries());

                        xi.setItineraries(LocalOpt.optimize(y).getItineraries());
                        distanceX = xi.calcDistance();
                    }


                }

            }
            t = t*lambda;
        }
        System.out.println("Final distance : "+distanceX);
        System.out.println("xi : ");
        System.out.println(xi);
        /*random = new Random();
        this.itineraries = itineraries;
        this.neighborhoodStrategie= new InversionWithinItinerary();
*/
    }
}
