package robots;

import java.util.*;

public class Controler {

    Field field;
    List<Robot> robots;

    public Controler() {
        this(new Field(5, 5));
    }

    public Controler(Field field) {
        this.field = field;
        robots = new ArrayList<>();
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
        field.add(robot);
    }

    public void execute(List<String> instructions) {
        Robot robot;
        robot = robots.get(robots.size() - 1);

        for (String instruction : instructions) {
            System.out.println(instruction);
            switch (instruction) {
                case "M":

                    boolean moved = move(robot);
                    if (!moved) {
                        System.out.println("Couldn't move robot, space blocked or out of bounds, stopping robot.");
                        return;
                    }
                    break;

                case "L":
                case "R":
                    rotate(robot, instruction);
                    break;
                default:
                    System.out.println("Wrong instruction: " + instruction + " stopping robot.");
                    return;

            }
        }
    }

    private boolean move(Robot robot) {
        Integer[] next_move = robot.calc_move();
        boolean ability = field.inBounds(next_move[0], next_move[1]) && field.possitionEmpty(next_move[0], next_move[1]);

        if (!ability) return false;

        int x_old = robot.getX();
        int y_old = robot.getY();

        robot.move();
        field.move(robot, x_old, y_old);
        System.out.println("After move operation:");
        System.out.println("Robot: " + robot.toString());
        field.show_tableu();

        return true;
    }

    private void rotate(Robot robot, String instruction) {
        robot.rotate(instruction);
        System.out.println(robot.toString());
    }


}
