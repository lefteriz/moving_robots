package robots;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private Integer x_coo;
    private Integer y_coo;
    private Integer orientation;
    private Integer id;

    public Robot(Integer x_coo, Integer y_coo, Integer orientation, Integer id) {
        this.x_coo = x_coo;
        this.y_coo = y_coo;
        this.orientation = orientation;
        this.id = id;
    }

    public Robot(List<Integer> params, Integer id) {
        this(params.get(0), params.get(1), params.get(2), id);
    }

    public void rotate(String direction) {
        if (direction.equals("R")) {
            orientation = orientation + 90;
        } else if (direction.equals("L")) {
            orientation = orientation - 90;
        }

        if (orientation >= 360) orientation = orientation - 360;

        if (orientation < 0) orientation = orientation + 360;
    }

    public Integer[] calc_move() {
        Integer next_x_coo = x_coo;
        Integer next_y_coo = y_coo;

        if (orientation == 0) {
            next_y_coo++;
        } else if (orientation == 90) {
            next_x_coo++;
        } else if (orientation == 180) {
            next_y_coo--;
        } else if (orientation == 270) {
            next_x_coo--;
        }

        return new Integer[]{next_x_coo, next_y_coo};
    }

    public void move() {
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

    public String getOrientation(){
        return orientation2String(orientation);
    }

    private String orientation2String(Integer x) {
        String directions[] = {"N", "E", "S", "W", "N"};
        return directions[(Integer) Math.round(((x % 360) / 90))];
    }

    public Integer getX() {
        return x_coo;
    }

    public Integer getY() {
        return y_coo;
    }

    public Integer getId() {
        return id;
    }

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
