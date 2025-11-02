# TLS and SSL – Comprehensive Notes

## 1. What is TLS and SSL

**SSL (Secure Sockets Layer):**  
An older cryptographic protocol designed to secure communication between a client and a server. It provided encryption and authentication but is now deprecated due to security vulnerabilities.

**TLS (Transport Layer Security):**  
TLS is the modern and secure successor to SSL. It ensures encrypted, authenticated, and tamper-proof communication between two endpoints over the internet.  
All modern HTTPS communication uses TLS (commonly versions 1.2 or 1.3).

---

## 2. Difference Between SSL and TLS

| Aspect | SSL | TLS |
|--------|-----|-----|
| **Status** | Deprecated | Active and standard |
| **Versions** | SSL 2.0, SSL 3.0 | TLS 1.0 → TLS 1.3 |
| **Security** | Weak, vulnerable to attacks | Strong encryption and algorithms |
| **Handshake** | Slower and less efficient | Fewer round trips, faster and safer |
| **Cipher Suites** | Outdated | Modern and secure (e.g., AES, ECDHE) |
| **Usage** | Historical | Used in all HTTPS communications today |

In practice, when people refer to "SSL certificates," they actually mean **TLS certificates**.

---

## 3. TLS Properties

TLS ensures three major security properties:

### a. Encryption  
- Protects the confidentiality of data transmitted between client and server.  
- Achieved through symmetric encryption algorithms (e.g., AES).  
- The session key used for encryption is generated during the TLS handshake.

### b. Authentication  
- Verifies the identity of the parties involved.  
- The server (and optionally the client) presents a digital certificate issued by a trusted Certificate Authority (CA).  
- Prevents impersonation or man-in-the-middle attacks.

### c. Integrity  
- Ensures that data is not altered during transmission.  
- Implemented using MAC (Message Authentication Code) or AEAD tags.  
- If any modification occurs, the receiver detects it immediately.

---

## 4. Flow of Proper TLS in a Client-Server Model
(Example: Locally deployed Spring Boot app → Temenos server)

### Step 1: TCP Handshake
- Client and server establish a reliable connection using TCP (SYN, SYN-ACK, ACK).

### Step 2: TLS Handshake
1. **ClientHello** – The client (Spring Boot app) sends supported TLS versions, ciphers, and a random number.  
2. **ServerHello** – The Temenos server responds with chosen cipher and its digital certificate (containing public key).  
3. **Certificate Verification** – The client verifies the server certificate using its truststore or system CA store.  
4. **Key Exchange** – Both sides use asymmetric cryptography (e.g., Diffie–Hellman) to derive a shared symmetric session key.  
5. **Finished Messages** – Both confirm handshake completion and start secure communication.

### Step 3: Encrypted Communication
- After the handshake, both sides use the derived **symmetric session key** for all encryption and decryption.  
- Asymmetric cryptography is used only during the handshake, while symmetric encryption ensures faster data transfer.

---

## 5. Difference Between Self-Signed and Public CA Certificates

| Aspect | Self-Signed Certificate | Public CA Certificate |
|--------|-------------------------|-----------------------|
| **Issuer** | Signed by the organization itself | Signed by a trusted Certificate Authority (e.g., DigiCert, Google Trust) |
| **Trust Level** | Not trusted by default | Trusted globally by browsers and systems |
| **Usage** | Internal/test environments | Public production systems |
| **Validation** | Manual certificate installation needed | Automatically verified via trusted root CAs |

If Temenos uses a private/self-signed certificate, it must be manually imported into your application's truststore.

---

## 6. Use of TLS in Mobile Apps

- TLS applies to all network communications, including mobile applications.  
- Mobile apps use HTTPS (which is TLS over HTTP) to securely call backend APIs.  
- Certificate verification occurs via the mobile OS’s built-in trust store.  
- Some apps use **certificate pinning**, storing the expected server public key or fingerprint to prevent interception or fake certificates.

---

## 7. Working of TLS in Addition to TCP

1. **TCP Layer:**  
   Establishes a reliable transport connection using a 3-way handshake (SYN, SYN-ACK, ACK).

2. **TLS Layer (on top of TCP):**  
   Performs the TLS handshake to authenticate, negotiate encryption parameters, and establish secure keys.

3. **Data Transmission:**  
   Once TLS is established, encrypted application data (e.g., HTTPS traffic) is sent securely over the TCP connection.

**Sequence:**
   TCP 3-Way Handshake
           ↓
    TLS Handshake
           ↓
  Encrypted Data Exchange (HTTPS)

---

## 8. What is mTLS and Where It Is Used

**mTLS (Mutual TLS):**  
An enhanced version of TLS where **both client and server authenticate each other** using their own certificates.

### How It Works
- The server presents its certificate to the client (as in normal TLS).  
- The client also presents its certificate to the server.  
- Both sides verify each other's certificates using their respective truststores.

### Use Cases
- Secure API-to-API communication (e.g., Spring Boot ↔ Temenos).  
- Enterprise systems, banking, and financial services.  
- Microservices communication within secure networks.  
- Any environment requiring strict mutual identity verification.

---
