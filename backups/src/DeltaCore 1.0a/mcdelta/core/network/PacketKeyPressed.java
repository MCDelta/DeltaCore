package mcdelta.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcdelta.tuxweapons.TuxWeaponsCore;
import mcdelta.tuxweapons.sound.Sounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.Player;

public class PacketKeyPressed extends PacketDelta
{
    private String key;

    public PacketKeyPressed()
    {
        super(EnumPacketTypes.KEY_PRESSED);
    }

    public PacketKeyPressed(String s)
    {
        super(EnumPacketTypes.KEY_PRESSED);
        this.key = s;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        data.writeUTF(key);
    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        key = data.readUTF();
    }

    @Override
    public void execute(INetworkManager manager, Player playerParam)
    {
        EntityPlayer player = (EntityPlayer) playerParam;

        World world = player.worldObj;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;

        int note = Integer.parseInt(key.replace("key." + TuxWeaponsCore.MOD_ID + ":note.", ""));

        world.playSoundEffect(x, y, z, Sounds.ukulele, 0.3F, ((float) (note - 5) / 10) + 1);
    }
}