package utils.objLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class PolygonGroup {

    private ArrayList<Polygon> polygons;
    private HashMap<Integer, SmoothingGroup> smoothingGroups;
    private String name = "";

    public PolygonGroup() {
        smoothingGroups = new HashMap<>();
        polygons = new ArrayList<>();
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, SmoothingGroup> getSmoothingGroups() {
        return smoothingGroups;
    }

    public void setSmoothingGroups(HashMap<Integer, SmoothingGroup> smoothingGroups) {
        this.smoothingGroups = smoothingGroups;
    }

}
