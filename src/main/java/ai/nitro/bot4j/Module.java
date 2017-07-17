/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j;

import com.google.inject.AbstractModule;

import ai.nitro.bot4j.bot.Bot;
import com.google.inject.multibindings.MapBinder;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
		install(new Bot4jModule());

		final MapBinder<Integer, Bot> botBinder = MapBinder.newMapBinder(binder(),
				Integer.class, Bot.class);
		//botBinder.addBinding(0).to(ExampleBot.class);

	}

}