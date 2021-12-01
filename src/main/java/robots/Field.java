package robots;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Field class is designed to the represent the space that the robots are going to explore.
 * It stores the dimensions the tableau and the positions of the robots.
 */
public class Field {

    ArrayList<ArrayList<String>> tableau; // Object that the represents the tableau/matrix that robots operate in.
    private int x_dim; // Dimension x of the matrix.
    private int y_dim; // Dimension y of the matrix.

    /**
     * Constructor for creating fast a Field.
     */
    public Field() {
        this(5, 6);
    }

    /**
     * Constructor that creates the Field object, given it's dimension.
     *
     * @param x Dimensions of the matrix in the x-axis.
     * @param y Dimensions of the matrix in the y-axis.
     */
    public Field(int x, int y) {
        this.x_dim = x;
        this.y_dim = y;

        // Create tableau field and initialize it with "empty" values.
        tableau = new ArrayList<>();
        for (int i = 0; i < x_dim; i++) {
            ArrayList<String> row = new ArrayList<>(y_dim);
            for (int j = 0; j < y_dim; j++) {
                row.add("em");
            }
            tableau.add(row);
        }
    }

    /**
     * Method for adding a Robot object in the field.
     *
     * @param robot The inserted Robot object.
     */
    public void add(Robot robot) {
        this.add(robot.getX(), robot.getY(), robot.getId());
    }

    /**
     * Method for adding a robot in the field, given its position (x,y) and identifier.
     *
     * @param x  The position of the Robot on the x-axis.
     * @param y  The position of the Robot on the y-axis.
     * @param id The unique identifier of the Robot.
     */
    public void add(int x, int y, Integer id) {
        //Set the robot on it's position inside the tableau.
        tableau.get(x).set(y, "R" + id + "");
    }

    /**
     * Method for moving a Robot from one position of the tableau to a new one.
     *
     * @param robot The Robot object.
     * @param x_old The old x-axis position of the robot object.
     * @param y_old The old y-axis position of the robot object.
     */
    public void move(Robot robot, int x_old, int y_old) {
        this.clearPosition(x_old, y_old); // Remove the robot from its old position.
        this.add(robot); // Add the robot into the new position.
    }

    /**
     * Method for clearing a position in the table given its x and y coordinates.
     *
     * @param x The x-axis position.
     * @param y The y-axis position.
     */
    private void clearPosition(int x, int y) {
        tableau.get(x).set(y, "em"); // Setting the position to empty.
    }

    /**
     * Method for checking if the given coordinates (x,y) are within the limits of the matrix represent by
     * the Field object.
     *
     * @param x The x-axis position.
     * @param y The y-axis position.
     * @return Evaluation result as a boolean values.
     */
    public boolean inBounds(int x, int y) {
        return ((x >= 0) && (x < x_dim) && (y >= 0) && (y < y_dim));
    }

    /**
     * Checking if the current position is empty.
     *
     * @param x The x-axis position.
     * @param y The y-axis position.
     * @return Evaluation result as a boolean values.
     */
    public boolean possitionEmpty(int x, int y) {
        return (tableau.get(x).get(y).equals("em"));
    }

    /**
     * Getter for the size of the x dimension of the matrix.
     *
     * @return The size of x.
     */
    public int getX_dim() {
        return x_dim;
    }


    /**
     * Getter for the size of the y dimension of the matrix.
     *
     * @return The size of y.
     */
    public int getY_dim() {
        return y_dim;
    }

    /**
     * Method for printing the tableau.
     *
     * @return A String object that represents the current state of field of exploration.
     */
    public String table2String() {
        StringBuilder str = new StringBuilder();

        str.append(IntStream.range(0, x_dim * 4).mapToObj(i -> "-").collect(Collectors.joining("")) + "\n");
        for (int col = (y_dim - 1); col >= 0; col--) {
            str.append(col + ": ");
            for (int row = 0; row < x_dim; row++) {
                str.append(tableau.get(row).get(col).toString() + " ");
            }
            str.append("\n");

        }
        str.append("y/x ");
        str.append(IntStream.range(0, x_dim).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining("  ")) + "\n");
        str.append(IntStream.range(0, x_dim * 4).mapToObj(i -> "-").collect(Collectors.joining("")));

        return str.toString();
    }

}
