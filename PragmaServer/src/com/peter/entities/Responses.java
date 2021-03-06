package com.peter.entities;

import com.peter.server.Global;

public class Responses{
	
	private String receiver;
	private int dice;
	
	public Responses(String type) {
		this.receiver = type;
	}

	public String call(Entity caller, boolean hostile){
		if(receiver == "Shopkeep"){
			return Shopkeep(caller, hostile);
		}
		if(receiver == "Citizen")
			return Citizen(caller, hostile);
		
		if(receiver == "Monster"){
			dice = Global.rand(4, 0);
			switch(dice){
			case 0:
				return "*Gurgle*";
			case 1:
				return "*Screech*";
			case 2:
				return "*Vomit*";
			case 3:
				return "*Squirm*";
			}
		}
		
		return "I don't have a response programmed";
	}
	private String Shopkeep(Entity caller, boolean hostile){
		dice = Global.rand(4, 0);
		if(hostile)
			switch(dice){
			case 0:
				return "Leave me alone!";
			case 1:
				return "Help!";
			case 2:
				return "Guards!";
			case 3:
				return "By the nine!";
			}
		if(caller instanceof Monster)
			switch(dice){
			case 0:
				return "Eww, a " + caller.getType() + "!";
			case 1:
				return "Back vile beast!";
			case 2:
				return "How revolting!";
			case 3:
				return "Do you want to come home with me?";
			}
		if(caller instanceof NPC)
			switch(dice){
			case 0:
				return "Hello " + caller.getName().split(" ")[0] + "!";
			case 1:
				return "Greetings " + caller.getName().split(" ")[0] + "!";
			case 2:
				return "How goes it " + caller.getName().split(" ")[0] + "?";
			case 3:
				return "Nice day isn't it?";
			}
		if(caller instanceof Player)
			switch(dice){
			case 0:
				return "I have the best prices in town!";
			case 1:
				return "Greetings traveler!";
			case 2:
				return "Have you taken a look at my fine wares?";
			case 3:
				return "Looks like you need to retire.";
			}
		return "I don't have a response programmed";
	}
	
	private String Citizen(Entity caller, boolean hostile){
		if(hostile)
			switch(Global.rand(5, 0)){
			case 0:
				return "Arrgghh!";
			case 1:
				return "Help!";
			case 2:
				return "Guards!";
			case 3:
				return "Swine!";
			case 4:
				return "By my troth!";
			}
		if(caller instanceof Monster)
			switch(dice){
			case 0:
				return "Eww, a " + caller.getType().toLowerCase() + "!";
			case 1:
				return "Back vile beast!";
			case 2:
				return "How revolting!";
			case 3:
				return "Do you want to come home with me?";
			}
		if(caller instanceof NPC)
			switch(dice){
			case 0:
				return "Hello " + caller.getName().split(" ")[0] + "!";
			case 1:
				return "Greetings " + caller.getName().split(" ")[0] + "!";
			case 2:
				return "How goes it " + caller.getName().split(" ")[0] + "?";
			case 3:
				return "Nice day isn't it?";
			}
		if(caller instanceof Player)
			switch(Global.rand(6, 0)){
			case 0:
				return "What ails you " + caller.getName().split(" ")[0] + "?";
			case 1:
				return "Greetings traveler!";
			case 2:
			case 3:
				return "How goes your journey " + caller.getName().split(" ")[0] + "?";
			case 4:
				return "My, age has treated you well.";
			case 5:
				return "These sure are dark times.";
			}
		return "I don't have a response programmed";
	}
}



