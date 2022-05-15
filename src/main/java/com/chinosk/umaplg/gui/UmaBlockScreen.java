package com.chinosk.umaplg.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class UmaBlockScreen extends CottonInventoryScreen<UmaGuiDescription> {
    public UmaBlockScreen(UmaGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
