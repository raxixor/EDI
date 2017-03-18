package com.raxixor.edi.events;

import com.raxixor.edi.database.UserDatabase;
import com.raxixor.edi.database.entities.user.UserInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.sql.SQLException;

/**
 * Created by raxix on 18/03/2017, 19:31.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class MessageReceivedListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		JDA jda = event.getJDA();
		
		if (event.isFromType(ChannelType.TEXT)) {
			if (event.getTextChannel().getGuild().getId() == "110373943822540800") return;
		}
		
		try {
			if (UserDatabase.userExists(event.getAuthor().getId())) return;
			UserDatabase.addUser(new UserInfo(event.getAuthor().getId(), false));
			SimpleLog.getLog("Database").info("Added user " + event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " to database.");
		} catch (SQLException e) {
			SimpleLog.getLog("Database").warn(e);
		}
	}
}
