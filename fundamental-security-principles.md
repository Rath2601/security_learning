# Fundamental Security Principles

> Principles, not products. The **what** is universal; the **how** depends on your organization's infrastructure.

---

## CIA Triad

The three goals every security control ultimately serves.

### Confidentiality
Only authorized parties can read the data.

- **Achieved via:** encryption (TLS in transit, AES at rest), access controls, authentication/authorization (RBAC, MFA).
- **Where:**
  - **Databases** — encryption at rest (Transparent Data Encryption / TDE, AES-256), column-level encryption for sensitive fields
  - **Network traffic** — TLS / SSL
  - **Files** — full-disk encryption (BitLocker, LUKS), file-level encryption (AES)
  - **Secrets management** — dedicated vaults (HashiCorp Vault, AWS Secrets Manager, Azure Key Vault)

### Integrity
Data isn't altered — accidentally or maliciously — without detection.

- **Achieved via:** cryptographic hashes, digital signatures, checksums, HMACs, version control, write-once logs.
- **Where:** software updates (code signing), financial records, audit logs, backups.

### Availability
Systems and data are accessible when legitimately needed.

- **Achieved via:** redundancy, failover clusters, load balancing, backups, DDoS mitigation, capacity planning.
- **Where:** uptime-critical services, disaster recovery.

> **The tension:** these often trade off. Locking everything down (confidentiality) can hurt availability. Good design balances all three based on what the asset actually needs.

---

## Defense in Depth

Layered controls, so one failure doesn't mean a breach. The assumption is that **any single control will eventually fail**, so you stack independent ones:

`Physical (locks, badges)` → `Network (firewalls, segmentation, IDS/IPS)` → `Host (EDR, patching, hardening)` → `Application (input validation, WAF)` → `Data (encryption, DLP)` → `Identity (MFA, conditional access)`

**How it's used:** an attacker who phishes a password still hits MFA; if they clear that, network segmentation limits lateral movement; if they reach a server, least privilege caps what they can touch. No layer is trusted to be sufficient alone.

---

## Principle of Least Privilege (PoLP)

Every user, process, and service gets the **minimum** access needed to do its job — and nothing more.

- **How it's used:** role-based access control (RBAC), just-in-time / temporary elevation, scoped API tokens, narrowly-permissioned service accounts, removing admin rights by default, network policies allowing only required flows.
- **Why:** it shrinks the **blast radius**. A compromised account or a buggy process can only damage what it was allowed to reach.

---

## How They Connect

- **CIA** is *what you're protecting* (the objectives).
- **Defense in Depth** and **PoLP** are *how you protect it* (the strategy).
- PoLP is itself one of the layers within Defense in Depth, and both exist to preserve confidentiality, integrity, and availability.

This is why implementation is organization-specific:

| Organization | Weights most heavily | Why |
|---|---|---|
| Hospital | Availability + Confidentiality | Patient data + life-critical systems |
| Bank | Integrity + Confidentiality | Transactions must be exact |

**Same principles, different emphasis and tooling.**
