/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j;

import ai.nitro.bot4j.bots.*;
import ai.nitro.bot4j.integration.alexa.receive.webhook.AlexaWebhook;
import ai.nitro.bot4j.integration.deployment.receive.webhook.DeploymentWebhook;
import ai.nitro.bot4j.integration.facebook.receive.webhook.FacebookWebhook;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackActionWebhook;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackEventWebhook;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackOAuthWebhook;
import ai.nitro.bot4j.integration.telegram.receive.webhook.TelegramWebhook;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static spark.Spark.*;

public class Application {

	public static void main(final String[] args) {
		final Injector injector = Guice.createInjector(new Module());

		final AlexaWebhook alexaWebhook = injector.getInstance(AlexaWebhook.class);
		final FacebookWebhook facebookWebhook = injector.getInstance(FacebookWebhook.class);
		final SlackActionWebhook slackActionWebhook = injector.getInstance(SlackActionWebhook.class);
		final SlackEventWebhook slackEventWebhook = injector.getInstance(SlackEventWebhook.class);
		final SlackOAuthWebhook slackOAuthWebhook = injector.getInstance(SlackOAuthWebhook.class);
		final TelegramWebhook telegramWebhook = injector.getInstance(TelegramWebhook.class);

		final DeploymentWebhook deploymentWebhook = injector.getInstance(DeploymentWebhook.class);
		final StatefulBotProviderService botProviderService = injector.getInstance(StatefulBotProviderService.class);

		if (System.getenv("PORT") != null) {
			port(Integer.valueOf(System.getenv("PORT")));
		}

		post("/alexa", (req, res) -> alexaWebhook.post(req.raw(), res.raw()));

		get("/facebook", (req, res) -> facebookWebhook.get(req.raw(), res.raw())); //TODO: Find out what the get method does and if it needs a botId to work
		post("/facebook", (req, res) -> facebookWebhook.post(req.raw(), res.raw()));

		get("/slack/oauth", (req, res) -> slackOAuthWebhook.get(req.raw(), res.raw()));
		post("/slack/action", (req, res) -> slackActionWebhook.post(req.raw(), res.raw()));
		post("/slack/event", (req, res) -> slackEventWebhook.post(req.raw(), res.raw()));

		post("/slack/event", (req, res) -> slackEventWebhook.post(req.raw(), res.raw()));

		post("/telegram", (req, res) -> telegramWebhook.post(req.raw(), res.raw()));

		delete("/deploy", (req, res) -> deploymentWebhook.delete(req.raw(), res.raw()));
		get("/deploy", (req, res) -> deploymentWebhook.get(req.raw(), res.raw()));
		post("/deploy", (req, res) -> deploymentWebhook.post(req.raw(), res.raw()));
		put("/deploy", (req, res) -> deploymentWebhook.put(req.raw(), res.raw()));


		botProviderService.registerBot(InceptionBot.class, "InceptionBot");
		botProviderService.registerBot(InceptionBotNew.class, "InceptionBotNew");
	}
}
