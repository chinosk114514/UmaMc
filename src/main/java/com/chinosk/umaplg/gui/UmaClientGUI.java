package com.chinosk.umaplg.gui;

import com.chinosk.umaplg.uma_data.UmaData;
import com.chinosk.umaplg.uma_data.data_models.UmaSkills;
import com.google.gson.Gson;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class UmaClientGUI extends LightweightGuiDescription {
    public String umaUUIDstr;
    public UmaData umaData;
    public String playerName;  // 交互者的UUID
    public String playerUUID;  // 交互者的用户名


    public UmaClientGUI(String uma_data, String player_name, String player_uuid) {
        umaData = new Gson().fromJson(uma_data, UmaData.class);
        umaUUIDstr = umaData.umaUuid;
        playerName = player_name;
        playerUUID = player_uuid;
        initGUI();
    }

    public void initGUI() {
        WGridPanel root = new WGridPanel(1);
        setRootPanel(root);
        root.setSize(300, 200);


        WLabel label = new WLabel(umaData.umaDisplayName);  // 马名
        root.add(label, 0, 0);
        WLabel lMasterName = new WLabel(new TranslatableText("umaplg.umagui.t").getString() + ": " + (umaData.masterUUID.equals("") ? "暂无": umaData.masterName));
        root.add(lMasterName, 0, 15);

        WGridPanel statusPanel = getUmaDataPanel();
        root.add(statusPanel, 0, 30);  // 速耐力根智好感

        root.add(new WLabel(new TranslatableText("umaplg.umagui.mood_title").getString() + ": " +
                umaData.umaBaseData.getMood() + "/100 (" +
                        new TranslatableText("umaplg.umagui.moodstyle." + getMoodIndex(umaData.umaBaseData.getMood())).getString()
                + ")"), 5, 89);  // 心情
        root.add(new WLabel(new TranslatableText("umaplg.umagui.feed_title").getString() + ": " + umaData.umaBaseData.getFeed() + "/100"),
                5, 106);  // 饱食度
        root.add(new WLabel(new TranslatableText("umaplg.umagui.skill_title").getString()), 125, 84);  // 技能


        if (umaData.umaBaseData.getSkills() != null) {
            BiConsumer<UmaSkills, UmaSkillGUI> configurator = (UmaSkills s, UmaSkillGUI destination) -> {
                // destination.skill_name.setText(new TranslatableText("umaplg.skill." + s.getId() + ".name"));
                // destination.skill_icon.setImage(new Identifier("umares", "textures/icon/skill/" + s.getSkillIcon()));
                destination.skill_icon = new TextureIcon(new Identifier("umares", "textures/icon/skill/" + s.getSkillIcon()));
                destination.btn = new WButton(new TranslatableText("umaplg.skill." + s.getId() + ".name"));

                destination.add(destination.btn, 0, 0, 180, 18);
                destination.btn.setIcon(destination.skill_icon);
                destination.btn.setSize(150, 16);

                destination.btn.setOnClick(() -> {
                    MinecraftClient.getInstance().openScreen(new UmaClientScreen(
                            new UmaClientSkillDescGUI(this, s)
                    ));
                });
            };

            WListPanel<UmaSkills, UmaSkillGUI> list = new WListPanel<>(umaData.umaBaseData.getSkills(), UmaSkillGUI::new, configurator);
            list.setListItemHeight(18);
            // list.setBackgroundPainter(BackgroundPainter.createColorful(12566463));
            root.add(list, 116, 95, 180, 90);
        }

        /*
        WButton button = new WButton(new LiteralText("reply test"));
        button.setOnClick(() -> {
            System.out.println("尝试向服务器发包: " + umaUUIDstr.replace("-", ""));
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString("reply message from " + umaUUIDstr);
            ClientPlayNetworking.send(new Identifier("umaplg", umaUUIDstr.replace("-", "")), buf);
        });
        root.add(button, 1, 10, 20, 20);
        */
    }

    public static class UmaSkillGUI extends WPlainPanel {
        // WSprite skill_icon;
        // WLabel skill_name;
        TextureIcon skill_icon;
        WButton btn;

        public UmaSkillGUI() {
            /*
             btn = new WButton(new LiteralText("skill name"));
             this.add(btn, 0, 0, 180, 18);

             skill_icon = new TextureIcon(new Identifier("umares", "textures/item/ingame/item_random.png"));
             btn.setIcon(skill_icon);

             skill_icon = new WSprite(new Identifier("umares", "textures/item/ingame/item_random.png"));
             this.add(skill_icon, 0, 2, 16, 16);

             skill_name = new WLabel("skill name");
             this.add(skill_name, 18, 5);

             this.setSize(180, 18);
             */

        }
    }

    public static int getMoodIndex(int mood) {
        if (mood < 0) {
            return 0;  // ????????????????????????????????????
        } else if (mood <= 20) {
            return 1;  // 绝不调 0-20
        } else if (mood <= 40) {
            return 2;  // 不调 21-40
        } else if (mood < 70) {
            return 3;  // 普通 41-69
        } else if (mood < 90) {
            return 4;  // 好调 70-89
        } else if (mood <= 100) {
            return 5;  // 绝好调 90-100
        } else {
            return 6;  // hahahahahahahahahahahahahahahahahahaha
        }
    }

    private WGridPanel getUmaDataPanel() {
        WGridPanel statusPanel = new WGridPanel(1);
        // statusPanel.setSize();

        // 速
        WSprite iconSpeed = new WSprite(new Identifier("umares", "textures/icon/utx_ico_obtain_00.png"));
        statusPanel.add(iconSpeed, 0, 0, 20, 20);
        statusPanel.add(new WLabel(new TranslatableText("umaplg.umagui.speed_title").getString()), 23, 0);
        statusPanel.add(new WLabel(umaData.umaBaseData.getSpeed() + "/" + umaData.umaBaseData.getMax_speed()),
                23, 11);

        // 耐
        statusPanel.add(new WSprite(new Identifier("umares", "textures/icon/utx_ico_obtain_01.png")),
                106, 0, 20, 20);
        statusPanel.add(new WLabel(new TranslatableText("umaplg.umagui.endurance_title").getString()), 129, 0);
        statusPanel.add(new WLabel(umaData.umaBaseData.getEndurance() + "/" + umaData.umaBaseData.getMax_endurance()),
                129, 11);

        // 力
        statusPanel.add(new WSprite(new Identifier("umares", "textures/icon/utx_ico_obtain_02.png")),
                212, 0, 20, 20);
        statusPanel.add(new WLabel(new TranslatableText("umaplg.umagui.power_title").getString()), 235, 0);
        statusPanel.add(new WLabel(umaData.umaBaseData.getPower() + "/" + umaData.umaBaseData.getMax_power()),
                235, 11);

        // 根
        statusPanel.add(new WSprite(new Identifier("umares", "textures/icon/utx_ico_obtain_03.png")),
                0, 26, 20, 20);
        statusPanel.add(new WLabel(new TranslatableText("umaplg.umagui.perse_title").getString()), 23, 26);
        statusPanel.add(new WLabel(umaData.umaBaseData.getPerse() + "/" + umaData.umaBaseData.getMax_perse()),
                23, 11 + 26);

        // 智
        statusPanel.add(new WSprite(new Identifier("umares", "textures/icon/utx_ico_obtain_04.png")),
                106, 26, 20, 20);
        statusPanel.add(new WLabel(new TranslatableText("umaplg.umagui.intelli_title").getString()), 129, 26);
        statusPanel.add(new WLabel(umaData.umaBaseData.getIntelli() + "/" + umaData.umaBaseData.getMax_intelli()),
                129, 11 + 26);

        // 好感
        statusPanel.add(new WSprite(new Identifier("umares", "textures/item/ingame/item_icon_00057.png")),
                212, 26, 20, 20);
        statusPanel.add(new WLabel(new TranslatableText("umaplg.umagui.love_title").getString()), 235, 26);
        statusPanel.add(new WLabel((umaData.masterUUID.equals("") ? "-": umaData.umaBaseData.getLove()) + "/100"),
                235, 11 + 26);

        return statusPanel;
    }

}
