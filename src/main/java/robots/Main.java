package robots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class orchestrating the execution.
 */
public class Main {

    public static void main(String[] args) {
        Field field = null;
        Controller controller;
        int robotCount = 2;

        List<String> instructions;
        List<Integer> robotConf;

        try {
            /**
             * Read the table dimensions that the robots are going to explore.
             * Create an object Field that represents the exploration space.
             */
            List<Integer> dims = readDims(); //Read dimensions from terminal.
            field = new Field(dims.get(0), dims.get(1)); //Given those dimension create an object Field.
            System.out.println(field.table2String()); // Print the table for demonstration purposes.
        } catch (IOException e) {
            //Handle exception for reading input from keyboard.
            System.err.println("Error reading input.");
            e.printStackTrace();
            System.exit(-1);
        }

        if (field == null) {
            System.err.println("Error: Initializing field of exploration.");
            return;
        }

        controller = new Controller(field); //Crete a Controller object, that directs the process.
        try {
            // Iterate through all available robots and give them instructions to search the exploration field.
            for (int robotIT = 0; robotIT < robotCount; robotIT++) {
                /**
                 * Read configuration of the robot from the keyboard.
                 * Register the robot with the controller.
                 */
                robotConf = readRobotInit(field); // Read the robot configuration.
                Robot robot = new Robot(robotConf, robotIT); // Create a Robot object.
                controller.addRobot(robot); // Register robot with the controller.

                System.out.println(field.table2String()); // Print the table for demonstration purposes.

                /**
                 * Read instructions for the robot from the keyboard.
                 * Execute the instructions through the controller.
                 */
                instructions = readInstructions(); // Read instructions.
                controller.execute(instructions); // Execute instructions.

                /**
                 * Print output of the execution, i.e. the current coordinates of the robot and its orientation.
                 */
                System.out.println("Output of Robot " + robot.getId() + " : " + robot.getX() + " "
                        + robot.getY() + " " + robot.getOrientation());
            }
        } catch (IOException e) {
            //Handle exception for reading input from keyboard.
            System.err.println("Error reading instructions.");
            e.printStackTrace();
        }

    }

    /**
     * Read the dimensions from the CLI.
     *
     * @return A list of Integer objects that represent the dimensions read from the keyboard.
     * @throws IOException
     */
    private static List<Integer> readDims() throws IOException {
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in)); // Create reader for the keyboard.
        String userInput = ""; // Initialize user input.
        List<Integer> dims = new ArrayList<>(); // Initialize the list that is going to be returned.

        //Repeatedly check till the criteria of the input are satisfied.
        while (userInput == "") {
            System.out.print("Input upper-right coordinates of the test field [X Y]: "); // Give instructions to the user.
            userInput = bi.readLine().trim(); // Read from the keyboard and trim trailing spaces.

            try {
                // Split the input stream, map it into integers, and collect the results in a list.
                dims = Arrays.stream(userInput.split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                //Handle exception for reading character letters instead of numbers.
                System.out.println("Wrong input: Your input must contain numbers. Try again.");
                userInput = "";
                continue;
            }

            // Check if the provided input is exactly two numbers, otherwise input again.
            if (dims.size() != 2) {
                System.out.println("Wrong input: Your input contains " + dims.size() + " arguments. Try again.");
                userInput = "";
                continue;
            }

            // Check if the provided input is negative, otherwise input again.
            if ((dims.get(0) <= 0) || (dims.get(1) <= 0)) {
                System.out.println("Wrong input: Negative or zero dimensions. Try again.");
                userInput = "";
                continue;
            }

        }

        return dims; // Return dimensions.
    }

    /**
     * Read the robot instructions from the CLI.
     *
     * @return A list of String objects that represent the instructions read from the keyboard.
     * @throws IOException
     */
    private static List<String> readInstructions() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); // Create reader for the keyboard.
        String userInput = ""; // Initialize user input.
        List<String> instructions = new ArrayList<>(); // Initialize the list that is going to be returned.

        //Repeatedly check till the criteria of the input are satisfied.
        while (userInput == "") {
            System.out.println("Input Ruby Robots initialization instructions: \n" +
                    "'L': Makes the robot spin 90 degrees to the left, without moving from its current position.\n" +
                    "'R': Makes the robot spin 90 degrees to the right, without moving from its current position.\n" +
                    "'M': Makes the robot move forward one grid point, and maintain the same heading.\n" +
                    "Input:"); // Give instructions to the user.
            userInput = input.readLine().toUpperCase(Locale.ROOT).trim(); // Read from the keyboard and trim trailing spaces.

            // Check, using a regular expression, that the only valid letters are inserted, otherwise retry.
            if (!userInput.matches("[LRM]+")) {
                System.out.println("Wrong input: Ruby Robots instructions are not correct. Try again.");
                userInput = "";
                continue;
            }

            // Split the input stream, and collect the results in a list.
            instructions = Arrays.stream(userInput.split(""))
                    .collect(Collectors.toList());
        }

        return instructions;
    }

    /**
     * Read the robot initialization instructions from the CLI.
     *
     * @return A list of Integer objects that represent the instructions read from the keyboard.
     * @throws IOException
     */
    private static List<Integer> readRobotInit(Field field) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); // Create reader for the keyboard.
        String userInput = ""; // Initialize user input.
        List<Integer> position = new ArrayList<>(); // Initialize the list that is going to be returned.
        Integer x_coor = null; // Initialize the x coordinate that is going to be returned.
        Integer y_coor = null; // Initialize the y coordinate that is going to be returned.
        Integer orientation = null; // Initialize the orientation that is going to be returned.

        // Create a map that is going to be used for mappin N,E,S,W to degrees.
        Map<String, Integer> replace = new HashMap<>() {{
            put("N", 0);
            put("E", 90);
            put("S", 180);
            put("W", 270);
        }};

        //Repeatedly check till the criteria of the input are satisfied.
        while (userInput == "") {
            System.out.print("Input Ruby Robots initialization [X_coor Y_coor Orientation]: \n" +
                    "X_coor: X coordinate of robot. " + " Posible values: 0 - " + (field.getX_dim() - 1) + "\n" +
                    "Y_coor: Y coordinate of robot." + " Posible values: 0 - " + (field.getY_dim() - 1) + "\n" +
                    "Orientation: Orientation of the robot in the field [N|S|W|E].\n" +
                    "Input:"); // Give instructions to the user.
            userInput = input.readLine().toUpperCase(Locale.ROOT).trim(); // Read from the keyboard and trim trailing spaces.

            //Check input formatting with a regular expression, i.e. check that there are only two numbers and
            //one letter(NSWE).
            if (!userInput.matches("[0-9]+ [0-9]+ [NSWE]{1}")) {
                System.out.println("Wrong input: Ruby Robots positions are not correct. Try again.");
                userInput = "";
                continue;
            }
            // Parse coordinates into integers.
            x_coor = Integer.parseInt(userInput.split(" ")[0]);
            y_coor = Integer.parseInt(userInput.split(" ")[1]);

            //Check if coordinates are within the boundaries of the field object.
            if ((x_coor < 0) || (y_coor < 0) || (x_coor >= field.getX_dim()) || (y_coor >= field.getY_dim())) {
                System.out.println("Wrong input: Coordinates out of boundaries.");
                userInput = "";
                continue;
            }

            //Check if the position that the robot is going to occupy is already occupied by another robot.
            if (!field.possitionEmpty(x_coor, y_coor)) {
                System.out.println("Wrong input: Position is already occupied. Try again.");
                userInput = "";
                continue;
            }

            //Assign orientation.
            orientation = replace.get(userInput.split(" ")[2]);

        }

        // Add coordinates and orientation into a list and return the list.
        position.add(x_coor);
        position.add(y_coor);
        position.add(orientation);
        return position;
    }

}
