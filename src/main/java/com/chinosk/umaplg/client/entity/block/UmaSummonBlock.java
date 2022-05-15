package com.chinosk.umaplg.client.entity.block;

import com.chinosk.umaplg.client.ModEntities;
import com.chinosk.umaplg.client.entity.bio.UmaEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.chinosk.umaplg.Umaplg.ITEM_GREEN_JUICE;


public class UmaSummonBlock extends Block implements BlockEntityProvider {
    public List<Field> allUmamusumeEntities = getAllUmamusumeEntities();

    public UmaSummonBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (!world.isClient) {
            player.sendMessage(new LiteralText("Hello, world!"), false);
        }

        ItemStack itemStack = player.getStackInHand(hand);
        Item itemInHand = itemStack.getItem();
        if (itemInHand == ITEM_GREEN_JUICE) {
            itemStack.decrement(1);
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);

            Vec3d lightingLocate = new Vec3d(pos.getX(), pos.getY() - 8, pos.getZ());
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(lightingLocate);
            lightningEntity.setOnFireFor(0);
            world.spawnEntity(lightningEntity);

            world.removeBlock(pos, true);

            UmaEntity uma = getRandomUmamusumeEntity().create(world);
            if (uma != null) {
                uma.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
                world.spawnEntity(uma);
            } else {
                System.out.println("召唤失败");
            }

        }
        return ActionResult.SUCCESS;
    }

    public EntityType<UmaEntity> getRandomUmamusumeEntity() {
        int lenth = allUmamusumeEntities.toArray().length;
        if (lenth == 0) {
            return null;
        } else {
            int rand = new Random().nextInt(lenth);
            Field getF = allUmamusumeEntities.get(rand);
            try {
                return (EntityType<UmaEntity>) getF.get(ModEntities.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static List<Field> getAllUmamusumeEntities() {
        Field[] umaFds = ModEntities.class.getDeclaredFields();
        List<Field> returnumaFds = new ArrayList<>();

        for (Field umaFd: umaFds) {
            if (umaFd.getGenericType().toString().equals("net.minecraft.entity.EntityType<com.chinosk.umaplg.client.entity.bio.UmaEntity>") &&
                    umaFd.getName().startsWith("UMAMUSU")) {
                returnumaFds.add(umaFd);
            }
        }
        return returnumaFds;
    }



    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new UmaSummonBlockEntity();
    }

}
