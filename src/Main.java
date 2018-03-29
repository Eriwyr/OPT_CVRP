import dataStructure.Client;
import manageFiles.ParseFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ParseFiles parser = new ParseFiles("src/data/data01.txt");
        try {
            ArrayList<Client> clients =  parser.createClientsFromFile();
            for (Client client: clients) {
                System.out.println(client.getId());
            }
        }



        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("file doesn't exist");
        }
        catch (IOException e) {
            e.printStackTrace();

        }
    }


}
