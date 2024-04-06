package com.progwml6.natura.entities.entity.passive;

import static com.progwml6.natura.shared.NaturaCommons.soups;

import javax.annotation.Nullable;

import com.progwml6.natura.entities.NaturaEntities;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityImp extends EntityAnimal
{
    public EntityImp(World par1World)
    {
        super(par1World);
        this.setSize(0.75F, 1.45F);
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.75D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2D, soups, false));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return NaturaEntities.IMP;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return !par1ItemStack.isEmpty() && par1ItemStack.getItem() == soups && par1ItemStack.getItemDamage() >= 4;
    }

    @Override
    protected void consumeItemFromStack(EntityPlayer player, ItemStack stack)
    {
        if (stack.getItem() == soups)
        {
            ItemStack bowl = soups.getBowlType(stack.getItemDamage());

            if (!player.inventory.addItemStackToInventory(bowl))
            {
                player.dropItem(bowl, false, false);
            }
        }

        if (!player.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return new EntityImp(this.world);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (this.world.provider.doesWaterVaporize() || this.world.provider.isNether()) && this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
    }
    
    @Override
    public float getEyeHeight()
    {
        return this.height * 0.85F;
    }
}
