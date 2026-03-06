package org.sammancoaching;

import org.sammancoaching.dependencies.Project;

public class Pipeline {

    private final BuildContext context;

    public Pipeline(BuildContext context) {
        this.context = context;
    }

    public void run(Project project) {
        boolean testsPassed = executeTests(project);
        boolean deploySuccessful = testsPassed && executeDeploy(project);

        sendEmailSummary(testsPassed, deploySuccessful);
    }

    private boolean executeTests(Project project) {
        if (!project.hasTests()) {
            context.getLog().info("No tests");
            return true;
        }

        if (project.runTests()) {
            context.getLog().info("Tests passed");
            return true;
        }

        context.getLog().error("Tests failed");
        return false;
    }

    private boolean executeDeploy(Project project) {

        if (project.deploy()) {
            context.getLog().info("Deployment successful");
            return true;
        }

        context.getLog().error("Deployment failed");
        return false;
    }

    private void sendEmailSummary(boolean testsPassed, boolean deploySuccessful) {
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