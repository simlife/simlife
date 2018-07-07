package io.github.simlife.config.info;

import io.github.simlife.config.SimlifeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

/**
 * An {@link InfoContributor} that tells if mail service is enabled.
 *
 */
public class MailEnabledInfoContributor implements InfoContributor {

    private static final String MAIL_ENABLED = "mailEnabled";

    @Autowired
    private SimlifeProperties simLifeProperties;

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail(MAIL_ENABLED, simLifeProperties.getMail().isEnabled());
    }
}
