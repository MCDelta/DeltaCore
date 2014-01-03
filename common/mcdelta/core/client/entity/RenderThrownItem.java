package mcdelta.core.client.entity;

import mcdelta.core.assets.client.RenderAssets;
import net.minecraft.client.renderer.entity.Render;
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
    private final Item item;
    private final int damage;

    public RenderThrownItem(final Item par1Item, final int x)
    {
        item = par1Item;
        damage = x;
    }

    public RenderThrownItem(final Item item)
    {
        this(item, 0);
    }

    @Override
    public void doRender(final Entity entity, final double x, final double y, final double z, final float par8, final float par9)
    {
        final Icon icon = item.getIconFromDamage(damage);

        if (icon != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, (float) z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.5F, 0.5F, 0.5F);

            GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

            RenderAssets.renderItemInWorld(new ItemStack(item), icon);

            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(final Entity entity)
    {
        return TextureMap.locationItemsTexture;
    }
}
