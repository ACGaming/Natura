package com.progwml6.natura.entities.entity.monster;

import javax.annotation.Nullable;

import com.progwml6.natura.entities.NaturaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBabyHeatscarSpider extends EntitySpider {
    public EntityBabyHeatscarSpider(World worldIn) {
        super(worldIn);
        this.setSize(1.2F, 0.8F);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return NaturaEntities.BABY_HEATSCAR_SPIDER;
    }

    @Override
    protected float getJumpUpwardsMotion() {
        return 0.62F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                byte time = 0;
                
                if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                        time = 5;
                    } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                        time = 10;
                    }
                }

                if (time > 0) {
                    entity.setFire(time);
                }
            }

            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 0.55F;
    }
}
