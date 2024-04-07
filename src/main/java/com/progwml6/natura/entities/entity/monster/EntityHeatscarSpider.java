package com.progwml6.natura.entities.entity.monster;

import javax.annotation.Nullable;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.entities.NaturaEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityHeatscarSpider extends EntitySpider {
    public EntityHeatscarSpider(World worldIn) {
        super(worldIn);
        this.setSize(2.85F, 1.9F);
        this.isImmuneToFire = true;
        this.experienceValue = 25;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return NaturaEntities.HEATSCAR_SPIDER;
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
                        time = 7;
                    } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                        time = 15;
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
    public void setDead() {
        if (!this.world.isRemote) {
            int num = Config.babyHeatscarSpiderDeathSpawnMaximum - Config.babyHeatscarSpiderDeathSpawnMinimum + 1;
            int amount = this.rand.nextInt(num) + Config.babyHeatscarSpiderDeathSpawnMinimum;
            for (int i = 0; i < amount; i++) {
                double f = this.rand.nextDouble() * 2;
                double f1 = this.rand.nextDouble() * 2;
                EntityBabyHeatscarSpider babyspider = new EntityBabyHeatscarSpider(this.world);
                babyspider.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(babyspider);
            }
        }

        super.setDead();
    }

    @Override
    public float getEyeHeight() {
        return 1.3F;
    }
}
