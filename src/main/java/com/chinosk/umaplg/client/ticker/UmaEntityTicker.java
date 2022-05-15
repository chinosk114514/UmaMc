package com.chinosk.umaplg.client.ticker;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimationTickable;


public class UmaEntityTicker extends PathAwareEntity implements IAnimationTickable {
    private double mlastX = 0;
    private double mlastY = 0;
    private boolean mIsMoving = false;
    private int lastChangeMovingStateTime = 0;

    protected UmaEntityTicker(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


    public boolean getIsMove() {
        int time_now = tickTimer();
        if (time_now != lastChangeMovingStateTime) {
            lastChangeMovingStateTime = time_now;
            double nX = this.getX();
            double nY = this.getY();
            mIsMoving = nX != mlastX || nY != mlastY;
            mlastX = nX;
            mlastY = nY;
        }
        // System.out.println("马刻: " + time_now);
        return mIsMoving;
    }  // 以tick为单位获取移动状态


    @Override
    public int tickTimer() {
        return age;
    }

}
