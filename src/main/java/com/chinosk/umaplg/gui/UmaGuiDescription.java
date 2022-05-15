package com.chinosk.umaplg.gui;

import com.chinosk.umaplg.Umaplg;
import com.chinosk.umaplg.uma_data.UmaData;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.particle.SuspendParticle;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.LiteralText;

import javax.annotation.Nullable;

public class UmaGuiDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 1;
    public UmaData umaData;
    public String UMA_UUID;
    private static final int PROPERTY_COUNT = 3; // This should match PropertyDelegate.size().


    public static PropertyDelegate getBlockPropertyDelegate(ScreenHandlerContext ctx, int size, int entityid) {
        return ctx.get((world, pos) -> {
            // BlockEntity be = world.getBlockEntity(pos);
            Entity et = world.getEntityById(entityid);
            if (et!=null && et instanceof PropertyDelegateHolder) {
                return ((PropertyDelegateHolder)et).getPropertyDelegate();
            }

            return new ArrayPropertyDelegate(size);
        }).orElse(new ArrayPropertyDelegate(size));
    }

    public UmaGuiDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context,
                             @Nullable UmaData umaData, @Nullable String UMA_UUID) {
        // super(Umaplg.SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE),
        //        getBlockPropertyDelegate(context));
        super(Umaplg.SCREEN_HANDLER_TYPE, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE),
                getBlockPropertyDelegate(context, PROPERTY_COUNT));


        this.umaData = umaData;
        this.UMA_UUID = UMA_UUID;
        System.out.println("UmaGuiDescription被初始化, UUID: " + UMA_UUID);

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        WLabel label = new WLabel("wocaoniubia");
        root.add(label, 1, 1);

        WButton button = new WButton(new LiteralText("Hello, world!"));
        root.add(button, 10, 10, 20, 20);

        /*
        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        root.add(itemSlot, 4, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);
         */

        if (umaData != null) {
            WLabel label2 = new WLabel("马娘uuid: " + umaData.umaBaseData.getUuid());
            root.add(label2, 5, 1);
        } else {
            WLabel label2 = new WLabel("马娘uuid: null");
            root.add(label2, 5, 1);
        }

        PropertyDelegate pde = this.propertyDelegate;
        System.out.println("hahahaha: " + pde.get(2));



        root.validate(this);
    }

}