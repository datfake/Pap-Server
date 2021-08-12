package com.pap.service;

import com.pap.domain.Courier;
import com.pap.domain.Customer;
import com.pap.domain.ManagerRestaurant;
import com.pap.domain.User;
import io.github.jhipster.config.JHipsterProperties;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to Customer '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(Customer customer, String templateName, String titleKey) {
        if (customer.getEmail() == null) {
            log.debug("Email doesn't exist for customer '{}'", customer.getPhone());
            return;
        }
        Locale locale = Locale.forLanguageTag("vn");
        Context context = new Context(locale);
        context.setVariable(USER, customer);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(customer.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(ManagerRestaurant managerRestaurant, String templateName, String titleKey) {
        if (managerRestaurant.getEmail() == null) {
            log.debug("Email doesn't exist for customer '{}'", managerRestaurant.getPhone());
            return;
        }
        Locale locale = Locale.forLanguageTag("vn");
        Context context = new Context(locale);
        context.setVariable(USER, managerRestaurant);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(managerRestaurant.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(Courier courier, String templateName, String titleKey) {
        if (courier.getEmail() == null) {
            log.debug("Email doesn't exist for customer '{}'", courier.getPhone());
            return;
        }
        Locale locale = Locale.forLanguageTag("vn");
        Context context = new Context(locale);
        context.setVariable(USER, courier);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(courier.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(Customer customer) {
        log.debug("Sending activation email to '{}'", customer.getEmail());
        sendEmailFromTemplate(customer, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(Customer customer) {
        log.debug("Sending creation email to '{}'", customer.getEmail());
        sendEmailFromTemplate(customer, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(Customer customer) {
        log.debug("Sending password reset email to '{}'", customer.getEmail());
        sendEmailFromTemplate(customer, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendActivationEmail(ManagerRestaurant managerRestaurant) {
        log.debug("Sending activation email to '{}'", managerRestaurant.getEmail());
        sendEmailFromTemplate(managerRestaurant, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(ManagerRestaurant managerRestaurant) {
        log.debug("Sending creation email to '{}'", managerRestaurant.getEmail());
        sendEmailFromTemplate(managerRestaurant, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(ManagerRestaurant managerRestaurant) {
        log.debug("Sending password reset email to '{}'", managerRestaurant.getEmail());
        sendEmailFromTemplate(managerRestaurant, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendActivationEmail(Courier courier) {
        log.debug("Sending activation email to '{}'", courier.getEmail());
        sendEmailFromTemplate(courier, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(Courier courier) {
        log.debug("Sending creation email to '{}'", courier.getEmail());
        sendEmailFromTemplate(courier, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(Courier courier) {
        log.debug("Sending password reset email to '{}'", courier.getEmail());
        sendEmailFromTemplate(courier, "mail/passwordResetEmail", "email.reset.title");
    }
}
