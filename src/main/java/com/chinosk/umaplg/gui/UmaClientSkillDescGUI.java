package com.chinosk.umaplg.gui;

import com.chinosk.umaplg.uma_data.data_models.UmaSkills;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public class UmaClientSkillDescGUI extends LightweightGuiDescription {
    UmaClientGUI backGUI;
    UmaSkills umaSkills;

    public UmaClientSkillDescGUI(UmaClientGUI backGUI, UmaSkills usk) {
        this.backGUI = backGUI;
        this.umaSkills = usk;

        initGUI();
    }

    public void initGUI() {
        WGridPanel root = new WGridPanel(1);
        setRootPanel(root);
        root.setSize(300, 200);



        WButton backbtn = new WButton(new LiteralText("back"));  // 返回主页
        backbtn.setSize(50, 50);
        backbtn.setOnClick(() -> {
            MinecraftClient.getInstance().openScreen(new UmaClientScreen(backGUI));
        });
        root.add(backbtn, 230, 140, 50, 50);
    }

    public void opbook() {
        // NbtCompound bookNBT = new NbtCompound();
        /*
        bookNBT.putString("title", "这是标题");
        bookNBT.putString("author", "这是作者");
        bookNBT.putInt("generation", 114514);
        bookNBT.putBoolean("resolved", true);


        NbtCompound pageNBT = new NbtCompound();
        pageNBT.putString("text", "这是内容");
        pageNBT.putString("color", "black");
        pageNBT.putBoolean("underlined", false);
        */


        NbtList nt = new NbtList();
        // nt.add(NbtString.of("{\"text\":\"这是文本\",\"color\":\"black\",\"underlined\":false}"));
        for (int i=0; i < 25555; i++) {
            nt.add(NbtString.of("哈哈哈" + i));
        }
        // bookNBT.put("pages", nt);

        Item.Settings bookSet = new Item.Settings();
        Item bk = new WrittenBookItem(bookSet);

        ItemStack bkstack = new ItemStack(bk);
        // bkstack.setTag(bookNBT);
        bkstack.putSubTag("title", NbtString.of("这是标题"));
        bkstack.putSubTag("author", NbtString.of("这是作者"));
        bkstack.putSubTag("pages", nt);
        bkstack.putSubTag("resolved", NbtByte.of(true));

        MinecraftClient.getInstance().openScreen(new BookEditScreen(MinecraftClient.getInstance().player, bkstack, Hand.MAIN_HAND));
    }
}
