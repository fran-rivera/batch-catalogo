package com.frg.springbatch.common;

import com.frg.springbatch.login.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * This custom {@code ItemWriter} writes the information of the login to
 * the log.
 *
 * @author Fran Rivera
 */
public class LoggingLoginWriter implements ItemWriter<LoginDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingLoginWriter.class);

    @Override
    public void write(List<? extends LoginDTO> items) throws Exception {
        LOGGER.info("Received the information of {} login", items.size());

        items.forEach(i -> LOGGER.debug("Received the information of a student: {}", i));
    }
}
