package circuit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gate {
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "NOT";
    private String type;
    private List<Object> inputs;
    private int level;
    private int gateNumber;

    public Gate(String type, int level, int gateNumber) {
        if (!type.equals(AND) && !type.equals(OR) && !type.equals(NOT)) {
            throw new IllegalArgumentException("Invalid gate type entered. Please check your input.");
        }
        this.type = type;
        this.inputs = new ArrayList<>();
        this.level = level;
        this.gateNumber = gateNumber;
    }

    public void addInput(Object input) {
        inputs.add(input);
    }

    public int calculate(HashMap<Character, Integer> variableValues) {
        if (type.equals(NOT)) {
            return calculateNot(inputs.get(0), variableValues);
        } else if (type.equals(AND)) {
            return calculateAnd(variableValues);
        } else if (type.equals(OR)) {
            return calculateOr(variableValues);
        }
        return 0;
    }

    private int calculateNot(Object input, HashMap<Character, Integer> variableValues) {
        int inputValue = getInputValue(input, variableValues);
        return inputValue == 1 ? 0 : 1;
    }

    private int calculateOr(HashMap<Character, Integer> variableValues) {
        for (Object input : inputs) {
            if (getInputValue(input, variableValues) == 1) {
                return 1;
            }
        }
        return 0;
    }

    private int calculateAnd(HashMap<Character, Integer> variableValues) {
        for (Object input : inputs) {
            if (getInputValue(input, variableValues) == 0) {
                return 0;
            }
        }
        return 1;
    }

    private int getInputValue(Object input, HashMap<Character, Integer> variableValues) {
        if (input instanceof Character) {
            return variableValues.getOrDefault((Character) input, 0);
        } else if (input instanceof Gate) {
            return ((Gate) input).calculate(variableValues);
        }
        return 0;
    }

    public String getType() {
        return type;
    }

    public List<Object> getInputs() {
        return inputs;
    }

    public int getLevel() {
        return level;
    }

    public int getGateNumber() {
        return gateNumber;
    }
}