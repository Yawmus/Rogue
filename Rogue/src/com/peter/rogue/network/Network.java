package com.peter.rogue.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.peter.packets.AddPlayerPacket;
import com.peter.packets.MessagePacket;
import com.peter.packets.PlayerPacket;
import com.peter.packets.RemovePlayerPacket;
import com.peter.rogue.entities.Entity;


public class Network extends Listener {

	public Client client;
	private String ip = "localhost";
	private static int udpPort = 23989, tcpPort = 23989;
		
	public void connect(){
		System.out.println("Connecting to the server...");
		client = new Client();
		client.getKryo().register(PlayerPacket.class);
		client.getKryo().register(MessagePacket.class);
		client.getKryo().register(AddPlayerPacket.class);
		client.getKryo().register(RemovePlayerPacket.class);
		client.addListener(this);
		
		client.start();
		try {
			client.connect(5000, ip, udpPort, tcpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connected! The client program is now waiting for a packet...\n");
	}
	
	public void received(Connection c, Object o){
		if(o instanceof AddPlayerPacket){

			AddPlayerPacket packet = (AddPlayerPacket) o;
			Entity.map.connections.add(packet.ID);
		}
		else if(o instanceof RemovePlayerPacket){
			RemovePlayerPacket packet = (RemovePlayerPacket) o;
			Entity.map.players.remove(packet.id);
			
		}
		else if(o instanceof PlayerPacket){
			PlayerPacket packet = (PlayerPacket) o;
			if(Entity.map.players.get(packet.ID) != null){
				Entity.map.players.get(packet.ID).setX(packet.x);
				Entity.map.players.get(packet.ID).setY(packet.y);
				Entity.map.players.get(packet.ID).setOldX(packet.oldX);
				Entity.map.players.get(packet.ID).setOldY(packet.oldY);
				Entity.map.setMark("", packet.oldX, packet.oldY);
				Entity.map.setMark(Entity.map.players.get(packet.ID).getID().toString(), packet.x, packet.y);
			}
		}
	}
	
	public void setIP(String ip){
		this.ip = ip;
	}
}