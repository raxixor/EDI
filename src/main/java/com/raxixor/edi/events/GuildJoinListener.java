package com.raxixor.edi.events;

import com.raxixor.edi.Constants;
import com.raxixor.edi.database.GuildDatabase;
import com.raxixor.edi.database.entities.guild.ByeInfo;
import com.raxixor.edi.database.entities.guild.GreetInfo;
import com.raxixor.edi.database.entities.guild.GuildInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.sql.SQLException;

/**
 * Created by raxix on 04/04/2017, 00:11.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GuildJoinListener extends ListenerAdapter {
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		JDA jda = event.getJDA();
		long responseNumber = event.getResponseNumber();
		
		Guild guild = event.getGuild();
		
		GuildInfo info = new GuildInfo(
				guild.getId(),
				guild.getOwner().getUser().getId(),
				new GreetInfo(false, null, Constants.DEFAULT_GREET_MSG),
				new ByeInfo(false, null, Constants.DEFAULT_BYE_MSG),
				null,
				null
		);
		
		try {
			if (GuildDatabase.guildExists(guild.getId())) return;
			GuildDatabase.addGuild(info);
			SimpleLog.getLog("Database").info("Added Guild " + guild.getName() + " (" + guild.getId() + ") to database.");
		} catch (SQLException e) {
			SimpleLog.getLog("Database").warn(e);
		}
	}
	
}
