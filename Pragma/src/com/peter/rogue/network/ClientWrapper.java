package com.peter.rogue.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.peter.packets.AddNPCPacket;
import com.peter.packets.AddPlayerPacket;
import com.peter.packets.AddTradeItemPacket;
import com.peter.packets.AttackPacket;
import com.peter.packets.ChestPacket;
import com.peter.packets.ExperiencePacket;
import com.peter.packets.IDPacket;
import com.peter.packets.ItemPacket;
import com.peter.packets.MapPacket;
import com.peter.packets.MessagePacket;
import com.peter.packets.NPCPacket;
import com.peter.packets.PlayerPacket;
import com.peter.packets.RemoveItemPacket;
import com.peter.packets.RemoveNPCPacket;
import com.peter.packets.RemovePlayerPacket;
import com.peter.packets.RemoveTradeItemPacket;
import com.peter.packets.RequestFloorPacket;
import com.peter.packets.ThrowPacket;
import com.peter.rogue.Global;

public class ClientWrapper {

	public Client client;
	private Network network;
	private static int udpPort = 23989, tcpPort = 23989;
	
	public ClientWrapper(){
		System.out.println("Connecting to the server...");
		client = new Client();
		client = new Client(79000, 42000);
		network = new Network();
		client.getKryo().register(PlayerPacket.class);
		client.getKryo().register(NPCPacket.class);
		client.getKryo().register(ExperiencePacket.class);
		client.getKryo().register(AttackPacket.class);
		client.getKryo().register(AddNPCPacket.class);
		client.getKryo().register(RemoveNPCPacket.class);
		client.getKryo().register(MessagePacket.class);
		client.getKryo().register(AddPlayerPacket.class);
		client.getKryo().register(RemovePlayerPacket.class);
		client.getKryo().register(MapPacket.class);
		client.getKryo().register(RequestFloorPacket.class);
		client.getKryo().register(ChestPacket.class);
		client.getKryo().register(RemoveItemPacket.class);
		client.getKryo().register(RemoveTradeItemPacket.class);
		client.getKryo().register(AddTradeItemPacket.class);
		client.getKryo().register(IDPacket.class);
		client.getKryo().register(java.util.HashMap.class);
		client.getKryo().register(java.util.ArrayList.class);
		client.getKryo().register(ItemPacket[][].class);
		client.getKryo().register(ItemPacket[].class);
		client.getKryo().register(ItemPacket.class);
		client.getKryo().register(ThrowPacket.class);
		client.getKryo().register(String[][].class);
		client.getKryo().register(String[].class);
		client.getKryo().register(short[][].class);
		client.getKryo().register(short[].class);
		client.getKryo().register(byte[][].class);
		client.getKryo().register(byte[].class);
		
		client.addListener(network);
		client.start();
		try {
			client.connect(10000, Global.IP, udpPort, tcpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.setTimeout(0);
		System.out.println("Connected! The client program is now waiting for a packet...\n");
	}
}
