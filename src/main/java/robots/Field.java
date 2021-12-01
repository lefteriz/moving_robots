package robots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {

    private int x_dim;
    private int y_dim;
    ArrayList<ArrayList<String>> tableau;

    public Field() {
        this(5, 6);
    }

    public Field(int x, int y) {
        this.x_dim = x;
        this.y_dim = y;

        tableau = new ArrayList<>();
        for (int i = 0; i < x_dim; i++) {
            ArrayList<String> row = new ArrayList<>(y_dim);
            for (int j = 0; j < y_dim; j++) {
                row.add("em");
            }
            tableau.add(row);
        }
    }

    public void add(Robot robot) {
        this.add(robot.getX(), robot.getY(), robot.getId());
    }

    public void move(Robot robot, int x_old, int y_old) {
        this.remove(x_old, y_old);
        this.add(robot);
    }

    private void remove(int x, int y) {
        tableau.get(x).set(y, "em");
    }

    public void add(int x, int y, Integer id) {
        tableau.get(x).set(y, "R" + id + "");
    }

    public boolean inBounds(int x, int y) {
        return ((x >= 0) && (x < x_dim) && (y >= 0) && (y < y_dim));
    }

    public boolean possitionEmpty(int x, int y) {
        return (tableau.get(x).get(y).equals("em"));
    }

    public int getX_dim() {
        return x_dim;
    }

    public int getY_dim() {
        return y_dim;
    }

    public void show_tableu() {
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

        System.out.println(str.toString());
//
//        for (int col = 0; col < y_dim; col++) {
//            System.out.println();
//
//            for (int row = (x_dim - 1); row >= 0; row--) {
//
//                ArrayList<String> currentRow = tableau.get(row);
//                if (this.possitionEmpty(row, col)) {
//                    System.out.print(row + "" + col + " ");
//                } else {
//                    System.out.print(tableau.get(row).get(col).toString() + " ");
//                }
////                for (int col = 0; col < y_dim; col++) {
////
//////            ArrayList<String> row_con = tableau.get(row);
////
////
////                    if (this.possitionEmpty(row, col)) {
////                        System.out.print(row + "" + col + " ");
////                    } else {
////                        System.out.print(tableau.get(row).get(col).toString() + " ");
////                    }
//////                System.out.print(tableau.get(row).get(col).toString() + " ");
////
////                }
////                System.out.println();
////            }
////        ListIterator listIterator = tableau.listIterator(tableau.size());
////
////        while (listIterator.hasPrevious()) {
////            System.out.println(listIterator.previous().toString());
////        }
//            }
//        }
    }

}
