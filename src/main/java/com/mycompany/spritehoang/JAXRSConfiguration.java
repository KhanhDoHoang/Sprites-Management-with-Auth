/**
 * Configures JAX-RS for the application.
 * @author Hoang Do
 */
package com.mycompany.spritehoang;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "${'java:comp/DefaultDataSource'}",
    callerQuery = "#{'select password from appuser where userid = ?'}",
    groupsQuery = "select groupname from appuser where userid = ?",
    hashAlgorithm = PasswordHash.class,
    priority = 10
)
@BasicAuthenticationMechanismDefinition
@ApplicationScoped
@Named
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
    
}
