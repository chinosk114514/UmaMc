package com.chinosk.umaplg.client.renderer;

import com.chinosk.umaplg.client.entity.bio.UmaEntity;
import com.chinosk.umaplg.client.model.UmaEntityModel;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;


public class UmaRendererMq extends GeoEntityRenderer<UmaEntity> {

    public UmaRendererMq(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
        super(entityRenderDispatcher, new UmaEntityModel().setTextureLocation("textures/entity/uma/mejiromequeen.png"));
    }

}
