package mcdelta.core.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleHammerSmash extends EntityFX
{
    private int currentLife;
    private int maximumLife;
    private final int radius;

    public ParticleHammerSmash(World world, double x, double y, double z, int radi)
    {
        super(world, x, y, z);

        worldObj = world;

        posX = x;
        posY = y;
        posZ = z;

        radius = radi;
    }

    @Override
    public void onUpdate()
    {
        for (int i = 0; i < 150; ++i)
        {
            double xOffset = (rand.nextInt(radius * 2) - radius) + rand.nextDouble();
            double yOffset = rand.nextDouble();
            double zOffset = (rand.nextInt(radius * 2) - radius) + rand.nextDouble();

            double motionX = xOffset;
            double motionY = yOffset;
            double motionZ = zOffset;

            int id = worldObj.getBlockId(((int) (posX + xOffset)), ((int) posY - 1), ((int) (posZ + zOffset)));
            boolean flag = true;

            if (id == 0)
            {
                flag = false;
            }
            if (flag)
            {
                worldObj.spawnParticle("tilecrack_" + id + "_0", posX + xOffset, posY + yOffset, posZ + zOffset, motionX, motionY, motionZ);
            }
        }

        if (currentLife == maximumLife)
        {
            for (int i = 0; i < 10; ++i)
            {
                int radiusClose = 2;

                double xOffset = rand.nextInt(radiusClose * 2) - radiusClose;
                double zOffset = rand.nextInt(radiusClose * 2) - radiusClose;

                worldObj.spawnParticle("cloud", posX + xOffset, posY, posZ + zOffset, 0, 0, 0);
            }
        }
        ++currentLife;

        if (currentLife >= maximumLife)
        {
            setDead();
        }
    }

    @Override
    public int getFXLayer()
    {
        return 3;
    }
}