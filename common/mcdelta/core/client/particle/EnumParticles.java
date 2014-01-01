package mcdelta.core.client.particle;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum EnumParticles
{
    HAMMER_SMASH,
    CRITICAL_HIT,
    ITEM_BREAK,
    CRITICAL_HIT_NORMAL;

    public void spawnParticle(World world, double x, double y, double z, Object... obj)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if ((mc != null) && (mc.renderViewEntity != null) && (mc.effectRenderer != null))
        {
            int particleSetting = Minecraft.getMinecraft().gameSettings.particleSetting;

            if ((particleSetting == 2) || ((particleSetting == 1) && (world.rand.nextInt(3) == 0)))
            {
                return;
            }

            double distanceX = mc.renderViewEntity.posX - x;
            double distanceY = mc.renderViewEntity.posY - y;
            double distanceZ = mc.renderViewEntity.posZ - z;

            double maxDistance = 16;
            if (((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ)) > (maxDistance * maxDistance))
            {
                return;
            }

            EntityFX fx = null;

            switch (this)
            {
                case HAMMER_SMASH:
                    fx = new ParticleHammerSmash(world, x, y, z, (Integer) obj[0]);
                    break;

                case CRITICAL_HIT:

                    if (obj.length == 2)
                    {
                        fx = new ParticleCriticalHit((Entity) obj[0], (Integer) obj[1]);
                        break;
                    }

                    fx = new ParticleCriticalHit((Entity) obj[0], 0xAB3D3D, 1, 20, false);
                    break;

                case CRITICAL_HIT_NORMAL:

                    fx = new ParticleCriticalHit((Entity) obj[0], 0xe5e5e5, 1, 20, false);
                    break;

                case ITEM_BREAK:
                    fx = new ParticleItemBreak((Entity) obj[0], (ItemStack) obj[1], 2, 6);
                    break;

                default:
                    break;
            }

            if (fx != null)
            {
                Minecraft.getMinecraft().effectRenderer.addEffect(fx);
            }
        }
    }

    public void spawnParticleFromPacket(EntityPlayer player, double x, double y, double z)
    {
        World world = player.worldObj;
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class,
                AxisAlignedBB.getBoundingBox(x - 0.1D, y - 0.1D, z - 0.1D, x + 0.1D, y + 0.1D, z + 0.1D));

        switch (this)
        {
            case CRITICAL_HIT:
                if (!entities.isEmpty())
                {
                    spawnParticle(world, x, y, z, entities.get(0), 0xAB3D3D, 1, 20, false);
                }
                break;

            case CRITICAL_HIT_NORMAL:
                if (!entities.isEmpty())
                {
                    spawnParticle(world, x, y, z, entities.get(0), 0xffffff, 1, 20, false);
                }
                break;

            case ITEM_BREAK:
                if (!entities.isEmpty())
                {
                    spawnParticle(world, x, y, z, entities.get(0), player.getHeldItem(), 2, 6);
                }
                break;

            default:
                break;
        }
    }
}
