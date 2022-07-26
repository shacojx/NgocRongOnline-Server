package real.map;

import java.util.ArrayList;

public class WayPoint {

    public short minX;

    public short minY;

    public short maxX;

    public short maxY;

    public boolean isEnter;

    public boolean isOffline;

    public String name;

    public int goMap;

    public short goX;

    public short goY;
    
    public MapTemplate template;
    public String getName(int id){
      this.template = MapTemplate.arrTemplate[id];
      this.name = this.template.name;
      return this.name;
    }
}
