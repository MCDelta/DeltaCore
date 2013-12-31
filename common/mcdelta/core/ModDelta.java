package mcdelta.core;

import java.util.ArrayList;
import java.util.List;

public interface ModDelta
{
     public static List<ModDelta> mods = new ArrayList();
     
     public static enum Stage
     {
          LOAD_ITEMS,
          LOAD_BLOCKS,
          LOAD_ENCHANTS,
          LOAD_CONFIG;
     }
     
     public void doThings(Stage stage);
}
