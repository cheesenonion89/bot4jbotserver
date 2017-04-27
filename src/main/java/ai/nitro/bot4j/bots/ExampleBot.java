/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.bots;

import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.bot.impl.BotImpl;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.button.PostbackSendButton;
import ai.nitro.bot4j.middle.domain.send.payload.ButtonsSendPayload;

public class ExampleBot extends BotImpl {

    private static final String BUTTON = "button";

    protected final static Logger LOG = LogManager.getLogger(ExampleBot.class);

    @Override
    protected void onReceivePostback(final PostbackReceivePayload postback, final Participant sender) throws Exception {

        LOG.info("ON RECEIVE POSTBACK");
        final Participant recipient = sender;

        final String name = postback.getName();
        final String[] payload = postback.getPayload();

        switch (postback.getName()) {
            case BUTTON:
                final String joinedPayload = StringUtils.join(payload, ", ");
                sendText(joinedPayload, recipient);
                break;
            default:
                LOG.warn("Unknown postback {}", name);
        }
    }

    @Override
    protected void onReceiveText(final TextReceivePayload receiveTextPayload, final Participant sender)
            throws Exception {
        final Participant recipient = sender;
        final String text = receiveTextPayload.getText();

        LOG.info("ON RECEIVE TEXT");
        LOG.info("received {}", text);

        switch (text) {
            case BUTTON:
                sendButton(text, recipient);
                break;
            default:
                sendText("received '" + text, recipient);
                sendButton("This is Button", recipient);
                break;
        }
    }

    @Override
    protected void onReceiveAttachment(final UrlAttachmentReceivePayload payload, final Participant sender) {
        final Participant recipient = sender;
        LOG.info("RECEIVED AN ATTACHMENT");
        LOG.info(payload.getAttachmentType());
        LOG.info(payload.getTitle());
        LOG.info(payload.getUrl());
        sendText("Thank you for your attachment", recipient);
    }

    protected void sendButton(final String title, final Participant recipient) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.setRecipient(recipient);

        final ButtonsSendPayload buttonsSendPayload = new ButtonsSendPayload();
        buttonsSendPayload.setTitle("Click me Please");

        {
            final PostbackSendButton button = new PostbackSendButton();
            button.setTitle(title);
            button.setName(BUTTON);
            button.setPayload("test_payload");
            buttonsSendPayload.addButton(button);
        }

        sendMessage.setPayload(buttonsSendPayload);
        messageSender.send(sendMessage, null);
    }

}