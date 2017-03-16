package com.raxixor.edi.listeners;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.SimpleLog;

/**
 * Created by raxix on 16/03/2017, 13:40.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class ReadyListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent event) {
		SimpleLog.getLog("Ready").info("Ready.");
	}
	
}