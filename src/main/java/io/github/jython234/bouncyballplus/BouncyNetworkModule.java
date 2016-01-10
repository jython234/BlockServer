package io.github.jython234.bouncyballplus;

import org.blockserver.core.Server;
import org.blockserver.core.modules.network.NetworkModule;
import org.blockserver.core.modules.scheduler.SchedulerModule;

/**
 * Created by atzei on 1/10/2016.
 */
public class BouncyNetworkModule extends NetworkModule{
    public BouncyNetworkModule(Server server, SchedulerModule schedulerModule) {
        super(server, schedulerModule);
    }


}
