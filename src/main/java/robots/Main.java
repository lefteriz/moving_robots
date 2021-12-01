package robots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Field field = null;
        Controler controler;
        int robotCount = 4;

        List<String> instructions;
        List<Integer> robotConf;

        try {
            List<Integer> dims = readDims();
            field = new Field(dims.get(0), dims.get(1));
            field.show_tableu();
        } catch (IOException e) {
            System.err.println("Error reading input.");
            e.printStackTrace();
            System.exit(-1);
        }

        if (field == null) {
            System.err.println("Failed initializing field of exploration.");
            return;
        }

        controler = new Controler(field);
        try {
            for (int robotIT = 0; robotIT < robotCount; robotIT++) {
                robotConf = readRobotInit(field);
                System.out.println("Positions given: " + robotConf.toString());

                Robot robot = new Robot(robotConf, robotIT);
                controler.addRobot(robot);
                System.out.println(robot.toString());
                field.show_tableu();

                instructions = readInstructions();
                System.out.println("Instructions given: " + instructions.toString());
                controler.execute(instructions);
                System.out.println(robot.toString());


                System.out.println("Move: " + robot.calc_move()[0] + " " + robot.calc_move()[1]);
                System.out.println("In bounds: " + field.inBounds(robot.calc_move()[0], robot.calc_move()[1]));

                System.out.println("Output of Robot " + robot.getId() + " : " + robot.getX() + " "
                        + robot.getY() + " " + robot.getOrientation());


            }
        } catch (IOException e) {
            System.err.println("Error reading instructions.");
            e.printStackTrace();
        }

    }

    private static List<Integer> readDims() throws IOException {
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        List<Integer> dims = new ArrayList<>();

        while (userInput == "") {
            System.out.print("Input upper-right coordinates of the test field [X Y]: ");
            userInput = bi.readLine().trim();

            try {
                dims = Arrays.stream(userInput.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.out.println("Wrong input: Your input must contain numbers. Try again.");
                userInput = "";
                continue;
            }

            if (dims.size() != 2) {
                System.out.println("Wrong input: Your input contains " + dims.size() + " arguments. Try again.");
                userInput = "";
                continue;
            }

            if ((dims.get(0) <= 0) || (dims.get(1) <= 0)) {
                System.out.println("Wrong input: Negative or zero dimensions. Try again.");
                userInput = "";
                continue;
            }

        }

//        bi.close();
        return dims;
    }

    private static List<String> readInstructions() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        List<String> instructions = new ArrayList<>();

        while (userInput == "") {
            System.out.println("Input Ruby Robots initialization instructions: \n" +
                    "'L': Makes the robot spin 90 degrees to the left, without moving from its current position.\n" +
                    "'R': Makes the robot spin 90 degrees to the right, without moving from its current position.\n" +
                    "'M': Makes the robot move forward one grid point, and maintain the same heading.\n" +
                    "Input:");
            userInput = input.readLine().toUpperCase(Locale.ROOT).trim();

            if (!userInput.matches("[LRM]+")) {
                System.out.println("Wrong input: Ruby Robots instructions are not correct. Try again.");
                userInput = "";
                continue;
            }

            instructions = Arrays.stream(userInput.split(""))
                    .collect(Collectors.toList());
        }

        return instructions;
    }

    private static List<Integer> readRobotInit(Field field) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        List<Integer> position = new ArrayList<>();
        Integer x_coor = null;
        Integer y_coor = null;
        Integer orrientation = null;

        Map<String, Integer> replace = new HashMap<>() {{
            put("N", 0);
            put("E", 90);
            put("S", 180);
            put("W", 270);
        }};

        while (userInput == "") {
            System.out.print("Input Ruby Robots initialization [X_coor Y_coor Orientation]: \n" +
                    "X_coor: X coordinate of robot. " + " Posible values: 0 - " + (field.getX_dim() - 1) + "\n" +
                    "Y_coor: Y coordinate of robot." + " Posible values: 0 - " + (field.getY_dim() - 1) + "\n" +
                    "Orientation: Orientation of the robot in the field [N|S|W|E].\n" +
                    "Input:");
            userInput = input.readLine().toUpperCase(Locale.ROOT).trim();

            //Check input formatting.
            if (!userInput.matches("[0-9]+ [0-9]+ [NSWE]{1}")) {
                System.out.println("Wrong input: Ruby Robots positions are not correct. Try again.");
                userInput = "";
                continue;
            }

            x_coor = Integer.parseInt(userInput.split(" ")[0]);
            y_coor = Integer.parseInt(userInput.split(" ")[1]);

            //Check if withing boundaries.
            if ((x_coor < 0) || (y_coor < 0) || (x_coor >= field.getX_dim()) || (y_coor >= field.getY_dim())) {
                System.out.println("Wrong input: Coordinates out of boundaries.");
                userInput = "";
                continue;
            }

//            field.tableau.get(4).set(4, "r1");
            if (!field.possitionEmpty(x_coor, y_coor)) {
                System.out.println("Wrong input: Position is already occupied. Try again.");
                userInput = "";
                continue;
            }
            orrientation = replace.get(userInput.split(" ")[2]);

        }

        position.add(x_coor);
        position.add(y_coor);
        position.add(orrientation);
        return position;
    }

}
