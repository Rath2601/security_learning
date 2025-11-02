# OAuth2.0 & OpenID Connect (OIDC)

---

## Definition

**OAuth 2.0 (Open Authorization):**  
An **authorization framework** that lets a third-party application obtain **limited access** to a resource on behalf of a user — without sharing their credentials.  
It issues an **Access Token** that represents authorization to access resources.

**OpenID Connect (OIDC):**  
An **authentication layer built on top of OAuth2.0** to securely verify a user’s identity and obtain profile info.  
It adds **ID Token**, **UserInfo endpoint**, and **standard scopes** for authentication.

---

## OAuth2.0 Terminologies

| Term | Description | Types / Notes |
|------|--------------|---------------|
| **Resource Owner** | The end user who owns the data | e.g. Google account user |
| **Client** | The application requesting access | e.g. your Spring Boot app |
| **Authorization Server** | Issues tokens after authenticating user | Google OAuth server |
| **Resource Server** | Hosts protected resources, validates tokens | Google APIs |
| **Access Token** | Short-lived token representing granted access | May be JWT or opaque |
| **Refresh Token** | Long-lived token used to get new access tokens | Avoids re-login |
| **ID Token (OIDC only)** | JWT containing authenticated user info | Issued only in OIDC |
| **Scopes** | Define permissions the client requests | e.g. `openid`, `email`, `profile`, API-specific scopes |
| **Grant Type** | Steps a client uses to get authorization | Explained below |

### Grant Types & Use Cases

| Grant Type | Interaction | Tokens | Use Case |
|-------------|-------------|---------|-----------|
| **Authorization Code** | User → Browser → Server | access + refresh | Web app login |
| **Authorization Code + PKCE** | User → Mobile App | access + refresh | Native mobile SSO |
| **Implicit** | User → Browser | access only | Legacy SPA |
| **Client Credentials** | Server → Server | access only | M2M / Microservices |
| **Resource Owner Password (deprecated)** | User credentials directly sent | access only | Not recommended |

---

## OAuth2.0 Flow (Authorization Code Grant)

1. **Client → Authorization Server**  
   Redirects user to Auth server (e.g. Google) with client ID, redirect URI, scope, and grant type.  
2. **User Consent**  
   User authenticates and approves scopes → Auth server redirects back with **authorization code**.  
3. **Client ↔ Authorization Server (Back Channel)**  
   Client exchanges **authorization code** (via HTTPS) for **access token** (+ refresh token).  
4. **Client → Resource Server**  
   Uses **access token** to fetch allowed resources.  
5. **Tokens**  
   - `access_token`: short-lived  
   - `refresh_token`: long-lived  
   - `id_token`: (OIDC only) user identity in JWT  

---

## Problems with OAuth2.0 for Authentication

1. No standard way to get user info  
2. Scopes and user info endpoints vary across providers  
3. Each implementation differs → interoperability issues  

---

## OAuth2.0 vs OpenID Connect

| Aspect | **OAuth2.0** | **OpenID Connect (OIDC)** |
|--------|---------------|----------------------------|
| Purpose | Authorization (What you can do?) | Authentication (Who you are?) |
| Main Token | Access Token | ID Token (JWT) |
| Used For | API access | User login |
| Scopes | Custom per provider | Standardized (`openid`, `email`, `profile`) |
| User Info | Not standardized | `/userinfo` endpoint |
| Token Type | Any (JWT or opaque) | Always JWT |
| Typical Use | “Allow app to access your calendar” | “Login with Google” |

---

## Real-time Grant Use Cases

| Use Case | Flow | Security | Tokens | Example |
|-----------|------|-----------|---------|----------|
| Web App (Server backend) | Auth Code | High | Access + Refresh | Login via browser |
| Mobile App | Auth Code + PKCE | High | Access + Refresh | Google Sign-In in app |
| SPA (JS app) | Implicit | Low | Access | Legacy browser apps |
| M2M / Microservices | Client Credentials | High | Access | Backend API calls |

| Purpose | Scope(s) | Tokens Returned |
|----------|-----------|----------------|
| Authentication only | `openid email profile` | `id_token` |
| Authorization only | `https://mail.google.com/` etc. | `access_token` |
| Both | `openid email profile <api-scopes>` | `id_token + access_token` |

---

## Spring Boot: OAuth2 / OIDC Classes Involved

| Component | Description |
|------------|--------------|
| **Spring Security OAuth2 Client** | Manages OAuth2 login process |
| **OAuth2LoginAuthenticationFilter** | Handles authorization code exchange |
| **OAuth2AuthorizedClient** | Stores tokens (access + refresh + ID) |
| **OAuth2AuthorizedClientService** | Persists and retrieves tokens |
| **OAuth2AuthenticationToken** | Represents authenticated user session |
| **OAuth2User / OidcUser** | Holds user details (OIDC adds more claims) |
| **SecurityContext** | Maintains authenticated state in session |

### Flow inside Spring Boot:
1. User logs in via Google → redirected back with Auth Code.  
2. `OAuth2LoginAuthenticationFilter` exchanges code for tokens.  
3. `OAuth2AuthorizedClient` holds tokens.  
4. `OAuth2AuthenticationToken` created → added to `SecurityContext`.  
5. App authenticates user internally using that context (not using Google tokens directly).
