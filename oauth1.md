# OAuth 1.0, OAuth 2.0, and mTLS — Comprehensive Overview

---

## 1. OAuth 1.0 Explanation & Terminologies (with OAuth 2.0 Comparison)

| Concept | OAuth 1.0 | OAuth 2.0 (Equivalent) | Description |
|----------|------------|------------------------|--------------|
| **Consumer (Client)** | Application requesting access to a protected resource | **Client Application** | The app that wants access to user data (e.g., a web or mobile app). |
| **Service Provider** | Entity that owns the resource (e.g., API server) | **Authorization Server + Resource Server** | In OAuth2.0, these are separated for clarity. |
| **Request Token** | Temporary credential used to get user authorization | **Authorization Code** | Exchanged later for an access token. |
| **Access Token** | Token used to access protected resources | **Access Token** | Same purpose, but simpler handling in OAuth2.0. |
| **Token Secret** | Secret shared with the access token for signing requests | ❌ *Not used* | OAuth2.0 uses bearer tokens instead of signed requests. |
| **Signature / Signing Key** | Every request must be signed (HMAC-SHA1 or RSA-SHA1) | ❌ *Not required* | OAuth2.0 relies on HTTPS/TLS for protection. |
| **Verifier** | Code confirming user approval of the request token | **Authorization Code / PKCE Code Verifier** | Both confirm user authorization step. |

**In short:**  
OAuth1.0 = signed requests using keys and secrets.  
OAuth2.0 = simpler, token-based requests protected by HTTPS.

---

## 2. Difference Between OAuth 1.0 and OAuth 2.0

| Aspect | **OAuth 1.0** | **OAuth 2.0** |
|--------|----------------|----------------|
| **Security Model** | Application-level signatures (HMAC/RSA) per request | Relies on HTTPS (TLS) for encryption & integrity |
| **Token Exchange Flow** | Request Token → Access Token | Authorization Code → Access Token |
| **Request Signing** | Required for every request | Not required (Bearer tokens used) |
| **User Consent** | Delegated access via signed approval | Delegated access via redirect & code exchange |
| **Ease of Use** | Complex (nonces, timestamps, signatures) | Simpler (HTTPS handles security) |
| **Extensibility** | Rigid | Extensible — supports PKCE, JWT, mTLS, etc. |
| **Protection Level** | Built-in cryptographic verification | HTTPS/TLS channel provides encryption & trust |
| **Example Auth Header** | `Authorization: OAuth ...` (signed) | `Authorization: Bearer <token>` |

**Core difference:**  
OAuth1.0 authenticates and signs **every request itself**,  
while OAuth2.0 trusts **the secure HTTPS channel** to handle confidentiality and integrity.

---

## 3. Difference Between OAuth 1.0 and mTLS

| Aspect | **OAuth 1.0** | **mTLS (Mutual TLS)** |
|--------|----------------|-----------------------|
| **Purpose** | Delegated authorization framework | Transport-level client/server authentication |
| **Security Layer** | Application layer (within HTTP) | Transport layer (under HTTPS) |
| **Authentication Mechanism** | Shared secrets + request signatures | Client and server certificates |
| **Encryption** | None by itself (though can use HTTPS additionally) | Built-in as part of TLS handshake |
| **User Consent** | Often involves user authorization | No user — purely machine-to-machine identity |
| **Usage Together** | Rarely used | Common with OAuth 2.0 for secure API comms |
| **Verification** | Server checks digital signature in each request | Both sides verify each other’s certificates |

**Simplified:**  
OAuth1.0 → “Sign my message, so you know it’s from me.”  
mTLS → “Prove who you are before we even start talking.”

---

## 4. Today, Does OAuth 1.0 Use HTTPS?
Yes.  Modern implementations of OAuth1.0 **run over HTTPS**, combining:
- **TLS encryption** for transport-level protection, and  
- **Cryptographic signing** (HMAC/RSA) for per-request verification.

So it provides **double security**:
> TLS = encrypted channel  
> OAuth signature = verified message inside that channel  

However, this dual layer made OAuth1.0 heavy and complex, leading to OAuth2.0’s simplified HTTPS-only model.

---
## 5. Does All HTTPS Use TLS?
Absolutely.  Today, **all HTTPS = HTTP over TLS.**  Older SSL (Secure Sockets Layer) has been fully replaced by **TLS (Transport Layer Security)**.
> **HTTPS = HTTP + TLS encryption + authentication**

TLS ensures:
- **Encryption** – data unreadable to attackers  
- **Integrity** – data can’t be tampered in transit  
- **Authentication** – verifies the server’s identity  

---

## Summary Snapshot

| Concept | What Secures It | Key Mechanism |
|----------|-----------------|----------------|
| **OAuth 1.0** | Message-level security | HMAC / RSA signatures |
| **OAuth 2.0** | Transport-level security | HTTPS (TLS) |
| **mTLS** | Mutual authentication + encryption | TLS certificates (client + server) |

---
