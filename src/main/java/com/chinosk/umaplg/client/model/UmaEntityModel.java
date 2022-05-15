package com.chinosk.umaplg.client.model;

import com.chinosk.umaplg.client.entity.bio.UmaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class UmaEntityModel extends AnimatedGeoModel<UmaEntity> {

    private String ModelLocation = "geo/geo_umamusume.json";
    private String TextureLocation = "textures/entity/uma/nicenature.png";


    public UmaEntityModel setModelLocation (String model_location) {
        this.ModelLocation = model_location;
        return this;
    }

    public UmaEntityModel setTextureLocation (String t_location) {
        this.TextureLocation = t_location;
        return this;
    }

    @Override
    public Identifier getModelLocation(UmaEntity object) {
        return new Identifier("umares", ModelLocation);
    }

    @Override
    public Identifier getTextureLocation(UmaEntity object) {
        return new Identifier("umares", TextureLocation);
    }

    @Override
    public Identifier getAnimationFileLocation(UmaEntity animatable) {
        return new Identifier("umares", "animations/umaanimation.json");
    }


}
