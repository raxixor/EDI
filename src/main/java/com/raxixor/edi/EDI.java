package com.raxixor.edi;

import com.raxixor.edi.commands.all.*;
import com.raxixor.edi.commands.channel.*;
import com.raxixor.edi.commands.guildonly.*;
import com.raxixor.edi.commands.owner.*;
import com.raxixor.edi.commands.admin.*;
import com.raxixor.edi.commands.mod.*;
import com.raxixor.edi.events.*;
import com.typesafe.config.*;
import com.jagrosh.jdautilities.commandclient.CommandClient;
import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.*;
import net.dv8tion.jda.core.utils.SimpleLog;

import javax.security.auth.login.LoginException;
import java.awt.*;

/**
 * Created by raxix on 14/03/2017, 11:02.
 * @author raxix <raxixor@gmail.com>
 */
public class EDI {

    /**
     * The main function.
     * @param args Commandline arguments.
     */
    public static void main(String[] args) {
        try {
	        Config conf = ConfigFactory.load("config.json");
	        
        	String botToken = conf.getString("tokens.token");
            Bot bot = new Bot();
            
            EventWaiter waiter = new EventWaiter();

            CommandClient client = new CommandClientBuilder()
                    .useDefaultGame()
                    .setOwnerId(Constants.OWNER_ID)
                    .setPrefix(Constants.PREFIX)
                    .setEmojis(Constants.SUCCESS, Constants.WARNING, Constants.ERROR)
		            .setServerInvite(Constants.SUPPORT_SERVER_INVITE)
		            // .setDiscordBotsKey(conf.getString("tokens.discordbots"))
                    .addCommands(
                            new AboutCommand(Color.green.brighter(),
				                    "a (currently) small utility bot that is actively being developed. [GitHub](https://github.com/raxixor/EDI)",
				                    new String[] {"Actively developed", "Actually has support... unlike some..."}, bot),
                            new PingCommand(bot),
		                    
		                    new UserInfoCommand(bot),
		                    new RoleInfoCommand(bot),
		
		                    new CleanCommand(waiter, bot),
		                    new MagnetCommand(waiter, bot),
                            new KickCommand(bot),
		                    new TextChannelCommand(bot),
                            new VoiceChannelCommand(bot),
		                    
                            new BanCommand(bot),
		                    
		                    new EvalCommand(bot),
		                    new StatsCommand(bot),
		                    new SetStatusCommand(bot),
		                    new SetGameCommand(bot),
		                    new SetNameCommand(bot),
		                    new ShutdownCommand(bot),
		                    new GuildListCommand(bot, waiter),
		                    new EchoCommand(bot),
		                    new BroadcastCommand(bot)
                    ).build();
            new JDABuilder(AccountType.BOT)
                    .setToken(botToken)
                    .setStatus(OnlineStatus.DO_NOT_DISTURB)
                    .setGame(Game.of("Loading..."))
                    .addEventListener(waiter)
                    .addEventListener(client)
		            .addEventListener(new ReadyListener())
		            .addEventListener(new GuildAvailableListener())
		            .addEventListener(new MessageReceivedListener())
		            .addEventListener(new GuildJoinListener())
		            .addEventListener(new GuildLeaveListener())
                    .buildAsync();
        } catch (ConfigException | LoginException | IllegalArgumentException | RateLimitedException e) {
            SimpleLog.getLog("Startup").fatal(e);
        }
    }
}
