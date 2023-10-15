package net.leaderos.plugin.modules.connect;

import lombok.Getter;
import net.leaderos.plugin.Main;
import net.leaderos.shared.module.LeaderOSModule;
import net.leaderos.shared.socket.SocketClient;
import org.bukkit.Bukkit;

import java.net.URISyntaxException;

/**
 * Connect module main class
 *
 * @author poyrazinan
 * @since 1.0
 */
@Getter
public class Connect extends LeaderOSModule {

    private SocketClient socket;

    /**
     * onEnable method of module
     */
    public void onEnable() {
        try {
            socket = new SocketClient(Main.getInstance().getConfigFile().getSettings().getApiKey(),
                    Main.getInstance().getModulesFile().getConnect().getServerToken()) {
                /**
                 * Executes console command
                 * @param command command to execute
                 */
                @Override
                public void executeCommands(String command) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            };
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * onDisable method of module
     */
    public void onDisable() {
        socket.getSocket().close();
    }

    /**
     * Constructor of connect
     */
    public Connect() {}
}