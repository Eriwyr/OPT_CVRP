package algorithms;

import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import dataStructure.Client;
import dataStructure.Itineraries;
import dataStructure.Itinerary;
import dataStructure.neighborhood.TwoOpt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class GeneticAlgorithm implements Runnable{

    private Random random;
    private int nbIteration;
    private LinkedList<Itineraries> population;
    private LinkedList<Client> clients;
    private int probaCross;
    private int nbReproduction;
    private Itineraries bestKnown;
    private int populationSize;

    public GeneticAlgorithm(LinkedList<Client> clients, int nbIteration, int probaCross, int nbReproduction, Itineraries itineraries, int populationSize ) {
        this.clients = clients;
        this.population = new LinkedList<Itineraries>();
        this.nbIteration = nbIteration;
        this.probaCross = probaCross;
        this.nbReproduction = nbReproduction;
        random = new Random();
        this.bestKnown = itineraries;
        this.populationSize = populationSize;
    }



    public GeneticAlgorithm() {
        this.population = new LinkedList<>();
        random = new Random();
    }

    public GeneticAlgorithm(LinkedList<Client> clients) {
        this.clients = clients;
        this.random = new Random();
        population = new LinkedList<>();
    }

    public GeneticAlgorithm(LinkedList<Client> clients, Itineraries itineraries) {
        this.clients = clients;
        this.random = new Random();
        population = new LinkedList<>();
        this.bestKnown = itineraries;
    }

    public Itineraries mutation(Itineraries firstItinerary){
        TwoOpt twoOpt = new TwoOpt();
        return twoOpt.computeNeighbor(firstItinerary);
    }

    public LinkedList<Itineraries> mutationOverPopulation(int nbPopulationGoal) {

        int j = 0;
        for(int i =population.size() ; i<nbPopulationGoal ; i++) {
            population.add(mutation(population.get(j)));
            j++;
        }

        return null;
    }

    public LinkedList<Itineraries>  reproductionOverPopulationDeter(int nbReproductionGoal) {
        //double percent;
        LinkedList<Itineraries> newPopulation = new LinkedList<>();
        LinkedList<Itineraries> tmpPopulation = new LinkedList<>();
        LinkedList<Itineraries> sortedList  = new LinkedList<>();


        tmpPopulation.addAll(population);

        sortedList = tmpPopulation.stream().sorted(Comparator.comparing(Itineraries::getDistanceTotale)).collect(Collectors.toCollection(LinkedList::new));

        //System.out.println("Liste tirée");
        /*sortedList.forEach(itineraries -> {
            System.out.print(itineraries.getDistanceTotale()+", ");
        });*/

        for(int i = 0; i<nbReproductionGoal/2; i++) {
            newPopulation.add(new Itineraries(sortedList.get( sortedList.size()-i-1)));
            newPopulation.add(new Itineraries(sortedList.get( sortedList.size()-i-1)));

        }

        //System.out.println("Nouvelle Population : ");
        //System.out.println("Size = "+newPopulation.size());
        //System.out.println(newPopulation);
        return newPopulation;


    }

    public Itineraries getMin( LinkedList<Itineraries> tmpPopulation) {
        Itineraries minItineraries = tmpPopulation.get(0);

        for (int i =1; i<tmpPopulation.size(); i++) {
            if (tmpPopulation.get(i).getDistanceTotale()<minItineraries.getDistanceTotale()) {
                minItineraries=tmpPopulation.get(i);
            }
        }
        return minItineraries;
    }

    public void reproductionOverPopulationProba(int nbReproductionGoal){

        // System.out.println(population);
        LinkedList<Itineraries> newPopulation = new LinkedList<>();
        ArrayList threshholds = new ArrayList<>();
        Random random = new Random();

        double totalDistances = 0;
        double tmpTotal = 0;
        double distance = 0;
        double totalDistanceInverse = 0;


        // Répartition 1/x
        double inverse =0;

        for (Itineraries itineraries : population ) {
            distance = itineraries.calcDistance();
            inverse = 1/distance;

            threshholds.add(inverse+tmpTotal);
            tmpTotal += inverse;
        }

        //Repartition linéraire
        /*for (Itineraries itineraries : population )
            totalDistances += itineraries.calcDistance();
        for (Itineraries itineraries : population ) {
            distance = itineraries.calcDistance();
            threshholds.add(totalDistances-distance+tmpTotal);
            tmpTotal += totalDistances-distance;
        }

        totalDistanceInverse=(double)threshholds.get(threshholds.size()-1);*/

        for (int j = 0; j<nbReproductionGoal; j++ ) {

            //double pick = random.nextDouble() * totalDistanceInverse;
            double pick = random.nextDouble() * tmpTotal;
            /*System.out.println("Pick is "+pick);
            System.out.println("try to find it in ");
            System.out.println(threshholds);*/
            int i = 0;
            while (i < threshholds.size()) {
                if (pick < (double) threshholds.get(i)) break;
                i++;
            }
            if (i == threshholds.size()) {i=0;}

            //System.out.println("## index séléctionné :"+ i);
            newPopulation.add(population.get(i));
        }

        population = newPopulation;
        //System.out.println("========= Fin du repoduction : poputlation =========\n"+population);


    }

    public void crossoverOverPopulation(int nbPopulationGoal) {
        //System.out.println("début crossover : population size = "+population.size());

        if(nbPopulationGoal < population.size())
            System.out.println("ERROR, nbPopulationGoal < population.size()");

        int j =0;

        for (int i = population.size(); i<nbPopulationGoal; i+=2) {

            population.add(crossover(population.get(j), population.get(j+1)));
            population.add(crossover(population.get(j+1), population.get(j)));
            j+=2;

            /*System.out.println("===== population pendant crossoverOverPopulation =====");
            for (Itineraries itineraries : population) {
                System.out.print(itineraries.getClientsFromItineraries().size()+", ");
            }
            System.out.println("\n==================");*/

        }
        //System.out.println("========= Fin du crossover : poputlation =========\n"+population);
    }

    public Itineraries crossover(Itineraries firstItinerary, Itineraries secondItinerary){
        //System.out.println("Début cross over :" +firstItinerary.getClientsFromItineraries().size()+" "+secondItinerary.getClientsFromItineraries().size());

        int count = 0;
        Itineraries newItineraries = new Itineraries();

        boolean valideQuantities = false;
        while (!valideQuantities) {


            Client tempLogiticCenter = firstItinerary.getLogisticCenter();
            int crossOverPoint1 = 0;
            int crossOverPoint2 = 0;

            LinkedList<Client> p1;
            LinkedList<Client> p2;
            LinkedList<Client> e1 = new LinkedList<>();

            LinkedList<Client> tmpFirst = new LinkedList<>();
            LinkedList<Client> tmpLast = new LinkedList<>();

            p1 = firstItinerary.getClientsFromItineraries();
            p2 = secondItinerary.getClientsFromItineraries();

            //calc of the two crossOver points
            while (crossOverPoint2 == crossOverPoint1 || crossOverPoint2 < crossOverPoint1
                    || crossOverPoint2 == p1.size() - 1
                    || crossOverPoint1 == 0) {
                crossOverPoint1 = random.nextInt(p1.size());
                crossOverPoint2 = random.nextInt(p1.size());
            }

        /*System.out.println("firstItinerary "+firstItinerary.getClientsFromItineraries().size());
        System.out.println("secondItinerary "+secondItinerary.getClientsFromItineraries().size());
        System.out.println("crossOverPoint1 "+crossOverPoint1);
        System.out.println("crossOverPoint12 "+crossOverPoint2);*/

            //test
        /*System.out.println("p1 "+p1);
        System.out.println("Taille p1 "+p1.size());
        System.out.println("p2 "+p2);
        System.out.println("p1 "+p1);*/

            /*System.out.println("Taille p2 "+p2.size());*/

            //add into e1 client between two crossOver points
            //System.out.print("Ajout : ");
            for (int i = crossOverPoint1; i < crossOverPoint2; i++) {
                e1.add(p1.get(i));
                //System.out.print(p1.get(i)+" ");
            }
            //System.out.println();
            //System.out.println("\non a : "+e1);

            //Copy p1 part before the first crossOver point into tmp
            for (int i = 0; i < crossOverPoint1; i++) {
                tmpFirst.add(p1.get(i));
            }

            //Copy p1 part after the second crossOver point into tmp
            for (int i = crossOverPoint2; i < p1.size(); i++) {
                tmpLast.add(p1.get(i));
            }

            int index1 = 0;

            int j = crossOverPoint2;
            //fill part before the first crossOver point of e1 and last part of e1
            //for(int j = crossOverPoint2; j!=crossOverPoint2-1;j++){
            for (int index = 0; index < p1.size(); index++) {
                //System.out.println("indice "+j);
                if (j == p1.size()) {
                    j = 0;
                }
                if (tmpFirst.contains(p2.get(j))) {
                    e1.add(index1, p2.get(j));
                    //System.out.print( p2.get(j)+" à l'indice  "+index1+"; ");
                    index1++;
                    //System.out.println("On ajoute "+p2.get(j)+" au début (indice "+index1+")");
                }

                if (tmpLast.contains(p2.get(j))) {
                    e1.add(p2.get(j));
                    //System.out.print("On ajoute "+p2.get(j)+ " à la fin; ");

                }
                j++;
            }


            newItineraries = new Itineraries();
            e1.add(0, tempLogiticCenter);
            //System.out.println("size e1 : "+e1.size());


            newItineraries.generateFirstItineraries(e1);
            //System.out.println("newItineraries retournés par cross over "+newItineraries);

        /*System.out.println("======== newItineraries ========");
        System.out.println(newItineraries.getClientsFromItineraries().size());*/


            if (!newItineraries.validateQuantities(100)) {
                count ++;
                if (count>50) {
                    valideQuantities = true;
                }
            } else {
                valideQuantities = true;
            }


        }
        return newItineraries;
    }

    /*public void generateInitialPopulation(){
        Client A = new Client(0, 0, 0, 0);
        Client client1 = new Client(1, 1, 1, 0);
        Client client2 = new Client(2, 1, 1, 0);
        Client client3 = new Client(3, 1, 1, 0);
        Client client4 = new Client(4, 1, 1, 0);
        Client client5 = new Client(5, 1, 1, 0);
        Client client6 = new Client(6, 1, 1, 0);
        Client client7 = new Client(7, 1, 1, 0);
        Client client8 = new Client(8, 1, 1, 0);
        Client client9 = new Client(9, 1, 1, 0);
        Client client10 = new Client(10, 1, 1, 0);
        Client client11 = new Client(11, 1, 1, 0);
        Client client12 = new Client(12, 1, 1, 0);
        Client client13 = new Client(13, 1, 1, 0);
        Client client14 = new Client(14, 1, 1, 0);
        Client client15 = new Client(15, 1, 1, 0);
        Client client16 = new Client(16, 1, 1, 0);
        Client client17 = new Client(17, 1, 1, 0);
        Client client18 = new Client(18, 1, 1, 0);
        Client client19 = new Client(19, 1, 1, 0);
        Client client20 = new Client(20, 1, 1, 0);

        ArrayList list =new ArrayList();
        list.add(client1);
        list.add(client2);
        list.add(client3);
        list.add(client4);
        list.add(client5);
        list.add(client6);
        list.add(client7);
        list.add(client8);
        list.add(client9);
        list.add(client10);
        list.add(client11);
        list.add(client12);
        list.add(client13);
        list.add(client14);
        list.add(client15);
        list.add(client16);
        list.add(client17);
        list.add(client18);
        list.add(client19);
        list.add(client20);

        LinkedList p1 = new LinkedList();
        p1.add(A);
        p1.add(client20);
        p1.add(client1);
        p1.add(client2);
        p1.add(client4);
        p1.add(client3);
        p1.add(client5);
        p1.add(client6);
        p1.add(client7);
        p1.add(client10);
        p1.add(client8);
        p1.add(client9);
        p1.add(client11);
        p1.add(client12);
        p1.add(client13);
        p1.add(client14);
        p1.add(client16);
        p1.add(client15);
        p1.add(client17);
        p1.add(client18);
        p1.add(client19);

        LinkedList p2 = new LinkedList<>();
        p2.add(A);
        p2.add(client17);
        p2.add(client18);
        p2.add(client19);
        p2.add(client20);
        p2.add(client1);
        p2.add(client3);
        p2.add(client4);
        p2.add(client2);
        p2.add(client6);
        p2.add(client5);
        p2.add(client8);
        p2.add(client7);
        p2.add(client9);
        p2.add(client10);
        p2.add(client11);
        p2.add(client14);
        p2.add(client13);
        p2.add(client12);
        p2.add(client15);
        p2.add(client16);


        Itineraries P1 =  new Itineraries();
        P1.generateFirstItineraries(p1);
        Itineraries P2 =  new Itineraries();
        P2.generateFirstItineraries(p2);

        //System.out.println(crossover(P1, P2));





    }*/

    private Itineraries foundBestSolution(){
        Itineraries bestItineraries = new Itineraries();

        double minDistance;
        double currentDistance;
        try {
            minDistance = population.get(0).calcDistance();
            bestItineraries = population.get(0);

            //System.out.println("Best solution : size population = "+population.size());
            //System.out.print("Solutions observées : ");
            for (Itineraries itineraries : population) {
                currentDistance = itineraries.calcDistance();

                //System.out.print(currentDistance+", ");
                if (currentDistance < minDistance) {
                    bestItineraries = itineraries;
                    minDistance = currentDistance;

                }
            }
            //System.out.println();
        } catch (Exception e) {
            System.out.println("IndexOutOfBound (maybe)");
        }
        //System.out.println("Melleure solution retournée : "+bestItineraries);
        if (bestItineraries.calcDistance() < bestKnown.calcDistance() )
            return bestItineraries;
        else
            return bestKnown;

    }

    @Override
    public void run() {


        /*Itineraries tmpItineraries = new Itineraries();
        for (int i = 0; i <10; i++ ) {
            tmpItineraries = new Itineraries();
            tmpItineraries.generateRandomItineraries(clients);
            population.add(tmpItineraries);
        }


        reproductionOverPopulation(5);*/
        //tmpItineraries.generateRandomItineraries();

        int i = 0;
        Itineraries tmpItineraries = new Itineraries();
        for (int j = 0; j <populationSize; j++ ) {

            tmpItineraries = new Itineraries();

            tmpItineraries.generateRandomItineraries(clients);
            //tmpItineraries.generateFirstItineraries(clients);

            population.add(tmpItineraries);
        }

        /*System.out.println("===== Initial population =====");
        for (Itineraries itineraries : population) {
            System.out.print(itineraries.getClientsFromItineraries().size()+", ");
        }
        //System.out.println(population);
        System.out.println("\n==================");*/


//        reproductionOverPopulation(5);
        //tmpItineraries.generateRandomItineraries();


        System.out.println("Start genetic algorithm");

        bestKnown.setItineraries(foundBestSolution().getItineraries());

        /*System.out.println("===== Initial population =====");
        System.out.println(population);
        System.out.println("==================");*/

        double begin = bestKnown.calcDistance();
        System.out.println("first solution "+bestKnown.calcDistance());

        //System.out.println("first best solution :"+bestKnown.calcDistance());

        //nbIteration=10;
        while(i<nbIteration){
            System.out.println("=== iteration "+i+" ===");

            reproductionOverPopulationProba(nbReproduction);
            //System.out.println("population après reproduction \n"+population.size());

            /*System.out.println("===== population après reproduction =====");
            for (Itineraries itineraries : population) {
                System.out.print(itineraries.getClientsFromItineraries().size()+", ");
            }*/
            System.out.println("\n==================");

            if(random.nextInt(100)<probaCross){
               mutationOverPopulation(populationSize);
               //System.out.println("population après mutation \n"+population.size());

            }

            else {
                crossoverOverPopulation(populationSize);

                /*System.out.println("===== population après crossoverOverPopulation =====");
                for (Itineraries itineraries : population) {
                    System.out.print(itineraries.getClientsFromItineraries().size()+", ");
                }
                //System.out.println(population);
                System.out.println("\n==================");*/


            }

            bestKnown.setItineraries(foundBestSolution().getItineraries());

            System.out.println("current best solution :"+bestKnown.calcDistance());
            i++;
        }

        System.out.println("Begin = "+begin);
        System.out.println("Final best solution :"+bestKnown.calcDistance());
        /*for (Itinerary itinerary : bestKnown.getItineraries()) {

            System.out.print(itinerary.calcTotalQuantity()+", ");
        }
        System.out.println();*/

        //System.out.println(bestKnown.getClientsFromItineraries().size());
        System.out.println("Finished");
    }
}
