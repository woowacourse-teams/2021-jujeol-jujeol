package com.jujeol.commons.config.database;

import static com.jujeol.commons.config.database.DatabaseReplicaType.REPLICA1;
import static com.jujeol.commons.config.database.DatabaseReplicaType.REPLICA2;
import static com.jujeol.commons.config.database.DatabaseReplicaType.SOURCE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private boolean replicaSelector = false;

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            DatabaseReplicaType replicaType = chooseNextReplica();
            log.info("Select replica DB : {}", replicaType);
            return replicaType;
        } else {
            log.info("Select source DB : {}", SOURCE);
            return SOURCE;
        }
    }

    private DatabaseReplicaType chooseNextReplica(){
        replicaSelector = !replicaSelector;
        return replicaSelector ? REPLICA1 : REPLICA2;
    }
}
