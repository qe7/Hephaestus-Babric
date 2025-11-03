package io.github.qe7.mixins;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.PacketEvent;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.DataInputStream;
import java.util.List;

import static net.minecraft.src.NetworkManager.field_28145_d;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Shadow
    private List<Packet> readPackets;

    @Shadow
    private DataInputStream socketInputStream;

    @Shadow
    private NetHandler netHandler;

    @Shadow
    private boolean isTerminating;

    @Shadow
    public abstract void networkShutdown(String string, Object... objects);

    @Shadow
    protected abstract void onNetworkError(Exception exception);

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    public void addToSendQueue(Packet packet, CallbackInfo ci) {
        final PacketEvent packetEvent = new PacketEvent(PacketEvent.Type.OUTGOING, packet);
        ManagerFactory.get(EventManager.class).publishEvent(packetEvent);

        if (packetEvent.isCancelled()) ci.cancel();
    }

    /**
     * @author qe7
     * @reason Hooking incoming packet event
     */
    @Overwrite
    private boolean readPacket() {
        boolean var1 = false;

        try {
            Packet readPacket = Packet.readPacket(this.socketInputStream, this.netHandler.isServerHandler());
            if (readPacket != null) {
                final PacketEvent packetEvent = new PacketEvent(PacketEvent.Type.INCOMING, readPacket);
                ManagerFactory.get(EventManager.class).publishEvent(packetEvent);

                if (packetEvent.isCancelled()) return false;

                int[] var10000 = field_28145_d;
                int var10001 = readPacket.getPacketId();
                var10000[var10001] += readPacket.getPacketSize() + 1;
                this.readPackets.add(readPacket);
                var1 = true;
            } else {
                this.networkShutdown("disconnect.endOfStream");
            }

            return var1;
        } catch (Exception var3) {
            if (!this.isTerminating) {
                this.onNetworkError(var3);
            }

            return false;
        }
    }
}
