package manageFiles;

import dataStructure.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class ParseFiles {

    private String fileName;

    public ParseFiles(String fileName) {
        this.fileName = fileName;
    }

    private LinkedList<String> readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String stringBuild = sb.toString();
            return new LinkedList<>(Arrays.asList(stringBuild.split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return null;
    }

    public LinkedList<Client> createClientsFromFile() throws IOException {
        LinkedList<Client> clients = new LinkedList<Client>();

        LinkedList<String> lines = readFile(); //each line is client informations
        lines.remove(0); //remove header

        String[] informations;

        for (String line : lines) {
            informations = line.split(";");
            Client client = new Client(Integer.parseInt(informations[0]), //We create a client with correct informations for each line
                    Integer.parseInt(informations[1]),
                    Integer.parseInt(informations[2]),
                    Integer.parseInt(informations[3]));

            clients.add(client);
        }

        return clients;
    }


}
