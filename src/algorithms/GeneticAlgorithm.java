package algorithms;

import dataStructure.Client;
import dataStructure.Itineraries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GeneticAlgorithm {

    Random random;
    private LinkedList<Itineraries> population;


    public GeneticAlgorithm(LinkedList<Itineraries> population) {
        this.population = population;
        random = new Random();
    }

    public GeneticAlgorithm() {
        this.population = new LinkedList<>();
        random = new Random();
    }

    public void mutation(){

    }

    public void reproduction(){

    }

    public Itineraries crossover(Itineraries firstItinerary, Itineraries secondItinerary){

        Client tempLogiticCenter = firstItinerary.getLogisticCenter();
        int crossOverPoint1=0;
        int crossOverPoint2=0;

        LinkedList<Client> p1;
        LinkedList<Client> p2;
        ArrayList<Client> e1=new ArrayList<>();

        LinkedList<Client> tmpFirst = new LinkedList<>();
        LinkedList<Client> tmpLast = new LinkedList<>();

        p1 = firstItinerary.getClientsFromItineraries();
        p2 = secondItinerary.getClientsFromItineraries();

        //calc of the two crossOver points
        while (crossOverPoint2 == crossOverPoint1 || crossOverPoint2<crossOverPoint1){
            crossOverPoint1 = random.nextInt(p1.size());
            crossOverPoint2 = random.nextInt(p1.size());
        }
        //test
                System.out.println(p1);
                System.out.println(p2);
        crossOverPoint1 = 6;
        crossOverPoint2 = 15;

        //add into e1 client between two crossOver points
        System.out.println("Ajout : ");
        for(int i = crossOverPoint1; i<crossOverPoint2;i++){
            e1.add(p1.get(i));
            System.out.print(p1.get(i)+" ");
        }
        System.out.println("\non a : "+e1);

        //Copy p1 part before the first crossOver point into tmp
        for(int i = 0; i<crossOverPoint1; i++){
            tmpFirst.add(p1.get(i));
        }

        //Copy p1 part after the second crossOver point into tmp
        for(int i = crossOverPoint2; i<p1.size(); i++){
            tmpLast.add(p1.get(i));
        }

int index1=0;

        //fill part before the first crossOver point of e1 and last part of e1
        for(int j = crossOverPoint2; j!=crossOverPoint2-1;j++){

            System.out.println("indice "+j);
            if(j==p1.size()){
                j=0;
            }
            if(tmpFirst.contains(p2.get(j))){
                e1.add(index1, p2.get(j));
                index1++;
                System.out.println("On ajoute "+p2.get(j)+" au début (indice "+index1+")");
            }

            if(tmpLast.contains(p2.get(j))){
                e1.add(p2.get(j));
                System.out.println("On ajoute "+p2.get(j)+ " à la fin ");

            }
        }

        Itineraries newItineraries = new Itineraries();
        e1.add(0, tempLogiticCenter);

        newItineraries.generateFirstItineraries(e1);
        return newItineraries;
    }

    public void generateInitialPopulation(){
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

        /*ArrayList list =new ArrayList();
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
        list.add(client20);*/

        ArrayList p1 = new ArrayList();
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

        ArrayList p2 = new ArrayList<>();
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

        System.out.println(crossover(P1, P2));





    }
}
