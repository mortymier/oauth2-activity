# oauth2-activity

IT342 - Laboratory Activity #3

A small Java-based example demonstrating an OAuth 2.0 authorization flow (Authorization Code grant). This repository contains Java server-side code and simple HTML client pages used for the lab exercise.

## Architecture

High-level Authorization Code flow:
```
   +--------+                                +--------------------+
  | Browser| ---(1) Auth Request ---------->| Authorization      |
  | (User) |                                | Server (AuthZ)     |
  +--------+                                +--------------------+
       |                                          |
       |<-- (2) Redirect w/ Authorization Code ---|
       |                                          |
  +----------------------+                        |
  | Client App (Backend) |<--(3) Auth Code------- |
  | (confidential)       | --(4) Token Request--> |
  +----------------------+   (client_id/secret)   |
       |                                          |
       |<-- (5) Access Token ---------------------|
       |                                          |
       | ---(6) API Request w/ Bearer Token)----> |
       |                                          v
  +--------------------+                   +--------------------+
  | Resource Server    |<-- (7) Validate/---| Protected Resource |
  | (API)              |    Serve Data      +--------------------+
  +--------------------+
```
Legend:
- (1) Browser initiates login/consent to the Authorization Server.
- (2) Authorization Server issues an authorization code and redirects the browser back to the client callback.
- (3) Client backend receives the code.
- (4) Client backend exchanges the authorization code and client credentials for tokens.
- (5) Authorization Server returns an access token (and optional refresh token).
- (6) Client uses the access token to request protected resources from the Resource Server (API).
- (7) Resource Server validates the token and returns protected data to the client, which displays results to the user.

## Features
- Demonstrates OAuth 2.0 Authorization Code grant flow.
- Java backend with simple HTML front-end pages for demonstration.
- Intended for educational/lab use (IT342).

## Prerequisites
- Java 11+ (or the Java version used in the project)
- Maven or Gradle if the project includes a build file
- An OAuth 2.0 Authorization Server (local or external) or a mock provider for testing
- Configure client credentials (client_id, client_secret) and redirect URI

## Configuration
Update application configuration (e.g., application.properties, environment variables, or a settings file) with:
- oauth.client.id
- oauth.client.secret
- oauth.authz.url
- oauth.token.url
- oauth.resource.api.url
- redirect.uri

Adjust property names and file locations according to the actual project files.
