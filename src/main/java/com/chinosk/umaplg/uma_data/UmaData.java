package com.chinosk.umaplg.uma_data;

import com.chinosk.umaplg.uma_data.data_models.UmaBaseData;
import com.chinosk.umaplg.uma_data.data_models.UmaSkillEffect;
import com.chinosk.umaplg.uma_data.data_models.UmaSkills;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class UmaData {
    // public String file_path;
    // public String file_name;

    public String umaUuid;
    public String umaDisplayName;
    public String masterName = "";
    public String masterUUID = "";
    public UmaBaseData umaBaseData;

    /*
    public UmaData(String umaUuid) {
        this.umaUuid = umaUuid;

        file_path = "./umamusume_data";
        file_name = this.umaUuid + ".json";
        if (!readData()) {
            System.out.println("数据初始化失败");
        }
    }
    */

    public UmaData(String json_str, String uuid, String displayName) {
        this.umaBaseData = new Gson().fromJson(json_str, UmaBaseData.class);
        this.umaUuid = uuid;
        this.umaDisplayName = displayName;
    }

    public UmaData(String uuid, String displayName) {
        this.umaBaseData = getInitializationData();
        this.umaUuid = uuid;
        this.umaDisplayName = displayName;
    }

    public String getDataStr() {
        return new Gson().toJson(this.umaBaseData);
    }

    public void setMasterInfo(String master_name, String master_uuid) {
        masterName = master_name;
        masterUUID = master_uuid;
        umaBaseData.setMastername(master_name);
        umaBaseData.setMasteruuid(master_uuid);
    }

    /*
    public boolean readData() {
        try {
            File mPath = new File(file_path);
            if (!mPath.exists()) {
                if (!mPath.mkdirs()) {
                    return false;
                }
            }

            File mFile = new File(file_path, file_name);
            if (!mFile.exists()) {
                if (!mFile.createNewFile()) {
                    return false;
                }
            }


            String content;
            StringBuilder builder = new StringBuilder();
            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(mFile), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((content = bufferedReader.readLine()) != null) {
                builder.append(content);
            }

            String json_str = builder.toString();
            System.out.println("jsonData: " + json_str);
            if (!json_str.equals("")) {
                this.umaBaseData = new Gson().fromJson(json_str, UmaBaseData.class);
            } else {
                this.umaBaseData = getInitializationData();
                if (!writeData()) {
                    System.out.println("初始化数据写入失败");
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取失败");
            return false;
        }

    }


    public boolean writeData() {
        try {
            if (this.umaBaseData == null) {
                return false;
            }
            String wData = new Gson().toJson(this.umaBaseData);
            BufferedWriter f = new BufferedWriter(new FileWriter(file_path + "/" + file_name));
            f.write(wData);
            f.close();
            return true;
        } catch (IOException ie) {
            ie.printStackTrace();
            System.out.println("文件写入失败");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

     */

    public UmaBaseData getInitializationData() {
        UmaBaseData umaInitData = new UmaBaseData();
        umaInitData.setMasteruuid("");  // 主人uuid
        umaInitData.setMastername("");  // 主人名
        umaInitData.setUuid(umaUuid);  // 自身uuid
        umaInitData.setSpeed(120);  // 速
        umaInitData.setMax_speed(1200);
        umaInitData.setEndurance(100);  // 耐
        umaInitData.setMax_endurance(1200);
        umaInitData.setPower(150);  // 力
        umaInitData.setMax_power(1200);
        umaInitData.setPerse(80);  // 根
        umaInitData.setMax_perse(1200);
        umaInitData.setIntelli(100);  // 智
        umaInitData.setMax_intelli(1200);
        umaInitData.setMood(50);  // 心情
        umaInitData.setLove(50);  // 好感
        umaInitData.setFeed(80);  // 饱食度

        umaInitData.setSkills(geneTestSkills());

        return umaInitData;
    }

    private List<UmaSkills> geneTestSkills() {
        String skillstr = "{\"skills\":[{\"skillIcon\":\"20043.png\",\"rare\":1,\"score\":135,\"id\":\"testskill1\",\"description\":\"desc1\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill2\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill3\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill4\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill5\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill6\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill7\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill8\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill9\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]},{\"skillIcon\":\"30012.png\",\"rare\":1,\"score\":135,\"id\":\"testskill10\",\"description\":\"desc2\",\"effects\":[{\"condition\":\"rain\",\"effect_type\":\"speed\",\"effect_value\":200},{\"condition\":\"summer\",\"effect_type\":\"power\",\"effect_value\":150}]}]}";

        return new Gson().fromJson(skillstr, UmaBaseData.class).getSkills();
    }

}
