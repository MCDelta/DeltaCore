package mcdelta.core.assets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.Position;
import mcdelta.core.logging.Logger;
import mcdelta.core.network.PacketDelta;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
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
     public static String       worldAccessError = " Cannot set to air! Position's blockAccess needs to be an instanceof World.";
     
     
     
     
     // ========== Items ==========
     
     public static void clearCurrentItem (final EntityPlayer player)
     {
          player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
     }
     
     
     
     
     public static Item findItemWithName (final String name)
     {
          for (final Item element : Item.itemsList)
          {
               if (element != null && element.getUnlocalizedName().contains(name))
               {
                    return element;
               }
          }
          return null;
     }
     
     
     
     
     // ========== Entities ==========
     
     public static Class<?> getEntityFromName (final String name)
     {
          final Class<?> clazz = (Class<?>) EntityList.stringToClassMapping.get(name);
          return clazz;
     }
     
     
     
     
     public static boolean resourceExists (final ResourceLocation loc)
     {
          try
          {
               final Resource tmp = Minecraft.getMinecraft().getResourceManager().getResource(loc);
               tmp.getClass();
          }
          catch (final Exception e)
          {
               return false;
          }
          return true;
     }
     
     
     
     
     public static float[] hexToRGB (final int i)
     {
          final float[] rgb = new float[3];
          
          final int color = i;
          rgb[0] = (color >> 16 & 255) / 255.0F;
          rgb[1] = (color >> 8 & 255) / 255.0F;
          rgb[2] = (color & 255) / 255.0F;
          
          return rgb;
     }
     
     
     
     
     public static float average (final float[] arr)
     {
          float returnVal = 0;
          
          for (final float element : arr)
          {
               returnVal += element;
          }
          return returnVal / arr.length;
     }
     
     
     
     
     public static boolean isClient ()
     {
          return FMLCommonHandler.instance().getEffectiveSide().isClient();
     }
     
     
     
     
     public static boolean isServer ()
     {
          return FMLCommonHandler.instance().getEffectiveSide().isServer();
     }
     
     
     
     
     public static void placeBlock (final Position pos, final BlockData data)
     {
          if (pos.world instanceof World)
          {
               final World world = (World) pos.world;
               
               world.setBlock(pos.x, pos.y, pos.z, data.block.blockID, data.meta, 2);
               world.notifyBlocksOfNeighborChange(pos.x, pos.y, pos.z, data.block.blockID);
               
               if (data.tile != null)
               {
                    world.setBlockTileEntity(pos.x, pos.y, pos.z, data.tile);
               }
          }
          else
          {
               Logger.severe("[ placeBlock() ]" + worldAccessError, pos);
          }
     }
     
     
     
     
     public static boolean setToAir (final Position pos)
     {
          if (pos.world instanceof World)
          {
               final World world = (World) pos.world;
               
               return world.setBlockToAir(pos.x, pos.y, pos.z);
          }
          Logger.severe("[ setToAir() ]" + worldAccessError, pos);
          return false;
     }
     
     
     
     
     public static EnumFacing getFacing (final Position pos)
     {
          return getFacing(pos.getMeta());
     }
     
     
     
     
     public static EnumFacing getFacing (final int face)
     {
          switch (face)
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
     
     
     
     
     public static EnumFacing invertFace (final EnumFacing facing)
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
     
     
     
     
     public static boolean isAirBlock (final Position pos)
     {
          return pos.world.isAirBlock(pos.x, pos.y, pos.z);
     }
     
     
     
     
     public static boolean breakBlock (final Position pos, final boolean b)
     {
          if (pos.world instanceof World)
          {
               final World world = (World) pos.world;
               
               if (b)
               {
                    pos.getBlockData().block.dropBlockAsItem(world, pos.x, pos.y, pos.z, pos.getMeta(), 0);
               }
               
               return Assets.setToAir(pos);
          }
          Logger.severe("[ breakBlock() ]" + worldAccessError, pos);
          
          return false;
     }
     
     
     
     
     public static void moveEntityTowards (final Entity entity, final double targetX, final double targetY, final double targetZ, final double time)
     {
          final boolean xAlign = (int) entity.posX == (int) targetX;
          final boolean yAlign = (int) entity.posY == (int) targetY;
          final boolean zAlign = (int) entity.posZ == (int) targetZ;
          
          if (!xAlign && targetX != 0)
          {
               final double distance = Math.abs(entity.prevPosX - targetX);
               final double speed = distance / time;
               
               final int direction = entity.posX < targetX ? 1 : -1;
               
               entity.motionX = speed * direction;
          }
          if (!yAlign && targetY != 0)
          {
               final double distance = Math.abs(entity.prevPosY - targetY);
               final double speed = distance / time;
               
               final int direction = entity.posY < targetY ? 1 : -1;
               
               entity.motionY = speed * direction;
          }
          if (!zAlign && targetZ != 0)
          {
               final double distance = Math.abs(entity.prevPosZ - targetZ);
               final double speed = distance / time;
               
               final int direction = entity.posZ < targetZ ? 1 : -1;
               
               entity.motionZ = speed * direction;
          }
     }
     
     
     
     
     public static boolean isNegative (final Number num)
     {
          if (num.doubleValue() < 0)
          {
               return true;
          }
          return false;
     }
     
     
     
     
     public static void updateBlock (final Position pos)
     {
          if (pos.world instanceof World)
          {
               final World world = (World) pos.world;
               
               world.markBlockForUpdate(pos.x, pos.y, pos.z);
          }
          else
          {
               Logger.severe("[ Assets.updateBlock() ]" + worldAccessError, pos);
          }
     }
     
     
     
     
     public static List<Position> checkAdjacentBlocks (final Block block, final Position pos)
     {
          final List<Position> positions = new ArrayList<Position>();
          
          for (final EnumFacing face : EnumFacing.values())
          {
               if (pos.move(face).getBlockData() != null && pos.move(face).getBlockData().block == block)
               {
                    positions.add(pos.move(face));
               }
          }
          return positions;
     }
     
     
     
     
     public static List<Position> checkAdjacentBlocks (final Position pos)
     {
          final List<Position> positions = new ArrayList<Position>();
          
          for (final EnumFacing face : EnumFacing.values())
          {
               positions.add(pos.move(face));
          }
          return positions;
     }
     
     
     
     
     public static Packet populatePacket (final PacketDelta packet)
     {
          final byte[] data = packet.populate();
          
          final Packet250CustomPayload packet250 = new Packet250CustomPayload();
          packet250.channel = DeltaCore.MOD_ID;
          packet250.data = data;
          packet250.length = data.length;
          packet250.isChunkDataPacket = false;
          
          return packet250;
     }
     
     
     
     
     public static int getPowerInput (final Position pos)
     {
          if (pos.world instanceof World)
          {
               final World world = (World) pos.world;
               
               return world.getBlockPowerInput(pos.x, pos.y, pos.z);
          }
          Logger.severe("[ Assets.getPowerInput() ]" + worldAccessError, pos);
          
          return 0;
     }
     
     
     
     
     public static boolean isPoweredDirectly (final Position pos)
     {
          return getPowerInput(pos) != 0;
     }
     
     
     
     
     public static boolean isPoweredIndirectly (final Position pos)
     {
          if (isPoweredDirectly(pos))
          {
               return true;
          }
          if (pos.world instanceof World)
          {
               final World world = (World) pos.world;
               
               return world.isBlockIndirectlyGettingPowered(pos.x, pos.y, pos.z);
          }
          Logger.log(Level.SEVERE, "[ Assets.isPoweredIndirectly() ]" + worldAccessError, pos);
          
          return false;
     }
     
     
     
     
     public static List<Integer> getNeighborPowerInputs (final Position pos)
     {
          return getNeighborPowerInputs(new ArrayList<EnumFacing>(), pos);
     }
     
     
     
     
     public static List<Integer> getNeighborPowerInputs (final EnumFacing face, final Position pos)
     {
          final List<EnumFacing> list = new ArrayList<EnumFacing>();
          list.add(face);
          
          return getNeighborPowerInputs(list, pos);
     }
     
     
     
     
     public static List<Integer> getNeighborPowerInputs (final List<EnumFacing> skip, final Position pos)
     {
          final List<Integer> list = new ArrayList<Integer>();
          
          for (final EnumFacing face : EnumFacing.values())
          {
               if (!skip.contains(face))
               {
                    list.add(Assets.getPowerInput(pos.move(face)));
               }
          }
          return list;
     }
     
     
     
     
     public static Field getField (final Class<?> clazz, final String s) throws Exception
     {
          return clazz.getField(s);
     }
     
     
     
     
     public static String capitalize (final String string)
     {
          final char[] chars = string.toLowerCase().toCharArray();
          boolean found = false;
          
          for (int i = 0; i < chars.length; i++)
          {
               if (!found && Character.isLetter(chars[i]))
               {
                    chars[i] = Character.toUpperCase(chars[i]);
                    found = true;
               }
               else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'')
               {
                    found = false;
               }
          }
          return String.valueOf(chars);
     }
}
