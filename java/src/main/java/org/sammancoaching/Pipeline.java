package org.sammancoaching;

import org.sammancoaching.dependencies.Project;

public class Pipeline {

    private final BuildContext context;
    private final BuildNotifier notifier;

    public Pipeline(BuildContext context) {
        this.context = context;
        this.notifier = new BuildNotifier(context);
    }

    public void run(Project project) {
        // Fase 1: Testes
        boolean testsPassed = executeTests(project);

        // Fase 2: Deploy
        boolean deploySuccessful = testsPassed && executeDeploy(project);

        // Fase 3: Notificação
        notifier.sendSummary(testsPassed, deploySuccessful);
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
}