package mcdelta.core.client.particle;

import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.item.ItemWeapon;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class FXItemBreak extends EntityFX
{
     private final ItemStack stack;
     
     
     
     
     public FXItemBreak (final World world, final double x, final double y, final double z, final ItemStack item)
     {
          this(world, x, y, z, item, 0);
     }
     
     
     
     
     public FXItemBreak (final World world, final double x, final double y, final double z, final ItemStack item, final int damage)
     {
          super(world, x, y, z, 0.0D, 0.0D, 0.0D);
          this.stack = item;
          this.setParticleIcon(this.stack.getItem().getIconFromDamage(damage));
          this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
          this.particleGravity = Block.blockSnow.blockParticleGravity;
          this.particleScale /= 2.0F;
     }
     
     
     
     
     public FXItemBreak (final World world, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final ItemStack item, final int damage)
     {
          this(world, x, y, z, item, damage);
          this.motionX *= 0.10000000149011612D;
          this.motionY *= 0.10000000149011612D;
          this.motionZ *= 0.10000000149011612D;
          this.motionX += motionX;
          this.motionY += motionY;
          this.motionZ += motionZ;
     }
     
     
     
     
     @Override
     public int getFXLayer ()
     {
          return 2;
     }
     
     
     
     
     @Override
     public void renderParticle (final Tessellator tessellator, final float pt, final float x, final float y, final float z, final float rotationA, final float rotationB)
     {
          float f6 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
          float f7 = f6 + 0.015609375F;
          float f8 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
          float f9 = f8 + 0.015609375F;
          final float f10 = 0.1F * this.particleScale;
          
          final int passes = 1;
          
          final Icon[] icons = new Icon[passes];
          icons[0] = this.stack.getItem().getIconFromDamage(this.stack.getItemDamage());
          
          final int[] colors = new int[passes];
          colors[0] = 0xffffff;
          
          if (this.stack.getItem() instanceof IExtraPasses && this.stack.getItem() instanceof ItemWeapon)
          {
               for (int i = 0; i < passes; i++)
               {
                    icons[i] = ((IExtraPasses) this.stack.getItem()).getIconFromPass(this.stack, i + 1);
               }
               for (int i = 0; i < passes; i++)
               {
                    colors[i] = ((IExtraPasses) this.stack.getItem()).getColorFromPass(this.stack, i + 1);
               }
          }
          for (int i = 0; i < passes; i++)
          {
               if (icons[i] != null)
               {
                    f6 = icons[i].getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
                    f7 = icons[i].getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
                    f8 = icons[i].getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
                    f9 = icons[i].getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
               }
               final float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * pt - interpPosX);
               final float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * pt - interpPosY);
               final float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * pt - interpPosZ);
               final float f14 = 1.0F;
               
               final float[] rgb = Assets.hexToRGB(colors[i]);
               tessellator.setColorOpaque_F(f14 * rgb[0], f14 * rgb[1], f14 * rgb[2]);
               
               tessellator.addVertexWithUV(f11 - x * f10 - rotationA * f10, f12 - y * f10, f13 - z * f10 - rotationB * f10, f6, f9);
               tessellator.addVertexWithUV(f11 - x * f10 + rotationA * f10, f12 + y * f10, f13 - z * f10 + rotationB * f10, f6, f8);
               tessellator.addVertexWithUV(f11 + x * f10 + rotationA * f10, f12 + y * f10, f13 + z * f10 + rotationB * f10, f7, f8);
               tessellator.addVertexWithUV(f11 + x * f10 - rotationA * f10, f12 - y * f10, f13 + z * f10 - rotationB * f10, f7, f9);
          }
     }
}
