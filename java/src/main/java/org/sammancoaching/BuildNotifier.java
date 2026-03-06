package org.sammancoaching;

// Esta classe tem UMA única responsabilidade: decidir se e o que notificar.
public class BuildNotifier {

    private final BuildContext context;

    public BuildNotifier(BuildContext context) {
        this.context = context;
    }

    public void sendSummary(boolean testsPassed, boolean deploySuccessful) {
        if (!context.getConfig().sendEmailSummary()) {
            context.getLog().info("Email disabled");
            return;
        }

        context.getLog().info("Sending email");

        if (!testsPassed) {
            context.getEmailer().send("Tests failed");
        } else if (!deploySuccessful) {
            context.getEmailer().send("Deployment failed");
        } else {
            context.getEmailer().send("Deployment completed successfully");
        }
    }
}