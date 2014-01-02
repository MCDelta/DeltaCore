package mcdelta.core.entity;

import java.util.Iterator;
import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.BlockData;
import mcdelta.core.assets.Position;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityMovingBlock extends Entity implements EntityDelta
{
     public int            blockID;
     public int            metadata;
     public boolean        shouldDropItem;
     public NBTTagCompound tileData;
     public double         targetX;
     public double         targetY;
     public double         targetZ;
     private double        time;
     public double         ticksExisted;
     private boolean       setBlock;
     
     
     
     
     public EntityMovingBlock (World world)
     {
          super(world);
          this.shouldDropItem = true;
          this.renderDistanceWeight = 0.0D;
          this.noClip = true;
     }
     
     
     
     
     public EntityMovingBlock (World world, Position spawn, Position target, BlockData block, double speed, boolean setBlock)
     {
          this(world, spawn.x, spawn.y, spawn.z, block.block.blockID, block.meta, block.tile, target.x, target.y, target.z, speed, setBlock);
     }
     
     
     
     
     public EntityMovingBlock (World world, double x, double y, double z, int id, int meta, TileEntity tile, double tx, double ty, double tz, double d, boolean set)
     {
          super(world);
          
          this.setBlock = set;
          this.shouldDropItem = true;
          this.blockID = id;
          this.metadata = meta;
          
          this.preventEntitySpawning = true;
          
          this.setSize(0.9F, 0.9F);
          this.yOffset = 0.1F;
          
          this.setPosition(x + 0.5, y + 0.5, z + 0.5);
          
          this.motionX = 0.0D;
          this.motionY = 0.0D;
          this.motionZ = 0.0D;
          
          this.prevPosX = x + 0.5;
          this.prevPosY = y + 0.5;
          this.prevPosZ = z + 0.5;
          
          if (tile != null)
          {
               this.tileData = new NBTTagCompound();
               tile.writeToNBT(this.tileData);
          }
          
          this.targetX = tx + 0.5;
          this.targetY = ty + 0.5;
          this.targetZ = tz + 0.5;
          
          this.time = d;
          this.ticksExisted = time;
          
          this.noClip = true;
     }
     
     
     
     
     protected boolean canTriggerWalking ()
     {
          return true;
     }
     
     
     
     
     protected void entityInit ()
     {
     }
     
     
     
     
     public boolean canBeCollidedWith ()
     {
          return true;
     }
     
     
     
     
     public void applyEntityCollision (Entity entity)
     {
          if (entity.riddenByEntity != this && entity.ridingEntity != this)
          {
               double d0 = entity.posX - this.posX;
               double d1 = entity.posZ - this.posZ;
               double d2 = MathHelper.abs_max(d0, d1);
               
               if (d2 >= 0.009999999776482582D)
               {
                    d2 = (double) MathHelper.sqrt_double(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;
                    
                    if (d3 > 1.0D)
                    {
                         d3 = 1.0D;
                    }
                    
                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.05000000074505806D;
                    d1 *= 0.05000000074505806D;
                    d0 *= (double) (1.0F - this.entityCollisionReduction);
                    d1 *= (double) (1.0F - this.entityCollisionReduction);
                    entity.addVelocity(d0, 0.0D, d1);
               }
          }
     }
     
     
     
     
     public void onUpdate ()
     {
          this.ticksExisted--;
          
          Assets.moveEntityTowards(this, targetX, targetY, targetZ, time);
          
          if (this.ticksExisted == 0)
          {
               if (this.setBlock)
               {
                    Position target = new Position(this.worldObj, (int) this.targetX, (int) this.targetY, (int) this.targetZ);
                    
                    Assets.placeBlock(target, new BlockData(Block.blocksList[this.blockID], this.metadata));
                    
                    if (this.tileData != null && Block.blocksList[this.blockID].hasTileEntity(this.metadata))
                    {
                         TileEntity tileentity = target.getTile();
                         
                         if (tileentity != null)
                         {
                              NBTTagCompound nbttagcompound = new NBTTagCompound();
                              tileentity.writeToNBT(nbttagcompound);
                              Iterator iterator = this.tileData.getTags().iterator();
                              
                              while (iterator.hasNext())
                              {
                                   NBTBase nbtbase = (NBTBase) iterator.next();
                                   
                                   if (!nbtbase.getName().equals("x") && !nbtbase.getName().equals("y") && !nbtbase.getName().equals("z"))
                                   {
                                        nbttagcompound.setTag(nbtbase.getName(), nbtbase.copy());
                                   }
                              }
                              
                              tileentity.readFromNBT(nbttagcompound);
                              tileentity.onInventoryChanged();
                         }
                    }
               }
               
               this.kill();
          }
          
          this.moveEntity(this.motionX, this.motionY, this.motionZ);
     }
     
     
     
     
     protected void fall (float par1)
     {
          
     }
     
     
     
     
     protected void writeEntityToNBT (NBTTagCompound nbtTag)
     {
          nbtTag.setByte("Tile", (byte) this.blockID);
          nbtTag.setInteger("TileID", this.blockID);
          nbtTag.setByte("Data", (byte) this.metadata);
          nbtTag.setBoolean("DropItem", this.shouldDropItem);
          
          nbtTag.setDouble("targetX", this.targetX);
          nbtTag.setDouble("targetY", this.targetY);
          nbtTag.setDouble("targetZ", this.targetZ);
          
          nbtTag.setDouble("time", this.time);
          nbtTag.setDouble("ticksExisted", this.ticksExisted);
          
          if (tileData != null)
          {
               nbtTag.setCompoundTag("TileEntityData", tileData);
          }
     }
     
     
     
     
     protected void readEntityFromNBT (NBTTagCompound nbtTag)
     {
          this.targetX = nbtTag.getDouble("targetX");
          this.targetY = nbtTag.getDouble("targetY");
          this.targetZ = nbtTag.getDouble("targetZ");
          
          this.time = nbtTag.getDouble("time");
          
          this.ticksExisted = nbtTag.getDouble("ticksExisted");
          
          if (nbtTag.hasKey("TileID"))
          {
               this.blockID = nbtTag.getInteger("TileID");
          }
          else
          {
               this.blockID = nbtTag.getByte("Tile") & 255;
          }
          
          this.metadata = nbtTag.getByte("Data") & 255;
          
          if (nbtTag.hasKey("DropItem"))
          {
               this.shouldDropItem = nbtTag.getBoolean("DropItem");
          }
          
          if (nbtTag.hasKey("TileEntityData"))
          {
               this.tileData = nbtTag.getCompoundTag("TileEntityData");
          }
          
          if (this.blockID == 0)
          {
               this.blockID = Block.sandStone.blockID;
          }
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public float getShadowSize ()
     {
          return 0.0F;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public World getWorld ()
     {
          return this.worldObj;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public boolean canRenderOnFire ()
     {
          return false;
     }
     
     
     
     
     public AxisAlignedBB getCollisionBox (Entity entity)
     {
          return entity.boundingBox;
     }
     
     
     
     
     @Override
     protected void doBlockCollisions ()
     {
     }
     
     
     
     
     public AxisAlignedBB getBoundingBox ()
     {
          return this.boundingBox;
     }
     
     
     
     
     public boolean canBePushed ()
     {
          return true;
     }
     
}