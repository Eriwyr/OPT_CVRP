package DataStructure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Itinerary {
    private LinkedList<Client> itinerary;
    private int totalQuantity;
    private int totalDistance;



    public void CalcTotalQuantity(){

        for (Client client:itinerary) {
            totalQuantity += client.getQuantity();
        }

    }

    public void calcTotaDistance(){
        ListIterator<Client> iterator = itinerary.listIterator();
        Client client = itinerary.getFirst();
        Client nextClient = null;
        double X = 0;
        double Y = 0;

        while(iterator.hasNext()){
            nextClient = iterator.next();

            X = pow(((double)client.getX() - (double)nextClient.getX()),2);
            Y = pow(((double)client.getY() - (double)nextClient.getY()),2);

            totalDistance += (sqrt(X+Y));

            client = nextClient;
        }
    }
}
