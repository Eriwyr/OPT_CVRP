package algorithms;


import dataStructure.Client;
import dataStructure.Itineraries;
import dataStructure.neighborhood.TwoOpt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticAlgorithm implements Runnable {

    private Random random;
    private int nbIteration;
    private LinkedList<Itineraries> population;
    private LinkedList<Client> clients;
    private int probaCross;
    private int nbReproduction;
    private Itineraries bestKnown;
    private int populationSize;
    private boolean isFinished;

    public GeneticAlgorithm(LinkedList<Client> clients, int nbIteration, int probaCross, int nbReproduction, Itineraries itineraries, int populationSize) {
        this.clients = clients;
        this.population = new LinkedList<Itineraries>();
        this.nbIteration = nbIteration;
        this.probaCross = probaCross;
        this.nbReproduction = nbReproduction;
        random = new Random();
        this.bestKnown = itineraries;
        this.populationSize = populationSize;
        isFinished = false;
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

    public Itineraries mutation(Itineraries firstItinerary) {
        TwoOpt twoOpt = new TwoOpt();
        return twoOpt.computeNeighbor(firstItinerary);
    }

    public LinkedList<Itineraries> mutationOverPopulation(int nbPopulationGoal) {

        int j = 0;
        for (int i = population.size(); i < nbPopulationGoal; i++) {
            population.add(mutation(population.get(j)));
            j++;
        }

        return null;
    }

    public LinkedList<Itineraries> reproductionOverPopulationDeter(int nbReproductionGoal) {
        //double percent;
        LinkedList<Itineraries> newPopulation = new LinkedList<>();
        LinkedList<Itineraries> tmpPopulation = new LinkedList<>();
        LinkedList<Itineraries> sortedList = new LinkedList<>();


        tmpPopulation.addAll(population);

        sortedList = tmpPopulation.stream().sorted(Comparator.comparing(Itineraries::getDistanceTotale)).collect(Collectors.toCollection(LinkedList::new));

        for (int i = 0; i < nbReproductionGoal / 2; i++) {
            newPopulation.add(new Itineraries(sortedList.get(sortedList.size() - i - 1)));
            newPopulation.add(new Itineraries(sortedList.get(sortedList.size() - i - 1)));

        }

        return newPopulation;

    }

    public Itineraries getMin(LinkedList<Itineraries> tmpPopulation) {
        Itineraries minItineraries = tmpPopulation.get(0);

        for (int i = 1; i < tmpPopulation.size(); i++) {
            if (tmpPopulation.get(i).getDistanceTotale() < minItineraries.getDistanceTotale()) {
                minItineraries = tmpPopulation.get(i);
            }
        }
        return minItineraries;
    }

    public void reproductionOverPopulationProba(int nbReproductionGoal) {

        LinkedList<Itineraries> newPopulation = new LinkedList<>();
        ArrayList threshholds = new ArrayList<>();
        Random random = new Random();

        double totalDistances = 0;
        double tmpTotal = 0;
        double distance = 0;
        double totalDistanceInverse = 0;


        // Répartition 1/x
        double inverse = 0;

        for (Itineraries itineraries : population) {
            distance = itineraries.calcDistance();
            inverse = 1 / distance;

            threshholds.add(inverse + tmpTotal);
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

        for (int j = 0; j < nbReproductionGoal; j++) {

            double pick = random.nextDouble() * tmpTotal;
            int i = 0;

            while (i < threshholds.size()) {
                if (pick < (double) threshholds.get(i)) break;
                i++;
            }
            if (i == threshholds.size()) {
                i = 0;
            }

            newPopulation.add(population.get(i));
        }

        population = newPopulation;
    }

    public void crossoverOverPopulation(int nbPopulationGoal) {

        if (nbPopulationGoal < population.size())
            System.out.println("ERROR, nbPopulationGoal < population.size()");

        int j = 0;

        for (int i = population.size(); i < nbPopulationGoal; i += 2) {

            population.add(crossover(population.get(j), population.get(j + 1)));
            population.add(crossover(population.get(j + 1), population.get(j)));
            j += 2;

        }
    }

    public Itineraries crossover(Itineraries firstItinerary, Itineraries secondItinerary) {

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


            //add into e1 client between two crossOver points
            for (int i = crossOverPoint1; i < crossOverPoint2; i++) {
                e1.add(p1.get(i));
            }

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

                if (j == p1.size()) {
                    j = 0;
                }
                if (tmpFirst.contains(p2.get(j))) {
                    e1.add(index1, p2.get(j));
                    index1++;
                }

                if (tmpLast.contains(p2.get(j))) {
                    e1.add(p2.get(j));
                }

                j++;
            }

            newItineraries = new Itineraries();
            e1.add(0, tempLogiticCenter);

            newItineraries.generateFirstItineraries(e1);

            if (!newItineraries.validateQuantities(100)) {
                count++;
                if (count > 50) {
                    valideQuantities = true;
                }
            } else {
                valideQuantities = true;
            }


        }
        return newItineraries;
    }


    private Itineraries foundBestSolution() {
        Itineraries bestItineraries = new Itineraries();

        double minDistance;
        double currentDistance;
        try {
            minDistance = population.get(0).calcDistance();
            bestItineraries = population.get(0);

            for (Itineraries itineraries : population) {
                currentDistance = itineraries.calcDistance();

                if (currentDistance < minDistance) {
                    bestItineraries = itineraries;
                    minDistance = currentDistance;

                }
            }

        } catch (Exception e) {
            System.out.println("IndexOutOfBound");
        }
        if (bestItineraries.calcDistance() < bestKnown.calcDistance())
            return bestItineraries;
        else
            return bestKnown;

    }

    @Override
    public void run() {

        int i = 0;
        Itineraries tmpItineraries = new Itineraries();
        for (int j = 0; j < populationSize; j++) {

            tmpItineraries = new Itineraries();

            tmpItineraries.generateRandomItineraries(clients);

            population.add(tmpItineraries);
        }

        System.out.println("Start genetic algorithm");

        bestKnown.setItineraries(foundBestSolution().getItineraries());

        double begin = bestKnown.calcDistance();

        while (i < nbIteration) {


            reproductionOverPopulationProba(nbReproduction);

            if (random.nextInt(100) < probaCross) {
                mutationOverPopulation(populationSize);
            } else {
                crossoverOverPopulation(populationSize);
            }

            bestKnown.setItineraries(foundBestSolution().getItineraries());

            i++;
        }


        System.out.println("Begin = " + begin);
        System.out.println("Final best solution :" + bestKnown.calcDistance());
        System.out.println("Finished");
        isFinished = true;
        Thread.interrupted();
    }

    public boolean isFinished() {
        return isFinished;
    }
}
