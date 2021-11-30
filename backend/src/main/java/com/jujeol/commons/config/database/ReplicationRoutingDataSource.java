package com.jujeol.commons.config.database;

import static com.jujeol.commons.config.database.DatabaseReplicaType.SOURCE;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    private final AtomicInteger replicaIndex = new AtomicInteger(0);
    private List<String> datasourceNameList;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        datasourceNameList =
            targetDataSources.keySet()
                .stream()
                .map(Object::toString)
                .filter(string -> string.contains("replica"))
                .collect(toList());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            String nextReplicaDatasourceName = chooseNextReplica();
            log.info("Select replica DB : {}", nextReplicaDatasourceName);
            return nextReplicaDatasourceName;
        } else {
            log.info("Select source DB : {}", SOURCE);
            return SOURCE.toString();
        }
    }

    private String chooseNextReplica() {
        int nextId = replicaIndex
            .updateAndGet(current -> current == Integer.MAX_VALUE ? 0 : current + 1);
        return datasourceNameList.get(nextId % datasourceNameList.size());
    }
}
