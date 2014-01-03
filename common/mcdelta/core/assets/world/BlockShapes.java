package mcdelta.core.assets.world;

import net.minecraft.util.EnumFacing;

public class BlockShapes
{
     public static float[] crusherHeadThingies (final EnumFacing face, final int i)
     {
          final float yMinN = 0.875F;
          final float yMaxN = 1;
          
          final float yMinP = 0;
          final float yMaxP = 0.125F;
          
          if (i == 0)
          {
               final float f1 = 0.125F;
               final float f2 = 0.625F;
               final float f3 = 0.375F;
               final float f4 = 0.875F;
               
               switch (face)
               {
                    case UP:
                         return shape(f1, yMinN, f2, f3, yMaxN, f4);
                    case DOWN:
                         return shape(f1, yMinP, f2, f3, yMaxP, f4);
                    case SOUTH:
                         return shape(f1, f2, yMinN, f3, f4, yMaxN);
                    case NORTH:
                         return shape(f1, f2, yMinP, f3, f4, yMaxP);
                    case WEST:
                         return shape(yMinN, f1, f2, yMaxN, f3, f4);
                    case EAST:
                         return shape(yMinP, f1, f2, yMaxP, f3, f4);
               }
          }
          if (i == 1)
          {
               final float f1 = 0.625F;
               final float f2 = 0.125F;
               final float f3 = 0.875F;
               final float f4 = 0.375F;
               
               switch (face)
               {
                    case UP:
                         return shape(f1, yMinN, f2, f3, yMaxN, f4);
                    case DOWN:
                         return shape(f1, yMinP, f2, f3, yMaxP, f4);
                    case SOUTH:
                         return shape(f1, f2, yMinN, f3, f4, yMaxN);
                    case NORTH:
                         return shape(f1, f2, yMinP, f3, f4, yMaxP);
                    case WEST:
                         return shape(yMinN, f1, f2, yMaxN, f3, f4);
                    case EAST:
                         return shape(yMinP, f1, f2, yMaxP, f3, f4);
               }
          }
          if (i == 2)
          {
               final float f1 = 0.125F;
               final float f2 = 0.125F;
               final float f3 = 0.375F;
               final float f4 = 0.375F;
               
               switch (face)
               {
                    case UP:
                         return shape(f1, yMinN, f2, f3, yMaxN, f4);
                    case DOWN:
                         return shape(f1, yMinP, f2, f3, yMaxP, f4);
                    case SOUTH:
                         return shape(f1, f2, yMinN, f3, f4, yMaxN);
                    case NORTH:
                         return shape(f1, f2, yMinP, f3, f4, yMaxP);
                    case WEST:
                         return shape(yMinN, f1, f2, yMaxN, f3, f4);
                    case EAST:
                         return shape(yMinP, f1, f2, yMaxP, f3, f4);
               }
          }
          if (i == 3)
          {
               final float f1 = 0.625F;
               final float f2 = 0.625F;
               final float f3 = 0.875F;
               final float f4 = 0.875F;
               
               switch (face)
               {
                    case UP:
                         return shape(f1, yMinN, f2, f3, yMaxN, f4);
                    case DOWN:
                         return shape(f1, yMinP, f2, f3, yMaxP, f4);
                    case SOUTH:
                         return shape(f1, f2, yMinN, f3, f4, yMaxN);
                    case NORTH:
                         return shape(f1, f2, yMinP, f3, f4, yMaxP);
                    case WEST:
                         return shape(yMinN, f1, f2, yMaxN, f3, f4);
                    case EAST:
                         return shape(yMinP, f1, f2, yMaxP, f3, f4);
               }
          }
          return defaultShape();
     }
     
     
     
     
     public static float[] crusherNonExtended (final EnumFacing face)
     {
          final float f1 = 0.563F;
          final float f2 = 0.437F;
          
          switch (face)
          {
               case UP:
                    return shape(0, 0, 0, 1, f1, 1);
               case DOWN:
                    return shape(0, f2, 0, 1, 1, 1);
               case SOUTH:
                    return shape(0, 0, 0, 1, 1, f1);
               case NORTH:
                    return shape(0, 0, f2, 1, 1, 1);
               case WEST:
                    return shape(0, 0, 0, f1, 1, 1);
               case EAST:
                    return shape(f2, 0, 0, 1, 1, 1);
          }
          return defaultShape();
     }
     
     
     
     
     public static float[] crusherExtended (final EnumFacing face)
     {
          final float f1 = 0.875F;
          final float f2 = 0.125F;
          
          switch (face)
          {
               case UP:
                    return shape(0, 0, 0, 1, f1, 1);
               case DOWN:
                    return shape(0, f2, 0, 1, 1, 1);
               case SOUTH:
                    return shape(0, 0, 0, 1, 1, f1);
               case NORTH:
                    return shape(0, 0, f2, 1, 1, 1);
               case WEST:
                    return shape(0, 0, 0, f1, 1, 1);
               case EAST:
                    return shape(f2, 0, 0, 1, 1, 1);
          }
          return defaultShape();
     }
     
     
     
     
     public static float[] crusherExtension (final EnumFacing face, final float f)
     {
          float f1 = 0.563F;
          float f2 = 0.875F;
          
          float f3 = 0.125F;
          float f4 = 0.437F;
          
          switch (face)
          {
               case UP:
                    f1 += f;
                    f2 += f;
                    return shape(0, f1, 0, 1, f2, 1);
               case DOWN:
                    f3 -= f;
                    f4 -= f;
                    return shape(0, f3, 0, 1, f4, 1);
               case NORTH:
                    f3 -= f;
                    f4 -= f;
                    return shape(0, 0, f3, 1, 1, f4);
               case SOUTH:
                    f1 += f;
                    f2 += f;
                    return shape(0, 0, f1, 1, 1, f2);
               case WEST:
                    f1 += f;
                    f2 += f;
                    return shape(f1, 0, 0, f2, 1, 1);
               case EAST:
                    f3 -= f;
                    f4 -= f;
                    return shape(f3, 0, 0, f4, 1, 1);
          }
          return defaultShape();
     }
     
     
     
     
     public static float[] extensionShaft (final EnumFacing face, final float shift, final float f)
     {
          float f1 = 0 + shift;
          float f2 = 1 + shift;
          
          final float f3 = 0.313F;
          final float f4 = 0.687F;
          
          switch (face)
          {
               case UP:
                    f1 -= f;
                    return shape(f3, f1, f3, f4, f2, f4);
               case DOWN:
                    f2 += f;
                    return shape(f3, f1, f3, f4, f2, f4);
               case SOUTH:
                    f1 -= f;
                    return shape(f3, f3, f1, f4, f4, f2);
               case NORTH:
                    f2 += f;
                    return shape(f3, f3, f1, f4, f4, f2);
               case WEST:
                    f1 -= f;
                    return shape(f1, f3, f3, f2, f4, f4);
               case EAST:
                    f2 += f;
                    return shape(f1, f3, f3, f2, f4, f4);
          }
          return defaultShape();
     }
     
     
     
     
     private static float[] defaultShape ()
     {
          return shape(0, 0, 0, 1, 1, 1);
     }
     
     
     
     
     private static float[] shape (final float f1, final float f2, final float f3, final float f4, final float f5, final float f6)
     {
          return new float[]
          { f1, f2, f3, f4, f5, f6 };
     }
}
