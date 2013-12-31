package mcdelta.core.client.particle;

import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.config.Settings;
import mcdelta.core.item.ItemWeapon;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FXItemBreak extends EntityFX
{
    private ItemStack stack;

    public FXItemBreak(World world, double x, double y, double z, ItemStack item)
    {
        this(world, x, y, z, item, 0);
    }

    public FXItemBreak(World world, double x, double y, double z, ItemStack item, int damage)
    {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.stack = item;
        this.setParticleIcon(this.stack.getItem().getIconFromDamage(damage));
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Block.blockSnow.blockParticleGravity;
        this.particleScale /= 2.0F;
    }

    public FXItemBreak(World world, double x, double y, double z, double motionX, double motionY, double motionZ, ItemStack item, int damage)
    {
        this(world, x, y, z, item, damage);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += motionX;
        this.motionY += motionY;
        this.motionZ += motionZ;
    }

    public int getFXLayer()
    {
        return 2;
    }

    public void renderParticle(Tessellator tessellator, float pt, float x, float y, float z, float rotationA, float rotationB)
    {
        float f6 = ((float) this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = ((float) this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;

        int passes = 1;
        
        Icon[] icons = new Icon[passes];
        icons[0] = stack.getItem().getIconFromDamage(stack.getItemDamage());
        
        int[] colors = new int[passes];
        colors[0] = 0xffffff;
        
        if (stack.getItem() instanceof IExtraPasses && stack.getItem() instanceof ItemWeapon)
        {
            ItemWeapon weapon = (ItemWeapon) stack.getItem();
            
            for (int i = 0; i < passes; i++)
            {
                 icons[i] = ((IExtraPasses) stack.getItem()).getIconFromPass(stack, i + 1);
            }
            
            for (int i = 0; i < passes; i++)
            {
                 colors[i] = ((IExtraPasses) stack.getItem()).getColorFromPass(stack, i + 1);
            }
        }
        
        for (int i = 0; i < passes; i++)
        {
            if (icons[i] != null)
            {
                f6 = icons[i].getInterpolatedU((double) (this.particleTextureJitterX / 4.0F * 16.0F));
                f7 = icons[i].getInterpolatedU((double) ((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
                f8 = icons[i].getInterpolatedV((double) (this.particleTextureJitterY / 4.0F * 16.0F));
                f9 = icons[i].getInterpolatedV((double) ((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
            }

            float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) pt - interpPosX);
            float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) pt - interpPosY);
            float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) pt - interpPosZ);
            float f14 = 1.0F;
            
            float[] rgb = Assets.hexToRGB(colors[i]);
            tessellator.setColorOpaque_F(f14 * rgb[0], f14 * rgb[1], f14 * rgb[2]);
            
            tessellator.addVertexWithUV((double) (f11 - x * f10 - rotationA * f10), (double) (f12 - y * f10), (double) (f13 - z * f10 - rotationB * f10), (double) f6, (double) f9);
            tessellator.addVertexWithUV((double) (f11 - x * f10 + rotationA * f10), (double) (f12 + y * f10), (double) (f13 - z * f10 + rotationB * f10), (double) f6, (double) f8);
            tessellator.addVertexWithUV((double) (f11 + x * f10 + rotationA * f10), (double) (f12 + y * f10), (double) (f13 + z * f10 + rotationB * f10), (double) f7, (double) f8);
            tessellator.addVertexWithUV((double) (f11 + x * f10 - rotationA * f10), (double) (f12 - y * f10), (double) (f13 + z * f10 - rotationB * f10), (double) f7, (double) f9);
        }
    }
}