package mcdelta.core.client.item;

import mcdelta.core.assets.client.RenderAssets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RenderExtraPasses implements IItemRenderer
{
     int zLevel = 0;
     
     
     
     
     @Override
     public boolean handleRenderType (final ItemStack item, final ItemRenderType type)
     {
          return type != ItemRenderType.FIRST_PERSON_MAP;
     }
     
     
     
     
     @Override
     public boolean shouldUseRenderHelper (final ItemRenderType type, final ItemStack item, final ItemRendererHelper helper)
     {
          return helper == ItemRendererHelper.ENTITY_ROTATION && Minecraft.getMinecraft().gameSettings.fancyGraphics || helper == ItemRendererHelper.ENTITY_BOBBING;
     }
     
     
     
     
     @Override
     public void renderItem (final ItemRenderType type, final ItemStack stack, final Object... data)
     {
          GL11.glPushMatrix();
          
          final int passes = ((IExtraPasses) stack.getItem()).getPasses(stack);
          final Icon[] icons = new Icon[passes];
          final int[] colors = new int[passes];
          final boolean[] shiny = new boolean[passes];
          
          for (int i = 0; i < passes; i++)
          {
               icons[i] = ((IExtraPasses) stack.getItem()).getIconFromPass(stack, i + 1);
          }
          for (int i = 0; i < passes; i++)
          {
               colors[i] = ((IExtraPasses) stack.getItem()).getColorFromPass(stack, i + 1);
          }
          for (int i = 0; i < passes; i++)
          {
               shiny[i] = ((IExtraPasses) stack.getItem()).getShinyFromPass(stack, i + 1);
          }
          final TextureManager engine = Minecraft.getMinecraft().getTextureManager();
          
          if (type == ItemRenderType.INVENTORY)
          {
               RenderAssets.renderItemInventory(stack, engine, passes, icons, colors, shiny, zLevel);
          }
          if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON)
          {
               RenderAssets.renderEquippedItem(stack, engine, passes, icons, colors, shiny);
          }
          if (type == ItemRenderType.ENTITY)
          {
               final EntityItem entityItem = (EntityItem) data[1];
               
               RenderAssets.renderEntityItem(entityItem, stack, passes, icons, colors, shiny);
          }
          GL11.glPopMatrix();
     }
}
