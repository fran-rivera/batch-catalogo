package com.frg.springbatch.common;

import com.frg.springbatch.model.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * This custom {@code ItemProcessor} simply writes the information of the
 * processed student to the log and returns the processed object.
 *
 * @author Fran Rivera
 */
public class LoggingLoginProcessor implements ItemProcessor<LoginDTO, LoginDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingLoginProcessor.class);

    @Override
    public LoginDTO process(LoginDTO item) throws Exception {
        LOGGER.info("Processing model information: {}", item);
        return item;
    }
}
