package crussell52.poi.actions;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import crussell52.poi.PoiException;
import crussell52.poi.PoiManager;

public abstract class ActionHandler {
	
	protected static Logger _log = Logger.getLogger("Minecraft");
	
	protected PoiManager _poiManager;
	public ActionHandler(PoiManager poiManager) {
		this._poiManager = poiManager;
	}
	
	public abstract void handleAction(CommandSender sender, String action, String[] args);
	
	protected boolean _playerCheck(CommandSender sender)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage("This action can only be performed by an in-game Player.");
			return false;
		}
		
		return true;
	}
	
	protected boolean _selectPOI(String[] args, int expectedIndex, Player player)
	{
		try {
			int	id = Integer.parseInt(args[0]);
			this._poiManager.selectPOI(id, player);
			return true;
		}
		catch (PoiException ex) {
			
			switch (ex.getErrorCode()) {
				case PoiException.NO_POI_AT_ID:
					player.sendMessage("No POI found with the specified id.");
					break;
				case PoiException.POI_OUT_OF_WORLD:
					player.sendMessage("Specified POI is in another world.");
					break;
				default:
					player.sendMessage("A system error occurred while trying to select the POI");
					ActionHandler._log.severe(ex.toString());
					break;
			}
			
			return false;
		}
		catch (IndexOutOfBoundsException ex) {
			player.sendMessage("ID must be specified.");
			return false;
		}
		catch (NumberFormatException ex) {
			player.sendMessage("ID must be a number.");
			return false;
		}
	}
}