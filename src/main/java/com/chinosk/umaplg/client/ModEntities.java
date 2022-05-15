package com.chinosk.umaplg.client;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import com.chinosk.umaplg.Umaplg;
import com.chinosk.umaplg.client.entity.bio.UmaEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    // 注册实体
    public static final EntityType<UmaEntity> UMAMUSU = GenerateEntity("uma_nicenature");
    public static final EntityType<UmaEntity> UMAMUSU_MQ = GenerateEntity("uma_mequeen");

    public static void registerEntities() {
        // 注册实体属性
        // EntityRendererRegistry.INSTANCE.register(ModEntities.UMAMUSU, UmaRenderer::new);
        FabricDefaultAttributeRegistry.register(UMAMUSU, UmaEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(UMAMUSU_MQ, UmaEntity.createMobAttributes());

        System.out.println("Registering mod mobs for " + Umaplg.MOD_ID);
    }

    private static EntityType<UmaEntity> GenerateEntity(String path){
        return Registry.register(Registry.ENTITY_TYPE,
                new Identifier(Umaplg.MOD_ID, path), FabricEntityTypeBuilder
                        .create(SpawnGroup.CREATURE, UmaEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 2f))
                        .build());
    }

}
