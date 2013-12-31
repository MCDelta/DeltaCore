package mcdelta.core.assets.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderAssets
{
     private static final ResourceLocation RES_ITEM_GLINT         = new ResourceLocation("textures/misc/enchanted_item_glint.png");
     private static final ResourceLocation RES_MAP_BACKGROUND     = new ResourceLocation("textures/map/map_background.png");
     private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
     
     public static ResourceLocation        glint                  = new ResourceLocation("textures/misc/enchanted_item_glint.png");
     private static Random                 rand                   = new Random();
     
     public static List<Item>              flipInInventory        = new ArrayList();
     
     
     
     
     public static void rotateSidedRenderer (RenderBlocks renderer, EnumFacing face)
     {
          switch (face)
          {
               case UP:
                    break;
               case DOWN:
                    renderer.uvRotateEast = 3;
                    renderer.uvRotateWest = 3;
                    renderer.uvRotateSouth = 3;
                    renderer.uvRotateNorth = 3;
                    break;
               case NORTH:
                    renderer.uvRotateSouth = 1;
                    renderer.uvRotateNorth = 2;
                    break;
               case SOUTH:
                    renderer.uvRotateSouth = 2;
                    renderer.uvRotateNorth = 1;
                    renderer.uvRotateTop = 3;
                    renderer.uvRotateBottom = 3;
                    break;
               case EAST:
                    renderer.uvRotateEast = 1;
                    renderer.uvRotateWest = 2;
                    renderer.uvRotateTop = 2;
                    renderer.uvRotateBottom = 1;
                    break;
               case WEST:
                    renderer.uvRotateEast = 2;
                    renderer.uvRotateWest = 1;
                    renderer.uvRotateTop = 1;
                    renderer.uvRotateBottom = 2;
                    break;
          }
     }
     
     
     
     
     public static void renderEntityItem (EntityItem entityItem, ItemStack stack, int passes, Icon[] icons, int[] colors, boolean[] shiny)
     {
          byte b0 = getMiniBlockCount(stack);
          
          for (int i = 0; i < passes; i++)
          {
               Icon icon = icons[i];
               
               rand.setSeed(187L);
               
               int color = colors[i];
               float r = (float) (color >> 16 & 255) / 255.0F;
               float g = (float) (color >> 8 & 255) / 255.0F;
               float b = (float) (color & 255) / 255.0F;
               
               renderDroppedItem(entityItem, stack, icon, b0, 0, r, g, b, 0, shiny[i]);
          }
     }
     
     
     
     
     public static void renderItemInWorld (ItemStack stack, TextureManager texturemanager, Icon icon)
     {
          renderItemInWorld(stack, texturemanager, 1, new Icon[]
          { icon }, new int[]
          { 0xffffff }, new boolean[]
          { false });
     }
     
     
     
     
     public static void renderItemInWorld (ItemStack stack, TextureManager texturemanager, int passes, Icon[] icons, int[] colors, boolean[] shiny)
     {
          for (int i = 0; i < passes; i++)
          {
               Icon icon = icons[i];
               
               rand.setSeed(187L);
               
               int color = colors[i];
               float r = (float) (color >> 16 & 255) / 255.0F;
               float g = (float) (color >> 8 & 255) / 255.0F;
               float b = (float) (color & 255) / 255.0F;
               
               renderDroppedItem(null, stack, icon, 0, 0, r, g, b, 0, shiny[i]);
          }
          
     }
     
     
     
     
     public static void renderEquippedItem (ItemStack stack, TextureManager texturemanager, Icon icon)
     {
          renderEquippedItem(stack, texturemanager, 1, new Icon[]
          { icon }, new int[]
          { 0xffffff }, new boolean[]
          { false });
     }
     
     
     
     
     public static void renderEquippedItem (ItemStack stack, TextureManager texturemanager, int passes, Icon[] icons, int[] colors, boolean[] shiny)
     {
          for (int i = 0; i < passes; i++)
          {
               Icon icon = icons[i];
               
               if (icon == null)
               {
                    GL11.glPopMatrix();
                    return;
               }
               
               texturemanager.bindTexture(texturemanager.getResourceLocation(stack.getItemSpriteNumber()));
               Tessellator tessellator = Tessellator.instance;
               float f = icon.getMinU();
               float f1 = icon.getMaxU();
               float f2 = icon.getMinV();
               float f3 = icon.getMaxV();
               
               int color = colors[i];
               float r = (float) (color >> 16 & 255) / 255.0F;
               float g = (float) (color >> 8 & 255) / 255.0F;
               float b = (float) (color & 255) / 255.0F;
               GL11.glColor4f(r, g, b, 1.0F);
               
               ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
               
               if (stack.hasEffect(0) || shiny[i])
               {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    texturemanager.bindTexture(glint);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f7 = 0.76F;
                    GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f8 = 0.125F;
                    GL11.glScalef(f8, f8, f8);
                    float f9 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f9, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f8, f8, f8);
                    f9 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f9, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
               }
               
               GL11.glDisable(GL12.GL_RESCALE_NORMAL);
          }
     }
     
     
     
     
     public static void renderItemInventory (ItemStack stack, TextureManager engine, int zLevel)
     {
          renderItemInventory(stack, engine, 1, new Icon[]
          { stack.getIconIndex() }, new int[]
          { 0xffffff }, null, zLevel);
     }
     
     
     
     
     public static void renderItemInventory (ItemStack stack, TextureManager engine, int passes, Icon[] icons, int[] colors, boolean[] shiny, int zLevel)
     {
          int k = stack.itemID;
          int l = stack.getItemDamage();
          
          float f;
          int i1;
          float f1;
          float f2;
          
          ResourceLocation resourcelocation = engine.getResourceLocation(stack.getItemSpriteNumber());
          
          for (int i = 0; i < passes; i++)
          {
               GL11.glDisable(GL11.GL_LIGHTING);
               engine.bindTexture(resourcelocation);
               
               i1 = colors[i];
               f = (float) (i1 >> 16 & 255) / 255.0F;
               f1 = (float) (i1 >> 8 & 255) / 255.0F;
               f2 = (float) (i1 & 255) / 255.0F;
               
               GL11.glColor4f(f, f1, f2, 1.0F);
               
               if (icons[i] != null)
               {
                    renderIcon(0, 0, icons[i], 16, 16, zLevel);
               }
               
               GL11.glEnable(GL11.GL_LIGHTING);
               
               if ((shiny != null && shiny[i]) || stack.isItemEnchanted())
               {
                    renderEffect(engine, 0, 0, zLevel);
               }
               
               GL11.glEnable(GL11.GL_CULL_FACE);
          }
     }
     
     
     
     
     private static void renderIcon (int x, int y, Icon icon, int par4, int par5, int zLevel)
     {
          Tessellator tessellator = Tessellator.instance;
          tessellator.startDrawingQuads();
          tessellator.addVertexWithUV((double) (x + 0), (double) (y + par5), (double) zLevel, (double) icon.getMinU(), (double) icon.getMaxV());
          tessellator.addVertexWithUV((double) (x + par4), (double) (y + par5), (double) zLevel, (double) icon.getMaxU(), (double) icon.getMaxV());
          tessellator.addVertexWithUV((double) (x + par4), (double) (y + 0), (double) zLevel, (double) icon.getMaxU(), (double) icon.getMinV());
          tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel, (double) icon.getMinU(), (double) icon.getMinV());
          tessellator.draw();
     }
     
     
     
     
     private static void renderEffect (TextureManager manager, int x, int y, int zLevel)
     {
          GL11.glDepthFunc(GL11.GL_GREATER);
          GL11.glDisable(GL11.GL_LIGHTING);
          GL11.glDepthMask(false);
          manager.bindTexture(glint);
          zLevel -= 50.0F;
          GL11.glEnable(GL11.GL_BLEND);
          GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
          GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
          renderGlint(x * 431278612 + y * 32178161, x - 2, y - 2, 20, 20, zLevel);
          GL11.glDisable(GL11.GL_BLEND);
          GL11.glDepthMask(true);
          zLevel += 50.0F;
          GL11.glEnable(GL11.GL_LIGHTING);
          GL11.glDepthFunc(GL11.GL_LEQUAL);
     }
     
     
     
     
     private static void renderGlint (int par1, int par2, int par3, int par4, int par5, int zLevel)
     {
          for (int j1 = 0; j1 < 2; ++j1)
          {
               if (j1 == 0)
               {
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
               }
               
               if (j1 == 1)
               {
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
               }
               
               float f = 0.00390625F;
               float f1 = 0.00390625F;
               float f2 = (float) (Minecraft.getSystemTime() % (long) (3000 + j1 * 1873)) / (3000.0F + (float) (j1 * 1873)) * 256.0F;
               float f3 = 0.0F;
               Tessellator tessellator = Tessellator.instance;
               float f4 = 4.0F;
               
               if (j1 == 1)
               {
                    f4 = -1.0F;
               }
               
               tessellator.startDrawingQuads();
               tessellator.addVertexWithUV((double) (par2 + 0), (double) (par3 + par5), (double) zLevel, (double) ((f2 + (float) par5 * f4) * f), (double) ((f3 + (float) par5) * f1));
               tessellator.addVertexWithUV((double) (par2 + par4), (double) (par3 + par5), (double) zLevel, (double) ((f2 + (float) par4 + (float) par5 * f4) * f), (double) ((f3 + (float) par5) * f1));
               tessellator.addVertexWithUV((double) (par2 + par4), (double) (par3 + 0), (double) zLevel, (double) ((f2 + (float) par4) * f), (double) ((f3 + 0.0F) * f1));
               tessellator.addVertexWithUV((double) (par2 + 0), (double) (par3 + 0), (double) zLevel, (double) ((f2 + 0.0F) * f), (double) ((f3 + 0.0F) * f1));
               tessellator.draw();
          }
     }
     
     
     
     
     private static void renderDroppedItem (EntityItem entityItem, ItemStack stack, Icon icon, int par3, float par4, float par5, float par6, float par7, int pass, boolean shiny)
     {
          TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
          Tessellator tessellator = Tessellator.instance;
          
          if (entityItem != null && icon == null)
          {
               ResourceLocation resourcelocation = texturemanager.getResourceLocation(entityItem.getEntityItem().getItemSpriteNumber());
               icon = ((TextureMap) texturemanager.getTexture(resourcelocation)).getAtlasSprite("missingno");
          }
          
          float f4 = ((Icon) icon).getMinU();
          float f5 = ((Icon) icon).getMaxU();
          float f6 = ((Icon) icon).getMinV();
          float f7 = ((Icon) icon).getMaxV();
          float f8 = 1.0F;
          float f9 = 0.5F;
          float f10 = 0.25F;
          float f11;
          
          if (Minecraft.getMinecraft().gameSettings.fancyGraphics)
          {
               GL11.glPushMatrix();
               
               float f12 = 0.0625F;
               f11 = 0.021875F;
               int j = stack.stackSize;
               byte b0 = getMiniItemCount(stack);
               
               GL11.glTranslatef(-f9, -f10, -((f12 + f11) * (float) b0 / 2.0F));
               
               for (int k = 0; k < b0; ++k)
               {
                    if (k > 0)
                    {
                         float x = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                         float y = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                         float z = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                         GL11.glTranslatef(x, y, f12 + f11);
                    }
                    else
                    {
                         GL11.glTranslatef(0f, 0f, f12 + f11);
                    }
                    
                    if (stack.getItemSpriteNumber() == 0)
                    {
                         texturemanager.bindTexture(TextureMap.locationBlocksTexture);
                    }
                    else
                    {
                         texturemanager.bindTexture(TextureMap.locationItemsTexture);
                    }
                    
                    GL11.glColor4f(par5, par6, par7, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, ((Icon) icon).getIconWidth(), ((Icon) icon).getIconHeight(), f12);
                    
                    if (stack.hasEffect(pass) || shiny)
                    {
                         GL11.glDepthFunc(GL11.GL_EQUAL);
                         GL11.glDisable(GL11.GL_LIGHTING);
                         texturemanager.bindTexture(glint);
                         GL11.glEnable(GL11.GL_BLEND);
                         GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                         float f13 = 0.76F;
                         GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
                         GL11.glMatrixMode(GL11.GL_TEXTURE);
                         GL11.glPushMatrix();
                         float f14 = 0.125F;
                         GL11.glScalef(f14, f14, f14);
                         float f15 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                         GL11.glTranslatef(f15, 0.0F, 0.0F);
                         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                         ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
                         GL11.glPopMatrix();
                         GL11.glPushMatrix();
                         GL11.glScalef(f14, f14, f14);
                         f15 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                         GL11.glTranslatef(-f15, 0.0F, 0.0F);
                         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                         ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f12);
                         GL11.glPopMatrix();
                         GL11.glMatrixMode(GL11.GL_MODELVIEW);
                         GL11.glDisable(GL11.GL_BLEND);
                         GL11.glEnable(GL11.GL_LIGHTING);
                         GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }
               }
               
               GL11.glPopMatrix();
          }
          
          else
          {
               for (int l = 0; l < par3; ++l)
               {
                    GL11.glPushMatrix();
                    
                    if (l > 0)
                    {
                         f11 = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
                         float f16 = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
                         float f17 = (rand.nextFloat() * 2.0F - 1.0F) * 0.3F;
                         GL11.glTranslatef(f11, f16, f17);
                    }
                    
                    GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                    
                    GL11.glColor4f(par5, par6, par7, 1.0F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    tessellator.addVertexWithUV((double) (0.0F - f9), (double) (0.0F - f10), 0.0D, (double) f4, (double) f7);
                    tessellator.addVertexWithUV((double) (f8 - f9), (double) (0.0F - f10), 0.0D, (double) f5, (double) f7);
                    tessellator.addVertexWithUV((double) (f8 - f9), (double) (1.0F - f10), 0.0D, (double) f5, (double) f6);
                    tessellator.addVertexWithUV((double) (0.0F - f9), (double) (1.0F - f10), 0.0D, (double) f4, (double) f6);
                    tessellator.draw();
                    GL11.glPopMatrix();
               }
          }
     }
     
     
     
     
     private static byte getMiniItemCount (ItemStack stack)
     {
          byte ret = 1;
          if (stack.stackSize > 1)
               ret = 2;
          if (stack.stackSize > 15)
               ret = 3;
          if (stack.stackSize > 31)
               ret = 4;
          return ret;
     }
     
     
     
     
     private static byte getMiniBlockCount (ItemStack stack)
     {
          byte ret = 1;
          if (stack.stackSize > 1)
               ret = 2;
          if (stack.stackSize > 5)
               ret = 3;
          if (stack.stackSize > 20)
               ret = 4;
          if (stack.stackSize > 40)
               ret = 5;
          return ret;
     }
     
     
     
     
     public static void renderItem (EntityLivingBase living, ItemStack stack, int par3, ItemRenderType type, RenderBlocks renderBlocks)
     {
          GL11.glPushMatrix();
          TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
          
          Block block = null;
          if (stack.getItem() instanceof ItemBlock && stack.itemID < Block.blocksList.length)
          {
               block = Block.blocksList[stack.itemID];
          }
          
          IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(stack, type);
          if (customRenderer != null)
          {
               texturemanager.bindTexture(texturemanager.getResourceLocation(stack.getItemSpriteNumber()));
               ForgeHooksClient.renderEquippedItem(type, customRenderer, renderBlocks, living, stack);
          }
          else if (block != null && stack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.blocksList[stack.itemID].getRenderType()))
          {
               texturemanager.bindTexture(texturemanager.getResourceLocation(0));
               renderBlocks.renderBlockAsItem(Block.blocksList[stack.itemID], stack.getItemDamage(), 1.0F);
          }
          else
          {
               Icon icon = living.getItemIcon(stack, par3);
               
               if (icon == null)
               {
                    GL11.glPopMatrix();
                    return;
               }
               
               texturemanager.bindTexture(texturemanager.getResourceLocation(stack.getItemSpriteNumber()));
               Tessellator tessellator = Tessellator.instance;
               float f = icon.getMinU();
               float f1 = icon.getMaxU();
               float f2 = icon.getMinV();
               float f3 = icon.getMaxV();
               float f4 = 0.0F;
               float f5 = 0.3F;
               GL11.glEnable(GL12.GL_RESCALE_NORMAL);
               GL11.glTranslatef(-f4, -f5, 0.0F);
               float f6 = 1.5F;
               GL11.glScalef(f6, f6, f6);
               GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
               ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
               
               if (stack.hasEffect(par3))
               {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    texturemanager.bindTexture(glint);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f7 = 0.76F;
                    GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f8 = 0.125F;
                    GL11.glScalef(f8, f8, f8);
                    float f9 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f9, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f8, f8, f8);
                    f9 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f9, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
               }
               GL11.glDisable(GL12.GL_RESCALE_NORMAL);
          }
          GL11.glPopMatrix();
     }
}
