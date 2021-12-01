package robots;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class is designed to direct and synchronize the communication of the Robot and Field classes.
 */
public class Controller {

    Field field; // Field object that is going to be explored.
    List<Robot> robots; // List of robots operated by the controller.

    /**
     * Default constructor that creates a Controller object.
     *
     * @param field The Field object inside which the robots will be deployed.
     */
    public Controller(Field field) {
        this.field = field; // Assign field to the controller.
        robots = new ArrayList<>();
    }

    /**
     * Method for adding robots to the Controller and the Field that it directs.
     *
     * @param robot A Robot object.
     */
    public void addRobot(Robot robot) {
        robots.add(robot); // Add robot to the list of robots.
        field.add(robot); // Add robot in the field.
    }

    /**
     * Method for executing the instructions provided by the user.
     *
     * @param instructions A list of instructions for the controller to execute. Each element is a single-letter
     *                     instruction.
     */
    public void execute(List<String> instructions) {
        Robot robot; // Current robot reference.
        robot = robots.get(robots.size() - 1); // Get that last robot that was inserted into the controller.

        //Iterate through the list of instructions, each String in the list is a single-letter instruction.
        for (String instruction : instructions) {
            switch (instruction) {
                case "M": // If it is a move instruction.
                    boolean moved = this.move(robot); // Execute the move method and keep the results of the execution.

                    // Check if the execution failed, due to moving the robot into a blocked space or out of bounds of
                    // the field. If true stop the execution of subsequent instructions since the robot has reached
                    // a dead-end.
                    if (!moved) {
                        System.out.println("Couldn't move robot, space blocked or out of bounds, stopping robot.");
                        return;
                    }
                    break;
                // If is a left or right instruction.
                case "L":
                case "R":
                    this.rotate(robot, instruction); // Rotate the robot the robot using the rotate method.
                    break;
                default: // If it is not a valid instruction, stop the execution of this instruction set.
                    System.out.println("Wrong instruction: " + instruction + " stopping robot.");
                    return;
            }
        }
    }

    /**
     * Method for executing the move instruction on the Robot and Field objects.
     *
     * @param robot The Robot object that the move operation is going to be applied on.
     * @return A boolean value that indicates if the move operation was successful or not.
     */
    private boolean move(Robot robot) {
        // Calculate the future position of the robot, given its current orientation.
        Integer[] futurePosition = robot.calc_move();

        // Check if the future position of the robot is within boundaries of the Field object and
        // that the future position is not occupied by another robot.
        boolean availability = field.inBounds(futurePosition[0], futurePosition[1]) &&
                field.possitionEmpty(futurePosition[0], futurePosition[1]);

        if (!availability)
            return false; // If the position is not available terminate the execution of the move command.

        // Store the old position of the robot, in order to update the Field.
        int x_old = robot.getX();
        int y_old = robot.getY();

        // Execute the move operation and update the field.
        robot.move(); // Move operation.
        field.move(robot, x_old, y_old); // Update the Field object.

        System.out.println(field.table2String()); // Print the table for demonstration purposes.

        return true;
    }

    /**
     * Method for executing the rotation operation on the Robot.
     *
     * @param robot       The Robot object that the rotation operation is going to be applied on.
     * @param instruction The specific rotation instruction 'L' or 'R'.
     */
    private void rotate(Robot robot, String instruction) {
        robot.rotate(instruction); // Rotate the robot calling the Robot.Rotate method.
    }

}
