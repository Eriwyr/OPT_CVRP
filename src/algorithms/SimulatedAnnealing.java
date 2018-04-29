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
    float temperature;
    float coolingRate;

    public SimulatedAnnealing(Itineraries itineraries, int iterationNumber, int bearingNumber, float temperature, float coolingRate) {

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
        this.temperature = temperature;
        this.coolingRate = coolingRate;

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

        long startTime = System.currentTimeMillis();

        double distanceMin = xmin.calcDistance();

        System.out.println("Start distance : "+distanceMin);

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




        double fmin = itineraries.calcDistance();
        for(int k = 0 ; k<iterationNumber; k++){
            for(int l = 0 ; l<bearingNumber; l++){

                int n = random.nextInt(listNeighborhoodStrategie.size());
                neighborhood = listNeighborhoodStrategie.get(n);


                y = neighborhood.computeNeighbor(xi);
                distanceNeighbor = y.calcDistance();

                distanceX = xi.calcDistance();
                deltaDistance = distanceNeighbor - distanceX;
                if (deltaDistance <= 0){

                    xi.setItineraries(y.getItineraries());
                    distanceX = xi.calcDistance();


                } else {
                    p = random.nextFloat()*temperature;

                    //System.out.println("exp = "+exp(-deltaDistance/temperature));
                    if (p<exp(-deltaDistance/temperature)) {
                        xi.setItineraries(y.getItineraries());

                        //xi.setItineraries(LocalOpt.optimize(y).getItineraries());
                        distanceX = xi.calcDistance();
                    } else {
                        xi.setItineraries(xi.getItineraries());
                    }


                }


            }

            temperature = temperature*coolingRate;


        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("fin : "+elapsedTime);

        //xi.setItineraries(xi.getItineraries());
        //System.out.println("Final distance : "+distanceX);
        /*System.out.println("xi : ");*/
        //System.out.println(xi);
        /*random = new Random();
        this.itineraries = itineraries;
        this.neighborhoodStrategie= new InversionWithinItinerary();
*/
    }
}
