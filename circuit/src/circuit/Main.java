// Main.java
package circuit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static HashMap<Integer, String[]> termsMap = new HashMap<>();
    static HashMap<Character, Integer> variablesMap = new HashMap<>();
    static int termKeyCounter = 1;
    static int variableKeyCounter = 1;

    public static void main(String[] args) {
        try {
            String fileName = "boole.txt";
            readFile(fileName);

            System.out.println("boole.txt file read successfully");
            
            Circuit circuit = new Circuit(termsMap, variablesMap);
            circuit.printCircuitInfo();
            
            int result = circuit.calculateCircuitResult();
            System.out.println("\nCircuit result: " + result);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void readFile(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("F = ")) {
                    String expression = line.substring(4).replaceAll("\\s+", "");
                    String[] terms = expression.split("\\+");
                    
                    for (String term : terms) {
                        String[] characters = new String[term.length()];
                        for (int i = 0; i < term.length(); i++) {
                            characters[i] = String.valueOf(term.charAt(i));
                        }
                        termsMap.put(termKeyCounter++, characters);

                        Set<Character> tempVariables = new LinkedHashSet<>();
                        for (int i = 0; i < term.length(); i++) {
                            char c = term.charAt(i);
                            if (c != '\'' && c != '’') {
                                tempVariables.add(c);
                            }
                        }

                        for (Character variable : tempVariables) {
                            if (!variablesMap.containsKey(variable)) {
                                variablesMap.put(variable, variableKeyCounter++);
                            }
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            throw e;
        }
    }
}