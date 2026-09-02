## Threat Modeling (STRIDE) & OWASP Top 10 — Interview Prep Notes
---
### Threat Modeling (STRIDE)
**What it is :** A Microsoft-originated mnemonic for classifying threats; you walk each element of a system (processes, data stores, data flows, external entities) and ask "which of these six can happen here?"

**Each STRIDE threat is the violation of a specific security property** — that mapping is the part interviewers probe.

| Threat | What it is (one line) | Violates | Prevention (one line) |
|---|---|---|---|
| **S** — Spoofing | Attacker impersonates another user, device, or service to gain trust. | Authentication | Enforce strong auth — MFA, mutual TLS, signed/short-lived tokens, sound session management. |
| **T** — Tampering | Unauthorized modification of data in transit, at rest, or in code/config. | Integrity | Use hashing/digital signatures, TLS, input validation, and least-privilege write access. |
| **R** — Repudiation | A user performs an action then denies it, and you can't prove otherwise. | Non-repudiation | Keep tamper-evident audit logs with secure timestamps and signed transactions. |
| **I** — Information Disclosure | Sensitive data leaks to parties who shouldn't see it. | Confidentiality | Encrypt at rest and in transit, apply access controls, and mask/minimize sensitive data. |
| **D** — Denial of Service | Attacker exhausts resources so legitimate users can't use the system. | Availability | Apply rate limiting, throttling, quotas, autoscaling, and upstream DDoS protection. |
| **E** — Elevation of Privilege | Attacker gains rights beyond what they're authorized to have. | Authorization | Enforce least privilege, server-side authorization checks, and validate all inputs. |

#### How to actually run a threat model (say this if asked "walk me through it") (Re-verify at https://owasp.org/Top10/ ) 
1. **Scope & diagram** — draw a Data Flow Diagram (DFD); mark **trust boundaries** (where data crosses privilege levels — the highest-value places to look).
2. **Enumerate** — apply STRIDE to each element on the diagram.
3. **Mitigate** — pick a control for each credible threat (see table above).
4. **Validate** — confirm mitigations exist, are tested, and are monitored.

> **Bonus points:** mention **DREAD** (Damage, Reproducibility, Exploitability, Affected users, Discoverability) as a lightweight way to *prioritize* the threats STRIDE surfaces, and note that threat modeling should happen at **design time**, not after code ships.

---
### OWASP Top 10 (2025) (**⚠️ This list changes (~every 4 years). Always check the official source before an interview:**)
**What it is :** A consensus list of the ten most critical web application security risks, based on real-world CVE/CWE (Common Vulnerabilities and Exposures/Common Weakness Enumeration)  data and practitioner surveys, increasingly organized around *root causes* rather than symptoms.

| # | Category | What it is (one line) | Prevention (one line) |
|---|---|---|---|
| **A01** | [Broken Access Control](https://owasp.org/Top10/2025/A01_2025-Broken_Access_Control/) *(now includes SSRF)* | Users act outside intended permissions, or the server is tricked into making unintended requests (SSRF). | Deny by default, enforce authorization server-side, and validate/allow-list all outbound requests. |
| **A02** | [Security Misconfiguration](https://owasp.org/Top10/2025/A02_2025-Security_Misconfiguration/) | Insecure defaults, verbose errors, open buckets, or unhardened components expose the app. | Harden and patch systematically, disable unused features, and automate secure config baselines. |
| **A03** | [Software Supply Chain Failures](https://owasp.org/Top10/2025/A03_2025-Software_Supply_Chain_Failures/) | Compromised or vulnerable third-party libraries, tools, or build pipelines get exploited. | Maintain an SBOM, verify signatures/provenance, pin dependencies, and secure the CI/CD pipeline. |
| **A04** | [Cryptographic Failures](https://owasp.org/Top10/2025/A04_2025-Cryptographic_Failures/) | Weak, missing, or misused cryptography exposes sensitive data. | Use strong modern algorithms, encrypt in transit and at rest, and manage keys/secrets properly. |
| **A05** | [Injection](https://owasp.org/Top10/2025/A05_2025-Injection/) | Untrusted input is interpreted as code or commands (SQL, NoSQL, OS, LDAP, XSS). | Use parameterized queries, validate/escape input, and prefer safe APIs/ORMs. |
| **A06** | [Insecure Design](https://owasp.org/Top10/2025/A06_2025-Insecure_Design/) | Security flaws baked into the architecture itself, not just the implementation. | Threat model early, apply secure design patterns, and reuse vetted reference architectures. |
| **A07** | [Authentication Failures](https://owasp.org/Top10/2025/A07_2025-Authentication_Failures/) | Weak login, session, or credential handling lets attackers take over accounts. | Enforce MFA, block weak/breached passwords, and manage sessions, lockouts, and resets securely. |
| **A08** | [Software or Data Integrity Failures](https://owasp.org/Top10/2025/A08_2025-Software_or_Data_Integrity_Failures/) | Code or data is trusted without verifying it wasn't tampered with (e.g., insecure deserialization, unsigned updates). | Verify integrity via digital signatures, use trusted sources, and secure the update/deploy flow. |
| **A09** | [Security Logging and Alerting Failures](https://owasp.org/Top10/2025/A09_2025-Security_Logging_and_Alerting_Failures/) | Missing or ignored logs let attacks go undetected and unaddressed. | Log security events centrally, monitor and alert in real time, and test detection and response. |
| **A10** | [Mishandling of Exceptional Conditions](https://owasp.org/Top10/2025/A10_2025-Mishandling_of_Exceptional_Conditions/) *(new)* | Errors and edge cases are handled poorly — fail-open logic, leaked stack traces, inconsistent state. | Fail securely (deny by default), handle errors uniformly, and never leak internals to users. |

### What changed from 2021 → 2025 (good to mention — shows you track the field)
- **SSRF** (its own category in 2021) is now folded into **A01 Broken Access Control**.
- **Security Misconfiguration** climbed to **#2** (was #5).
- **Software Supply Chain Failures** rose to **#3** — an expansion of the old "Vulnerable and Outdated Components," now covering the whole build/dependency lifecycle.
- **A10 Mishandling of Exceptional Conditions** is the only *brand-new* category.
- Renames: "Identification and Authentication Failures" → **Authentication Failures**; "Logging and Monitoring Failures" → **Security Logging and Alerting Failures**.
- Broader theme: continued shift from **symptoms** to **root causes** (Injection is now the only remaining "symptom" category).

---
### Tying It Together
- **STRIDE is the *lens*; OWASP is the *checklist*.** STRIDE helps you *discover* threats against your specific design; the OWASP Top 10 is the *industry baseline* of what commonly goes wrong.
- They overlap cleanly — e.g. STRIDE *Elevation of Privilege* ↔ OWASP *A01 Broken Access Control*; *Tampering* ↔ *A08 Integrity Failures*; *Information Disclosure* ↔ *A04 Cryptographic Failures*.
- **Shift security left:** the cheapest bug to fix is the one caught in design (threat modeling) or CI (SAST/dependency scanning), not in production.
---
