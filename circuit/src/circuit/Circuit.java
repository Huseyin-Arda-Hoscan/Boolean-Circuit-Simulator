package circuit;

import java.util.*;

public class Circuit {
    private final HashMap<Integer, String[]> termsMap;
    private final HashMap<Character, Integer> variablesMap;
    private final List<List<Gate>> levels;

    public Circuit(HashMap<Integer, String[]> termsMap, HashMap<Character, Integer> variablesMap) {
        this.termsMap = termsMap;
        this.variablesMap = variablesMap;
        this.levels = new ArrayList<>();
        createCircuit();
    }

    private void createCircuit() {
        // Level 1: NOT gates
        List<Gate> level1 = new ArrayList<>();
        int gateNumber = 1;

        for (String[] term : termsMap.values()) {
            for (int i = 0; i < term.length; i++) {
                if (term[i].equals("'")) {
                    String variable = term[i - 1];
                    Gate notGate = new Gate(Gate.NOT, 1, gateNumber++);
                    notGate.addInput(variable.charAt(0));
                    level1.add(notGate);
                }
            }
        }

        if (!level1.isEmpty()) {
            levels.add(level1);
        }

        // Level 2: AND gates
        List<Gate> level2 = new ArrayList<>();
        gateNumber = 1;

        for (String[] originalTerm : termsMap.values()) {
            List<String> correctTerm = new ArrayList<>();

            for (int j = 0; j < originalTerm.length; j++) {
                if (j + 1 < originalTerm.length && originalTerm[j + 1].equals("'")) {
                    correctTerm.add(originalTerm[j] + "'");
                    j++;
                } else if (!originalTerm[j].equals("'")) {
                    correctTerm.add(originalTerm[j]);
                }
            }

            if (correctTerm.size() <= 1) continue;

            Gate andGate = new Gate(Gate.AND, 2, gateNumber++);

            for (String expression : correctTerm) {
                if (expression.endsWith("'")) {
                    char variable = expression.charAt(0);
                    for (Gate gate : level1) {
                        if (gate.getInputs().get(0).equals(variable)) {
                            andGate.addInput(gate);
                            break;
                        }
                    }
                } else {
                    andGate.addInput(expression.charAt(0));
                }
            }

            level2.add(andGate);
        }

        if (!level2.isEmpty()) {
            levels.add(level2);
        }

        // Level 3: OR gate
        List<Gate> level3 = new ArrayList<>();
        Gate orGate = new Gate(Gate.OR, 3, 1);

        for (String[] originalTerm : termsMap.values()) {
            List<String> correctTerm = new ArrayList<>();

            for (int j = 0; j < originalTerm.length; j++) {
                if (j + 1 < originalTerm.length && originalTerm[j + 1].equals("'")) {
                    correctTerm.add(originalTerm[j] + "'");
                    j++;
                } else if (!originalTerm[j].equals("'")) {
                    correctTerm.add(originalTerm[j]);
                }
            }

            if (correctTerm.size() > 1) {
                for (Gate andGate : level2) {
                    List<Object> inputs = andGate.getInputs();
                    boolean match = true;

                    for (String expression : correctTerm) {
                        boolean found = false;
                        for (Object input : inputs) {
                            if (expression.endsWith("'") && input instanceof Gate) {
                                Gate gate = (Gate) input;
                                if (gate.getType().equals("NOT") && gate.getInputs().get(0).equals(expression.charAt(0))) {
                                    found = true;
                                    break;
                                }
                            } else if (!expression.endsWith("'") && input.equals(expression.charAt(0))) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            match = false;
                            break;
                        }
                    }

                    if (match) {
                        orGate.addInput(andGate);
                        break;
                    }
                }
            } else {
                String expression = correctTerm.get(0);
                if (expression.endsWith("'")) {
                    char variable = expression.charAt(0);
                    for (Gate gate : level1) {
                        if (gate.getInputs().get(0).equals(variable)) {
                            orGate.addInput(gate);
                            break;
                        }
                    }
                } else {
                    orGate.addInput(expression.charAt(0));
                }
            }
        }

        if (!orGate.getInputs().isEmpty()) {
            level3.add(orGate);
            levels.add(level3);
        }
    }

    public void printCircuitInfo() {
        System.out.println("Circuit has " + levels.size() + " levels.");

        for (int i = 0; i < levels.size(); i++) {
            List<Gate> level = levels.get(i);
            System.out.println("Level " + (i + 1) + ":");

            String type = level.get(0).getType();
            System.out.println("Gate type: " + type + ", " + level.size() + " gates");

            for (int j = 0; j < level.size(); j++) {
                Gate gate = level.get(j);
                System.out.print(" " + (j + 1) + ". gate has " + gate.getInputs().size() + " inputs: ");

                for (Object input : gate.getInputs()) {
                    if (input instanceof Character) {
                        System.out.print(input + " ");
                    } else if (input instanceof Gate) {
                        Gate inputGate = (Gate) input;
                        System.out.print(inputGate.getGateNumber() + ". " + inputGate.getType() + " gate ");
                    }
                }
                System.out.println();
            }
        }
    }

    public int calculateCircuitResult() {
        HashMap<Character, Integer> variableValues = getUserInputs();

        if (levels.isEmpty())
            return 0;

        List<Gate> lastLevel = levels.get(levels.size() - 1);
        return lastLevel.get(0).calculate(variableValues);
    }

    private HashMap<Character, Integer> getUserInputs() {
        HashMap<Character, Integer> values = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        for (Character variable : variablesMap.keySet()) {
            if (Character.isLetter(variable)) {
                System.out.print("Enter value for variable " + variable + " (0 or 1): ");
                int value = scanner.nextInt();
                values.put(variable, value);
            }
        }
        
        return values;
    }
}