package it.inail.geodnotifapp.services.impl;

import it.inail.geodnotifapp.dto.MessageDto;
import it.inail.geodnotifapp.services.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String MSG = "Ciao, sono il template spring-boot";

    @Override
    public MessageDto getMessage() {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(MSG);
        return messageDto;
    }
}
