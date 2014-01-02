package mcdelta.core.client.entity;

import mcdelta.core.entity.EntityMovingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class RenderMovingBlock extends Render
{
     private final RenderBlocks renderBlocks = new RenderBlocks();
     
     
     
     
     public RenderMovingBlock ()
     {
          this.shadowSize = 0F;
     }
     
     
     
     
     public void doRenderMovingBlock (EntityMovingBlock movingBlock, double x, double y, double z, float par8, float partialTicks)
     {
          World world = movingBlock.getWorld();
          Block block = Block.blocksList[movingBlock.blockID];
          
          if (world.getBlockId(MathHelper.floor_double(movingBlock.posX), MathHelper.floor_double(movingBlock.posY), MathHelper.floor_double(movingBlock.posZ)) != movingBlock.blockID)
          {
               GL11.glPushMatrix();
               GL11.glTranslatef((float) x, (float) y, (float) z);
               this.getFallingSandTextures(movingBlock);
               GL11.glDisable(GL11.GL_LIGHTING);
               Tessellator tessellator = Tessellator.instance;
               
               if (block instanceof BlockAnvil && block.getRenderType() == 35)
               {
                    this.renderBlocks.blockAccess = world;
                    tessellator.startDrawingQuads();
                    tessellator.setTranslation((double) ((float) (-MathHelper.floor_double(movingBlock.posX)) - 0.5F), (double) ((float) (-MathHelper.floor_double(movingBlock.posY)) - 0.5F), (double) ((float) (-MathHelper.floor_double(movingBlock.posZ)) - 0.5F));
                    this.renderBlocks.renderBlockAnvilMetadata((BlockAnvil) block, MathHelper.floor_double(movingBlock.posX), MathHelper.floor_double(movingBlock.posY), MathHelper.floor_double(movingBlock.posZ), movingBlock.metadata);
                    tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                    tessellator.draw();
               }
               
               else if (block.getRenderType() == 27)
               {
                    this.renderBlocks.blockAccess = world;
                    tessellator.startDrawingQuads();
                    tessellator.setTranslation((double) ((float) (-MathHelper.floor_double(movingBlock.posX)) - 0.5F), (double) ((float) (-MathHelper.floor_double(movingBlock.posY)) - 0.5F), (double) ((float) (-MathHelper.floor_double(movingBlock.posZ)) - 0.5F));
                    this.renderBlocks.renderBlockDragonEgg((BlockDragonEgg) block, MathHelper.floor_double(movingBlock.posX), MathHelper.floor_double(movingBlock.posY), MathHelper.floor_double(movingBlock.posZ));
                    tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                    tessellator.draw();
               }
               
               else if (IMovingBlockRenderer.renderers.containsKey(block.getRenderType()))
               {
                    IMovingBlockRenderer.renderers.get(block.getRenderType()).renderMovingBlock(world, block, tessellator, movingBlock, x, y, z, par8, partialTicks, this.renderManager);
                    
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glPopMatrix();
                    
                    return;
               }
               
               else if (block != null)
               {
                    this.renderBlocks.setRenderBoundsFromBlock(block);
                    this.renderBlocks.renderBlockSandFalling(block, world, MathHelper.floor_double(movingBlock.posX), MathHelper.floor_double(movingBlock.posY), MathHelper.floor_double(movingBlock.posZ), movingBlock.metadata);
               }
               
               GL11.glEnable(GL11.GL_LIGHTING);
               GL11.glPopMatrix();
          }
     }
     
     
     
     
     protected ResourceLocation getFallingSandTextures (EntityMovingBlock movingBlock)
     {
          return TextureMap.locationBlocksTexture;
     }
     
     
     
     
     protected ResourceLocation getEntityTexture (Entity entity)
     {
          return this.getFallingSandTextures((EntityMovingBlock) entity);
     }
     
     
     
     
     public void doRender (Entity entity, double x, double y, double z, float par8, float partialTicks)
     {
          this.doRenderMovingBlock((EntityMovingBlock) entity, x, y, z, par8, partialTicks);
     }
}
