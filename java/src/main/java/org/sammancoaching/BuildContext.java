package org.sammancoaching;

import org.sammancoaching.dependencies.Config;
import org.sammancoaching.dependencies.Emailer;
import org.sammancoaching.dependencies.Logger;

// Este é o nosso "Objeto Auxiliar" que encapsula as dependências.
public class BuildContext {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public BuildContext(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public Config getConfig() { return config; }
    public Emailer getEmailer() { return emailer; }
    public Logger getLog() { return log; }
}