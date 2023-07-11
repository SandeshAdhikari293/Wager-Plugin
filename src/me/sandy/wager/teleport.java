package me.sandy.wager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class teleport {
	
	static main plugin;
	public teleport(main main) {
		plugin = main;
	}
	
	public static void tp(Player requester,Player requestee,Location loc,Location loc1,Boolean openGUI){
		
		requester.teleport(loc);
		requestee.teleport(loc1);
		
		if(openGUI){
			GUI.tradeGUI(requester, requestee);
		}

		
	}
	
	public static String arenaPicker(ArrayList<String> list,HashMap<String,Boolean> map){
		if(list.isEmpty()){
			
		}else{
			for(int i = 0; i > list.size(); i++){
				if(map.get(list.get(i))== true){
					return list.get(i);
				}
			}
		}

		return null;
	}
	public static boolean arenaChecker(ArrayList<String> list, HashMap<String,Boolean> map){
		if(!list.isEmpty()){
			if(map.containsValue(true)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public static Location location1Demtermine(String s){
		
		
		return main.arenaspawn1.get(s);
	}
	public static Location location2Determine(String s){
		return main.arenaspawn2.get(s);
		
	}
	
	public static boolean arenSpawnChecker(HashMap<String,Location> m1,HashMap<String,Location> m2,ArrayList<String> list,String s){
		if(!list.contains(s) || list.isEmpty()){
			return false;
		}else{
			if(m1.containsKey(s) && m2.containsKey(s)){
				return true;
			}else{
				return false;
			}
		}		
	}

}

