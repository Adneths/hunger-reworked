package me.adneths.hunger_reworked.network;

import me.adneths.hunger_reworked.HungerReworked;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages
{

	private static SimpleChannel INSTANCE;

	private static int packetId = 0;

	private static int id()
	{
		return packetId++;
	}

	public static void register()
	{
		SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(HungerReworked.MODID, "messages")).networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
		INSTANCE = net;

		net.messageBuilder(StomachPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(StomachPacket::new)
				.encoder(StomachPacket::toBytes).consumer(StomachPacket::handle).add();
	}
	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
}
