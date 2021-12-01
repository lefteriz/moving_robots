package robots;

import java.util.List;

/**
 * Robot class is designed to the Robots their coordinates, orientation and identifiers.
 */
public class Robot {

    private Integer x_coo; // X and Y coordinates
    private Integer y_coo;
    private Integer orientation; // Oriantion of the robot in degrees (0-270(.
    private Integer id; // Identifier of the robot.

    /**
     * Constructor that creates a Robot object.
     *
     * @param x_coo       The x-axis coordinate of the Robot.
     * @param y_coo       The y-axis coordinate of the Robot.
     * @param orientation The orientation of the Robot in degrees.
     * @param id          The unique identifier of the Robot.
     */
    public Robot(Integer x_coo, Integer y_coo, Integer orientation, Integer id) {
        this.x_coo = x_coo;
        this.y_coo = y_coo;
        this.orientation = orientation;
        this.id = id;
    }

    /**
     * Constructor that creates a Robot object.
     *
     * @param params The coordinates and orientation of the Robot, provided as a List.
     * @param id     The unique identifier of the Robot.
     */
    public Robot(List<Integer> params, Integer id) {
        this(params.get(0), params.get(1), params.get(2), id);
    }

    /**
     * Rotate method, which gets as input the direction (left or right) and rotates the robot respectively.
     *
     * @param direction Direction of rotation (left or right).
     */
    public void rotate(String direction) {
        /*If the rotation is to the right, then 90 degrees are added to the current rotation degrees.
          If the rotation is to the left, then 90 degrees are subtracted from the current rotation degrees.*/
        if (direction.equals("R")) {
            orientation = orientation + 90;
        } else if (direction.equals("L")) {
            orientation = orientation - 90;
        }

        // If new orientation is greater or equal to 360 degrees, then subtract 360 degrees and scale it back down.
        if (orientation >= 360) orientation = orientation - 360;

        // If new orientation is less than 0 degrees then add 360 degrees and scale it back up.
        if (orientation < 0) orientation = orientation + 360;
    }

    /**
     * Method that calculates where the future move of the robot is going to be.
     *
     * @return An array of Integer objects that reprsent the future coordinates of robot (x,y).
     */
    public Integer[] calc_move() {
        // Make a copy of current coordinates.
        Integer next_x_coo = x_coo;
        Integer next_y_coo = y_coo;

        /*Check the orientation of the robot. Based on each case it is going to move and on the y and x axis differently:
         * If degrees are 0 aka North then the next move will be: (x, y + 1)
         * If degrees are 90 aka East, then the next move will be: (x + 1, y)
         * If degrees are 180 aka South, then the next move will be: (x, y - 1)
         * If degrees are 270 aka West, then the next move will be: (x - 1, y)
         * */
        if (orientation == 0) {
            next_y_coo++;
        } else if (orientation == 90) {
            next_x_coo++;
        } else if (orientation == 180) {
            next_y_coo--;
        } else if (orientation == 270) {
            next_x_coo--;
        }

        // Return the possible future position of the robot.
        return new Integer[]{next_x_coo, next_y_coo};
    }

    /**
     * Method that executes the move operation of the robot.
     */
    public void move() {
        /*Check the orientation of the robot. Based on each case it is going to move on the x and y axis differently:
         * If degrees are 0 aka North then the next move will be: (x, y + 1)
         * If degrees are 90 aka East, then the next move will be: (x + 1, y)
         * If degrees are 180 aka South, then the next move will be: (x, y - 1)
         * If degrees are 270 aka West, then the next move will be: (x - 1, y)
         * */
        if (orientation == 0) {
            y_coo++;
        } else if (orientation == 90) {
            x_coo++;
        } else if (orientation == 180) {
            y_coo--;
        } else if (orientation == 270) {
            x_coo--;
        }

    }

    /**
     * Method that returns the orientation in the form of N,E,S,W.
     *
     * @return Orientation in string format.
     */
    public String getOrientation() {
        return orientation2String(orientation);
    }

    /**
     * Method that converts degrees to N,E,S,W format.
     *
     * @param x The orientation in degrees (0-270).
     * @return The orientation in compass format.
     */
    private String orientation2String(Integer x) {
        // Declare a String array that contains N,E,S,W
        String directions[] = {"N", "E", "S", "W", "N"};
        // Calculate the modulo of degrees % 360, i.e. bring down to a scale of 0 to 360 degrees
        // Keep the remainder and divide it with 90 degrees, this gives us how many rotations have
        // been done and thus the direction. Then get the mapping to the letter from the String Array.
        return directions[(Integer) Math.round(((x % 360) / 90))];
    }

    /**
     * Getter for the x coordinate of the Robot.
     *
     * @return The x coordinate of the Robot.
     */
    public Integer getX() {
        return x_coo;
    }

    /**
     * Getter for the y coordinate of the Robot.
     *
     * @return The y coordinate of the Robot.
     */
    public Integer getY() {
        return y_coo;
    }

    /**
     * Getter for the identifier of the Robot.
     *
     * @return The identifier of the Robot.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Overwrite the toString method, produce a more readable String.
     *
     * @return A String representation of the fields of the class Robot.
     */
    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", x_coo=" + x_coo +
                ", y_coo=" + y_coo +
                ", orientation=" + orientation + "(" + orientation2String(orientation) + ")" +
                '}';
    }


}
