package com.chinosk.umaplg.client.entity.bio;

import com.chinosk.umaplg.client.ticker.UmaEntityTicker;
import com.chinosk.umaplg.gui.UmaGuiDescription;
import com.chinosk.umaplg.uma_data.UmaData;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


import static com.chinosk.umaplg.Umaplg.ITEM_GREEN_JUICE;


public class UmaEntity extends UmaEntityTicker implements IAnimatable, NamedScreenHandlerFactory, PropertyDelegateHolder {  // BlockEntityClientSerializable
    private AnimationFactory factory = new AnimationFactory(this);
    public UmaData umaData = null;
    public String UMA_UUID;
    private boolean is_reg_net = false;

    private static final int MAX_PROGRESS = 500;
    private int progress = 0;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int size() {
            // This is how many properties you have. We have two of them, so we'll return 2.
            return 3;
        }

        @Override
        public int get(int index) {
            // Each property has a unique index that you can choose.
            // Our properties will be 0 for the progress and 1 for the maximum.

            if (index == 0) {
                return progress;
            } else if (index == 1) {
                return MAX_PROGRESS;
            } else if (index == 2) {
                return 1919810;
            }

            // Unknown property IDs will fall back to -1
            return -1;
        }

        @Override
        public void set(int index, int value) {
            // This is used on the other side of the sync if you're using extended screen handlers.
            // Generally you'll want to have a working implementation for mutable properties, such as our progress.

            if (index == 0) {
                progress = value;
            }
        }
    };

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    public UmaEntity(EntityType<? extends PathAwareEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreCameraFrustum = true;
        // this.umaData = new UmaData(this.getUuid().toString());

        if (umaData == null) {
            UMA_UUID = this.getUuid().toString();
            umaData = new UmaData(UMA_UUID, this.getDisplayName().getString());
        }
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        // System.out.println("写入nbt");
        super.writeCustomDataToNbt(nbt);
        if (umaData != null) {
            nbt.putString("customjsondata", umaData.getDataStr());
        }
        // sync();
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        // System.out.println("读取nbt");
        super.readCustomDataFromNbt(nbt);

        if (!nbt.containsUuid("uma_uuid")) {
            nbt.putUuid("uma_uuid", this.getUuid());
        }
        UMA_UUID = nbt.getUuid("uma_uuid").toString();

        if (nbt.getKeys().contains("customjsondata")) {
            umaData = new UmaData(nbt.getString("customjsondata"), UMA_UUID, this.getDisplayName().getString());
        } else {
            if (umaData == null) {
                umaData = new UmaData(UMA_UUID, this.getDisplayName().getString());
            }
        }
        // sync();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

    }

    private void serverRecv() {
        if (!this.world.isClient()) {
            ServerPlayNetworking.PlayChannelHandler channelHandler = new ServerPlayNetworking.PlayChannelHandler() {
                @Override
                public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
                    System.out.println(getShortUuidForRequest() + " 收到来自客户端的数据: " + buf.readString());
                }
            };
            System.out.println("server注册: " + UMA_UUID.replace("-", ""));
            ServerPlayNetworking.registerGlobalReceiver(new Identifier("umaplg", UMA_UUID.replace("-", "")), channelHandler);
            is_reg_net = true;
        }
    }



    public String getShortUuidForRequest() {
        return "bfdaeqq1";
        // return this.getUuidAsString().split("-")[0];
    }


    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if (age > 150) {
            super.applyDamage(source, amount);
        } else {
            System.out.println("保护");
        }
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item itemInHand = itemStack.getItem();
        ActionResult ret = ActionResult.PASS;

        // player.openHandledScreen(this.getBlockState().createScreenHandlerFactory(world, player.getBlockPos()));
        if (umaData == null)
            System.out.println(this.world.isClient() + "umadata为null");
        else
            System.out.println(this.world.isClient() + "速度114514: " + umaData.umaBaseData.getSpeed());

        if (!this.world.isClient()) {
            ret = interactMobServer(player, hand);
        } else {
            ret = interactMobClient(player, hand);
        }
        return ret;
    }

    public ActionResult interactMobClient(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    public ActionResult interactMobServer(PlayerEntity player, Hand hand) {
        // if (!is_reg_net) {
        //     serverRecv();
        // }

        openUmaGui(player);
        // player.openHandledScreen(this);

        ItemStack itemStack = player.getStackInHand(hand);
        Item itemInHand = itemStack.getItem();
        ActionResult ret = ActionResult.PASS;

        if (itemInHand == ITEM_GREEN_JUICE) {
            System.out.println("吃了青汁");
            umaData.umaBaseData.setSpeed(umaData.umaBaseData.getSpeed() + 10);
            // writeData();
            itemStack.decrement(1);
            ret = ActionResult.SUCCESS;
        } else if (itemInHand == Items.GRASS) {
            System.out.println("吃了草");
            itemStack.decrement(1);
            ret = ActionResult.SUCCESS;
        } else if (itemInHand == Items.AIR) {
            System.out.println("空手");
            System.out.println("马娘: " + UMA_UUID + "\n玩家: " + player.getUuid() + "\n速度: " + umaData.umaBaseData.getSpeed() +
                    "\n血量: " + this.getHealth());
            player.sendMessage(new LiteralText("血量: " + this.getHealth()), false);
        } else {
            System.out.println("不吃");
        }
        return ret;
    }

    private void openUmaGui(PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(new Gson().toJson(umaData));  // 马娘数据
        buf.writeUuid(player.getUuid());  // 玩家UUID
        buf.writeText(player.getName());  // 玩家名
        ServerPlayNetworking.send((ServerPlayerEntity) player, new Identifier("umaplg", "opengui"), buf);  // 向客户端发包
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        // System.out.println("AnimationEvent: " + event.getController().getName());
        AnimationBuilder abuilder = new AnimationBuilder();

        if (getIsMove()) {
            abuilder.addAnimation("animation.uma.walk", true);
        } else {
            abuilder.addAnimation("animation.uma.play", true);
        }

        event.getController().setAnimation(abuilder);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<UmaEntity> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);

    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }



    @Override
    public void tick() {

        PlayerEntity near_player =
                this.world.getClosestPlayer(this.getX(), this.getY(), this.getZ(), 15, false);
        if (near_player != null) {
            // this.lookControl.lookAt(near_player.getPos());
            // this.moveControl.moveTo(near_player.getX(), near_player.getY(), near_player.getZ(), 0.4);
            this.setPositionTarget(near_player.getBlockPos(), 15);
        }

        super.tick();
    }



    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 20.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        super.initGoals();
    }


    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new UmaGuiDescription(syncId, inventory, ScreenHandlerContext.create(world, this.getBlockPos()),
                umaData, UMA_UUID);
    }



    /*
    @Override
    public void fromClientTag(NbtCompound tag) {  // 客户端调用, 解码数据
        if (tag.containsUuid("uma_uuid")) {
            UMA_UUID = tag.getUuid("uma_uuid").toString();
            if (tag.getKeys().contains("customjsondata")) {
                umaData = new UmaData(tag.getString("customjsondata"), UMA_UUID);
            } else {
            if (umaData == null) {
                umaData = new UmaData(UMA_UUID);
            }
                System.out.println("customjsondata not found");
            }
        } else {
            System.out.println("uma_uuid not found");
        }

    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {  // 服务端调用, 生成数据
        if (UMA_UUID == null) {
            System.out.println("Warning: toClientTag - UMA_UUID is NULL");
        }
        return tag;
    }

    @Override
    public void sync() {
        // System.out.println("sync");
        World world = this.getEntityWorld();

        Preconditions.checkNotNull(world); //Maintain distinct failure case from below
        if (!(world instanceof ServerWorld)) throw new IllegalStateException("Cannot call sync() on the logical client! Did you check world.isClient first?");

        ((ServerWorld) world).getChunkManager().markForUpdate(this.getBlockPos());
    }
    */


}

