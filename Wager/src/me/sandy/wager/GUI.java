package me.sandy.wager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI implements Listener {
	
	
	static main plugin;
	public GUI(main main) {
		plugin = main;
	}
	//Bukkit.createInventory(null, 9+9+9+9, ChatColor.DARK_GRAY+ "Trading Unit..")
	
	static HashMap<String,Inventory> trading = new HashMap<String,Inventory>();
	static HashMap<String,Inventory> trading1 = new HashMap<String,Inventory>();
	

	static ArrayList<String> canDrag = new ArrayList<String>();
	
	
	static ItemStack greenwool = new ItemStack(Material.STAINED_CLAY,1,(short) 5);
	static ItemStack redwool = new ItemStack(Material.STAINED_CLAY,1,(short) 14);	
	static ItemStack bluewool = new ItemStack(Material.STAINED_CLAY,1,(short) 9);
	static ItemStack yellowwool = new ItemStack(Material.STAINED_CLAY,1,(short) 4);

	static ItemStack barrier = new ItemStack(Material.BARRIER,1);

	static ItemStack redpane = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 14);	
	static ItemStack blackpane = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 15);	

	
	static ItemStack banner = new ItemStack(Material.BANNER,1,(short) 0);	

	
	static ItemStack paper1 = new ItemStack(Material.PAPER);	
	static ItemStack paper2 = new ItemStack(Material.PAPER);	


	
	public static void tradeGUI(Player p, Player p2){

		Player requester = p;
		Player requestee = p2;
		
		trading.put(requester.getName(), Bukkit.createInventory(null, 9+9+9+9, ChatColor.DARK_GRAY+ "Trading Unit.."));		
		
		//
		ItemMeta greenwoolmeta = greenwool.getItemMeta();
		greenwoolmeta.setDisplayName("Accept");
		greenwool.setItemMeta(greenwoolmeta);
		
		ItemMeta redwoolmeta = redwool.getItemMeta();
		redwoolmeta.setDisplayName("Cancel");
		redwool.setItemMeta(redwoolmeta);                      //Item Meta's.
		
		ItemMeta bluewoolmeta = bluewool.getItemMeta();
		bluewoolmeta.setDisplayName("Remove all");
		bluewool.setItemMeta(bluewoolmeta);
		
		ItemMeta yellowwoolmeta = yellowwool.getItemMeta();
		yellowwoolmeta.setDisplayName("Finish trading");
		yellowwool.setItemMeta(yellowwoolmeta);
		//
		
		ItemMeta paper1meta = paper1.getItemMeta();
		paper1meta.setDisplayName("No information");
		paper1.setItemMeta(paper1meta);
		
		ItemMeta paper2meta = paper2.getItemMeta();
		paper2meta.setDisplayName("No information");
		paper2.setItemMeta(paper2meta);
		

		///-*
		trading.get(p.getName()).setItem(4,redpane);
		trading.get(p.getName()).setItem(9+4,redpane);
		trading.get(p.getName()).setItem(9+9+4,redpane);
		trading.get(p.getName()).setItem(9+9+9+4,redpane);
		
		paper2meta.setDisplayName("HI");
		paper2.setItemMeta(paper2meta);

		
		trading.get(p.getName()).setItem(2,paper1);
		trading.get(p.getName()).setItem(6,paper2);

		//trading.get(p.getName()).setItem(1,redpane);
		//trading.get(p.getName()).setItem(3,redpane);
		//trading.get(p.getName()).setItem(5,redpane);
		//trading.get(p.getName()).setItem(7,redpane);
		

		
		trading.get(p.getName()).setItem(0,greenwool);
		trading.get(p.getName()).setItem(9,redwool);
		trading.get(p.getName()).setItem(9+9,bluewool);
		trading.get(p.getName()).setItem(9+9+9,yellowwool);

		
		trading.get(p.getName()).setItem(8,greenwool);
		trading.get(p.getName()).setItem(9+8,redwool);
		trading.get(p.getName()).setItem(9+9+8,bluewool);
		trading.get(p.getName()).setItem(9+9+9+8,yellowwool);
		
		///-*
		

		
		///-*
		
		p.openInventory(trading.get(p.getName()));
		p2.openInventory(trading.get(p.getName()));
	}
	
	  public static Object getKeyFromValue(Map hm, Object value) {
		    for (Object o : hm.keySet()) {
		      if (hm.get(o).equals(value)) {
		        return o;
		      }
		    }
		    return null;
		  }
	
	@EventHandler
	public static void clickEvent(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		int slot = e.getSlot();

				
		if(trading.containsKey(player.getName()) || trading.containsKey(main.accepts.get(player.getName()))){
			if(e.getClickedInventory().equals(trading.get(player.getName()))){

				if(!(slot == 1 || slot == 2 || slot == 3 ||
						slot == 10 || slot == 11 || slot == 12 ||
						slot == 19 || slot == 20 || slot == 21 ||
						slot == 28 || slot == 29 || slot == 30)){
					e.setCancelled(true);
				}

			}else if(e.getClickedInventory().equals(trading.get(main.accepts.get(player.getName())))){
				if(!(slot == 5 || slot == 6 || slot == 7 ||
						slot == 14 || slot == 15 || slot == 16 ||
						slot == 23 || slot == 24 || slot == 25 ||
						slot == 32 || slot == 33 || slot == 34)){
					e.setCancelled(true);
				}

			}
		}
		
	}
	

	@EventHandler
	public static void dragEvent(InventoryDragEvent e){
		Player player =(Player) e.getWhoClicked();
		if(e.getInventory().equals(trading.get(player.getName()))){
			e.setCancelled(true);
		}else if(e.getInventory().equals(trading.get(main.accepts.get(player.getName())))){
			e.setCancelled(true);
		}

	}
	@EventHandler
	public static void onMove(PlayerMoveEvent e){
		/*if(trading.containsKey(e.getPlayer().getName())){
			e.setCancelled(true);
		}else if(trading.containsKey(main.accepts.get(e.getPlayer().getName()))){
			e.setCancelled(true);
		}*/
	}
	@EventHandler
	public static void closeEvent(InventoryCloseEvent e){
		
	}

}
