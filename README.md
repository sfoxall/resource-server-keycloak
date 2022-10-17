# Getting Started with Spring Resource Server for Keycloak

The Spring Resource Server will run on the port specified in `application.properties`. 

### Authorization Server URL

The authorization server URL is specified in `application.properties`:

`spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8180/realms/reperio`

### Configuration

The configuration is specified in `com.inventivum.resourceserversas.security.WebSecurity`.