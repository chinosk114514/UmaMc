package com.chinosk.umaplg;


import com.chinosk.umaplg.client.ModEntities;
import com.chinosk.umaplg.client.entity.block.UmaSummonBlock;
import com.chinosk.umaplg.client.entity.block.UmaSummonBlockEntity;
import com.chinosk.umaplg.gui.UmaGuiDescription;
import com.chinosk.umaplg.items.ItemGreenJuice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;



public class Umaplg implements ModInitializer {
    public static final String MOD_ID = "umaplg";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Item UMA_ICON = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item UMAMUSUME_SPAWN_EGG = new SpawnEggItem(ModEntities.UMAMUSU, 12895428, 11382189, new Item.Settings().group(ItemGroup.MISC));
    public static final Item UMAMUSUME_SPAWN_EGG_MQ = new SpawnEggItem(ModEntities.UMAMUSU_MQ, 12895428, 11382189, new Item.Settings().group(ItemGroup.MISC));

    public static final UmaSummonBlock UMA_BLOCK = new UmaSummonBlock(Block.Settings.of(Material.STONE));
    public static BlockEntityType<UmaSummonBlockEntity> UMA_BLOCK_ENTITY;
    public static ScreenHandlerType SCREEN_HANDLER_TYPE;

    public static final ItemGroup UMAMUSUME_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("umares", "group1"))
            .icon(() -> new ItemStack(UMA_ICON))
            .build();

    public static final ItemGreenJuice ITEM_GREEN_JUICE = new ItemGreenJuice(new FabricItemSettings()
            .group(UMAMUSUME_GROUP)
            .maxCount(64));


    private void registItems(){  // 注册item
        Registry.register(Registry.ITEM, new Identifier("umares", "uma_icon"), UMA_ICON);
        Registry.register(Registry.ITEM, new Identifier("umares", "green_juice"), ITEM_GREEN_JUICE);
        Registry.register(Registry.ITEM, new Identifier("umares", "uma_egg"), UMAMUSUME_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier("umares", "uma_egg_mq"), UMAMUSUME_SPAWN_EGG_MQ);
    }

    @Override
    public void onInitialize() {
        System.out.println("这是umaplg");
        registItems();

        ModEntities.registerEntities();  // 物品注册
        Registry.register(Registry.BLOCK, new Identifier("umares", "uma_summon"), UMA_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("umares", "uma_summon"),
                new BlockItem(UMA_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
        // TODO: 外观

        UMA_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "umaplg:uma_summon_block",
                BlockEntityType.Builder.create(UmaSummonBlockEntity::new, UMA_BLOCK).build(null));

        // SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(ModEntities.UMAMUSU.getLootTableId(), (syncId, inventory)
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(new Identifier("umagui"), (syncId, inventory)
                -> new UmaGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY, null, null));


        ServerPlayNetworking.PlayChannelHandler channelHandler = new ServerPlayNetworking.PlayChannelHandler() {
            @Override
            public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
                System.out.println("收到来自客户端的数据: " + buf.readString());
            }
        };
        ServerPlayNetworking.registerGlobalReceiver(new Identifier("umaplg", "testnet"), channelHandler);

        /*
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            Iterable<ItemStack> hItem = player.getItemsHand();

            return ActionResult.PASS;
        });

         */

        // FabricDefaultAttributeRegistry.register(ENTITY_UMA, EntityUma.createMobAttributes());

    }


}
