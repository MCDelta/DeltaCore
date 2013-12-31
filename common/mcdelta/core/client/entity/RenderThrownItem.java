package mcdelta.core.client.entity;

import mcdelta.core.assets.client.RenderAssets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderThrownItem extends Render
{
    private Item item;
    private int  damage;

    public RenderThrownItem(Item par1Item, int x)
    {
        this.item = par1Item;
        this.damage = x;
    }

    public RenderThrownItem(Item item)
    {
        this(item, 0);
    }

    public void doRender(Entity entity, double x, double y, double z, float par8, float par9)
    {
        Icon icon = this.item.getIconFromDamage(this.damage);
        TextureManager engine = Minecraft.getMinecraft().getTextureManager();

        if (icon != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, (float) z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            
            GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            
            RenderAssets.renderItemInWorld(new ItemStack(item), engine, icon);

            GL11.glPopMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TextureMap.locationItemsTexture;
    }

    private void func_77026_a(Tessellator tessalator, Icon xIcon)
    {
        float f = xIcon.getMinU();
        float f1 = xIcon.getMaxU();
        float f2 = xIcon.getMinV();
        float f3 = xIcon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessalator.startDrawingQuads();
        tessalator.setNormal(0.0F, 1.0F, 0.0F);
        tessalator.addVertexWithUV((double) (0.0F - f5), (double) (0.0F - f6), 0.0D, (double) f, (double) f3);
        tessalator.addVertexWithUV((double) (f4 - f5), (double) (0.0F - f6), 0.0D, (double) f1, (double) f3);
        tessalator.addVertexWithUV((double) (f4 - f5), (double) (f4 - f6), 0.0D, (double) f1, (double) f2);
        tessalator.addVertexWithUV((double) (0.0F - f5), (double) (f4 - f6), 0.0D, (double) f, (double) f2);
        tessalator.draw();
    }
}