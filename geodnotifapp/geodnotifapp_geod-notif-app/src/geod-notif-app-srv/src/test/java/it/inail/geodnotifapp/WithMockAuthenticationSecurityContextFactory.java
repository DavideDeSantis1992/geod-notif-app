package it.inail.geodnotifapp;

import it.inail.geodnotifapp.security.models.AuthDetails;
import it.inail.geodnotifapp.security.models.InailUserDetails;
import it.inail.geodnotifapp.security.models.JwtAuthenticationToken;
import it.inail.geodnotifapp.security.models.TokenDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.*;

public class WithMockAuthenticationSecurityContextFactory implements WithSecurityContextFactory<WithMockAuthentication> {

    private static final String CLIENT_ID = "TestClientId";

    @Override
    public SecurityContext createSecurityContext(WithMockAuthentication withMockCustomUser) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        String subject = withMockCustomUser.subject();
        String[] roles = withMockCustomUser.authorities() != null ? withMockCustomUser.authorities() : new String[]{};
        String sid = UUID.randomUUID().toString();
        String headOffice = withMockCustomUser.headOffice();
        String role = withMockCustomUser.role();
        String office = withMockCustomUser.office();

        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String grant : roles) {
            authorityList.add(new SimpleGrantedAuthority(grant));
        }

        InailUserDetails inailUserDetails = new InailUserDetails(subject, authorityList);
        AuthDetails authDetails = new AuthDetails(sid, null, headOffice, role, office, CLIENT_ID);

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.HOUR, 2);
        
        TokenDetails tokenDetails = new TokenDetails(subject, sid, expirationDate.getTime(), Arrays.asList(roles), Arrays.asList(roles),null, null);


        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(tokenDetails,
                authDetails,
                inailUserDetails,
                authorityList);

        context.setAuthentication(jwtAuthenticationToken);
        return context;
    }
}
