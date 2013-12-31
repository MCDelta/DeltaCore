package mcdelta.core.assets;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import mcdelta.core.DeltaCore;
import mcdelta.core.Logger;
import mcdelta.core.assets.client.RenderAssets;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.core.network.PacketDelta;
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
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class Assets
{
    public static RenderAssets render = new RenderAssets();
    public static BlockShapes shapes = new BlockShapes();
    public static String worldAccessError = " Cannot set to air! Position's blockAccess needs to be an instanceof World.";

    // ========== Items ==========

    public static void clearCurrentItem(EntityPlayer player)
    {
        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
    }

    public static Item findItemWithName(String name)
    {
        for (Item element : Item.itemsList)
        {
            if ((element != null) && element.getUnlocalizedName().contains(name))
            {
                return element;
            }
        }

        return null;
    }

    // ========== Entities ==========

    public static Class<?> getEntityFromName(String name)
    {
        Class<?> clazz = (Class<?>) EntityList.stringToClassMapping.get(name);
        return clazz;
    }

    // ========== Prints ==========

    public static void log(Object... message)
    {
        Logger.log(message);
    }

    public static void log(Level level, Object... message)
    {
        Logger.log(level, message);
    }

    public static boolean resourceExists(ResourceLocation loc)
    {
        Minecraft.getMinecraft().getResourceManager().getResource(loc);

        return Minecraft.getMinecraft().getResourceManager().getResource(loc) != null;
    }

    public static float[] hexToRGB(int i)
    {
        float[] rgb = new float[3];

        int color = i;
        rgb[0] = ((color >> 16) & 255) / 255.0F;
        rgb[1] = ((color >> 8) & 255) / 255.0F;
        rgb[2] = (color & 255) / 255.0F;

        return rgb;
    }

    public static float average(float[] arr)
    {
        float returnVal = 0;

        for (float element : arr)
        {
            returnVal += element;
        }

        return (returnVal / arr.length);
    }

    public static boolean isClient()
    {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    public static boolean isServer()
    {
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    public static void placeBlock(Position pos, BlockData data)
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
            Logger.log(Level.SEVERE, "[ placeBlock() ]" + worldAccessError, pos);
        }
    }

    public static boolean setToAir(Position pos)
    {
        if (pos.blockAccess instanceof World)
        {
            World world = (World) pos.blockAccess;

            return world.setBlockToAir(pos.x, pos.y, pos.z);
        }
        Logger.log(Level.SEVERE, "[ setToAir() ]" + worldAccessError, pos);
        return false;
    }

    public static EnumFacing getFacing(Position pos)
    {
        return getFacing(pos.getMeta());
    }

    public static EnumFacing getFacing(int i)
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

    public static EnumFacing invertFace(EnumFacing facing)
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

    public static boolean isAirBlock(Position pos)
    {
        return pos.blockAccess.isAirBlock(pos.x, pos.y, pos.z);
    }

    public static boolean breakBlock(Position pos, boolean b)
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
        Logger.log(Level.SEVERE, "[ breakBlock() ]" + worldAccessError, pos);

        return false;
    }

    public static void moveEntityTowards(Entity entity, double targetX, double targetY, double targetZ, double time)
    {
        boolean xAlign = (int) entity.posX == (int) targetX;
        boolean yAlign = (int) entity.posY == (int) targetY;
        boolean zAlign = (int) entity.posZ == (int) targetZ;

        if (!xAlign && (targetX != 0))
        {
            double distance = Math.abs(entity.prevPosX - targetX);
            double speed = distance / time;

            int direction = (entity.posX < targetX) ? 1 : -1;

            entity.motionX = speed * direction;
        }

        if (!yAlign && (targetY != 0))
        {
            double distance = Math.abs(entity.prevPosY - targetY);
            double speed = distance / time;

            int direction = (entity.posY < targetY) ? 1 : -1;

            entity.motionY = speed * direction;
        }

        if (!zAlign && (targetZ != 0))
        {
            double distance = Math.abs(entity.prevPosZ - targetZ);
            double speed = distance / time;

            int direction = (entity.posZ < targetZ) ? 1 : -1;

            entity.motionZ = speed * direction;
        }
    }

    public static boolean isNegative(Number num)
    {
        if (num.doubleValue() < 0)
        {
            return true;
        }

        return false;
    }

    public static void updateBlock(Position pos)
    {
        if (pos.blockAccess instanceof World)
        {
            World world = (World) pos.blockAccess;

            world.markBlockForUpdate(pos.x, pos.y, pos.z);
        }

        else
        {
            Logger.log(Level.SEVERE, "[ Assets.updateBlock() ]" + worldAccessError, pos);
        }
    }

    public static List<Position> checkAdjacentBlocks(Block block, Position pos)
    {
        List<Position> positions = new ArrayList<Position>();

        for (EnumFacing face : EnumFacing.values())
        {
            if ((pos.move(face).getBlockData() != null) && (pos.move(face).getBlockData().block == block))
            {
                positions.add(pos.move(face));
            }
        }

        return positions;
    }

    public static List<Position> checkAdjacentBlocks(Position pos)
    {
        List<Position> positions = new ArrayList<Position>();

        for (EnumFacing face : EnumFacing.values())
        {
            positions.add(pos.move(face));
        }

        return positions;
    }

    public static Packet populatePacket(PacketDelta packet)
    {
        byte[] data = packet.populate();

        log("sending a packet", packet.getClass().getSimpleName(), data.length);

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = DeltaCore.MOD_ID;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = false;

        return packet250;
    }

    public static int getPowerInput(Position pos)
    {
        if (pos.blockAccess instanceof World)
        {
            World world = (World) pos.blockAccess;

            return world.getBlockPowerInput(pos.x, pos.y, pos.z);
        }
        Logger.log(Level.SEVERE, "[ Assets.getPowerInput() ]" + worldAccessError, pos);

        return 0;
    }

    public static void logBlank()
    {
        log("\n");
    }

    public static boolean isPoweredDirectly(Position pos)
    {
        return getPowerInput(pos) != 0;
    }

    public static boolean isPoweredIndirectly(Position pos)
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
        Logger.log(Level.SEVERE, "[ Assets.isPoweredIndirectly() ]" + worldAccessError, pos);

        return false;
    }

    public static List<Integer> getNeighborPowerInputs(Position pos)
    {
        return getNeighborPowerInputs(new ArrayList<EnumFacing>(), pos);
    }

    public static List<Integer> getNeighborPowerInputs(EnumFacing face, Position pos)
    {
        List<EnumFacing> list = new ArrayList<EnumFacing>();
        list.add(face);

        return getNeighborPowerInputs(list, pos);
    }

    public static List<Integer> getNeighborPowerInputs(List<EnumFacing> skip, Position pos)
    {
        List<Integer> list = new ArrayList<Integer>();

        for (EnumFacing face : EnumFacing.values())
        {
            if (!skip.contains(face))
            {
                list.add(Assets.getPowerInput(pos.move(face)));
            }
        }

        return list;
    }

    public static ModContainer getMod(String s)
    {
        return Loader.instance().getIndexedModList().get(s);
    }

    public static ModContainer deltaCore()
    {
        return getMod("DeltaCore");
    }

    public static Field getField(Class<?> clazz, String s) throws Exception
    {
        return clazz.getField(s);
    }

    public static String capitalize(String string)
    {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;

        for (int i = 0; i < chars.length; i++)
        {
            if (!found && Character.isLetter(chars[i]))
            {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            }

            else if (Character.isWhitespace(chars[i]) || (chars[i] == '.') || (chars[i] == '\''))
            {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}