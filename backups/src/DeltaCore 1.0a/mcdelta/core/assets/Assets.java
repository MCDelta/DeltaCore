package mcdelta.core.assets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mcdelta.core.DeltaCore;
import mcdelta.core.block.BlockSided;
import mcdelta.core.network.PacketDelta;
import mcdelta.tuxweapons.TuxWeaponsCore;
import mcdelta.tuxweapons.WeaponMaterial;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class Assets
{
     public static Logger log;
     public static String worldAccessError = " Cannot set to air! Position's blockAccess needs to be an instanceof World.";
     
     
     
     
     public static WeaponMaterial getWeaponMatFromName (String name)
     {
          for (WeaponMaterial material : TuxWeaponsCore.weaponMats)
          {
               if (material.getName().equals(name))
               {
                    return material;
               }
          }
          
          return null;
     }
     
     
     
     
     // ========== Items ==========
     
     public static void clearCurrentItem (EntityPlayer player)
     {
          player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
     }
     
     
     
     
     public static Item findItemWithName (String name)
     {
          for (int i = 0; i < Item.itemsList.length; i++)
          {
               if (Item.itemsList[i] != null && Item.itemsList[i].getUnlocalizedName().contains(name))
               {
                    return Item.itemsList[i];
               }
          }
          
          return null;
     }
     
     
     
     
     // ========== Entities ==========
     
     public static Class getEntityFromName (String name)
     {
          Class clazz = (Class) EntityList.stringToClassMapping.get(name);
          return clazz;
     }
     
     
     
     
     // ========== Prints ==========
     
     public static void p (Object... message)
     {
          print(message);
     }
     
     
     
     
     public static void p (Level level, Object... message)
     {
          print(level, message);
     }
     
     
     
     
     public static void print (Level level, Object... message)
     {
          StringBuilder sb = new StringBuilder();
          
          for (Object obj : message)
          {
               if (obj instanceof List)
               {
                    printList((List) obj);
               }
               
               else
               {
                    if (obj != message[0])
                    {
                         sb.append("  :  ");
                    }
                    
                    try
                    {
                         sb.append(obj);
                    }
                    catch (java.lang.NullPointerException e)
                    {
                         sb.append("!!!!! This would have crashed all the things  " + e + " !!!!!");
                    }
               }
          }
          
          if (level == Level.SEVERE)
          {
               sb.insert(0, "!!!!! ");
               sb.append(" !!!!!");
          }
          
          doPrint(sb, level);
     }
     
     
     
     
     /**
      * Shortcut, easier to type & includes modid
      * 
      * @param printedStrings
      */
     public static void print (Object... message)
     {
          print(Level.INFO, message);
     }
     
     
     
     
     public static void doPrint (Object message, Level level)
     {
          if (message == null)
          {
               message = "";
          }
          
          try
          {
               log.log(level, String.format(message.toString()));
          }
          catch (java.lang.NullPointerException e)
          {
               log.log(Level.SEVERE, "!!!!! This would have crashed all the things " + e + " !!!!!");
          }
     }
     
     
     
     
     /**
      * Shortcut to print a list
      * 
      * @param printedList
      */
     public static void printList (List<?> list)
     {
          StringBuilder sb = new StringBuilder();
          
          sb.append("\n");
          
          if (list == null)
          {
               sb.append("\n");
               print(Level.WARNING, "nuuuuuull");
               sb.append("\n");
               
               return;
          }
          
          if (list.isEmpty())
          {
               sb.append("\n");
               print(Level.WARNING, "list is empty");
               sb.append("\n");
               
               return;
          }
          
          for (Object obj : list)
          {
               sb.append("\n");
               sb.append(obj);
               sb.append("\n");
          }
          
          print(sb);
     }
     
     
     
     
     public static void printArray (Object... args)
     {
          printList(Arrays.asList(args));
     }
     
     
     
     
     public static void printArray (int... args)
     {
          printList(Arrays.asList(args));
     }
     
     
     
     
     /**
      * Shortcut to print coordinates
      * 
      * @param x
      * @param y
      * @param z
      */
     public static void printCoords (Object x, Object y, Object z)
     {
          print("x: " + x + "  y: " + y + "  z: " + z);
     }
     
     
     
     
     // ========== Misc ==========
     
     public static boolean rescourceExists (ResourceLocation loc, WeaponMaterial weaponMat)
     {
          boolean override = false;
          
          try
          {
               Minecraft.getMinecraft().getResourceManager().getResource(loc);
               
               override = true;
          }
          catch (IOException e)
          {
          }
          
          if (weaponMat.overrideDefaultTextures)
          {
               override = true;
          }
          
          return override;
     }
     
     
     
     
     public static float[] hexToRGB (int i)
     {
          float[] rgb = new float[3];
          
          int color = i;
          rgb[0] = (float) (color >> 16 & 255) / 255.0F;
          rgb[1] = (float) (color >> 8 & 255) / 255.0F;
          rgb[2] = (float) (color & 255) / 255.0F;
          
          return rgb;
     }
     
     
     
     
     public static float average (float[] arr)
     {
          float returnVal = 0;
          
          for (int i = 0; i < arr.length; i++)
          {
               returnVal += arr[i];
          }
          
          return (returnVal / arr.length);
     }
     
     
     
     
     public static boolean isClient ()
     {
          return FMLCommonHandler.instance().getEffectiveSide().isClient();
     }
     
     
     
     
     public static boolean isServer ()
     {
          return FMLCommonHandler.instance().getEffectiveSide().isServer();
     }
     
     
     
     
     public static void placeBlock (Position pos, BlockData data)
     {
          if (pos.blockAccess instanceof World)
          {
               World world = (World) pos.blockAccess;
               
               world.setBlock(pos.x, pos.y, pos.z, data.block.blockID, data.meta, 2);
               world.notifyBlocksOfNeighborChange(pos.x, pos.y, pos.z, data.block.blockID);
               
               if (data.tile != null)
               {
                    world.setBlockTileEntity(pos.x, pos.y, pos.z, data.tile);
               }
          }
          
          else
          {
               Assets.print(Level.SEVERE, "[ placeBlock() ]" + worldAccessError, pos);
          }
     }
     
     
     
     
     public static boolean setToAir (Position pos)
     {
          if (pos.blockAccess instanceof World)
          {
               World world = (World) pos.blockAccess;
               
               return world.setBlockToAir(pos.x, pos.y, pos.z);
          }
          
          else
          {
               Assets.print(Level.SEVERE, "[ setToAir() ]" + worldAccessError, pos);
               return false;
          }
     }
     
     
     
     
     public static EnumFacing getFacing (Position pos)
     {
          return getFacing(pos.getMeta());
     }
     
     
     
     
     public static EnumFacing getFacing (int i)
     {
          switch (i)
          {
               case 0:
                    return EnumFacing.DOWN;
               case 1:
                    return EnumFacing.UP;
               case 2:
                    return EnumFacing.NORTH;
               case 3:
                    return EnumFacing.SOUTH;
               case 4:
                    return EnumFacing.EAST;
               case 5:
                    return EnumFacing.WEST;
               default:
                    return null;
          }
     }
     
     
     
     
     public static EnumFacing invertFace (EnumFacing facing)
     {
          switch (facing)
          {
               case UP:
                    return EnumFacing.DOWN;
               case DOWN:
                    return EnumFacing.UP;
               case NORTH:
                    return EnumFacing.SOUTH;
               case SOUTH:
                    return EnumFacing.NORTH;
               case EAST:
                    return EnumFacing.WEST;
               case WEST:
                    return EnumFacing.EAST;
               default:
                    return null;
          }
     }
     
     
     
     
     public static boolean isAirBlock (Position pos)
     {
          return pos.blockAccess.isAirBlock(pos.x, pos.y, pos.z);
     }
     
     
     
     
     public static boolean breakBlock (Position pos, boolean b)
     {
          if (pos.blockAccess instanceof World)
          {
               World world = (World) pos.blockAccess;
               
               if (b)
               {
                    pos.getBlockData().block.dropBlockAsItem(world, pos.x, pos.y, pos.z, pos.getMeta(), 0);
               }
               
               return Assets.setToAir(pos);
          }
          
          else
          {
               Assets.print(Level.SEVERE, "[ breakBlock() ]" + worldAccessError, pos);
               
               return false;
          }
     }
     
     
     
     
     public static void moveEntityTowards (Entity entity, double targetX, double targetY, double targetZ, double time)
     {
          boolean xAlign = (int) entity.posX == (int) targetX;
          boolean yAlign = (int) entity.posY == (int) targetY;
          boolean zAlign = (int) entity.posZ == (int) targetZ;
          
          if (!xAlign && targetX != 0)
          {
               double distance = Math.abs(entity.prevPosX - targetX);
               double speed = distance / time;
               
               int direction = (entity.posX < targetX) ? 1 : -1;
               
               entity.motionX = speed * direction;
          }
          
          if (!yAlign && targetY != 0)
          {
               double distance = Math.abs(entity.prevPosY - targetY);
               double speed = distance / time;
               
               int direction = (entity.posY < targetY) ? 1 : -1;
               
               entity.motionY = speed * direction;
          }
          
          if (!zAlign && targetZ != 0)
          {
               double distance = Math.abs(entity.prevPosZ - targetZ);
               double speed = distance / time;
               
               int direction = (entity.posZ < targetZ) ? 1 : -1;
               
               entity.motionZ = speed * direction;
          }
     }
     
     
     
     
     public static boolean isNegative (Number num)
     {
          if (num.doubleValue() < 0)
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     public static void updateBlock (Position pos)
     {
          if (pos.blockAccess instanceof World)
          {
               World world = (World) pos.blockAccess;
               
               world.markBlockForUpdate(pos.x, pos.y, pos.z);
          }
          
          else
          {
               Assets.print(Level.SEVERE, "[ updateBlock() ]" + worldAccessError, pos);
          }
     }
     
     
     
     
     public static List<Position> checkAdjacentBlocks (Block block, Position pos)
     {
          List<Position> positions = new ArrayList();
          
          for (EnumFacing face : EnumFacing.values())
          {
               if (pos.move(face).getBlockData() != null && pos.move(face).getBlockData().block == block)
               {
                    positions.add(pos.move(face));
               }
          }
          
          return positions;
     }
     
     
     
     
     public static List<Position> checkAdjacentBlocks (Position pos)
     {
          List<Position> positions = new ArrayList();
          
          for (EnumFacing face : EnumFacing.values())
          {
               positions.add(pos.move(face));
          }
          
          return positions;
     }
     
     
     
     
     public static Packet populatePacket (PacketDelta packet)
     {
          byte[] data = packet.populate();
          
          Assets.p("sending a packet", packet.getClass().getSimpleName(), data.length);
          
          Packet250CustomPayload packet250 = new Packet250CustomPayload();
          packet250.channel = DeltaCore.MOD_ID;
          packet250.data = data;
          packet250.length = data.length;
          packet250.isChunkDataPacket = false;
          
          return packet250;
     }
     
     
     
     
     public static int getPowerInput (Position pos)
     {
          if (pos.blockAccess instanceof World)
          {
               World world = (World) pos.blockAccess;
               
               return world.getBlockPowerInput(pos.x, pos.y, pos.z);
          }
          
          else
          {
               Assets.print(Level.SEVERE, "[ getPowerInput() ]" + worldAccessError, pos);
               
               return 0;
          }
     }
     
     
     
     
     public static boolean isPoweredDirectly (Position pos)
     {
          return getPowerInput(pos) != 0;
     }
     
     
     
     
     public static boolean isPoweredIndirectly (Position pos)
     {    
          if (isPoweredDirectly(pos))
          {
               return true;
          }
          
          if (pos.blockAccess instanceof World)
          {
               World world = (World) pos.blockAccess;
               
               return world.isBlockIndirectlyGettingPowered(pos.x, pos.y, pos.z);
          }
          
          else
          {
               Assets.print(Level.SEVERE, "[ isPoweredIndirectly() ]" + worldAccessError, pos);
               
               return false;
          }
     }
     
     
     
     
     public static List<Integer> getNeighborPowerInputs (Position pos)
     {
          return getNeighborPowerInputs(new ArrayList(), pos);
     }
     
     
     
     
     public static List<Integer> getNeighborPowerInputs (EnumFacing face, Position pos)
     {
          List<EnumFacing> list = new ArrayList();
          list.add(face);
          
          return getNeighborPowerInputs(list, pos);
     }
     
     
     
     
     public static List<Integer> getNeighborPowerInputs (List<EnumFacing> skip, Position pos)
     {
          List<Integer> list = new ArrayList();
          
          for (EnumFacing face : EnumFacing.values())
          {
               if (!skip.contains(face))
               {
                    list.add(Assets.getPowerInput(pos.move(face)));
               }
          }
          
          return list;
     }
}
