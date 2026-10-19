# Rolling Deployment

## Mechanism

Replace old instances incrementally while maintaining a configured number of available replicas. Kubernetes Deployments implement rolling updates; see the [sample manifest](../deploy/kubernetes/deployment.yaml). Set surge and unavailable limits according to capacity and availability requirements.

```mermaid
flowchart LR
  Old[Old replicas all serving] --> Step1[Start candidate replica]
  Step1 --> Ready[Wait for readiness]
  Ready --> Step2[Remove one old replica]
  Step2 --> Repeat[Repeat within capacity limits]
  Repeat --> Done[All candidate replicas]
```

## Operational checklist

- Ensure old and new versions can coexist at the API, message, and schema boundaries.
- Readiness must mean safe to receive production traffic; liveness should not restart an overloaded but recoverable process.
- Budget enough capacity for `maxSurge`; set `maxUnavailable` to preserve the required serving floor.
- Configure graceful shutdown, endpoint removal, connection draining, and interruption handling.
- Watch per-version health, deployment progress, saturation, and customer-facing SLOs. Pause/rollback on a clear regression.

## Failure modes and trade-offs

This is usually more resource-efficient than blue/green, but rollback takes time and requests may hit either version during transition. A bad new replica can repeatedly fail readiness and stall the rollout. Insufficient surge capacity can make progress impossible; too much unavailable capacity violates the service objective.

## Exercise

For a six-replica service with a three-replica minimum serving requirement, select safe surge/unavailable values. Explain database compatibility, health probes, and rollback behavior under an unavailable dependency.

## References

- [Kubernetes: Deployments and rolling updates](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)
- [Kubernetes: Configure a rolling update](https://kubernetes.io/docs/tasks/run-application/rolling-update-replication-controller/)
