package com.chinosk.umaplg.client;



import com.chinosk.umaplg.Umaplg;
import com.chinosk.umaplg.client.renderer.UmaRenderer;
import com.chinosk.umaplg.client.renderer.UmaRendererMq;
import com.chinosk.umaplg.gui.UmaClientGUI;
import com.chinosk.umaplg.gui.UmaClientScreen;
import com.chinosk.umaplg.gui.UmaBlockScreen;
import com.chinosk.umaplg.gui.UmaGuiDescription;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.UUID;


@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class UmaplgClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        System.out.println("这是umaplg client");
        EntityRendererRegistry.INSTANCE.register(ModEntities.UMAMUSU, UmaRenderer::new);
        EntityRendererRegistry.INSTANCE.register(ModEntities.UMAMUSU_MQ, UmaRendererMq::new);

        ScreenRegistry.<UmaGuiDescription, UmaBlockScreen>register(Umaplg.SCREEN_HANDLER_TYPE,
                (gui, inventory, title) -> new UmaBlockScreen(gui, inventory.player, title));

        ClientPlayNetworking.registerGlobalReceiver(new Identifier("umaplg", "opengui"), (client, handler, buf, responseSender) -> {
            String getdata = buf.readString();  // 马娘数据
            UUID playerUUID = buf.readUuid();  // 玩家UUID
            String playerName = buf.readString();  // 玩家名

            // System.out.println("收到来自服务器打开GUI的数据: " + getdata);
            client.execute(() -> {
                MinecraftClient.getInstance().openScreen(new UmaClientScreen(
                        new UmaClientGUI(getdata, playerName, playerUUID.toString())
                ));
            });
        });

    }
}
