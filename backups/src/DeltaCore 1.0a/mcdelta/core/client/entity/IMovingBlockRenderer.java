package mcdelta.core.client.entity;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.entity.EntityMovingBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.World;

public interface IMovingBlockRenderer
{
     public static Map<Integer, IMovingBlockRenderer> renderers    = new HashMap();
     final RenderBlocks                               renderBlocks = new RenderBlocks();
     
     
     
     
     void renderMovingBlock (World world, Block block, Tessellator tessellator, EntityMovingBlock movingBlock, double x, double y, double z, float par8, float partialTicks, RenderManager manager);
}
