package me.sandy.wager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin{
	static HashMap<String, String> requests = new HashMap<String, String>();
	static HashMap<String, String> accepts = new HashMap<String, String>();
	
	static HashMap<String, Boolean> arenas = new HashMap<String, Boolean>();
	static HashMap<String, String> playerArena = new HashMap<String, String>();
	
	static ArrayList<String> arenaList = new ArrayList<String>();
	static HashMap<String, Location> arenaspawn1 = new HashMap<String, Location>();
	static HashMap<String, Location> arenaspawn2 = new HashMap<String, Location>();

	Location lobbyLocation;

	
	Player tplayer;
	
	@Override
	public void onEnable(){
		getLogger().severe("Starting up the plugin");
		getServer().getPluginManager().registerEvents(new GUI(this), this);


	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(label.equalsIgnoreCase("wager")){
			if(sender instanceof Player){
				if(args.length == 0){
					sender.sendMessage(ChatColor.YELLOW + "Commands: ");
					sender.sendMessage(ChatColor.YELLOW + "/wager");
					sender.sendMessage(ChatColor.YELLOW + "/wager request <name>");
					sender.sendMessage(ChatColor.YELLOW + "/wager accept");
					sender.sendMessage(ChatColor.YELLOW + "/wager decline");
					sender.sendMessage(ChatColor.YELLOW + "/wager cancel");
					sender.sendMessage(ChatColor.YELLOW + "/wager arena create <name>");
					sender.sendMessage(ChatColor.YELLOW + "/wager arena delete <name>");
					sender.sendMessage(ChatColor.YELLOW + "/wager arena list");
					sender.sendMessage(ChatColor.YELLOW + "/wager arena spawn <name> (1/2)");
					sender.sendMessage(ChatColor.YELLOW + "/wager lobby location");

					sender.sendMessage(ChatColor.YELLOW + "/debug");

				}
				else if(args.length == 2){
					if(args[0].equalsIgnoreCase("request")){
						if(teleport.arenaChecker(arenaList, arenas)){
							if(lobbyLocation != null){
								if(!accepts.containsKey(sender.getName()) && !requests.containsKey(sender.getName()) && !accepts.containsValue(sender.getName()) && !requests.containsValue(sender.getName())){
									String targetplayer = args[1];
									tplayer = Bukkit.getPlayer(targetplayer);
									if(!getServer().getOnlinePlayers().contains(tplayer)){
										sender.sendMessage("Sorry, the player you spesified is currently not available");
									}
									else if(tplayer.getName().equals(sender.getName())){
										sender.sendMessage(ChatColor.RED + "You cannot wager yourself.");
									}
									else{
										if(!requests.containsKey(sender.getName()) && !requests.containsValue(sender.getName()) && !accepts.containsKey(sender.getName()) && !accepts.containsValue(sender.getName()) ){
											tplayer.sendMessage(ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + " Wishes to make a wager with you. Do you"+ ChatColor.DARK_GREEN + " Accept" + ChatColor.YELLOW +" or " + ChatColor.DARK_RED + "Decline" + ChatColor.YELLOW +"?");
											sender.sendMessage(ChatColor.YELLOW + "You have sent a wager request to " + ChatColor.GOLD +tplayer.getName());
											
											requests.put(targetplayer, sender.getName());
										}
											
											//           person who got asked-|-person asked 
									}
										
								}else{
									sender.sendMessage(ChatColor.RED + "You must deal with your current wager before initiating another.");
								}
		
							}else{
								sender.sendMessage("It appears that there is no trading lobby set! please contact an administator.");
							}
						}else{
							sender.sendMessage(ChatColor.RED + "Sorry, All the arenas are currently in use. check back some other time! If this is not the case please ");
						}
					}
				
					else if(args[0].equalsIgnoreCase("arena")){
						if(args[1].equalsIgnoreCase("list")){
							sender.sendMessage(arenaList.toString());
						}else{
							sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
						}
					}
					else if(args[0].equalsIgnoreCase("lobby")){
						if(args[1].equalsIgnoreCase("location")){
							lobbyLocation = ((Player) sender).getLocation();
							sender.sendMessage("You have successfully set the location for the trading lobby.");
									
						}else{
							sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
					}
				}
				//sender = target player
				else if(args.length == 1){
					if(args[0].equalsIgnoreCase("accept")){
						if(teleport.arenaChecker(arenaList, arenas)){
							
							if(!requests.containsKey(sender.getName())){
								sender.sendMessage(ChatColor.YELLOW + "Sorry, you don't have any pending invites");
							}else{
								sender.sendMessage(ChatColor.GREEN + "You have accepted the wager!");
								Bukkit.getPlayer(requests.get(sender.getName())).sendMessage(ChatColor.GREEN + sender.getName() + " Has accepted your wager request.");
								
								accepts.put(sender.getName(), requests.get(sender.getName()));
								//          Person asked        Person who asked
								
								sender.sendMessage(ChatColor.GREEN + "You will be teleported in 10 seconds.");
								
								if(teleport.arenaChecker(arenaList, arenas)){
									String arena = teleport.arenaPicker(arenaList, arenas);
									playerArena.put(sender.getName(), arena);
									main.arenas.replace(arena, false);
								}
																
								Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable(){
									
									
									public void run() {
										teleport.tp(Bukkit.getPlayer(accepts.get(sender.getName())), (Player)sender, lobbyLocation, lobbyLocation, true);
										
									}
									
								}, 20*1);
								
								
								
								Bukkit.getPlayer(requests.get(sender.getName())).sendMessage(ChatColor.GREEN + "You will be teleported in 10 seconds.");
	
								//GUI.tradeGUI(Bukkit.getPlayer(accepts.get(sender.getName())), (Player)sender);
								
								requests.remove(sender.getName());
							}
						}else{
							sender.sendMessage(ChatColor.RED + "Sorry, you cannot accept this wager, all the arenas are currently in use.");
						}
							
					}
					else if(args[0].equalsIgnoreCase("decline")){
						if(!requests.containsKey(sender.getName())){
							sender.sendMessage(ChatColor.YELLOW + "Sorry, you don't have any pending invites.");
						}else{
							sender.sendMessage(ChatColor.RED + "You have decline the wager!");
							Bukkit.getPlayer(requests.get(sender.getName())).sendMessage(ChatColor.RED + sender.getName() + " Has declined your wager request.");
							requests.remove(sender.getName());
						}
					}

					else if(args[0].equalsIgnoreCase("cancel")){
						if(accepts.containsKey(sender.getName())){
							Object key = GUI.getKeyFromValue(accepts, sender.getName());

							sender.sendMessage(ChatColor.RED + "You have cancelled your wager with " + accepts.get(sender.getName()));
							Bukkit.getPlayer(accepts.get(sender.getName())).sendMessage(ChatColor.RED + sender.getName() + " has cancelled the wager you.");

							accepts.remove(sender.getName());
						}else if(accepts.containsValue(sender.getName())){
							Object key = GUI.getKeyFromValue(accepts, sender.getName());

							
							sender.sendMessage(ChatColor.RED + "You have cancelled your wager with " + key);
							Bukkit.getPlayer((String) key).sendMessage(ChatColor.RED + sender.getName() + " has cancelled the wager with you.");

							accepts.remove(key);

						}
						
						
						else if(requests.containsKey(sender.getName())){
							Object key = GUI.getKeyFromValue(requests, sender.getName());

							sender.sendMessage(ChatColor.RED + "You have cancelled your wager with " + requests.get(sender.getName()));
							Bukkit.getPlayer(requests.get(sender.getName())).sendMessage(ChatColor.RED + sender.getName() + " has cancelled the wager you.");

							requests.remove(sender.getName());

						}else if(requests.containsValue(sender.getName())){
							Object key = GUI.getKeyFromValue(requests, sender.getName());

							sender.sendMessage(ChatColor.RED + "You have cancelled your wager with " + key);
							Bukkit.getPlayer((String) key).sendMessage(ChatColor.RED + sender.getName() + " has cancelled the wager with you.");

							requests.remove(key);
						}else{
							sender.sendMessage(ChatColor.RED + "You do not have any pending wagers.");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
					}
				}
				
				else if(args.length == 3){
					if(args[0].equalsIgnoreCase("arena")){
						if(args[1].equalsIgnoreCase("create")){
							if(!arenas.containsKey(args[2])){
								arenas.put(args[2], true);
								arenaList.add(args[2]);
								sender.sendMessage("Created the arena: " + args[2]);
							}else{
								sender.sendMessage("You cannot have two arenas with the same name.");
							}

						}
						else if(args[1].equalsIgnoreCase("delete")){
							if(arenas.containsKey(args[2])){
								arenas.remove(args[2]);
								arenaList.remove(args[2]);
								sender.sendMessage("Successfully removed: " + args[2]);
							}else{
								sender.sendMessage("Could not remove: " + args[2]);
							}
						}else{
							sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
						}
					}else{
						sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
					}

				}
				else if(args.length == 4){
					if(args[0].equals("arena")){
						if(args[1].equalsIgnoreCase("spawn")){
							if(!arenas.containsKey(args[2])){
								sender.sendMessage("Sorry, please enter a valid arena name");
							}else{
								if(!args[3].equalsIgnoreCase("1") && !args[3].equalsIgnoreCase("2")){
									sender.sendMessage("Sorry, please enter a valid spawn number (1/2)");
								}else if(args[3].equalsIgnoreCase("1")){
									if(arenaspawn1.containsKey(args[2])){
										arenaspawn1.replace(args[2], ((Player) sender).getLocation());
									}else{
										arenaspawn1.put(args[2], ((Player) sender).getLocation());
									}
									sender.sendMessage("You have successfully selected the location of the first location for arena " + args[2]);
								}
								else if(args[3].equalsIgnoreCase("2")){
									if(arenaspawn2.containsKey(args[2])){
										arenaspawn2.replace(args[2], ((Player) sender).getLocation());
									}else{
										arenaspawn2.put(args[2], ((Player) sender).getLocation());
									}
									sender.sendMessage("You have successfully selected the location of the second location for arena " + args[2]);

								}
							}
						}else{
							sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "Invalid command. Please do /wager for additional help.");
					}
					
				}
			
			}
			else{
				System.out.println(ChatColor.RED + "You must be online to do that command.");
			}
		
		}
		else if(label.equalsIgnoreCase("debug")){
			System.out.println(requests);
			System.out.println(accepts);
			System.out.println(GUI.trading);

			
		}
		return false;
	}
	
	@Override 
	public void onDisable(){
		getLogger().info("Shutting down the plugin");
		
	}
}
